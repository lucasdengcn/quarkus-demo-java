package com.example.demo.cache;

import com.example.demo.model.Order;
import com.example.demo.service.OrderService;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@QuarkusTest
class OrderCacheTests {

    @Inject
    OrderCache orderCache;

    @Inject
    OrderService orderService;

    @Test
    void test_on_set(){
        Order orderEntity = Order.builder().id(1)
                .price(100).customerId(10000).productCount(10).build();
        orderCache.set(orderEntity);
        //
        Order cachedObject = orderCache.get(1);
        //
        Assertions.assertEquals(orderEntity.getPrice(), cachedObject.getPrice());
        Assertions.assertEquals(orderEntity.getCustomerId(), cachedObject.getCustomerId());
        Assertions.assertEquals(orderEntity.getProductCount(), cachedObject.getProductCount());
        //
        Order order = orderService.findOrderById(1);
        Assertions.assertEquals(1, order.getId());
    }

}