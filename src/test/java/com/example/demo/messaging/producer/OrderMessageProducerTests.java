package com.example.demo.messaging.producer;

import com.example.demo.model.Order;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class OrderMessageProducerTests {

    @Inject
    OrderMessageProducer orderMessageProducer;

    @Disabled
    @Test
    void send_order() {
        orderMessageProducer.sendCreated(Order.builder().id(1).build());
    }


}