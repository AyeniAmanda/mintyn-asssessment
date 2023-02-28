package com.mintyn.service.serviceImpl;

import com.mintyn.dto.ProductDto;
import com.mintyn.dto.UpdateProductDto;
import com.mintyn.exception.CommonsModuleException;
import com.mintyn.model.Product;
import com.mintyn.repositories.ProductRepository;
import com.mintyn.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImplementation implements ProductService {

    private final ProductRepository productRepository;

    private void validateProductRequest(ProductDto productDto) throws CommonsModuleException {
        String productId = productDto.getProductName().toLowerCase();
        BigDecimal productPrice = productDto.getProductPrice();

        if (productRepository.existsById(Long.valueOf(productId))) {
            throw new CommonsModuleException("product.exists", HttpStatus.BAD_REQUEST);
        }

        if (productPrice.compareTo(BigDecimal.ZERO) <= 0) {
            throw new CommonsModuleException("invalid.price", HttpStatus.FORBIDDEN);
        }
    }

    @Override
    public ProductDto createProduct(ProductDto productDto) throws CommonsModuleException {
        validateProductRequest(productDto);
        Product product = new Product();
        BeanUtils.copyProperties(productDto, product);
        productRepository.save(product);
        return productDto;
    }


    @Override
    public List<ProductDto> getAllProducts() {
        List<Product> productList = productRepository.findAll();
        List<ProductDto> productDtoList = new ArrayList<>();

        for (Product product : productList) {
            ProductDto productDto = new ProductDto();
            BeanUtils.copyProperties(product, productDto);
            productDtoList.add(productDto);
        }

        return productDtoList;
    }


    @Override
    public ProductDto getProduct(Long productId) throws CommonsModuleException {
        Product productOptional = productRepository.findById(productId)
                .orElseThrow(() -> new CommonsModuleException("product.does.not.exists",HttpStatus.NOT_FOUND));
            ProductDto productDto = new ProductDto();
            BeanUtils.copyProperties(productOptional, productDto);
            return productDto;
    }


    @Override
    public ProductDto updateProductPrice(Long id, BigDecimal productPrice) throws CommonsModuleException {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new CommonsModuleException("product.does.not.exists",HttpStatus.NOT_FOUND));
        product.setCostPrice(productPrice);
        Product updatedProduct = productRepository.save(product);
        ProductDto productDto = new ProductDto();
        BeanUtils.copyProperties(updatedProduct, productDto);
        return productDto;
    }


    @Override
    public UpdateProductDto updateProduct(Long id, UpdateProductDto updateProductDto) throws CommonsModuleException {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new CommonsModuleException("product.does.not.exists", HttpStatus.NOT_FOUND));
        BeanUtils.copyProperties(updateProductDto, product);
          productRepository.save(product);
        return new UpdateProductDto();
    }

}
