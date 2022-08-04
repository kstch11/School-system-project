package cz.cvut.kbss.ear.sis.dao;

import cz.cvut.kbss.ear.sis.model.SubjectReservation;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.Collection;
import java.util.List;

public abstract class BaseDao<T> implements GenericDao<T> {

    @PersistenceContext
    protected EntityManager em;

    protected final Class<T> type;

    public BaseDao(Class<T> type) {
        this.type = type;
    }


    @Override
    public T find(int id) {
        return em.find(type, id);
    }

    @Override
    public List<T> findAll() {
        try {
            return em.createQuery("SELECT e FROM " + type.getSimpleName() + " e", type).getResultList();
        } catch (RuntimeException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public void persist(T entity) {
        try {
            em.persist(entity);
        } catch (RuntimeException e) {
            throw new PersistenceException(e);
        }

    }

    @Override
    public void persist(Collection<T> entities) {
        if (entities.isEmpty()) {
            return;
        }
        try {
            entities.forEach(this::persist);
        } catch (RuntimeException e) {
            throw new PersistenceException(e);
        }

    }

    @Override
    public T update(T entity) {
        try {
            return em.merge(entity);
        } catch (RuntimeException e) {
            throw new PersistenceException(e);
        }

    }

    @Override
    public void remove(T entity) {
        try {
            final T toRemove = em.merge(entity);
            if (toRemove != null) {
                em.remove(toRemove);
            }
        } catch (RuntimeException e) {
            throw new PersistenceException(e);
        }

    }


    @Override
    public boolean exists(Integer id) {
        return id != null && em.find(type, id) != null;

    }

}
