package com.mintyn.utils;

import com.mintyn.dto.OrderDto;
import com.mintyn.dto.OrderResponseDto;
import com.mintyn.model.OrderedProduct;
import com.mintyn.model.Product;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
@Component
public class OrderMapper {

    public OrderedProduct mapToOrderDto(OrderDto orderDto, Product product){
        OrderedProduct order = new OrderedProduct();
        order.setCustomerName(orderDto.getCustomerName().toLowerCase());
        order.setCustomerPhoneNumber(orderDto.getCustomerPhoneNumber());
        order.setProductName(orderDto.getProductName().toLowerCase());
        order.setOrderQuantity(orderDto.getOrderQuantity());
        order.setProductPrice(product.getCostPrice());
        order.setTotalProductPrice(product.getCostPrice().multiply(BigDecimal.valueOf(order.getOrderQuantity())));
        order.setProduct(product);

        return order;
    }

    public OrderResponseDto mapToOrderResponse(OrderedProduct order){
        OrderResponseDto response = new OrderResponseDto();
        response.setId(order.getId());
        response.setCustomerName(order.getCustomerName());
        response.setCustomerPhoneNumber(order.getCustomerPhoneNumber());
        response.setProductName(order.getProductName());
        response.setCreatedDate(order.getCreatedAt());
        response.setOrderQuantity(order.getOrderQuantity());
        response.setProductPrice(order.getProductPrice());
        response.setTotalProductPrice(order.getTotalProductPrice());

        return response;
    }
}
