package com.example.demo.messaging.serde;

import com.example.demo.messaging.model.OrderMessage;
import com.example.demo.model.Order;
import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;

public class OrderMessageDeserializer extends ObjectMapperDeserializer<OrderMessage> {

    public OrderMessageDeserializer() {
        super(OrderMessage.class);
    }

}
