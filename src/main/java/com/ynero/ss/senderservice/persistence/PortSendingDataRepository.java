package com.ynero.ss.senderservice.persistence;

import com.ynero.ss.senderservice.domain.PortSendingData;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface PortSendingDataRepository extends MongoRepository<PortSendingData, String> {
    PortSendingData findByPortNameAndDeviceId(String portName, UUID deviceId);
}
