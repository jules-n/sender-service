package com.ynero.ss.senderservice.consumers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ynero.ss.senderservice.domain.DeviceSendingData;
import com.ynero.ss.senderservice.persistence.DeviceSendingDataRepository;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.UUID;

@Service
@Log4j2
public class DeviceSendingDataConsumer {

    private final ObjectMapper objectMapper;

    private final DeviceSendingDataRepository repository;

    public DeviceSendingDataConsumer(ObjectMapper objectMapper, DeviceSendingDataRepository repository) {
        this.objectMapper = objectMapper;
        this.repository = repository;
    }

    @SneakyThrows
    @KafkaListener(topics = "device-sending-data", groupId = "device-sending-data_group_id")
    private void consume(String message){
        var map = objectMapper.readValue(message, LinkedHashMap.class);
        var data = objectMapper.convertValue(map, DeviceSendingData.class);
        data.setDataId(UUID.randomUUID());
        log.info(data);
        repository.save(data);
    }
}
