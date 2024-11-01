package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.hibernate.sql.ast.Clause.INSERT;

@Entity
@Table(name = "orders")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_orders_id")
    @SequenceGenerator(name = "seq_orders_id", sequenceName = "seq_orders_id", allocationSize = 10)
    private Integer id;

    private Integer customerId;

    private Integer productId;

    private Integer productCount;

    private Integer price;

    @CreationTimestamp
    private LocalDateTime createdDate;

    @UpdateTimestamp
    private LocalDateTime updatedDate;

    private LocalDate endDate;

    private LocalTime endTime;

    private String status;

    private String source;

}
