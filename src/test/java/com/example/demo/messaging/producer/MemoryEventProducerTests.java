package com.example.demo.messaging.producer;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Disabled
@QuarkusTest
class MemoryEventProducerTests {

    @Inject
    MemoryEventProducer eventProducer;

    @Test
    void generate_message() {
        eventProducer.generate();
    }

}