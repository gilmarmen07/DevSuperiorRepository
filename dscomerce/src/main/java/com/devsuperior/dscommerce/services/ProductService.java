package com.devsuperior.dscommerce.services;

import com.devsuperior.dscommerce.dto.ProductDTO;
import com.devsuperior.dscommerce.entities.Product;
import com.devsuperior.dscommerce.mapper.ProductMapper;
import com.devsuperior.dscommerce.repositories.ProductRepository;
import com.devsuperior.dscommerce.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    @Transactional(readOnly = true)
    public ProductDTO findById(Long id) {
        return productMapper.productToProductDTO(productRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Register not found")));
    }

//    @Transactional(readOnly = true)
//    public Page<ProductDTO> findAll(Pageable pageable) {
//        return productRepository.findAll(pageable).map(product -> productMapper.productToProductDTO(product));
//    }

    @Transactional(readOnly = true)
    public Page<ProductDTO> findAll(String name, Pageable pageable) {
        return productRepository.searchByName(name, pageable).map(product -> productMapper.productToProductDTO(product));
    }


    @Transactional
    public ProductDTO insert(ProductDTO productDTO) {
        return productMapper.productToProductDTO(productRepository.save(productMapper.productDTOToProduct(productDTO)));
    }

    @Transactional
    public ProductDTO update(Long id, ProductDTO productDTO) {
        try {
            Product product = productRepository.getReferenceById(id);
            productMapper.copyProductDTOToProduct(productDTO, product);
            return productMapper.productToProductDTO(productRepository.save(product));
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Register not found");
        }
    }

    @Transactional
    public void deleteById(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Register not found");
        }
        productRepository.deleteById(id);
    }
}
