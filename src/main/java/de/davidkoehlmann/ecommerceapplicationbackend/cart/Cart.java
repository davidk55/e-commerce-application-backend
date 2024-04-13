package de.davidkoehlmann.ecommerceapplicationbackend.cart;

import de.davidkoehlmann.ecommerceapplicationbackend.cartproduct.CartProduct;
import jakarta.persistence.*;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "Cart")
public class Cart {
  @Id @GeneratedValue private Long id;

  @OneToMany(mappedBy = "cart")
  private List<CartProduct> cartProducts;

  public void addProduct(CartProduct cartProduct) {
    cartProducts.add(cartProduct);
  }
}

