package com.example.demo.messaging.consumer;

import io.smallrye.reactive.messaging.annotations.Broadcast;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

import java.util.Random;

@ApplicationScoped
@Slf4j
public class MemoryEventConsumer {

    @Incoming("in-memory-channel")
    void consumeAndLog(double price) {
        log.info("consume-log: {}", price);
    }

    @Incoming("in-memory-channel")
    void consumeAndSend(double price) {
        log.info("consume-send: {}", price);
    }

}
