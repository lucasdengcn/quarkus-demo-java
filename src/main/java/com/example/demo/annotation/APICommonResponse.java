package com.example.demo.annotation;

import com.example.demo.exception.HttpProblem;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

import java.lang.annotation.*;

@APIResponses(value = {
        @APIResponse(responseCode = "404", description = "Order Not Found",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = HttpProblem.class))),
        @APIResponse(responseCode = "500", description = "Internal server error",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = HttpProblem.class)))
})
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface APICommonResponse {
}
