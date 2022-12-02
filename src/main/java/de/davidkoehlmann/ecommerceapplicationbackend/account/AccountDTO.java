package de.davidkoehlmann.ecommerceapplicationbackend.account;

import de.davidkoehlmann.ecommerceapplicationbackend.cart.CartDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDTO {
    private String username;
    private String password;
    private CartDTO cart;
}
