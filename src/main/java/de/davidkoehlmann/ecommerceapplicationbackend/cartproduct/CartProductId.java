package de.davidkoehlmann.ecommerceapplicationbackend.cartproduct;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartProductId implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Long cartId;
    private Long productId;
}
