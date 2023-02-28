package com.mintyn.service;

import com.mintyn.dto.ProductOrderReportDto;
import com.mintyn.dto.ProductReportResponse;
import com.mintyn.exception.CommonsModuleException;

import java.time.LocalDate;

public interface ProductOrderReportService {

    void saveProductOrder(ProductOrderReportDto productOrderReportDto);

    ProductReportResponse exportProductOrderReportAsExcelFile(LocalDate earliestDate, LocalDate latestDate) throws CommonsModuleException;
}
