package com.example.demo.exception;

import jakarta.annotation.Nullable;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.media.SchemaProperty;

import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Representation of RFC7807 Problem schema.
 */
@Schema(description = "ErrorResponse Common Schema")
public class HttpProblem {

    public static final MediaType MEDIA_TYPE = new MediaType("application", "problem+json");

    @Schema(description = "url type")
    private final URI type;

    @Schema(description = "error title", example = "Not Found")
    private final String title;

    @Schema(description = "error status code", example = "404")
    private final int statusCode;

    @Schema(description = "error detail", example = "error detail message")
    private final String detail;

    @Schema(description = "request url", example = "/example/url")
    private final URI instance;

    @Schema(description = "extra parameters")
    private final Map<String, Object> parameters;

    @Schema(description = "extra headers")
    private final Map<String, Object> headers;


    protected HttpProblem(Builder builder) {
        this.type = builder.type;
        this.title = builder.title;
        this.statusCode = builder.statusCode;
        this.detail = builder.detail;
        this.instance = builder.instance;
        this.parameters = Collections.unmodifiableMap(Optional.of(builder.parameters).orElseGet(LinkedHashMap::new));
        this.headers = Collections.unmodifiableMap(Optional.of(builder.headers).orElseGet(LinkedHashMap::new));
    }

    private static String createMessage(String title, String detail) {
        return Stream.of(title, detail)
                .filter(Objects::nonNull)
                .collect(Collectors.joining(": "));
    }

    public static HttpProblem valueOf(Response.Status status) {
        return builder()
                .withTitle(status.getReasonPhrase())
                .withStatus(status)
                .build();
    }

    public static HttpProblem valueOf(Response.Status status, String detail) {
        return builder()
                .withTitle(status.getReasonPhrase())
                .withStatus(status)
                .withDetail(detail)
                .build();
    }

    public static Builder builder() {
        return new Builder();
    }

    /**
     * Creates Builder instance and initializes it with fields from given HttpProblem
     *
     * @param original Problem 'prototype'
     * @return Builder object with values taken from origin HttpProblem
     */
    public static Builder builder(HttpProblem original) {
        Builder builder = builder()
                .withType(original.getType())
                .withInstance(original.getInstance())
                .withTitle(original.getTitle())
                .withStatus(original.getStatusCode())
                .withDetail(original.getDetail());
        original.parameters.forEach(builder::with);
        original.headers.forEach(builder::withHeader);
        return builder;
    }

    public URI getType() {
        return this.type;
    }

    public String getTitle() {
        return this.title;
    }

    public int getStatusCode() {
        return this.statusCode;
    }

    public String getDetail() {
        return this.detail;
    }

    public URI getInstance() {
        return this.instance;
    }

    public Map<String, Object> getParameters() {
        return this.parameters;
    }

    public Map<String, Object> getHeaders() {
        return this.headers;
    }

    public Response toResponse() {
        Response.ResponseBuilder builder = Response
                .status(getStatusCode())
                .type(HttpProblem.MEDIA_TYPE)
                .entity(this);

        getHeaders().forEach(builder::header);

        return builder.build();
    }

    public static class Builder {

        private static final Set<String> RESERVED_PROPERTIES = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(
                "type", "title", "statusCode", "detail", "instance")));

        private URI type;
        private String title;
        private int statusCode = 500;
        private String detail;
        private URI instance;
        private final Map<String, Object> headers = new LinkedHashMap<>();
        private final Map<String, Object> parameters = new LinkedHashMap<>();

        private Builder() {
        }

        public Builder withType(@Nullable URI type) {
            this.type = type;
            return this;
        }

        public Builder withTitle(@Nullable String title) {
            this.title = title;
            return this;
        }

        public Builder withStatus(Response.StatusType status) {
            Objects.requireNonNull(status);
            this.statusCode = status.getStatusCode();
            return this;
        }

        public Builder withStatus(int statusCode) {
            this.statusCode = statusCode;
            return this;
        }

        public Builder withDetail(@Nullable String detail) {
            this.detail = detail;
            return this;
        }

        public Builder withInstance(@Nullable URI instance) {
            this.instance = instance;
            return this;
        }

        public Builder withHeader(String headerName, Object value) {
            headers.put(headerName, value);
            return this;
        }

        /**
         * Adds custom property to the response.
         *
         * @throws IllegalArgumentException if key is any of type, title, status, detail or instance
         */
        public Builder with(String key, Object value) throws IllegalArgumentException {
            if (RESERVED_PROPERTIES.contains(key)) {
                throw new IllegalArgumentException("Property " + key + " is reserved");
            }
            parameters.put(key, value);
            return this;
        }

        public HttpProblem build() {
            return new HttpProblem(this);
        }

    }

}
