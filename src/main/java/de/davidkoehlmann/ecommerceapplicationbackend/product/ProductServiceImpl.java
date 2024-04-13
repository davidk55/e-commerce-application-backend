package de.davidkoehlmann.ecommerceapplicationbackend.product;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier("postgreSQLProductService")
public class ProductServiceImpl implements ProductService {
  private final ProductRepository productRepository;

  public ProductServiceImpl(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  @Override
  public ProductDTO getProductById(Long id) {
    Optional<Product> productOptional = productRepository.findById(id);

    if (productOptional.isEmpty()) throw new RuntimeException("Product not found.");

    Product productEntity = productOptional.get();
    ProductDTO product = new ProductDTO();
    BeanUtils.copyProperties(productEntity, product);

    return product;
  }

  @Override
  public List<ProductDTO> getAllProducts() {
    List<Product> productEntities = productRepository.findAll();
    List<ProductDTO> products = new ArrayList<>();
    productEntities.forEach(
        product -> {
          ProductDTO productDTO = new ProductDTO();
          BeanUtils.copyProperties(product, productDTO);
          products.add(productDTO);
        });
    return products;
  }

  @Override
  public ProductDTO createProduct(ProductDTO product) {
    Product productEntity = new Product();
    BeanUtils.copyProperties(product, productEntity);
    productRepository.save(productEntity);
    return product;
  }

  @Override
  public ProductDTO updateProduct(Long id, ProductDTO product) {
    Optional<Product> productEntityOptional = productRepository.findById(id);

    if (productEntityOptional.isEmpty()) throw new RuntimeException("Product not found.");

    Product productEntity = productEntityOptional.get();
    BeanUtils.copyProperties(product, productEntity);
    productRepository.save(productEntity);
    return product;
  }

  @Override
  public Boolean deleteProduct(Long id) {
    productRepository.deleteById(id);
    return true;
  }
}
