package com.example.demo.annotation;

import com.example.demo.model.common.DangerOpOutput;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@APIResponse(responseCode = "200", description = "Successful",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = DangerOpOutput.class)))
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ResponseOnDangerOp {
}
