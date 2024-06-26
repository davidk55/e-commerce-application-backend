package de.davidkoehlmann.ecommerceapplicationbackend.cartproduct;

import jakarta.persistence.Embeddable;
import java.io.Serial;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartProductId implements Serializable {
  @Serial private static final long serialVersionUID = 1L;

  private Long cartId;
  private Long productId;
}
