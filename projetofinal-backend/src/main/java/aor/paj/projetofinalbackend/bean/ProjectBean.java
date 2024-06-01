package aor.paj.projetofinalbackend.bean;

import aor.paj.projetofinalbackend.dao.ProjectDao;
import aor.paj.projetofinalbackend.dao.UserDao;
import aor.paj.projetofinalbackend.dto.ProjectDto;
import aor.paj.projetofinalbackend.entity.ProjectEntity;
import aor.paj.projetofinalbackend.entity.UserEntity;
import aor.paj.projetofinalbackend.mapper.ProjectMapper;
import aor.paj.projetofinalbackend.security.JwtUtil;
import aor.paj.projetofinalbackend.utils.ProjectStatus;
import io.jsonwebtoken.Claims;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ProjectBean {

    @EJB
    private UserDao userDao;

    @EJB
    private ServiceBean serviceBean;

    @EJB
    private ProjectDao projectDao;


    public ProjectEntity addProject (ProjectDto projectDto, String token) {
        Long userId = serviceBean.getUserIdFromToken(token);
        UserEntity user =  userDao.findUserById(userId);

        if (user == null) {
            throw new IllegalArgumentException("Invalid creator ID");
        }

        ProjectEntity projectEntity = ProjectMapper.toEntity(projectDto);
        projectEntity.setOwner(user);
        for (ProjectStatus status : ProjectStatus.values()) {
            if (status.getValue() == projectDto.getStatus()) {
                projectEntity.setStatus(status);
                break;
            }
        }

        projectDao.persist(projectEntity);
        return projectEntity;
    }
}
