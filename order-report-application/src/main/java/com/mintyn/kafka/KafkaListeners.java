package com.mintyn.kafka;

import com.mintyn.dto.ProductOrderReportDto;
import com.mintyn.exception.CommonsModuleException;
import com.mintyn.service.ProductOrderReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Component
@RequiredArgsConstructor
public class KafkaListeners {

    private final ProductOrderReportService productOrderReportService;

    @KafkaListener(topics = "order", groupId = "mintyn", containerFactory = "reportListenerContainerFactory")
    void listener(ProductOrderReportDto productOrderReportDto) throws CommonsModuleException {

            if(!ObjectUtils.isEmpty(productOrderReportDto)){
                productOrderReportService.saveProductOrder(productOrderReportDto);
            } else {
                throw  CommonsModuleException.badRequest("invalid.order");
            }
    }

}
