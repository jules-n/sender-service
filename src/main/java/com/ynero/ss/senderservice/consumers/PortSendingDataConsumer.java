package com.ynero.ss.senderservice.consumers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ynero.ss.senderservice.domain.PortSendingData;
import com.ynero.ss.senderservice.persistence.PortSendingDataRepository;
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

    @KafkaListener(topics = "port-sending-data", groupId = "port-sending-data_group_id")
    private void consume(LinkedHashMap message){
        var data = objectMapper.convertValue(message, PortSendingData.class);
        data.setDataId(UUID.randomUUID());
        log.info(data);
        repository.save(data);
    }
}
