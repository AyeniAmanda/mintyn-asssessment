package com.mintyn.controller;

import com.mintyn.dto.OrderDto;
import com.mintyn.exception.CommonsModuleException;
import com.mintyn.response.ResponseDto;
import com.mintyn.service.OrderedProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "product order Controller")
@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderedProductService orderedProductService;

    @ApiOperation(value = "REST API to create order")
    @PostMapping("/create-order")
    public ResponseDto<?> createOrder(@RequestBody List<OrderDto> orderDto) throws CommonsModuleException {
        return ResponseDto.wrapSuccessResult(orderedProductService.createOrder(orderDto), "request.successful");
    }

    @ApiOperation(value = "REST API to get all order")
    @GetMapping("/orders")
    public ResponseDto<?> getAllOrders() {
        return ResponseDto.wrapSuccessResult(orderedProductService.getAllOrders(), "request.successful");
    }

    @ApiOperation(value = "REST API to get an order")
    @GetMapping("/orders/{id}")
    public ResponseDto<?> getOrder(@PathVariable("id") Long id) throws CommonsModuleException {
        return ResponseDto.wrapSuccessResult(orderedProductService.getOrder(id), "request.successful");
    }
}
