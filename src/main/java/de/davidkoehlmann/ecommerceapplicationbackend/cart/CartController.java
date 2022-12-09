package de.davidkoehlmann.ecommerceapplicationbackend.cart;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@PreAuthorize("isAuthenticated()")
@RequestMapping("/api/v1")
public class CartController {
    private final CartService cartService;

    public CartController(@Qualifier("postgreSQLCartService") CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/cart")
    public ResponseEntity<CartDTO> getCart() {
        return ResponseEntity.ok(cartService.getCart());
    }

    @PostMapping("/cart/add/{productId}/{amount}")
    public ResponseEntity<Boolean> addProductToCart(@PathVariable Long productId, @PathVariable Integer amount) {
        return ResponseEntity.ok(cartService.addProductToCart(productId, amount));
    }

    @DeleteMapping("/cart/remove/{productId}")
    public ResponseEntity<Boolean> removeProductFromCart(@PathVariable Long productId) {
        return ResponseEntity.ok(cartService.deleteProductInCart(productId));
    }

    @PutMapping("/cart/update/{productId}/{amount}")
    public ResponseEntity<Boolean> updateProductInCart(@PathVariable Long productId, @PathVariable Integer amount) {
        return ResponseEntity.ok(cartService.changeProductAmountInCart(productId, amount));
    }
}
