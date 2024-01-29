package com.devsuperior.DSCatalog.dto;

import com.devsuperior.DSCatalog.entities.Category;
import com.devsuperior.DSCatalog.entities.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.validation.constraints.*;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    private Long id;

    @NotBlank(message = "Required field")
    private String name;

    @NotBlank(message = "Required field")
    private String description;

    @Positive(message = "The price must be positive")
    @NotNull(message = "Required field")
    private Double price;

    private String imgUrl;
    private Instant date;

    @NotEmpty(message = "Must have at least one category")
    private Set<CategoryDTO> categories = new HashSet<>();

    public ProductDTO(Product entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.description = entity.getDescription();
        this.price = entity.getPrice();
        this.imgUrl = entity.getImgUrl();
        this.date = entity.getDate();
        this.categories.addAll(entity.getCategories().stream().map(CategoryDTO::new).toList());
    }
}
