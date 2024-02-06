package com.devsuperior.DSCatalog.repositories;

import com.devsuperior.DSCatalog.tests.Factory;
import com.devsuperior.DSCatalog.entities.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
public class ProductRepositoryTests {
    Long existingId;
    Long nonExistingId;
    Integer countTotalProducts;

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        existingId = 1L;
        nonExistingId = 26L;
        countTotalProducts = 25;
    }

    @Test
    public void findByIdReturnNonEmptyOptionalProductsWhenIdExists() {
        Optional<Product> optional = productRepository.findById(existingId);
        Assertions.assertTrue(optional.isPresent());
    }

    @Test
    public void findByIdReturnEmptyOptionalProductsWhenIdDoesNotExists() {
        Optional<Product> optional = productRepository.findById(nonExistingId);
        Assertions.assertTrue(optional.isEmpty());
    }

    @Test
    public void saveShouldPersistWithAutoIncrementWhenIdIsNull() {
        Product product = Factory.createProduct();
        product.setId(null);
        product = productRepository.save(product);

        Assertions.assertNotNull(product.getId());
        Assertions.assertEquals(countTotalProducts + 1, product.getId());
    }

    @Test
    public void deleteShouldDeleteObjectWhenIdExists() {

        productRepository.deleteById(existingId);

        Optional<Product> optional = productRepository.findById(existingId);
        Assertions.assertFalse(optional.isPresent());
    }
}
