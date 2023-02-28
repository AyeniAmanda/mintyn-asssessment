package com.mintyn.service;

import com.mintyn.dto.ProductDto;
import com.mintyn.dto.UpdateProductDto;
import com.mintyn.exception.CommonsModuleException;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {
    ProductDto createProduct(ProductDto productDto) throws CommonsModuleException;

    ProductDto getProduct(Long productId) throws CommonsModuleException;

    List<ProductDto> getAllProducts();

    ProductDto updateProductPrice(Long id, BigDecimal productPrice) throws CommonsModuleException;

    UpdateProductDto updateProduct(Long id, UpdateProductDto updateProductDto) throws CommonsModuleException;

}
