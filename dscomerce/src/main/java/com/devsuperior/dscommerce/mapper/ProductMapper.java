package com.devsuperior.dscommerce.mapper;

import com.devsuperior.dscommerce.dto.CategoryDTO;
import com.devsuperior.dscommerce.dto.ProductDTO;
import com.devsuperior.dscommerce.dto.ProductMinDTO;
import com.devsuperior.dscommerce.entities.Product;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class ProductMapper {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    public ProductDTO productToProductDTO(Product product) {
        return modelMapper.map(product, ProductDTO.class);
    }

    public ProductMinDTO productToProductMinDTO(Product product) {
        return modelMapper.map(product, ProductMinDTO.class);
    }

    public Product productDTOToProduct(ProductDTO productDTO) {
        return modelMapper.map(productDTO, Product.class);
    }

    public void copyProductDTOToProduct(ProductDTO productDTO, Product product) {
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setImgUrl(productDTO.getImgUrl());
        product.setPrice(productDTO.getPrice());
        product.getCategories().clear();
        for (CategoryDTO dto : productDTO.getCategories()) {
            product.getCategories().add(categoryMapper.categoryDTOToCategory(dto));
        }
    }
}
