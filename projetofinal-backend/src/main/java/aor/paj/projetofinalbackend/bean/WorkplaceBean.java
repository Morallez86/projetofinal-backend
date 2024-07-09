package aor.paj.projetofinalbackend.bean;

import aor.paj.projetofinalbackend.dao.ProjectDao;
import aor.paj.projetofinalbackend.dao.UserProjectDao;
import aor.paj.projetofinalbackend.dao.WorkplaceDao;
import aor.paj.projetofinalbackend.dto.WorkplaceDto;
import aor.paj.projetofinalbackend.entity.WorkplaceEntity;
import aor.paj.projetofinalbackend.mapper.WorkplaceMapper;
import aor.paj.projetofinalbackend.pojo.WorkplaceProjectCount;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;

import java.util.ArrayList;
import java.util.List;

/**
 * Stateless bean for managing workplace-related operations.
 * @see WorkplaceDao
 *
 * @author João Morais
 * @author Ricardo Elias
 */
@Stateless
public class WorkplaceBean {

    @EJB
    WorkplaceDao workplaceDao;

    /**
     * Creates a new workplace entity with the specified name if it does not already exist.
     *
     * @param name The name of the workplace to create.
     * @return The created WorkplaceEntity, or null if a workplace with the same name already exists.
     */
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

    /**
     * Retrieves all workplaces and maps them to WorkplaceDto objects.
     *
     * @return A list of WorkplaceDto objects representing all workplaces.
     */
    public List<WorkplaceDto> getAllWorkplaces() {
        List<WorkplaceEntity> workplaces = workplaceDao.findAllWorkplaces();
        List<WorkplaceDto> workplaceDtos = new ArrayList<>();
        for (WorkplaceEntity workplace : workplaces) {
            workplaceDtos.add(WorkplaceMapper.toDto(workplace));
        }
        return workplaceDtos;
    }

    /**
     * Retrieves the count of projects per workplace along with the percentage of each workplace's contribution.
     *
     * @return A list of WorkplaceProjectCount objects containing workplace names, project counts, and percentages.
     */
    public List<WorkplaceProjectCount> getProjectCountPerWorkplace() {
        List<Object[]> results = workplaceDao.getProjectCountPerWorkplace();
        List<WorkplaceProjectCount> workplaceProjectCount = new ArrayList<>();

        long totalProjects = results.stream().mapToLong(result -> (long) result[1]).sum();

        for (Object[] result : results) {
            String workplaceName = (String) result[0];
            long projectCount = (long) result[1];
            double percentage = ((double) projectCount / totalProjects) * 100;
            workplaceProjectCount.add(new WorkplaceProjectCount(workplaceName, projectCount, percentage));
        }
        return workplaceProjectCount;
    }
}
