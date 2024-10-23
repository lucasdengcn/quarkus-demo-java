package com.example.demo.messaging.producer;

import com.example.demo.messaging.model.OrderMessage;
import com.example.demo.model.Order;
import io.smallrye.reactive.messaging.MutinyEmitter;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import java.util.function.BiConsumer;

@ApplicationScoped
@Slf4j
public class OrderMessageProducer {

    @Channel("ordersOut")
    Emitter<OrderMessage> emitter;

    /**
     *
     * @param order
     */
    public void sendCreated(Order order){
        log.info("send message: {}", order);
        OrderMessage orderMessage = OrderMessage.builder().action("CREATED").order(order).build();
        //
        emitter.send(orderMessage).whenCompleteAsync(new BiConsumer<Void, Throwable>() {
            @Override
            public void accept(Void unused, Throwable throwable) {
                if (null != throwable){
                    log.error("send message error: ", throwable);
                } else {
                    log.info("send message success: {}", orderMessage);
                }
            }
        });
    }
    
}
