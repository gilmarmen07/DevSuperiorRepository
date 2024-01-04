package com.devsuperior.dscomerce.services;

import com.devsuperior.dscomerce.dto.ProductDTO;
import com.devsuperior.dscomerce.entities.Product;
import com.devsuperior.dscomerce.repositories.ProductRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Transactional(readOnly = true)
    public ProductDTO findById(Long id){
        ProductDTO productDTO = new ProductDTO();
        Optional<Product> optionalProduct = productRepository.findById(id);
        optionalProduct.ifPresent(product -> BeanUtils.copyProperties(product, productDTO));
        return productDTO;
    }
}
