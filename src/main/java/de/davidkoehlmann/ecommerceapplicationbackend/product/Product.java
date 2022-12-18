package de.davidkoehlmann.ecommerceapplicationbackend.product;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Entity
@Table(name = "Product")
@Getter
@Setter
@ToString
public class Product {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String name;
    private BigDecimal price;
    private String description;
    private String category;
    private String imageUrl;
}
