package com.mintyn.controller;

import com.mintyn.dto.ProductDto;
import com.mintyn.dto.UpdateProductDto;
import com.mintyn.exception.CommonsModuleException;
import com.mintyn.response.ResponseDto;
import com.mintyn.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping("/add-product")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDto<ProductDto> addProduct(@RequestBody ProductDto inventoryProductDto) throws CommonsModuleException {
        return ResponseDto.wrapSuccessResult(productService.createProduct(inventoryProductDto), "request.successful");
    }

    @PutMapping("/update-product/{id}")
    public ResponseDto<UpdateProductDto> updateProduct(@PathVariable("id") Long id, @RequestBody UpdateProductDto productRequest) throws CommonsModuleException {
        return ResponseDto.wrapSuccessResult(productService.updateProduct(id, productRequest), "request.successful");
    }

    @GetMapping("/products")
    public ResponseDto<?> getAllProducts() {
        return ResponseDto.wrapSuccessResult(productService.getAllProducts(), "request.successful");
    }

    @GetMapping("/products/{id}")
    public ResponseDto<?> getProduct(@PathVariable("id") Long id) throws CommonsModuleException {
        return ResponseDto.wrapSuccessResult(productService.getProduct(id), "request.successful");
    }
}
