package com.ynero.ss.senderservice.consumers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ynero.ss.senderservice.domain.PortSendingData;
import com.ynero.ss.senderservice.persistence.PortSendingDataRepository;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.UUID;

@Log4j2
@Service
public class PortSendingDataConsumer {
    private final ObjectMapper objectMapper;

    private final PortSendingDataRepository repository;

    public PortSendingDataConsumer(ObjectMapper objectMapper, PortSendingDataRepository repository) {
        this.objectMapper = objectMapper;
        this.repository = repository;
    }

    @SneakyThrows
    @KafkaListener(topics = "port-sending-data", groupId = "port-sending-data_group_id")
    private void consume(String message) {
        var map = objectMapper.readValue(message, LinkedHashMap.class);
        var data = objectMapper.convertValue(map, PortSendingData.class);
        data.setDataId(UUID.randomUUID());
        log.info(data);
        repository.save(data);
    }
}
