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

import java.util.List;
import java.util.stream.Collectors;

/**
 * Bean class that handles operations related to skills for users.
 * Implements the TagBean interface for managing skill attributes.
 *
 * @see TagBean
 * @see SkillDto
 * @see SkillEntity
 * @see UserEntity
 * @see SkillMapper
 * @see JwtUtil
 * @see SkillDao
 * @see UserDao
 * @see Claims
 *
 * @author João Morais
 * @author Ricardo Elias
 */
@ApplicationScoped
public class SkillBean implements TagBean<SkillDto> {

    @EJB
    UserDao userDao;

    @EJB
    SkillDao skillDao;

    /**
     * Retrieves the user ID from the provided JWT token.
     *
     * @param token the JWT token
     * @return the user ID extracted from the token
     */
    @Override
    public Long getUserIdFromToken(String token) {
        Claims claims = JwtUtil.validateToken(token);
        return claims.get("id", Long.class);
    }

    /**
     * Retrieves all skill attributes from the system.
     *
     * @return a list of SkillDto representing all skill attributes
     */
    @Override
    public List<SkillDto> getAllAttributes() {
        try {
            List<SkillEntity> entities = skillDao.findAll();
            return entities.stream()
                    .map(SkillMapper::toDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Adds a list of skill attributes to the user identified by the provided JWT token.
     *
     * @param dtos the list of SkillDto to add
     * @param token the JWT token identifying the user
     */
    @Override
    @Transactional
    public void addAttributes(List<SkillDto> dtos, String token) {
        Long creatorId = getUserIdFromToken(token);
        UserEntity creator = userDao.findUserById(creatorId);
        if (creator == null) {
            throw new IllegalArgumentException("Invalid creator ID");
        }

        for (SkillDto dto : dtos) {
            SkillEntity existingEntity = skillDao.findByName(dto.getName());
            if (existingEntity == null) {
                SkillEntity entity = SkillMapper.toEntity(dto);
                entity.setCreator(creator);
                skillDao.merge(entity);
                creator.getSkills().add(entity);
            } else {
                creator.getSkills().add(existingEntity);
            }
        }
        userDao.merge(creator);
    }

    /**
     * Removes a list of skill attributes from the user identified by the provided JWT token.
     *
     * @param skillIds the list of IDs of the skill attributes to remove
     * @param token the JWT token identifying the user
     */
    @Override
    @Transactional
    public void removeAttributes(List<Long> skillIds, String token) {
        Long userId = getUserIdFromToken(token);
        UserEntity user = userDao.findUserById(userId);
        if (user == null) {
            throw new IllegalArgumentException("Invalid user ID");
        }
        for (Long skillId : skillIds) {
            SkillEntity skillEntity = skillDao.findById(skillId);
            if (skillEntity != null) {
                user.getSkills().remove(skillEntity);
            }
        }
        userDao.merge(user);
    }
}
