package com.mintyn.service.serviceImpl;

import com.mintyn.dto.ProductOrderReportDto;
import com.mintyn.dto.ProductReportResponse;
import com.mintyn.exception.CommonsModuleException;
import com.mintyn.model.ProductOrderReport;
import com.mintyn.repository.ProductOrderReportRepository;
import com.mintyn.utils.FileGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static java.time.Month.MARCH;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductOrderReportServiceImplTest {

    LocalDate earliestDate;
    LocalDate latestDate;
    @Mock
    private ProductOrderReportRepository mockProductOrderReportRepository;
    @Mock
    private FileGenerator mockFileGenerator;
    private ProductOrderReportServiceImpl productOrderReportServiceImplUnderTest;

    @BeforeEach
    void setUp() {
        productOrderReportServiceImplUnderTest = new ProductOrderReportServiceImpl(mockProductOrderReportRepository,
                mockFileGenerator);

        earliestDate = LocalDate.of(2023, MARCH, 1);
        latestDate = LocalDate.of(2023, MARCH, 3);

    }

    @Test
    void testSaveProductOrder() {
        final ProductOrderReportDto productOrderReportDto = new ProductOrderReportDto();
        productOrderReportDto.setOrderCreatedDate(earliestDate);
        productOrderReportDto.setCustomerName("Amanda");
        productOrderReportDto.setCustomerPhoneNumber("+2347037068857");
        productOrderReportDto.setProductName("Cake");
        productOrderReportDto.setOrderQuantity(3);
        productOrderReportDto.setProductPrice(new BigDecimal("25.00"));
        productOrderReportDto.setTotalProductPrice(new BigDecimal("75.00"));

        final ProductOrderReport productOrderReport = new ProductOrderReport(1L,
                earliestDate, "Amanda", "+2347037068857",
                "Cake", 3, new BigDecimal("25.00"), new BigDecimal("75.00"));

        when(mockProductOrderReportRepository.save(any(ProductOrderReport.class))).thenReturn(productOrderReport);

        productOrderReportServiceImplUnderTest.saveProductOrder(productOrderReportDto);

        verify(mockProductOrderReportRepository, times(1)).save(any(ProductOrderReport.class));
    }

    @Test
    void testExportProductOrderReportAsExcelFile() throws Exception {
        final ProductReportResponse expectedResult = new ProductReportResponse("file exported", HttpStatus.OK);

        final Optional<List<ProductOrderReport>> productOrderReports = Optional.of(
                List.of(new ProductOrderReport(1L, earliestDate,
                        "Amanda", "+2347037068857", "Cake", 2, new BigDecimal("50.00"),
                        new BigDecimal("100.00")), new ProductOrderReport(2L, latestDate,
                        "Tolani", "+2348036789382", "Mango", 1, new BigDecimal("75.00"),
                        new BigDecimal("75.00"))));
        when(mockProductOrderReportRepository.findAllByCreatedAtBetween(earliestDate,
                latestDate)).thenReturn(productOrderReports);

        final ProductReportResponse result = productOrderReportServiceImplUnderTest.exportProductOrderReportAsExcelFile(
                earliestDate, latestDate);

        assertThat(result).isEqualTo(expectedResult);
        verify(mockFileGenerator, times(1)).generateTerminalRequestData(productOrderReports.get(), earliestDate + " to " + latestDate);
    }

    @Test
    void testExportProductOrderReportAsExcelFile_ProductOrderReportRepositoryReturnsAbsent() {
        when(mockProductOrderReportRepository.findAllByCreatedAtBetween(earliestDate,
                latestDate)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> productOrderReportServiceImplUnderTest.exportProductOrderReportAsExcelFile(
                earliestDate, latestDate)).isInstanceOf(CommonsModuleException.class);
    }
}
