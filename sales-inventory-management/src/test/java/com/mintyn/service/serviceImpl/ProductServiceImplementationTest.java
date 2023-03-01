package com.mintyn.service.serviceImpl;

import com.mintyn.dto.ProductDto;
import com.mintyn.dto.UpdateProductDto;
import com.mintyn.exception.CommonsModuleException;
import com.mintyn.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplementationTest {
    @Mock
    private ProductRepository mockProductRepository;
    private ProductServiceImplementation productServiceImplementationUnderTest;

    @BeforeEach
    void setUp() {
        productServiceImplementationUnderTest = new ProductServiceImplementation(mockProductRepository);
    }

    @Test
    void testGetAllProducts_ProductRepositoryReturnsNoItems() {
        when(mockProductRepository.findAll()).thenReturn(Collections.emptyList());

        final List<ProductDto> result = productServiceImplementationUnderTest.getAllProducts();

        assertThat(result).isEqualTo(Collections.emptyList());
    }


    @Test
    void testGetProduct_ProductRepositoryReturnsAbsent() {
        when(mockProductRepository.findById(0L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> productServiceImplementationUnderTest.getProduct(0L))
                .isInstanceOf(CommonsModuleException.class);
    }

    @Test
    void testUpdateProductPrice_ProductRepositoryFindByIdReturnsAbsent() {
        when(mockProductRepository.findById(0L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> productServiceImplementationUnderTest.updateProductPrice(0L,
                new BigDecimal("0.00"))).isInstanceOf(CommonsModuleException.class);
    }


    @Test
    void testUpdateProduct_ProductRepositoryFindByIdReturnsAbsent() {
        final UpdateProductDto updateProductDto = new UpdateProductDto();
        updateProductDto.setId(0L);
        updateProductDto.setProductName("productName");
        updateProductDto.setProductDescription("productDescription");
        updateProductDto.setProductPrice(new BigDecimal("0.00"));
        updateProductDto.setProductQuantity(0);

        when(mockProductRepository.findById(0L)).thenReturn(Optional.empty());

        assertThatThrownBy(
                () -> productServiceImplementationUnderTest.updateProduct(0L, updateProductDto))
                .isInstanceOf(CommonsModuleException.class);
    }
}
