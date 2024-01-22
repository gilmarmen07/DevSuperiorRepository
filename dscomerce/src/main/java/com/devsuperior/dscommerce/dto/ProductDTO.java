package com.devsuperior.dscommerce.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private Long id;

    @Size(min = 3, max = 80, message = "The name must be 3 to 80 characters long")
    @NotBlank(message = "Required field")
    private String name;

    @Size(min = 10, message = "The description must be at least 10 characters long")
    @NotBlank(message = "Required field")
    private String description;

    @Positive(message = "The price must be positive")
    @NotNull(message = "Required field")
    private Double price;
    private String imgUrl;

    @NotEmpty(message = "Must have at least one category")
    private List<CategoryDTO> categories = new ArrayList<>();
}
