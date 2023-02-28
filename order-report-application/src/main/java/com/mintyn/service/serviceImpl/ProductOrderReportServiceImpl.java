package com.mintyn.service.serviceImpl;

import com.mintyn.dto.ProductOrderReportDto;
import com.mintyn.model.ProductOrderReport;
import com.mintyn.repository.ProductOrderReportRepository;
import com.mintyn.service.ProductOrderReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductOrderReportServiceImpl implements ProductOrderReportService {

    private final ProductOrderReportRepository productOrderReportRepository;


    @Override
    public void saveProductOrder(ProductOrderReportDto productOrderReportDto) {

        ProductOrderReport productOrderReport = new ProductOrderReport();
        BeanUtils.copyProperties(productOrderReportDto, productOrderReport);

        productOrderReportRepository.save(productOrderReport);
    }


}
