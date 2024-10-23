package com.example.demo.service;

import com.example.demo.entity.OrderEntity;
import com.example.demo.mapper.OrderMapper;
import com.example.demo.model.Order;
import com.example.demo.model.OrderInput;
import com.example.demo.repository.OrderRepository;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import io.quarkus.logging.Log;

import java.util.List;

@ApplicationScoped
public class OrderService {

    @Inject
    private OrderRepository orderRepository;

    @Inject
    private OrderMapper orderMapper;

    public List<Order> findAllOrders(){
        PanacheQuery<OrderEntity> panacheQuery = orderRepository.findAll();
        List<OrderEntity> orderEntities = panacheQuery.stream().toList();
        return orderMapper.toModels(orderEntities);
    }

    public Order findOrderById(Integer id){
        OrderEntity orderEntity = orderRepository.findById(id);
        return orderMapper.toModel(orderEntity);
    }

    public Order createOrder(OrderInput orderInput){
        OrderEntity orderEntity = orderMapper.toEntity(orderInput);
        orderRepository.persist(orderEntity);
        Log.infov("saved entity. {}", orderEntity);
        return orderMapper.toModel(orderEntity);
    }

    public void deleteOrder(Integer id){
        OrderEntity orderEntity = orderRepository.findById(id);
        if (orderEntity != null) {
            orderRepository.delete(orderEntity);
        } else {
            Log.infov("No entity found with id: {}", id);
        }
    }

    public Order updateOrder(Integer id, OrderInput orderInput){
        OrderEntity orderEntity = orderRepository.findById(id);
        if (orderEntity == null) {
            Log.infov("No entity found with id: {}", id);
            return null;
        }
        OrderEntity entity = orderMapper.toEntity(orderInput);
        entity.setId(id);
        orderRepository.persist(entity);
        return orderMapper.toModel(entity);
    }

}
