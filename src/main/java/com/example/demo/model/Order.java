package com.example.demo.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Builder
public class Order {

    private Integer id;

    private Integer customerId;

    private Integer productId;

    private Integer productCount;

    private Integer price;

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;

    private LocalDate endDate;

    private LocalTime endTime;

}
