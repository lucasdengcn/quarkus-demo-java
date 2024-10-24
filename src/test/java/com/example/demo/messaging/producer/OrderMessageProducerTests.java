package com.example.demo.messaging.producer;

import com.example.demo.model.Order;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Disabled
@QuarkusTest
class OrderMessageProducerTests {

    @Inject
    OrderMessageProducer orderMessageProducer;

    @Test
    void send_order_created() {
        orderMessageProducer.sendCreated(Order.builder().id(1).build());
    }

    @Test
    void send_order_updated() {
        orderMessageProducer.sendUpdated(Order.builder().id(1).build());
    }

}