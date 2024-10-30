package com.example.demo.api;

import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.ws.rs.CookieParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

import org.eclipse.microprofile.jwt.Claims;
import org.eclipse.microprofile.jwt.JsonWebToken;

import io.quarkus.oidc.OidcTenantConfig;
import io.quarkus.oidc.runtime.OidcUtils;
import io.quarkus.oidc.runtime.TenantConfigBean;
import io.smallrye.jwt.auth.principal.DefaultJWTParser;
import io.vertx.ext.web.RoutingContext;

@PermitAll
@Path("/session-expired-page")
public class SessionExpiredResource {

    @Inject
    RoutingContext context;

    @Inject
    TenantConfigBean tenantConfig;

    @GET
    public String sessionExpired(@CookieParam("session_expired") String sessionExpired) throws Exception {
        // Cookie format: jwt|<tenant id>
        if (null == sessionExpired){
            return "";
        }

        String[] pair = sessionExpired.split("\\|");
        OidcTenantConfig oidcConfig = tenantConfig.getStaticTenantsConfig().get(pair[1]).getOidcTenantConfig();
        JsonWebToken jwt = new DefaultJWTParser().decrypt(pair[0], oidcConfig.credentials.secret.get());
        OidcUtils.removeCookie(context, oidcConfig, "session_expired");
        return jwt.getClaim(Claims.preferred_username) + ", your session has expired. "
                + "Please login again at http://localhost:8081/" + oidcConfig.tenantId.get();
    }

}