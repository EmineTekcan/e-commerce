package com.eminetekcan.productservice.service.impl;

import com.eminetekcan.productservice.dto.ProductDto;
import com.eminetekcan.productservice.model.Product;
import com.eminetekcan.productservice.repository.ProductRepository;
import com.eminetekcan.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public ProductDto createProduct(ProductDto productDto) {
        Product product=dtoToProduct(productDto);
        Product savedProduct=productRepository.save(product);
        log.info("Product is saved with id: {}",savedProduct.getId());
        return productToDto(savedProduct);
    }

    @Override
    public ProductDto updateProduct(ProductDto productDto, String id) {
        Product product=productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: "+id));
        product.setName(product.getName());
        product.setDescription(product.getDescription());
        product.setPrice(productDto.getPrice());
        Product updatedProduct=productRepository.save(product);
        return productToDto(updatedProduct);
    }

    @Override
    public void deleteProductById(String id) {
        Product product=productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: "+id));
        productRepository.delete(product);
    }

    @Override
    public ProductDto getProductById(String id) {
        Product product=productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: "+id));
        return productToDto(product);
    }

    @Override
    public List<ProductDto> getAllProducts() {
        List<Product> products=productRepository.findAll();
        List<ProductDto> productDtos=products.stream().map(this::productToDto).toList();
        return productDtos;
    }

    private Product dtoToProduct(ProductDto productDto){
        return Product.builder()
                .name(productDto.getName())
                .description(productDto.getDescription())
                .price(productDto.getPrice())
                .build();
    }

    private ProductDto productToDto(Product product){
        return ProductDto.builder()
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }
}
