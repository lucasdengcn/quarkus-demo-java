package com.example.demo.messaging.consumer;

import com.example.demo.messaging.model.OrderMessage;
import com.example.demo.model.Order;
import io.smallrye.mutiny.Multi;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Incoming;

@ApplicationScoped
@Slf4j
public class OrderMessageConsumer {

    @Incoming("ordersIn")
    public void process(OrderMessage orderMessage){
        log.info("receive message: {}", orderMessage);
    }

}
