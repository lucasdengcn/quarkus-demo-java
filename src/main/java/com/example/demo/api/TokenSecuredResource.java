package com.example.demo.api;

import com.example.demo.annotation.APICommonResponse;
import com.example.demo.annotation.APIJwtScheme;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.SecurityContext;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.HashMap;
import java.util.Map;

@Path("/secured")
@Tag(description = "Secured APIs")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@APICommonResponse
@Slf4j
public class TokenSecuredResource {

    @Inject
    JsonWebToken jwt;

    @Context
    SecurityContext ctx;

    @GET
    @Path("/v1/permit-all")
    @PermitAll
    public Map<String, Object> permitAll(){
        log.info("jwt is {}", jwt);
        return getResponseMap(ctx);
    }

    @GET
    @Path("/v1/roles-allowed")
    @RolesAllowed({"User", "Admin"})
    @APIJwtScheme
    public Map<String, Object> rolesAllowed(){
        log.info("jwt is {}", jwt);
        Map<String, Object> map = getResponseMap(ctx);
        map.put("birthdate", jwt.getClaim("birthdate"));
        return map;
    }

    private Map<String, Object> getResponseMap(SecurityContext ctx) {
        String name;
        if (ctx.getUserPrincipal() == null) {
            name = "anonymous";
        } else if (!ctx.getUserPrincipal().getName().equals(jwt.getName())) {
            throw new InternalServerErrorException("Principal and JsonWebToken names do not match");
        } else {
            name = ctx.getUserPrincipal().getName();
        }

        Map<String, Object> map = Map.of(
                "name", name,
                "isHttps", ctx.isSecure(),
                "authScheme", ctx.getAuthenticationScheme() == null ? "test" : ctx.getAuthenticationScheme(),
                "hasJWT", hasJwt()
        );

        return new HashMap<>(map);
    }

    private boolean hasJwt() {
        return jwt.getClaimNames() != null;
    }

}
