package com.example.springintegration.errorhandler;

import lombok.extern.log4j.Log4j2;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class SiSystemExceptionHandler {

    public void handle(Message<Throwable> msg) {
        Throwable t = msg.getPayload();
        log.error(t.getMessage(), t);
    }
}
