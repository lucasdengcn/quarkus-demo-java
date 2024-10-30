package com.example.demo.filter;

import io.quarkus.arc.Unremovable;
import io.quarkus.oidc.OidcConfigurationMetadata;
import io.quarkus.oidc.common.OidcRequestContextProperties;
import io.quarkus.oidc.common.OidcRequestFilter;
import io.vertx.mutiny.core.buffer.Buffer;
import io.vertx.mutiny.ext.web.client.HttpRequest;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

@ApplicationScoped
@Unremovable
@Slf4j
public class OidcTokenRequestCustomizer implements OidcRequestFilter {

    @Override
    public void filter(HttpRequest<Buffer> request, Buffer buffer, OidcRequestContextProperties contextProps) {
        log.info("Filter: {}", request.uri());
        OidcConfigurationMetadata metadata = contextProps.get(OidcConfigurationMetadata.class.getName());
        if (null == metadata || null == metadata.getTokenUri()){
            return;
        }
        // Metadata URI is absolute, request URI value is relative
        if (metadata.getTokenUri().endsWith(request.uri())) {
            request.putHeader("TokenGrantDigest", calculateDigest(buffer.toString()));
        }
    }

    private String calculateDigest(String bodyString) {
        // Apply the required digest algorithm to the body string
        return "D-123";
    }


}
