package com.ynero.ss.senderservice.senders;

import lombok.extern.log4j.Log4j2;
import senders.Sender;

import java.util.Map;

@Log4j2
public class DefaultSender implements Sender {
    @Override
    public void send(Map<String, Object> map, Object o) {
        log.info("value: {}", o);
        System.out.println("value: " + o);
    }
}
