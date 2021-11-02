package com.ynero.ss.senderservice.consumers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ynero.ss.senderservice.services.SendingDataSeeker;
import dtos.ResultDTO;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;

@Service
@Log4j2
public class PipelineResultsConsumer {
    private final ObjectMapper objectMapper;

    private final SendingDataSeeker sendingDataSeeker;

    public PipelineResultsConsumer(ObjectMapper objectMapper, SendingDataSeeker sendingDataSeeker) {
        this.objectMapper = objectMapper;
        this.sendingDataSeeker = sendingDataSeeker;
    }

    @SneakyThrows
    @KafkaListener(topics = "results-sending-data", groupId = "results-sending-data_group_id")
    private void consume(String message){
        log.info("message: {}", message);
        var map = objectMapper.readValue(message, List.class);
        map.stream().forEach(
                val -> {
                    var list = objectMapper.convertValue(val, List.class);
                    list.forEach(
                            el -> {
                                var dto = objectMapper.convertValue(el, ResultDTO.class);
                                sendingDataSeeker.find(dto);
                            }
                    );
                }
        );
    }
}
