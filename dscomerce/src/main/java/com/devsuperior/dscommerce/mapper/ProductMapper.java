package com.devsuperior.dscommerce.mapper;

import com.devsuperior.dscommerce.dto.ProductDTO;
import com.devsuperior.dscommerce.entities.Product;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class ProductMapper {

    @Autowired
    private ModelMapper modelMapper;

    public ProductDTO productToProductDTO(Product product) {
        return modelMapper.map(product, ProductDTO.class);
    }

    public Product productDTOToProduct(ProductDTO productDTO) {
        return modelMapper.map(productDTO, Product.class);
    }

    public void copyProductDTOToProduct(ProductDTO productDTO, Product product) {
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setImgUrl(productDTO.getImgUrl());
        product.setPrice(productDTO.getPrice());
    }
}
