package com.ynero.ss.senderservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = TenantSendingData.COLLECTION_NAME)
public class TenantSendingData extends SendingData{
    public final static String COLLECTION_NAME = "tenants-data";
    private String tenantId;
}
