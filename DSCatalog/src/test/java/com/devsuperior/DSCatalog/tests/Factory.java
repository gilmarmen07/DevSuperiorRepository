package com.devsuperior.DSCatalog.tests;

import com.devsuperior.DSCatalog.dto.ProductDTO;
import com.devsuperior.DSCatalog.entities.Category;
import com.devsuperior.DSCatalog.entities.Product;
import com.devsuperior.DSCatalog.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

public class Factory {

    @Autowired
    private ProductMapper productMapper;

    public static Product createProduct() {
        Set<Category> categories = new HashSet<>();
        categories.add(new Category(2L, "Electronics"));
        return new Product(1L, "Phone", "Good Phone", 800.0, "https://img.com/img.png", Instant.parse("2020-07-13T20:50:07.12345Z"), categories);
    }

    public static ProductDTO createProductDTO() {
        return new ProductDTO(createProduct());
    }
}
