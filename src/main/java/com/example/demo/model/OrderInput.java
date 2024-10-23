package com.example.demo.model;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.media.SchemaProperty;
import org.hibernate.validator.constraints.Range;

@Data
@Builder
@Schema
public class OrderInput {

    @SchemaProperty(name = "customerId", description = "customer's id", example = "123")
    @NotNull(message = "customer's id should not be NULL")
    private Integer customerId;

    @SchemaProperty(name = "productId", description = "product's id", example = "123")
    @NotNull(message = "product's id should not be NULL")
    private Integer productId;

    @SchemaProperty(name = "productCount", description = "the number of product purchased", example = "10")
    @NotNull(message = "productCount should not be NULL")
    @Range(min = 1, max = 10)
    private Integer productCount;

    @SchemaProperty(name = "price", description = "product's price", example = "50")
    @NotNull(message = "price should not be NULL")
    @Range(min = 0, max = 100)
    private Integer price;

}
