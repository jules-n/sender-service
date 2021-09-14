package com.ynero.ss.senderservice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;

import static com.ynero.ss.senderservice.domain.PortSendingData.COLLECTION_NAME;

@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
@Document(collection = COLLECTION_NAME)
public class PortSendingData extends DeviceSendingData {
    public static final String COLLECTION_NAME= "port-sending-data";
    private String portName;
}
