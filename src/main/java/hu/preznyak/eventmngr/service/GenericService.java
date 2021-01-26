package hu.preznyak.eventmngr.service;

import java.util.List;

public abstract class GenericService<T> {

    public abstract T save(T entity);
    public abstract void delete(T entity);
    public abstract T findById(Integer id);
    public abstract T update(T entity);
    public abstract Iterable<T> findAll();
}
