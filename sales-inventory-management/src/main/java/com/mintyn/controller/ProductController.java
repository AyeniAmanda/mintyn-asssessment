package com.mintyn.controller;

import com.mintyn.dto.ProductDto;
import com.mintyn.dto.UpdateProductDto;
import com.mintyn.exception.CommonsModuleException;
import com.mintyn.response.ResponseDto;
import com.mintyn.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Api(value = "inventory product Controller")
@RestController
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @ApiOperation(value = "REST API to add a product")
    @PostMapping("/add-product")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDto<ProductDto> addProduct(@RequestBody ProductDto inventoryProductDto) throws CommonsModuleException {
        return ResponseDto.wrapSuccessResult(productService.createProduct(inventoryProductDto), "request.successful");
    }

    @ApiOperation(value = "REST API to update product")
    @PutMapping("/update-product/{id}")
    public ResponseDto<UpdateProductDto> updateProduct(@PathVariable("id") Long id, @RequestBody UpdateProductDto productRequest) throws CommonsModuleException {
        return ResponseDto.wrapSuccessResult(productService.updateProduct(id, productRequest), "request.successful");
    }

    @ApiOperation(value = "REST API to get all product")
    @GetMapping("/products")
    public ResponseDto<?> getAllProducts() {
        return ResponseDto.wrapSuccessResult(productService.getAllProducts(), "request.successful");
    }

    @ApiOperation(value = "REST API to get a product")
    @GetMapping("/products/{id}")
    public ResponseDto<?> getProduct(@PathVariable("id") Long id) throws CommonsModuleException {
        return ResponseDto.wrapSuccessResult(productService.getProduct(id), "request.successful");
    }
}
