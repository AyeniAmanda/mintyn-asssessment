package com.mintyn.service;

import com.mintyn.dto.ProductOrderReportDto;
import com.mintyn.dto.ProductReportResponse;
import com.mintyn.exception.CommonsModuleException;

import java.util.Date;

public interface ProductOrderReportService {

    void saveProductOrder(ProductOrderReportDto productOrderReportDto);

    ProductReportResponse exportProductOrderReportAsExcelFile(Date earliestDate, Date latestDate) throws CommonsModuleException;
}
