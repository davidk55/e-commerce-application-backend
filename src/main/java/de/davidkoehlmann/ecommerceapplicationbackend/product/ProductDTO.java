package de.davidkoehlmann.ecommerceapplicationbackend.product;

import java.math.BigDecimal;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductDTO {
  private Long id;
  private String name;
  private BigDecimal price;
  private String description;
  private String category;
  private String imageUrl;
}
