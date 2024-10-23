package com.example.demo.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import io.quarkus.jackson.ObjectMapperCustomizer;
import jakarta.inject.Singleton;

@Singleton
public final class JacksonProblemModuleRegistrar implements ObjectMapperCustomizer {

    @Override
    public void customize(ObjectMapper mapper) {
        SimpleModule module = new SimpleModule("RFC7807 problem");
        module.addSerializer(HttpProblem.class, new JacksonProblemSerializer());
        mapper.registerModule(module);
    }

}