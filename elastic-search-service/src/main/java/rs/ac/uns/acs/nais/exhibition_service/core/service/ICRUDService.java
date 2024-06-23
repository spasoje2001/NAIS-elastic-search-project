package rs.ac.uns.acs.nais.exhibition_service.core.service;

import java.util.NoSuchElementException;

public interface ICRUDService<T, ID> {

    T findById(ID id) throws NoSuchElementException;

    Iterable<T> findAll();

    Iterable<T> findAllByIds(Iterable<ID> ids);

    T save(T entity);

    void deleteById(ID id);

    void delete(T entity);
    
}
