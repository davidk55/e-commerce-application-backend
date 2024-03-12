package de.davidkoehlmann.ecommerceapplicationbackend.account;

import de.davidkoehlmann.ecommerceapplicationbackend.cart.Cart;
import de.davidkoehlmann.ecommerceapplicationbackend.cart.CartDTO;
import de.davidkoehlmann.ecommerceapplicationbackend.cart.CartRepository;
import de.davidkoehlmann.ecommerceapplicationbackend.cartproduct.CartProductDTO;
import de.davidkoehlmann.ecommerceapplicationbackend.security.PasswordConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {

    private AccountService underTest;
    @Mock private AccountRepository accountRepository;
    @Mock private CartRepository cartRepository;
    @Mock private PasswordConfig passwordConfig;
    @Mock private BCryptPasswordEncoder bCryptPasswordEncoder;

    @BeforeEach
    void setUp() {
        underTest = new AccountServiceImpl(accountRepository, cartRepository, passwordConfig, null, null, null);
    }

    @Test
    void creatingAnAccount() {
        // given
        String username = "joe.shmoe@gmail.com";
        String password = "123";
        BCryptPasswordEncoder myEncoder = new BCryptPasswordEncoder(10);
        String encodedPassword = myEncoder.encode(password);
        AccountDTO given = new AccountDTO(username, password, null);

        Account compareToAccount = new Account();
        compareToAccount.setRole(AccountRole.USER);
        compareToAccount.setEnabled(true);
        compareToAccount.setAccountNonExpired(true);
        compareToAccount.setAccountNonLocked(true);
        compareToAccount.setCredentialsNonLocked(true);
        compareToAccount.setUsername(username);
        compareToAccount.setPassword(encodedPassword);

        given(accountRepository.findAccountByUsername(username)).willReturn(Optional.empty());
        given(passwordConfig.passwordEncoder()).willReturn(bCryptPasswordEncoder);
        given(bCryptPasswordEncoder.encode(password)).willReturn(encodedPassword);

        // when
        underTest.createAccount(given);

        // then
        ArgumentCaptor<Cart> cartArgCaptor = ArgumentCaptor.forClass(Cart.class);
        ArgumentCaptor<Account> accountArgCaptor = ArgumentCaptor.forClass(Account.class);
        verify(cartRepository).save(cartArgCaptor.capture());
        verify(accountRepository).save(accountArgCaptor.capture());

        compareToAccount.setCart(cartArgCaptor.getValue());
        Account account = accountArgCaptor.getValue();
        assertThat(account).usingRecursiveComparison().isEqualTo(compareToAccount);
    }

    @Test
    void creatingAnAccountWithEmptyUsername() {
        // given
        String username = "";
        String password = "123";
        AccountDTO given = new AccountDTO(username, password, null);
        String compareToString = "Enter a valid username";

        // when
        IllegalArgumentException illegalArgumentException = Assertions.assertThrows(IllegalArgumentException.class, () -> underTest.createAccount(given));

        // then
        Assertions.assertEquals(compareToString, illegalArgumentException.getMessage());
    }

}