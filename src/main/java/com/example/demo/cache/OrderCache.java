package com.example.demo.cache;

import com.example.demo.model.Order;
import io.quarkus.redis.datasource.RedisDataSource;
import io.quarkus.redis.datasource.value.ValueCommands;
import io.smallrye.common.constraint.NotNull;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class OrderCache {

    public static final int EXP_SECONDS = 300;

    final ValueCommands<String, Order> valueCommands;

    public OrderCache(RedisDataSource redisDataSource) {
        this.valueCommands = redisDataSource.value(Order.class);
    }

    public void set(Order order){
        String key = getCacheKey(order.getId());
        valueCommands.setex(key, EXP_SECONDS, order);
    }

    public Order get(Integer id){
        String key = getCacheKey(id);
        return valueCommands.get(key);
    }

    private static @NotNull String getCacheKey(Integer id) {
        return "orders:" + id;
    }

}
