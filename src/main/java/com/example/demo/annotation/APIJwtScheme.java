package com.example.demo.annotation;

import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeIn;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@SecurityScheme(scheme = "Bearer", in = SecuritySchemeIn.HEADER, type = SecuritySchemeType.OAUTH2, bearerFormat="JWT")
@SecurityRequirement(name = "oauth2")
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface APIJwtScheme {
}
