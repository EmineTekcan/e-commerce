package com.eminetekcan.productservice.service;

import com.eminetekcan.productservice.dto.ProductDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {

    public ProductDto createProduct(ProductDto productDto);
    public ProductDto updateProduct(ProductDto productDto, String id);
    public void deleteProductById(String id);
    public ProductDto getProductById(String id);
    public List<ProductDto> getAllProducts();
}
