package com.example.demo.model;

import lombok.Builder;
import lombok.Data;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.media.SchemaProperty;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Builder
@Schema
public class Order {

    @Schema(description = "order's id", example = "123")
    private Integer id;

    @Schema(description = "customer's id", example = "1001")
    private Integer customerId;

    @Schema(description = "product's id", example = "1100")
    private Integer productId;

    @Schema(description = "the number of product purchased", example = "10")
    private Integer productCount;

    @Schema(description = "product's price", example = "10")
    private Integer price;

    @Schema(description = "order's create date time", example = "2024-10-23 14:34:23")
    private LocalDateTime createdDate;

    @Schema(description = "order's last update date time", example = "2024-10-23 14:34:23")
    private LocalDateTime updatedDate;

    @Schema(description = "order's expiration date", example = "2024-10-23")
    private LocalDate endDate;

    @Schema(description = "order's expiration time", example = "14:34:23")
    private LocalTime endTime;

}
