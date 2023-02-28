package com.mintyn.service.serviceImpl;

import com.mintyn.dto.OrderDto;
import com.mintyn.dto.OrderResponseDto;
import com.mintyn.exception.CommonsModuleException;
import com.mintyn.kafka.report.ReportSenderService;
import com.mintyn.model.OrderedProduct;
import com.mintyn.model.Product;
import com.mintyn.repositories.OrderedProductRepository;
import com.mintyn.repositories.ProductRepository;
import com.mintyn.service.OrderedProductService;
import com.mintyn.utils.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderedProductServiceImplementation implements OrderedProductService {

    private final OrderedProductRepository orderedProductRepository;

    private final ProductRepository productRepository;

    private final OrderMapper orderMapper;

    private final ReportSenderService reportSenderService;


    public List<OrderResponseDto> createOrder(List<OrderDto> orderRequests) throws CommonsModuleException {
        List<OrderResponseDto> orderResponses = new ArrayList<>();

        for (OrderDto newOrderRequest : orderRequests) {
            Product product = productRepository.findById(newOrderRequest.getProductId())
                    .orElseThrow(() ->new CommonsModuleException("product.does.not.exists",HttpStatus.NOT_FOUND));

            int orderQuantity = newOrderRequest.getOrderQuantity();
            int availableQuantity = product.getQuantity();

            if (availableQuantity < orderQuantity){
                throw new CommonsModuleException("invalid.quantity", HttpStatus.BAD_REQUEST);
            }

            product.setQuantity(availableQuantity - orderQuantity);

            productRepository.save(product);

            OrderedProduct order = orderMapper.mapToOrderDto(newOrderRequest, product);

            order = orderedProductRepository.save(order);

            OrderResponseDto orderResponse = orderMapper.mapToOrderResponse(order);
            orderResponses.add(orderResponse);

            reportSenderService.sendOrderReport(orderResponse);

        }

        return orderResponses;
    }


    @Override
    public OrderResponseDto getOrder(Long id) throws CommonsModuleException {
        OrderedProduct order = orderedProductRepository.findById(id)
                .orElseThrow(() -> new CommonsModuleException("order.not.found", HttpStatus.NOT_FOUND));

        return orderMapper.mapToOrderResponse(order);
    }


    @Override
    public List<OrderResponseDto> getAllOrders() {
        List<OrderedProduct> orders = orderedProductRepository.findAll();
        List<OrderResponseDto> orderResponses = new ArrayList<>(orders.size());

        for (OrderedProduct order : orders) {
            orderResponses.add(orderMapper.mapToOrderResponse(order));
        }

        return orderResponses;
    }


}
