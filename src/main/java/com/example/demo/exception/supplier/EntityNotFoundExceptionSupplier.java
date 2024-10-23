package com.example.demo.exception.supplier;

import jakarta.persistence.EntityNotFoundException;

import java.util.function.Supplier;

public class EntityNotFoundExceptionSupplier<K> implements Supplier<EntityNotFoundException> {

    String message;

    public EntityNotFoundExceptionSupplier(Class<?> resource, K id) {
        this.message = resource.getSimpleName() + " Not Found: " + id;
    }

    @Override
    public EntityNotFoundException get() {
        return new EntityNotFoundException(message);
    }

}
