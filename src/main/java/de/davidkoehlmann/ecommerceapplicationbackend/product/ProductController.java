package de.davidkoehlmann.ecommerceapplicationbackend.product;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1")
public class ProductController {

    private final ProductService productService;


    public ProductController(@Qualifier(value = "postgreSQLProductService") ProductService productService) {
        this.productService = productService;
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/product/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/products")
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/product/create")
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO product) {
        return ResponseEntity.ok(productService.createProduct(product));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/product/update/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@RequestBody ProductDTO product, @PathVariable Long id) {
        return ResponseEntity.ok(productService.updateProduct(id, product));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/product/delete/{id}")
    public ResponseEntity<Boolean> deleteProduct(@PathVariable Long id) {
        return ResponseEntity.ok(productService.deleteProduct(id));
    }
}
