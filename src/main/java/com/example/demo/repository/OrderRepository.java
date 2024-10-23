package com.example.demo.repository;

import com.example.demo.entity.OrderEntity;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class OrderRepository implements PanacheRepositoryBase<OrderEntity, Integer> {

    public PanacheQuery<OrderEntity> findByPage(Page page){
        return find("ORDER BY id desc").page(page);
    }

}
