package com.example.demo.filter;

import io.quarkus.vertx.web.RouteFilter;
import io.vertx.ext.web.RoutingContext;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TrafficSplitFilter {

    @RouteFilter
    public void addTrafficHeader(RoutingContext context) {
//        String customHeader = context.request().getHeader("X-Custom-Header");
//
//        if (customHeader == null || !customHeader.equals("CUSTOM_TOKEN")) {
//            // You can perform custom security checks here
//            // For example, you can throw a ForbiddenException for unauthorized requests
//            throw new ForbiddenException("Unauthorized access");
//        }
        context.response().putHeader("X-Env", "Blue");
        // If the custom header check passes, allow the request to proceed
        context.next();
    }
}
