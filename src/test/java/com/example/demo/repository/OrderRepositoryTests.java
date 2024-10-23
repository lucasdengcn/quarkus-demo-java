package com.example.demo.repository;

import com.example.demo.entity.OrderEntity;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import io.quarkus.panache.common.Page;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class OrderRepositoryTests {

    @Inject
    OrderRepository orderRepository;

    @Test
    void find_orders_page_success() {
        Page page = Page.of(0, 10);
        PanacheQuery<OrderEntity> panacheQuery = orderRepository.findByPage(page);
        long count = panacheQuery.count();
        assertTrue(count > 0);
        List<OrderEntity> orderEntities = panacheQuery.list();
        assertFalse(orderEntities.isEmpty());
    }

    @Test
    void find_orders_page_empty() {
        Page page = Page.of(1000000000, 10);
        PanacheQuery<OrderEntity> panacheQuery = orderRepository.findByPage(page);
        long count = panacheQuery.count();
        assertTrue(count > 0);
        List<OrderEntity> orderEntities = panacheQuery.stream().toList();
        assertTrue(orderEntities.isEmpty());
    }

}