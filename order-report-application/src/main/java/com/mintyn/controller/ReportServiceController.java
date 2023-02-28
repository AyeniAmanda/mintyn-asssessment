package com.mintyn.controller;

import com.mintyn.exception.CommonsModuleException;
import com.mintyn.response.ResponseDto;
import com.mintyn.service.ProductOrderReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v6/report")
public class ReportServiceController {
    private final ProductOrderReportService productOrderReportService;

    @GetMapping("/generate-report/{begin}/{end}")
    public ResponseDto<?> generateReport(
            @PathVariable("begin") LocalDate begin,
            @PathVariable("end") LocalDate end) throws CommonsModuleException {
        return ResponseDto.wrapSuccessResult(productOrderReportService.exportProductOrderReportAsExcelFile(begin, end), "request.successful");
    }

}
