package com.devsuperior.dscommerce.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private Double price;
    private String imgUrl;

    @NotEmpty(message = "Must have at least one category")
    private List<CategoryDTO> categories = new ArrayList<>();
}
