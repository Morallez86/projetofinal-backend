package aor.paj.projetofinalbackend.dao;

import aor.paj.projetofinalbackend.entity.ProjectEntity;
import aor.paj.projetofinalbackend.entity.ResourceEntity;
import jakarta.ejb.Stateless;
import jakarta.persistence.TypedQuery;

import java.util.HashSet;
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
}

