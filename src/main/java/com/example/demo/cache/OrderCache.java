package com.example.demo.cache;

import com.example.demo.model.Order;
import io.quarkus.cache.Cache;
import io.quarkus.cache.CacheName;
import io.quarkus.redis.datasource.RedisDataSource;
import io.quarkus.redis.datasource.value.ValueCommands;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jetbrains.annotations.NotNull;

@ApplicationScoped
public class OrderCache {

    @Inject
    @CacheName("orders-cache")
    Cache cache;

    final ValueCommands<String, Order> valueCommands;

    public OrderCache(RedisDataSource redisDataSource) {
        this.valueCommands = redisDataSource.value(Order.class);
    }

    public void set(Order order){
        String key = getCacheKey(order.getId());
        valueCommands.setex(key, 10, order);
    }

    public Order get(Integer id){
        String key = getCacheKey(id);
        return valueCommands.get(key);
    }

    private static @NotNull String getCacheKey(Integer id) {
        return "orders:" + id;
    }

}
