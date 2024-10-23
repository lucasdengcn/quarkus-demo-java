package com.example.demo.annotation;

import com.example.demo.exception.HttpProblem;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

import java.lang.annotation.*;

@APIResponses(value = {
        @APIResponse(responseCode = "400", description = "Bad Request",
                content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = HttpProblem.class))),
        @APIResponse(responseCode = "401", description = "Unauthorized",
                content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = HttpProblem.class))),
        @APIResponse(responseCode = "403", description = "Forbidden",
                content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = HttpProblem.class))),
        @APIResponse(responseCode = "404", description = "Not Found",
                content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = HttpProblem.class))),
        @APIResponse(responseCode = "429", description = "Too Many Requests",
                content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = HttpProblem.class))),
        @APIResponse(responseCode = "500", description = "Internal server error",
                content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = HttpProblem.class))),
        @APIResponse(responseCode = "502", description = "Bad Gateway",
                content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = HttpProblem.class))),
        @APIResponse(responseCode = "503", description = "Service Unavailable",
                content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = HttpProblem.class))),
        @APIResponse(responseCode = "504", description = "Gateway Timeout",
                content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = HttpProblem.class)))
})
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface APICommonResponse {
    
}
