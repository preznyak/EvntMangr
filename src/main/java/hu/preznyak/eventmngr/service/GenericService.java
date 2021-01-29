package hu.preznyak.eventmngr.service;

import hu.preznyak.eventmngr.exception.EntityNotFoundException;

public abstract class GenericService<T> {

    public abstract T save(T entity);
    public abstract void delete(T entity);
    public abstract T findById(Integer id);
    public abstract T update(T entity) throws EntityNotFoundException;
    public abstract Iterable<T> findAll();
}
