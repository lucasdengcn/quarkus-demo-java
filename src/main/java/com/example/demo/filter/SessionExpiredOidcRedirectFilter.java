package com.example.demo.filter;

import io.quarkus.oidc.AuthorizationCodeTokens;
import io.quarkus.oidc.Redirect;
import io.quarkus.oidc.TenantFeature;
import io.quarkus.oidc.runtime.OidcUtils;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;

import io.quarkus.arc.Unremovable;
import io.quarkus.oidc.OidcRedirectFilter;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.jwt.Claims;

@ApplicationScoped
@Unremovable
@TenantFeature("tenant-refresh")
@Redirect(Redirect.Location.SESSION_EXPIRED_PAGE)
@Slf4j
public class SessionExpiredOidcRedirectFilter implements OidcRedirectFilter {

    @Override
    public void filter(OidcRedirectContext context) {
        log.info("Redirect: {}", context.redirectUri());
        log.info("TenantConfig: {}", context.oidcTenantConfig());
        if (context.redirectUri().contains("/session-expired-page")) {
            //
            context.additionalQueryParams().add("redirect-filtered", "true,");
            context.routingContext().response().putHeader("Redirect-Filtered", "true");
            //
            AuthorizationCodeTokens tokens = context.routingContext().get(AuthorizationCodeTokens.class.getName());
            String userName = OidcUtils.decodeJwtContent(tokens.getIdToken()).getString(Claims.preferred_username.name());
            String jwe = Jwt.preferredUserName(userName).jwe()
                    .encryptWithSecret(context.oidcTenantConfig().credentials.secret.get());
            //
            OidcUtils.createCookie(context.routingContext(),
                    context.oidcTenantConfig(), "session_expired",
                    jwe + "|" + context.oidcTenantConfig().tenantId.get(), 10);
        }
    }

}
