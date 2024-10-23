package com.example.demo.api;

import com.example.demo.model.Order;
import com.example.demo.model.OrderInput;
import com.example.demo.service.OrderService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import io.quarkus.logging.Log;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;

@Tag(name = "Orders API")
@Path("/orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderResource {

    @Inject
    OrderService orderService;

    @GET
    @Path("/v1/all")
    @Operation(description = "Get all orders")
    public List<Order> allOrders() {
        Log.info("allOrders");
        return orderService.findAllOrders();
    }

    @POST
    @Path("/v1/")
    @Operation(description = "Create a order")
    public Order createOrder(@Valid OrderInput input) {
        return orderService.createOrder(input);
    }

    @GET
    @Path("/v1/{id}")
    @Operation(description = "Get a order detail via id")
    public Order getOrder(@PathParam("id") Integer id){
        return orderService.findOrderById(id);
    }

    @DELETE
    @Path("/v1/{id}")
    @Operation(description = "Delete a order via id")
    public void deleteOrder(@PathParam("id") Integer id){
        orderService.deleteOrder(id);
    }

    @POST
    @Path("/v1/{id}")
    @Operation(description = "Update a order detail via id")
    public Order saveOrder(@PathParam("id") Integer id, @Valid OrderInput input) {
        return orderService.updateOrder(id, input);
    }
}
