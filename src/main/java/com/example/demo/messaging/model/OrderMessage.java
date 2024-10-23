package com.example.demo.messaging.model;

import com.example.demo.model.Order;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderMessage {
    private String action;
    private Order order;
}
