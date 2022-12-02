package de.davidkoehlmann.ecommerceapplicationbackend.cartproduct;

import de.davidkoehlmann.ecommerceapplicationbackend.cart.Cart;
import de.davidkoehlmann.ecommerceapplicationbackend.product.Product;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class CartProduct {
    @EmbeddedId
    private CartProductId id = new CartProductId();

    @ManyToOne
    @MapsId("cartId")
    private Cart cart;

    @ManyToOne
    @MapsId("productId")
    private Product product;

    private Integer amount;
}
