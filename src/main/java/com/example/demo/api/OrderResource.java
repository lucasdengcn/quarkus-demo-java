package com.example.demo.api;

import com.example.demo.annotation.APICommonResponse;
import com.example.demo.annotation.ResponseWithOrder;
import com.example.demo.annotation.ResponseWithOrderCreated;
import com.example.demo.annotation.ResponseOnBusinessAction;
import com.example.demo.model.Order;
import com.example.demo.model.OrderInput;
import com.example.demo.model.common.PageableOutput;
import com.example.demo.model.common.BusinessActionOutput;
import com.example.demo.service.OrderService;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Tag(name = "Orders API")
@Path("/orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@APICommonResponse
@Slf4j
public class OrderResource {

    @Inject
    OrderService orderService;

    @Schema
    public static class PageableOrder extends PageableOutput<Order> {
        // just for OpenAPI specification generation.
    }

    @GET
    @Path("/v1/all/{size}/{index}")
    @Operation(description = "Get pageable orders")
    @APIResponse(responseCode = "200", description = "Successful",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PageableOrder.class)))
    public PageableOutput<Order> ordersPaging(@PathParam("size") int size, @PathParam("index") int index) {
        log.info("allOrders pageable. {}, {}", size, index);
        return orderService.findOrdersPaged(index, size);
    }

    @POST
    @Path("/v1/")
    @Operation(description = "Create a order")
    @ResponseWithOrderCreated
    public Order createOrder(@Valid OrderInput input) {
        return orderService.createOrder(input);
    }

    @GET
    @Path("/v1/{id}")
    @Operation(description = "Get a order detail via id")
    @ResponseWithOrder
    public Order getOrder(@PathParam("id") Integer id){
        return orderService.findOrderById(id);
    }

    @DELETE
    @Path("/v1/{id}")
    @Operation(description = "Delete a order via id")
    @ResponseOnBusinessAction
    public BusinessActionOutput deleteOrder(@PathParam("id") Integer id) throws EntityNotFoundException {
        orderService.deleteOrder(id);
        return BusinessActionOutput.builder().success(true).build();
    }

    @POST
    @Path("/v1/{id}")
    @Operation(description = "Update a order detail via id")
    @ResponseWithOrder
    public Order saveOrder(@PathParam("id") Integer id, @Valid OrderInput input) {
        return orderService.updateOrder(id, input);
    }
}
