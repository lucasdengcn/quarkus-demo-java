package com.example.demo.security;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;

import io.quarkus.oidc.SecurityEvent;
import io.vertx.ext.web.RoutingContext;
import lombok.extern.slf4j.Slf4j;

@ApplicationScoped
@Slf4j
public class SecurityEventListener {

    /**
     * https://quarkus.io/guides/security-customization#observe-security-events
     *
     * @param event
     */
    public void event(@Observes SecurityEvent event) {
        String tenantId = event.getSecurityIdentity().getAttribute("tenant-id");
        RoutingContext vertxContext = event.getSecurityIdentity().getAttribute(RoutingContext.class.getName());
        String message = String.format("event:%s,tenantId:%s", event.getEventType().name(), tenantId);
        vertxContext.put("listener-message", message);
        log.info("onEvent: {}", message);
    }

}