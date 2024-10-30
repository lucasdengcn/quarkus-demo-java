package com.example.demo.api;

import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@PermitAll
@Path("/auth")
public class AuthResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/error")
    public String error() {
        return "Error on Auth";
    }
}
