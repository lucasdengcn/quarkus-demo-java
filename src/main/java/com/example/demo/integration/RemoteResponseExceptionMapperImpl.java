package com.example.demo.integration;

import com.example.demo.exception.RemoteAPICallException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.rest.client.ext.ResponseExceptionMapper;
import org.jboss.resteasy.reactive.client.impl.ClientResponseImpl;

@Slf4j
@Provider
public class RemoteResponseExceptionMapperImpl implements ResponseExceptionMapper<RuntimeException> {

    @Override
    public RuntimeException toThrowable(Response response) {
        String entity = response.readEntity(String.class);
        log.error("Remote ResponseStatus: {}, {}", response.getStatus(), entity);
        String url = null;
        if (response instanceof ClientResponseImpl){
            ClientResponseImpl impl = (ClientResponseImpl) response;
        }
        return new RemoteAPICallException(response.getStatus(), entity, null);
    }

}
