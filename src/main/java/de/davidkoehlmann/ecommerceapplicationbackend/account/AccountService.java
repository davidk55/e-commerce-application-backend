package de.davidkoehlmann.ecommerceapplicationbackend.account;

import java.util.List;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AccountService extends UserDetailsService {
  AccountDTO createAccount(AccountDTO accountDTO);

  List<AccountDTO> getAccounts();

  String generateAccessToken(String refreshToken);

  String logout();
}
