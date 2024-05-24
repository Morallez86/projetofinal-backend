package aor.paj.projetofinalbackend.bean;

import aor.paj.projetofinalbackend.dao.SkillDao;
import aor.paj.projetofinalbackend.dao.UserDao;
import aor.paj.projetofinalbackend.dto.SkillDto;
import aor.paj.projetofinalbackend.entity.SkillEntity;
import aor.paj.projetofinalbackend.entity.UserEntity;
import aor.paj.projetofinalbackend.mapper.SkillMapper;
import aor.paj.projetofinalbackend.security.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class SkillBean {

    @EJB
    SkillDao skillDao;

    @EJB
    UserDao userDao;

    private Long getUserIdFromToken(String token) {
        Claims claims = JwtUtil.validateToken(token);
        return claims.get("id", Long.class);
    }

    public List<SkillDto> getAllSkills() {
        try {
            List<SkillEntity> skillEntities = skillDao.findAllSkills();
            return skillEntities.stream()
                    .map(SkillMapper::toDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            return null;
        }
    }

    public void addSkill(SkillDto skillDto, String token) {
        // Extract creator ID from the JWT token
        Claims claims = JwtUtil.validateToken(token);
        Long creatorId = claims.get("id", Long.class);

        // Fetch the UserEntity from the database
        UserEntity creator = userDao.findUserById(creatorId);
        if (creator == null) {
            throw new IllegalArgumentException("Invalid creator ID");
        }

        // Convert SkillDto to SkillEntity using SkillMapper
        SkillEntity skillEntity = SkillMapper.toEntity(skillDto);

        // Set the creator of the skill
        skillEntity.setCreator(creator);

        // Save the skillEntity to the database
        skillDao.merge(skillEntity);
    }
}
