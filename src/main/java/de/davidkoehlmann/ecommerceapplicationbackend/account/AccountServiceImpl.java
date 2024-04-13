package de.davidkoehlmann.ecommerceapplicationbackend.account;

import de.davidkoehlmann.ecommerceapplicationbackend.cart.Cart;
import de.davidkoehlmann.ecommerceapplicationbackend.cart.CartDTO;
import de.davidkoehlmann.ecommerceapplicationbackend.cart.CartRepository;
import de.davidkoehlmann.ecommerceapplicationbackend.cart.CartServiceImpl;
import de.davidkoehlmann.ecommerceapplicationbackend.jwt.JwtHelper;
import de.davidkoehlmann.ecommerceapplicationbackend.security.PasswordConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Qualifier(value = "postgreSQLAccountService")
public class AccountServiceImpl implements AccountService {
  private final AccountRepository accountRepository;
  private final CartRepository cartRepository;
  private final PasswordConfig encoder;
  private final SecretKey secretKey;
  private final JwtHelper jwtHelper;
  private final HttpServletResponse httpServletResponse;

  @Override
  public AccountDTO createAccount(AccountDTO newAccountDTO) {
    Optional<Account> accountEntityOptional =
        accountRepository.findAccountByUsername(newAccountDTO.getUsername());

    // TODO: add validation
    if (accountEntityOptional.isPresent())
      throw new IllegalArgumentException(
          "E-mail " + newAccountDTO.getUsername() + " already taken");

    Account newAccount = new Account();
    String encryptedPassword = encoder.passwordEncoder().encode(newAccountDTO.getPassword());
    newAccountDTO.setPassword(encryptedPassword);

    BeanUtils.copyProperties(newAccountDTO, newAccount);
    newAccount.setRole(AccountRole.USER);
    newAccount.setEnabled(true);
    newAccount.setAccountNonExpired(true);
    newAccount.setAccountNonLocked(true);
    newAccount.setCredentialsNonLocked(true);

    Cart newCart = new Cart();
    newCart.setCartProducts(new ArrayList<>());
    cartRepository.save(newCart);
    newAccount.setCart(newCart);

    accountRepository.save(newAccount);
    return newAccountDTO;
  }

  @Override
  public List<AccountDTO> getAccounts() {
    List<Account> accountEntities = accountRepository.findAll();

    List<AccountDTO> accountDTOs = new ArrayList<>();

    accountEntities.forEach(
        curAccount -> {
          // Account
          AccountDTO accountDTO = new AccountDTO();
          BeanUtils.copyProperties(curAccount, accountDTO);

          // Cart
          Cart curCart = curAccount.getCart();
          CartDTO cartDTO = CartServiceImpl.convertCartToCartDTO(curCart);
          accountDTO.setCart(cartDTO);
          accountDTOs.add(accountDTO);
        });
    return accountDTOs;
  }

  @Override
  public String generateAccessToken(String refreshToken) {
    try {
      Jws<Claims> claimsJws =
          Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(refreshToken);

      Claims body = claimsJws.getBody();

      Optional<Account> accountByUsername =
          accountRepository.findAccountByUsername(body.getSubject());

      if (accountByUsername.isEmpty())
        throw new UsernameNotFoundException("User with given name not found");

      return jwtHelper.generateAccessTokenWithAccount(accountByUsername.get());
    } catch (JwtException e) {
      throw new IllegalArgumentException(String.format("The token %s is invalid", refreshToken));
    }
  }

  @Override
  public String logout() {
    Cookie emptyToken = new Cookie("jwt", "");
    emptyToken.setHttpOnly(true);
    emptyToken.setPath("/");
    emptyToken.setMaxAge(1);
    emptyToken.setSecure(true);
    httpServletResponse.addCookie(emptyToken);
    return "Sucessful logout";
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<Account> accountOptional = accountRepository.findAccountByUsername(username);

    if (accountOptional.isEmpty())
      throw new UsernameNotFoundException("User " + username + " not found");

    return accountOptional.get();
  }
}
