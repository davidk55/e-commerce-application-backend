package de.davidkoehlmann.ecommerceapplicationbackend.cart;

import de.davidkoehlmann.ecommerceapplicationbackend.account.Account;
import de.davidkoehlmann.ecommerceapplicationbackend.account.AccountRepository;
import de.davidkoehlmann.ecommerceapplicationbackend.cartproduct.CartProduct;
import de.davidkoehlmann.ecommerceapplicationbackend.cartproduct.CartProductRepository;
import de.davidkoehlmann.ecommerceapplicationbackend.product.Product;
import de.davidkoehlmann.ecommerceapplicationbackend.product.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Qualifier(value = "postgreSQLCartService")
public class CartServiceImpl implements CartService{
    private final AccountRepository accountRepository;
    private final ProductRepository productRepository;
    private final CartProductRepository cartProductRepository;
    private final CartRepository cartRepository;

    @Override
    public Boolean addProductToCart(Long productId, Integer amount) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<Account> accountOpt = accountRepository.findAccountByUsername(authentication.getName());
        Optional<Product> productOpt = productRepository.findById(productId);

        if(accountOpt.isEmpty()) throw new EntityNotFoundException("Account not found");
        if(productOpt.isEmpty()) throw new EntityNotFoundException("Product not found");

        Account account = accountOpt.get();
        Cart cart = account.getCart();
        CartProduct cartProduct = new CartProduct();
        cartProduct.setProduct(productOpt.get());
        cartProduct.setAmount(amount);
        cartProduct.setCart(cart);
        cart.addProduct(cartProduct);

        cartProductRepository.save(cartProduct);
        cartRepository.save(cart);
        return true;
    }

    @Override
    public Boolean deleteProductInCart(Long productId) {
        CartProduct cartProductToDelete = getCartProductByProductId(productId);

        cartProductRepository.delete(cartProductToDelete);
        return true;
    }

    @Override
    public Boolean changeProductAmountInCart(Long productId, Integer amount) {
        CartProduct cartProductToChange = getCartProductByProductId(productId);
        cartProductToChange.setAmount(amount);

        cartProductRepository.save(cartProductToChange);
        return true;
    }

    private CartProduct getCartProductByProductId(Long productId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<Account> accountOpt = accountRepository.findAccountByUsername(authentication.getName());

        if(accountOpt.isEmpty()) throw new EntityNotFoundException("Account not found");

        Account account = accountOpt.get();
        Cart cart = account.getCart();
        Optional<CartProduct> foundCartProduct = cart.getCartProducts()
                .stream()
                .filter(cartProduct -> Objects.equals(cartProduct.getProduct().getId(), productId))
                .findFirst();

        if (foundCartProduct.isEmpty()) throw new EntityNotFoundException("Product in Cart not found");

        return foundCartProduct.get();
    }
}
