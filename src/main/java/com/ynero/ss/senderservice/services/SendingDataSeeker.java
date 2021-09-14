package com.ynero.ss.senderservice.services;

import com.ynero.ss.senderservice.persistence.DeviceSendingDataRepository;
import com.ynero.ss.senderservice.persistence.PortSendingDataRepository;
import com.ynero.ss.senderservice.persistence.TenantSendingDataRepository;
import domain.SendingParameters;
import dtos.ResultDTO;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import senders.Sender;

import java.util.Map;

@Service
public class SendingDataSeeker {

    private final TenantSendingDataRepository tenantSendingDataRepository;

    private final PortSendingDataRepository portSendingDataRepository;

    private final DeviceSendingDataRepository deviceSendingDataRepository;

    public SendingDataSeeker(TenantSendingDataRepository tenantSendingDataRepository,
                             PortSendingDataRepository portSendingDataRepository,
                             DeviceSendingDataRepository deviceSendingDataRepository) {
        this.tenantSendingDataRepository = tenantSendingDataRepository;
        this.portSendingDataRepository = portSendingDataRepository;
        this.deviceSendingDataRepository = deviceSendingDataRepository;
    }

    public void find(ResultDTO dto) {
        var portName = dto.getPortName();
        var deviceId = dto.getDeviceId();
        var tenantId = dto.getTenantId();
        var value = dto.getValue();
        SendingParameters sendingParameters = null;

        var portSendingData = portSendingDataRepository.findByPortNameAndDeviceId(portName, deviceId);
        if (portSendingData != null) {
            sendingParameters = portSendingData.getParameters();
        } else {
            var deviceSendingData = deviceSendingDataRepository.findByDeviceId(deviceId);
            if (deviceSendingData != null) {
                sendingParameters = deviceSendingData.getParameters();
            } else {
                var tenantSendingData = tenantSendingDataRepository.findByTenantId(tenantId);
                if (tenantSendingData != null) {
                    sendingParameters = tenantSendingData.getParameters();
                } else {
                    sendingParameters = new SendingParameters();
                    sendingParameters.setSendingClassName("com.ynero.ss.senderservice.senders.DefaultSender");
                    sendingParameters.setParameters(Map.of());
                }
            }
        }
        send(sendingParameters, value);
    }

    @SneakyThrows
    private void send(SendingParameters parameters, Object value) {
        Sender sender = (Sender) Class.forName(parameters.getSendingClassName()).newInstance();
        sender.send(parameters.getParameters(), value);
    }
}
