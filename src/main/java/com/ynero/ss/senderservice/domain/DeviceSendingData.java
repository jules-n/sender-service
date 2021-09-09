package com.ynero.ss.senderservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = DeviceSendingData.COLLECTION_NAME)
public class DeviceSendingData extends TenantSendingData{
    public final static String COLLECTION_NAME = "devices-data";
    private UUID deviceId;
}
