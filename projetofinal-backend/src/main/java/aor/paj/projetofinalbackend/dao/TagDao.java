package aor.paj.projetofinalbackend.dao;

import aor.paj.projetofinalbackend.entity.TagEntity;
import jakarta.ejb.Stateless;

import java.util.Collections;
import java.util.List;

@Stateless
public abstract class TagDao<T extends TagEntity> extends AbstractDao<T> {

    private static final long serialVersionUID = 1L;

    public TagDao(Class<T> clazz) {
        super(clazz);
    }

    public List<T> findAllAttributes(String namedQuery) {
        try {
            return em.createNamedQuery(namedQuery, clazz).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public T findByName(String namedQuery, String name) {
        try {
            return em.createNamedQuery(namedQuery, clazz)
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
}

