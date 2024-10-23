package com.example.demo.mapper;

import com.example.demo.entity.OrderEntity;
import com.example.demo.model.Order;
import com.example.demo.model.OrderInput;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = QuarkusMappingConfig.class)
public interface OrderMapper {

    OrderEntity toEntity(OrderInput input);

    Order toModel(OrderEntity orderEntity);

    List<Order> toModels(List<OrderEntity> orderEntities);

}
