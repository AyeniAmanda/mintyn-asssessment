package com.mintyn.service.serviceImpl;

import com.mintyn.dto.ProductOrderReportDto;
import com.mintyn.dto.ProductReportResponse;
import com.mintyn.exception.CommonsModuleException;
import com.mintyn.model.ProductOrderReport;
import com.mintyn.repository.ProductOrderReportRepository;
import com.mintyn.service.ProductOrderReportService;
import com.mintyn.utils.FileGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductOrderReportServiceImpl implements ProductOrderReportService {

    private final ProductOrderReportRepository productOrderReportRepository;

    private final FileGenerator fileGenerator;


    @Override
    public void saveProductOrder(ProductOrderReportDto productOrderReportDto) {

        ProductOrderReport productOrderReport = new ProductOrderReport();
        BeanUtils.copyProperties(productOrderReportDto, productOrderReport);

        productOrderReportRepository.save(productOrderReport);
    }

    @Override
    public ProductReportResponse exportProductOrderReportAsExcelFile(LocalDate earliestDate, LocalDate latestDate) throws CommonsModuleException {

        List<ProductOrderReport> productOrderReportList = productOrderReportRepository.findAllByCreatedAtBetween(earliestDate, latestDate)
                .orElseThrow(() -> new CommonsModuleException("product.does.not.exists", HttpStatus.NOT_FOUND));

        fileGenerator.generateTerminalRequestData(productOrderReportList, earliestDate + " to " + latestDate);


        return new ProductReportResponse("file exported", HttpStatus.OK);
    }
}
