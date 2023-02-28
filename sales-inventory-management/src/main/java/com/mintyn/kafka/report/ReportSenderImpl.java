package com.mintyn.kafka.report;

import com.mintyn.dto.OrderResponseDto;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;




@Service
@Slf4j
@RequiredArgsConstructor
public class ReportSenderImpl implements ReportSenderService {

    private final KafkaTemplate<String, OrderResponseDto> kafkaTemplate;

    @Override
    public void sendOrderReport(OrderResponseDto orderResponseDto){
        String topicName = "order";
        log.info("Sending message='{}' to topic='{}'", orderResponseDto, topicName);
        ListenableFuture<SendResult<String, OrderResponseDto>> kafkaResultFuture = (ListenableFuture<SendResult<String, OrderResponseDto>>) kafkaTemplate.send(topicName, orderResponseDto);
        addCallback(topicName, orderResponseDto, kafkaResultFuture);
    }

    private void addCallback(String topicName, OrderResponseDto orderResponseDto,
                          ListenableFuture<SendResult<String, OrderResponseDto>> kafkaResultFuture) {
        kafkaResultFuture.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onFailure(@NonNull Throwable throwable) {
                log.error("Error while sending message {} to topic {}", orderResponseDto.toString(), topicName, throwable);
                throwable.printStackTrace();
            }

            @Override
            public void onSuccess(SendResult<String, OrderResponseDto> result) {
                RecordMetadata metadata = result.getRecordMetadata();
                log.debug("Received new metadata. Topic: {}; Partition {}; Offset {}; Timestamp {}, at time {}",
                        metadata.topic(),
                        metadata.partition(),
                        metadata.offset(),
                        metadata.timestamp(),
                        System.nanoTime());
            }
        });
    }
}
