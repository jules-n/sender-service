package com.ynero.ss.senderservice.domain;

import domain.SendingParameters;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SendingData {
    private UUID dataId;
    private SendingParameters parameters;
}
