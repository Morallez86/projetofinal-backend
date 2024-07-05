package aor.paj.projetofinalbackend.dao;

import aor.paj.projetofinalbackend.entity.ComponentEntity;
import aor.paj.projetofinalbackend.entity.ProjectEntity;
import aor.paj.projetofinalbackend.entity.ResourceEntity;
import jakarta.ejb.Stateless;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Stateless
public class ResourceDao extends AbstractDao<ResourceEntity> {

    private static final long serialVersionUID = 1L;

    public ResourceDao() {
        super(ResourceEntity.class);
    }

    public ResourceEntity findById(Long id) {
        try {
            TypedQuery<ResourceEntity> query = em.createNamedQuery("ResourceEntity.findById", ResourceEntity.class);
            query.setParameter("id", id);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public ResourceEntity findByName(String name) {
        try {
            TypedQuery<ResourceEntity> query = em.createNamedQuery("Resource.findByName", ResourceEntity.class);
            query.setParameter("name", name);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null; // Returns null if no component is found with the specified name
        }
    }

    public Set<ProjectEntity> findProjectsByResourceId(Long resourceId) {
        TypedQuery<ProjectEntity> query = em.createNamedQuery("ResourceEntity.findProjectsByResourceId", ProjectEntity.class);
        query.setParameter("id", resourceId);
        return new HashSet<>(query.getResultList());
    }

    public List<ResourceEntity> findAllOrderedByName(int page, int limit) {
        return em.createNamedQuery("Resource.findAllOrderedByName", ResourceEntity.class)
                .setFirstResult((page - 1) * limit)
                .setMaxResults(limit)
                .getResultList();
    }

    public long getTotalResourcesCount() {
        TypedQuery<Long> query = em.createNamedQuery("Resource.getTotalResourcesCount", Long.class);
        return query.getSingleResult();
    }

    public List<ResourceEntity> findByKeywordOrderedByName(int page, int limit,String keyword) {
        return em.createNamedQuery("Resource.findByKeywordOrderedByName", ResourceEntity.class)
                .setParameter("keyword", keyword)
                .setFirstResult((page - 1) * limit)
                .setMaxResults(limit)
                .getResultList();
    }

    public long countByKeyword(String keyword) {
        return em.createNamedQuery("Resource.countByKeyword", Long.class)
                .setParameter("keyword", keyword)
                .getSingleResult();
    }

    public List<ResourceEntity> findResourcesExpiringWithinWeek(LocalDateTime now, LocalDateTime oneWeekFromNow) {
        try{
            TypedQuery<ResourceEntity> query = em.createNamedQuery("ResourceEntity.findResourcesExpiringWithinWeek", ResourceEntity.class);
            query.setParameter("now", now);
            query.setParameter("oneWeekFromNow", oneWeekFromNow);

            return query.getResultList();
        } catch (NoResultException e) {
            return Collections.emptyList();
        }
    }

    public List<ResourceEntity> findUnusedResources() {
        try {
            TypedQuery<ResourceEntity> query = em.createNamedQuery("ResourceEntity.findUnusedResources", ResourceEntity.class);
            return query.getResultList();
        } catch (NoResultException e) {
            return Collections.emptyList();
        }
    }
}

