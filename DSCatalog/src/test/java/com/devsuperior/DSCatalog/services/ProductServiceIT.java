package com.devsuperior.DSCatalog.services;

import com.devsuperior.DSCatalog.dto.ProductDTO;
import com.devsuperior.DSCatalog.repositories.ProductRepository;
import com.devsuperior.DSCatalog.services.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class ProductServiceIT {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    private Long existingId;
    private Long nonExistingId;
    private Long countTotalProduct;

    @BeforeEach
    void setUp() {
        existingId = 1L;
        nonExistingId = 200L;
        countTotalProduct = 25L;
    }

    @Test
    public void deleteShouldDeleteResourceWhenIdExists() {
        productService.delete(existingId);
        Assertions.assertNotEquals(countTotalProduct, productRepository.count());
    }

    @Test
    public void deleteShouldResourceNotFundExceptionWhenIdDoesNotExists() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            productService.delete(nonExistingId);
        });
    }

    @Test
    public void findAllPageableShouldReturnPageWhenPage0Size10(){
        PageRequest pageRequest = PageRequest.of(0,10);

        Page<ProductDTO> result = productService.findAllPageable(pageRequest);

        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals(0, result.getNumber());
        Assertions.assertEquals(10, result.getSize());
        Assertions.assertEquals(countTotalProduct, result.getTotalElements());
    }

    @Test
    public void findAllPageableShouldReturnEmptyPageWhenPageDoesNotExists(){
        PageRequest pageRequest = PageRequest.of(50,10);

        Page<ProductDTO> result = productService.findAllPageable(pageRequest);

        Assertions.assertTrue(result.isEmpty());
        Assertions.assertEquals(50, result.getNumber());
        Assertions.assertEquals(10, result.getSize());
    }

    @Test
    public void findAllPageableShouldReturnSortedPageWhenSortByName() {
        PageRequest pageRequest = PageRequest.of(0,10, Sort.by("name"));

        Page<ProductDTO> result = productService.findAllPageable(pageRequest);

        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals(0, result.getNumber());
        Assertions.assertEquals(10, result.getSize());
        Assertions.assertEquals("Macbook Pro", result.getContent().get(0).getName());
        Assertions.assertEquals("PC Gamer", result.getContent().get(1).getName());
        Assertions.assertEquals("PC Gamer Alfa", result.getContent().get(2).getName());
    }
}
