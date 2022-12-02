package de.davidkoehlmann.ecommerceapplicationbackend.product;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class ProductDTO {
    private String name;
    private BigDecimal price;
    private String description;
    private String category;
    private String imageUrl;
}
