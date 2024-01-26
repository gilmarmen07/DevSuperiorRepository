package com.devsuperior.DSCatalog.mapper;

import com.devsuperior.DSCatalog.dto.CategoryDTO;
import com.devsuperior.DSCatalog.dto.ProductDTO;
import com.devsuperior.DSCatalog.entities.Product;
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
