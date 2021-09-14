package com.ynero.ss.senderservice.persistence;

import com.ynero.ss.senderservice.domain.TenantSendingData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TenantSendingDataRepository extends MongoRepository<TenantSendingData, UUID> {
    TenantSendingData findByTenantId(String tenantId);
}
