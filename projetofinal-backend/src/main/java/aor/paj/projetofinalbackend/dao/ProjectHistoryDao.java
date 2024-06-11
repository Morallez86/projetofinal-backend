package aor.paj.projetofinalbackend.dao;

import aor.paj.projetofinalbackend.entity.ProjectHistoryEntity;
import aor.paj.projetofinalbackend.entity.ResourceEntity;
import jakarta.ejb.Stateless;

@Stateless
public class ProjectHistoryDao extends AbstractDao<ProjectHistoryEntity> {

    private static final long serialVersionUID = 1L;

    public ProjectHistoryDao() {
        super(ProjectHistoryEntity.class);
    }
}
