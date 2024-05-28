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

@ApplicationScoped
public class SkillBean implements TagBean<SkillDto> {

    @EJB
    private UserDao userDao;

    @EJB
    private SkillDao skillDao;

    @Override
    public Long getUserIdFromToken(String token) {
        Claims claims = JwtUtil.validateToken(token);
        return claims.get("id", Long.class);
    }

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

    @Override
    @Transactional
    public void removeAttributes(List<SkillDto> dtos, String token) {
        Long userId = getUserIdFromToken(token);
        UserEntity user = userDao.findUserById(userId);
        if (user == null) {
            throw new IllegalArgumentException("Invalid user ID");
        }

        for (SkillDto dto : dtos) {
            SkillEntity skillEntity = skillDao.findByName(dto.getName());
            if (skillEntity != null) {
                user.getSkills().remove(skillEntity);
            }
        }
        userDao.merge(user);
    }
}
