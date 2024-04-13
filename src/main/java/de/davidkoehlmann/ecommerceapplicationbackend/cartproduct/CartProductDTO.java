package de.davidkoehlmann.ecommerceapplicationbackend.cartproduct;

import de.davidkoehlmann.ecommerceapplicationbackend.product.ProductDTO;
import lombok.Data;

@Data
public class CartProductDTO {
  private ProductDTO product;
  private Integer amount;
}
