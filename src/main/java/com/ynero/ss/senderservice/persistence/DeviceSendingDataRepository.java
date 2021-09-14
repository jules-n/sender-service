package com.ynero.ss.senderservice.persistence;

import com.ynero.ss.senderservice.domain.DeviceSendingData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DeviceSendingDataRepository extends MongoRepository<DeviceSendingData, UUID> {
    DeviceSendingData findByDeviceId(UUID deviceId);
}
