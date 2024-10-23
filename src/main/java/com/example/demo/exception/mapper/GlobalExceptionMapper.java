package com.example.demo.exception.mapper;

import com.example.demo.exception.HttpProblem;
import jakarta.annotation.Priority;
import jakarta.persistence.EntityNotFoundException;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import lombok.extern.slf4j.Slf4j;

@Provider
@Priority(Priorities.USER)
@Slf4j
public class GlobalExceptionMapper extends ExceptionMapperBase<Exception> {

    @Override
    protected HttpProblem.Builder toProblem(Exception exception) {
        log.error("Problem:", exception);
        if (exception instanceof EntityNotFoundException) {
            return buildNotFoundProblem(exception);
        }
        return buildDefaultProblem(exception);
    }

    private static HttpProblem.Builder buildNotFoundProblem(Exception exception) {
        return HttpProblem.builder()
                .withStatus(Response.Status.NOT_FOUND)
                .withTitle("Not Found")
                .withDetail(exception.getMessage());
    }

    private static HttpProblem.Builder buildDefaultProblem(Exception exception) {
        return HttpProblem.builder()
                .withStatus(Response.Status.INTERNAL_SERVER_ERROR)
                .withTitle("Internal Server Error")
                .withDetail(exception.getMessage());
    }

}
