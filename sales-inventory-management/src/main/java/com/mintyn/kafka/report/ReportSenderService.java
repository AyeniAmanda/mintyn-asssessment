package com.mintyn.kafka.report;

import com.mintyn.dto.OrderResponseDto;

public interface ReportSenderService {

    void sendOrderReport(OrderResponseDto orderResponseDto);
}
