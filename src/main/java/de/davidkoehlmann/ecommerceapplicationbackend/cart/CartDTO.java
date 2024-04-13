package de.davidkoehlmann.ecommerceapplicationbackend.cart;

import de.davidkoehlmann.ecommerceapplicationbackend.cartproduct.CartProductDTO;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CartDTO {
  private List<CartProductDTO> products;
}
