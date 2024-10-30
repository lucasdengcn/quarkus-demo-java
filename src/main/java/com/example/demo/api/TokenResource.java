package com.example.demo.api;

import com.example.demo.model.auth.AuthTokens;
import io.quarkus.oidc.IdToken;
import io.quarkus.oidc.RefreshToken;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.jwt.Claims;
import org.eclipse.microprofile.jwt.JsonWebToken;

@Slf4j
@Path("/tokens")
public class TokenResource {

    /**
     * Injection point for the ID token issued by the OpenID Connect provider
     */
    @Inject
    @IdToken
    JsonWebToken idToken;

    /**
     * Injection point for the access token issued by the OpenID Connect provider
     */
    @Inject
    JsonWebToken accessToken;

    /**
     * Injection point for the refresh token issued by the OpenID Connect provider
     */
    @Inject
    RefreshToken refreshToken;

    /**
     * Returns the tokens available to the application.
     * This endpoint exists only for demonstration purposes.
     * Do not expose these tokens in a real application.
     *
     * @return an HTML page containing the tokens available to the application.
     */
    @GET
    public AuthTokens getTokens() {
        //
        log.info("idToken: {}", idToken);
        log.info("accessToken: {}", accessToken);
        log.info("refreshToken: {}", refreshToken);
        //

        Object userName = this.idToken.getClaim(Claims.preferred_username);
        Object scopes = this.accessToken.getClaim("scope");
        //
        //
        return AuthTokens.builder().userName(userName + "")
                .scopes(scopes + "")
                .idToken(idToken.getRawToken())
                .accessToken(accessToken.getRawToken())
                .refreshToken(refreshToken.getToken())
                .build();
    }

}
