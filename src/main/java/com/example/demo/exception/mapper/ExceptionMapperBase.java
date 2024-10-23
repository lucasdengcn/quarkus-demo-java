package com.example.demo.exception.mapper;

import com.example.demo.exception.HttpProblem;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.ExceptionMapper;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public abstract class ExceptionMapperBase<E extends Throwable> implements ExceptionMapper<E> {

    @Context
    UriInfo uriInfo;

    @Override
    public final Response toResponse(E exception) {
        HttpProblem.Builder problem = toProblem(exception);
        problem.withInstance(pathToInstance(uriInfo.getPath()));
        return problem.build().toResponse();
    }

    public static URI pathToInstance(String path) {
        if (path == null) {
            return null;
        }
        try {
            return new URI(encodeUnwiseCharacters(path));
        } catch (URISyntaxException e) {
            return null;
        }
    }

    private static String encodeUnwiseCharacters(String path) {
        return URLEncoder.encode(path, StandardCharsets.UTF_8);
    }

    protected abstract HttpProblem.Builder toProblem(E exception);

}
