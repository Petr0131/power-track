package com.example.power_track_backend.service.recommendation.specification;

public interface Specification<T> {
    boolean isSatisfiedBy(T entity);
}
