package de.davidkoehlmann.ecommerceapplicationbackend.cart;

import de.davidkoehlmann.ecommerceapplicationbackend.cartproduct.CartProductDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class CartDTO {
    private List<CartProductDTO> products;
}
