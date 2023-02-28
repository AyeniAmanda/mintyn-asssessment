package com.mintyn.service;

import com.mintyn.dto.OrderDto;
import com.mintyn.dto.OrderResponseDto;
import com.mintyn.exception.CommonsModuleException;

import java.util.List;

public interface OrderedProductService {
    List<OrderResponseDto> createOrder(List<OrderDto> productsOrdered) throws CommonsModuleException;
    OrderResponseDto getOrder(Long id) throws CommonsModuleException;

    List<OrderResponseDto> getAllOrders();
}
