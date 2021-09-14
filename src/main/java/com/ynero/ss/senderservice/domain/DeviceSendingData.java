package com.ynero.ss.senderservice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

import static com.ynero.ss.senderservice.domain.DeviceSendingData.COLLECTION_NAME;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper=false)
@Document(collection = COLLECTION_NAME)
public class DeviceSendingData extends TenantSendingData {
    public static final String COLLECTION_NAME= "device-sending-data";
    private UUID deviceId;
}
