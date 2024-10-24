package com.example.demo.messaging.producer;

import io.smallrye.reactive.messaging.annotations.Broadcast;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import java.util.Random;
import java.util.function.BiConsumer;

@ApplicationScoped
@Slf4j
public class MemoryEventProducer {

    private final Random random = new Random();

    @Channel("in-memory-channel")
    @Broadcast
    Emitter<Double> emitter;

    public void generate() {
        double v = random.nextDouble();
        emitter.send(v).whenCompleteAsync(new BiConsumer<Void, Throwable>() {
            @Override
            public void accept(Void unused, Throwable throwable) {
                if (null != throwable){
                    log.error("Send Event Error: ", throwable);
                }
            }
        });
    }

}
