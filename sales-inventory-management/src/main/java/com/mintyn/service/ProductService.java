package com.mintyn.service;

import com.mintyn.dto.ProductDto;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {
    ProductDto createProduct(ProductDto productDto);

//    ProductDto updateProduct(Long id, UpdateProductRequest productRequest);
    List<ProductDto> getAllProducts();
    ProductDto getProduct(Long productId);

    ProductDto updateProductPrice(Long id, BigDecimal productPrice);
}
