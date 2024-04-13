package de.davidkoehlmann.ecommerceapplicationbackend.account;

import java.util.List;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class AccountController {
  private final AccountService accountService;

  public AccountController(
      @Qualifier(value = "postgreSQLAccountService") AccountService accountService) {
    this.accountService = accountService;
  }

  @PreAuthorize("permitAll()")
  @PostMapping("/register")
  public ResponseEntity<AccountDTO> registerAccount(@RequestBody AccountDTO accountDTO) {
    return ResponseEntity.ok(accountService.createAccount(accountDTO));
  }

  @GetMapping("/refresh")
  public ResponseEntity<String> generateAccessToken(@CookieValue("jwt") String refreshToken) {
    return ResponseEntity.ok(accountService.generateAccessToken(refreshToken));
  }

  @GetMapping("/logout")
  public ResponseEntity<String> logout() {
    return ResponseEntity.ok(accountService.logout());
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @GetMapping("/accounts")
  public ResponseEntity<List<AccountDTO>> getAccounts() {
    return ResponseEntity.ok(accountService.getAccounts());
  }
}
