package de.davidkoehlmann.ecommerceapplicationbackend.cart;

public interface CartService {
    Boolean addProductToCart(Long productId, Integer amount);

    Boolean deleteProductInCart(Long productId);

    Boolean changeProductAmountInCart(Long productId, Integer amount);

    CartDTO getCart();
}
