package com.ynero.ss.senderservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Builder
@Document(collection = PortSendingData.COLLECTION_NAME)
public class PortSendingData extends  DeviceSendingData{
    public final static String COLLECTION_NAME = "ports-data";
    private String portName;
}
