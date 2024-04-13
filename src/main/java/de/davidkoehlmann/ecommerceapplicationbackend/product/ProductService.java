package de.davidkoehlmann.ecommerceapplicationbackend.product;

import java.util.List;

public interface ProductService {
  ProductDTO getProductById(Long id);

  List<ProductDTO> getAllProducts();

  ProductDTO createProduct(ProductDTO product);

  ProductDTO updateProduct(Long id, ProductDTO product);

  Boolean deleteProduct(Long id);
}
