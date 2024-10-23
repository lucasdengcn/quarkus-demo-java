package com.example.demo.mapper;

import com.example.demo.entity.OrderEntity;
import com.example.demo.model.Order;
import com.example.demo.model.OrderInput;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(config = QuarkusMappingConfig.class, unmappedTargetPolicy= ReportingPolicy.IGNORE)
public interface OrderMapper {

    OrderEntity toEntity(OrderInput input);

    Order toModel(OrderEntity orderEntity);

    List<Order> toModels(List<OrderEntity> orderEntities);

}
