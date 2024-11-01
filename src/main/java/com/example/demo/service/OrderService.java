package com.example.demo.service;

import com.example.demo.entity.OrderEntity;
import com.example.demo.exception.supplier.EntityNotFoundExceptionSupplier;
import com.example.demo.mapper.OrderMapper;
import com.example.demo.messaging.producer.OrderMessageProducer;
import com.example.demo.model.Order;
import com.example.demo.model.OrderInput;
import com.example.demo.model.common.PageableOutput;
import com.example.demo.repository.OrderRepository;
import io.opentelemetry.instrumentation.annotations.SpanAttribute;
import io.opentelemetry.instrumentation.annotations.WithSpan;
import io.quarkus.cache.CacheInvalidate;
import io.quarkus.cache.CacheKey;
import io.quarkus.cache.CacheResult;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.SecurityContext;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.context.ManagedExecutor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Slf4j
@ApplicationScoped
public class OrderService {

    public static final String ORDERS_CACHE = "orders-cache";

    @Inject
    private OrderRepository orderRepository;

    @Inject
    private OrderMapper orderMapper;

    @Inject
    private OrderMessageProducer orderMessageProducer;

    @Context
    private SecurityContext ctx;

    @Inject
    private ManagedExecutor managedExecutor;

    public List<Order> findAllOrders(){
        PanacheQuery<OrderEntity> panacheQuery = orderRepository.findAll();
        // stream() would need in transaction context
        List<OrderEntity> orderEntities = panacheQuery.list();
        return orderMapper.toModels(orderEntities);
    }

    public PageableOutput<Order> findOrdersPaged(int index, int size){
        Page page = Page.of(index, size);
        PanacheQuery<OrderEntity> panacheQuery = orderRepository.findByPage(page);
        long count = panacheQuery.count();
        // stream() would need in transaction context
        List<OrderEntity> orderEntities = panacheQuery.list();
        List<Order> models = orderMapper.toModels(orderEntities);
        //
        return new PageableOutput<Order>(panacheQuery, models, size, index);
    }

    @Transactional
    @CacheResult(cacheName = ORDERS_CACHE)
    public Order findOrderById(@CacheKey Integer id){
        OrderEntity orderEntity = orderRepository.findByIdOptional(id)
                .orElseThrow(new EntityNotFoundExceptionSupplier<>(OrderEntity.class, id));
        return orderMapper.toModel(orderEntity);
    }

    @WithSpan
    @Transactional
    public Order createOrder(OrderInput orderInput){
        OrderEntity orderEntity = orderMapper.toEntity(orderInput);
        orderEntity.setEndDate(LocalDate.now().plusDays(7));
        orderEntity.setEndTime(LocalTime.now());
        orderRepository.persist(orderEntity);
        log.info("saved entity. {}", orderEntity);
        Order model = orderMapper.toModel(orderEntity);
        //
        orderMessageProducer.sendCreated(model);
        //
        return model;
    }

    @Transactional
    @CacheInvalidate(cacheName = ORDERS_CACHE)
    @WithSpan
    public void deleteOrder(@SpanAttribute(value = "orderId") @CacheKey Integer id) throws EntityNotFoundException {
        OrderEntity orderEntity = orderRepository.findByIdOptional(id)
                .orElseThrow(new EntityNotFoundExceptionSupplier<>(OrderEntity.class, id));
        orderRepository.delete(orderEntity);
    }

    @Transactional
    @CacheInvalidate(cacheName = ORDERS_CACHE)
    @WithSpan
    public Order updateOrder(@SpanAttribute(value = "orderId") @CacheKey Integer id, OrderInput orderInput){
        OrderEntity orderEntity = orderRepository.findByIdOptional(id)
                .orElseThrow(new EntityNotFoundExceptionSupplier<>(OrderEntity.class, id));
        OrderEntity entity = orderMapper.toEntity(orderInput);
        entity.setId(id);
        orderRepository.persist(entity);
        return orderMapper.toModel(orderEntity);
    }

}
