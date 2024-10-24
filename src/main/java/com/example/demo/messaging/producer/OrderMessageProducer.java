package com.example.demo.messaging.producer;

import com.example.demo.messaging.model.OrderMessage;
import com.example.demo.model.Order;
import io.smallrye.reactive.messaging.MutinyEmitter;
import io.smallrye.reactive.messaging.OutgoingMessageMetadata;
import io.smallrye.reactive.messaging.kafka.api.OutgoingKafkaRecordMetadata;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.reactive.messaging.*;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

@ApplicationScoped
@Slf4j
public class OrderMessageProducer {

    public static final String TOPIC_DEMO_SERVICE_ORDERS = "demoService-orders";

    @Channel("ordersOut")
    Emitter<OrderMessage> emitter;

    /**
     *
     * @param order
     */
    @Transactional
    public void sendCreated(Order order){
        log.info("sendCreated message: {}", order);
        OrderMessage orderMessage = OrderMessage.builder().action("CREATED").order(order).build();
        //
        OutgoingKafkaRecordMetadata<String> metadata = OutgoingKafkaRecordMetadata.<String>builder()
                .withTopic(TOPIC_DEMO_SERVICE_ORDERS)
                .withKey("orders:" + order.getId())
                .build();
        //
        Message<OrderMessage> payload = Message.of(orderMessage)
                .addMetadata(metadata)
                .withNackWithMetadata(new BiFunction<Throwable, Metadata, CompletionStage<Void>>() {
                    @Override
                    public CompletionStage<Void> apply(Throwable throwable, Metadata objects) {
                        log.error("sendCreated message error: {}", objects, throwable);
                        return CompletableFuture.completedFuture(null);
                    }
                })
                .withAckWithMetadata(new Function<Metadata, CompletionStage<Void>>() {
                    @Override
                    public CompletionStage<Void> apply(Metadata objects) {
                        log.info("sendCreated message success: {}, {}", objects, orderMessage);
                        return CompletableFuture.completedFuture(null);
                    }
                });
        //
        emitter.send(payload);
    }

    /**
     *
     * @param order
     */
    public void sendUpdated(Order order){
        log.info("sendUpdated message: {}", order);
        OrderMessage orderMessage = OrderMessage.builder().action("UPDATED").order(order).build();
        //
        OutgoingKafkaRecordMetadata<String> metadata = OutgoingKafkaRecordMetadata.<String>builder()
                .withTopic(TOPIC_DEMO_SERVICE_ORDERS)
                .withKey("orders:" + order.getId())
                .build();
        //
        Message<OrderMessage> payload = Message.of(orderMessage)
                .addMetadata(metadata)
                .withNackWithMetadata(new BiFunction<Throwable, Metadata, CompletionStage<Void>>() {
                    @Override
                    public CompletionStage<Void> apply(Throwable throwable, Metadata objects) {
                        log.error("sendUpdated message error: {}", objects, throwable);
                        return CompletableFuture.completedFuture(null);
                    }
                })
                .withAckWithMetadata(new Function<Metadata, CompletionStage<Void>>() {
                    @Override
                    public CompletionStage<Void> apply(Metadata objects) {
                        log.info("sendUpdated message success: {}, {}", objects, orderMessage);
                        return CompletableFuture.completedFuture(null);
                    }
                });
        //
        emitter.send(payload);
    }
}
