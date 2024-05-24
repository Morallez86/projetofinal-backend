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
import jakarta.transaction.Transactional;
import org.hibernate.Hibernate;

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

    @Transactional
    public void addSkills(List<SkillDto> skillDtos, String token) {
        // Extract creator ID from the JWT token
        Claims claims = JwtUtil.validateToken(token);
        Long creatorId = claims.get("id", Long.class);

        // Fetch the UserEntity from the database
        UserEntity creator = userDao.findUserById(creatorId);
        if (creator == null) {
            throw new IllegalArgumentException("Invalid creator ID");
        }

        // Initialize the skills collection to avoid LazyInitializationException
        Hibernate.initialize(creator.getSkills());

        // Convert each SkillDto to SkillEntity, set the creator, and save
        for (SkillDto skillDto : skillDtos) {
            SkillEntity existingSkill = skillDao.findByName(skillDto.getName());
            if (existingSkill == null) {
                SkillEntity skillEntity = SkillMapper.toEntity(skillDto);
                skillEntity.setCreator(creator);
                skillDao.merge(skillEntity);
                creator.getSkills().add(skillEntity);
            } else {
                creator.getSkills().add(existingSkill);
            }
        }
        userDao.merge(creator);
    }
}
