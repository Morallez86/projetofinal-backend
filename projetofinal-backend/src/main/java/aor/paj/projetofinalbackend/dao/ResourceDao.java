package aor.paj.projetofinalbackend.dao;

import aor.paj.projetofinalbackend.entity.ComponentEntity;
import aor.paj.projetofinalbackend.entity.ProjectEntity;
import aor.paj.projetofinalbackend.entity.ResourceEntity;
import jakarta.ejb.Stateless;
import jakarta.persistence.TypedQuery;

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
        TypedQuery<ResourceEntity> query = em.createNamedQuery("ResourceEntity.findById", ResourceEntity.class);
        query.setParameter("id", id);
        return query.getSingleResult();
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
}

