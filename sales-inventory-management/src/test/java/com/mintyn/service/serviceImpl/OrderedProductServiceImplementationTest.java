package com.mintyn.service.serviceImpl;

import com.mintyn.dto.OrderDto;
import com.mintyn.dto.OrderResponseDto;
import com.mintyn.exception.CommonsModuleException;
import com.mintyn.kafka.report.ReportSenderService;
import com.mintyn.model.OrderedProduct;
import com.mintyn.model.Product;
import com.mintyn.repositories.OrderedProductRepository;
import com.mintyn.repositories.ProductRepository;
import com.mintyn.utils.OrderMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static java.time.Month.MARCH;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderedProductServiceImplementationTest {

    LocalDate createdAt;
    LocalDate updatedAt;
    @Mock
    private OrderedProductRepository mockOrderedProductRepository;
    @Mock
    private ProductRepository mockProductRepository;
    @Mock
    private OrderMapper mockOrderMapper;
    @Mock
    private ReportSenderService mockReportSenderService;
    private OrderedProductServiceImplementation orderedProductServiceImplementationUnderTest;

    @BeforeEach
    void setUp() {
        orderedProductServiceImplementationUnderTest = new OrderedProductServiceImplementation(
                mockOrderedProductRepository, mockProductRepository, mockOrderMapper, mockReportSenderService);

        createdAt = LocalDate.of(2023, MARCH, 1);
        updatedAt = LocalDate.of(2023, MARCH, 3);
    }

    @Test
    void testCreateOrder() throws Exception {
        final List<OrderDto> orderRequests = List.of(
                new OrderDto(1L, "Amanda", "+2347085672396", "Pitzel", 3));

        final Optional<Product> product = Optional.of(
                new Product(1L, "Pitzel", "description", new BigDecimal("20.00"), 10,
                        createdAt, updatedAt));
        when(mockProductRepository.findById(1L)).thenReturn(product);

        final Product product1 = new Product(0L, "productName", "description", new BigDecimal("0.00"), 0,
                createdAt, updatedAt);
        when(mockProductRepository.save(any(Product.class))).thenReturn(product1);

        final OrderedProduct orderedProduct = new OrderedProduct(0L, "customerName", "customerPhoneNumber",
                "productName", 0, new BigDecimal("0.00"), new BigDecimal("0.00"),
                createdAt, updatedAt,
                new Product(0L, "productName", "description", new BigDecimal("0.00"), 0,
                        createdAt, updatedAt));
        when(mockOrderMapper.mapToOrderDto(any(OrderDto.class), any(Product.class))).thenReturn(orderedProduct);

        final OrderedProduct orderedProduct1 = new OrderedProduct(0L, "customerName", "customerPhoneNumber",
                "productName", 0, new BigDecimal("0.00"), new BigDecimal("0.00"),
                createdAt, updatedAt,
                new Product(0L, "productName", "description", new BigDecimal("0.00"), 0,
                        createdAt, updatedAt));
        when(mockOrderedProductRepository.save(any(OrderedProduct.class))).thenReturn(orderedProduct1);

        final OrderResponseDto orderResponseDto = new OrderResponseDto();
        orderResponseDto.setId(0L);
        orderResponseDto.setCreatedDate(createdAt);
        orderResponseDto.setCustomerName("customerName");
        orderResponseDto.setCustomerPhoneNumber("customerPhoneNumber");
        orderResponseDto.setProductName("productName");
        orderResponseDto.setOrderQuantity(0);
        orderResponseDto.setProductPrice(new BigDecimal("0.00"));
        orderResponseDto.setTotalProductPrice(new BigDecimal("0.00"));
        when(mockOrderMapper.mapToOrderResponse(any(OrderedProduct.class))).thenReturn(orderResponseDto);

        final List<OrderResponseDto> result = orderedProductServiceImplementationUnderTest.createOrder(orderRequests);

        verify(mockProductRepository, times(1)).save(any(Product.class));
        verify(mockReportSenderService, times(1)).sendOrderReport(any(OrderResponseDto.class));
    }

    @Test
    void testCreateOrder_ProductRepositoryFindByIdReturnsAbsent() {
        final List<OrderDto> orderRequests = List.of(
                new OrderDto(0L, "customerName", "customerPhoneNumber", "productName", 0));
        when(mockProductRepository.findById(0L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> orderedProductServiceImplementationUnderTest.createOrder(orderRequests))
                .isInstanceOf(CommonsModuleException.class);
    }

    @Test
    void testGetOrder() throws Exception {

        final Optional<OrderedProduct> orderedProduct = Optional.of(
                new OrderedProduct(0L, "customerName", "customerPhoneNumber", "productName", 0, new BigDecimal("0.00"),
                        new BigDecimal("0.00"), createdAt, updatedAt,
                        new Product(0L, "productName", "description", new BigDecimal("0.00"), 0,
                                createdAt, updatedAt)));
        when(mockOrderedProductRepository.findById(0L)).thenReturn(orderedProduct);

        final OrderResponseDto orderResponseDto = new OrderResponseDto();
        orderResponseDto.setId(0L);
        orderResponseDto.setCreatedDate(createdAt);
        orderResponseDto.setCustomerName("customerName");
        orderResponseDto.setCustomerPhoneNumber("customerPhoneNumber");
        orderResponseDto.setProductName("productName");
        orderResponseDto.setOrderQuantity(0);
        orderResponseDto.setProductPrice(new BigDecimal("0.00"));
        orderResponseDto.setTotalProductPrice(new BigDecimal("0.00"));
        when(mockOrderMapper.mapToOrderResponse(any(OrderedProduct.class))).thenReturn(orderResponseDto);

        final OrderResponseDto result = orderedProductServiceImplementationUnderTest.getOrder(0L);

    }

    @Test
    void testGetOrder_OrderedProductRepositoryReturnsAbsent() {
        when(mockOrderedProductRepository.findById(0L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> orderedProductServiceImplementationUnderTest.getOrder(0L))
                .isInstanceOf(CommonsModuleException.class);
    }
}
