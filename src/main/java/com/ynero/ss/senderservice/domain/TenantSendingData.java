package com.ynero.ss.senderservice.domain;

import domain.SendingData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper=false)
@Document(collection = TenantSendingData.COLLECTION_NAME)
public class TenantSendingData extends SendingData {
    public static final String COLLECTION_NAME= "tenant-sending-data";
    private String tenantId;
}
