package com.ynero.ss.senderservice.consumers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ynero.ss.senderservice.domain.TenantSendingData;
import com.ynero.ss.senderservice.persistence.TenantSendingDataRepository;
import domain.TenantSendingDataDTO;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.UUID;

@Service
@Log4j2
public class TenantSendingDataConsumer {

    private final ObjectMapper objectMapper;

    private final TenantSendingDataRepository repository;

    public TenantSendingDataConsumer(ObjectMapper objectMapper, TenantSendingDataRepository repository) {
        this.objectMapper = objectMapper;
        this.repository = repository;
    }

    @SneakyThrows
    @KafkaListener(topics = "tenants-sending-data", groupId = "tenants-sending-data_group_id")
    public void consume(String message) {
        log.info("new log: {}", message);
        var map = objectMapper.readValue(message, LinkedHashMap.class);
        var dto = objectMapper.convertValue(map, TenantSendingDataDTO.class);
        var tenantSendingData = objectMapper.convertValue(dto, TenantSendingData.class);
        tenantSendingData.setDataId(UUID.randomUUID());
        repository.save(tenantSendingData);
    }
}