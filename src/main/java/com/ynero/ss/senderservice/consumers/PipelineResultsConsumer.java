package com.ynero.ss.senderservice.consumers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ynero.ss.senderservice.services.SendingDataSeeker;
import dtos.ResultDTO;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;

@Service
public class PipelineResultsConsumer {
    private final ObjectMapper objectMapper;

    private final SendingDataSeeker sendingDataSeeker;

    public PipelineResultsConsumer(ObjectMapper objectMapper, SendingDataSeeker sendingDataSeeker) {
        this.objectMapper = objectMapper;
        this.sendingDataSeeker = sendingDataSeeker;
    }

    @KafkaListener(topics = "results-sending-data", groupId = "results-sending-data_group_id")
    private void consume(LinkedHashMap message){
        List<List< ResultDTO >> dataList = objectMapper.convertValue(message, List.class);
        dataList.forEach(
                innerList -> innerList.forEach(
                        dto -> sendingDataSeeker.find(dto)
                )
        );
    }
}