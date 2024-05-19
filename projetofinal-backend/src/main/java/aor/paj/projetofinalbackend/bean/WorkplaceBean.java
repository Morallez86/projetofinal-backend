package aor.paj.projetofinalbackend.bean;

import aor.paj.projetofinalbackend.dao.WorkplaceDao;
import aor.paj.projetofinalbackend.dto.WorkplaceDto;
import aor.paj.projetofinalbackend.entity.WorkplaceEntity;
import aor.paj.projetofinalbackend.mapper.WorkplaceMapper;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;

import java.util.ArrayList;
import java.util.List;

@Stateless
public class WorkplaceBean {

    @EJB
    WorkplaceDao workplaceDao;

    public WorkplaceEntity createWorkplace(String name) {
        // Check if a workplace with the same name already exists
        WorkplaceEntity existingWorkplace = workplaceDao.findWorkplaceByName(name);
        if (existingWorkplace != null) {
            // Workplace with the same name already exists
            return null;
        } else {
            // Create a new workplace
            WorkplaceEntity newWorkplace = new WorkplaceEntity();
            newWorkplace.setName(name);
            workplaceDao.persist(newWorkplace);
            return newWorkplace;
        }
    }

    public List<WorkplaceDto> getAllWorkplaces() {
        List<WorkplaceEntity> workplaces = workplaceDao.findAllWorkplaces();
        List<WorkplaceDto> workplaceDtos = new ArrayList<>();
        for (WorkplaceEntity workplace : workplaces) {
            workplaceDtos.add(WorkplaceMapper.toDto(workplace));
        }
        return workplaceDtos;
    }
}
