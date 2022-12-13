package de.davidkoehlmann.ecommerceapplicationbackend.account;

import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface AccountService extends UserDetailsService {
    AccountDTO createAccount(AccountDTO accountDTO);
    List<AccountDTO> getAccounts();
    String generateAccessToken(String refreshToken);
}
