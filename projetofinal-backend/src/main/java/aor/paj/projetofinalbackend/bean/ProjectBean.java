package aor.paj.projetofinalbackend.bean;

import aor.paj.projetofinalbackend.dao.UserDao;
import aor.paj.projetofinalbackend.dto.ProjectDto;
import aor.paj.projetofinalbackend.entity.UserEntity;
import aor.paj.projetofinalbackend.security.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ProjectBean {

    @EJB
    private UserDao userDao;

    @EJB
    private ServiceBean serviceBean;


    public void addProject (ProjectDto projectDto, String token) {
        Long userId = serviceBean.getUserIdFromToken(token);
        UserEntity user =  userDao.findUserById(userId);

        if (user == null) {
            throw new IllegalArgumentException("Invalid creator ID");
        }


    }
}
