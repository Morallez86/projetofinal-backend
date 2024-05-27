package aor.paj.projetofinalbackend.bean;

import aor.paj.projetofinalbackend.dao.InterestDao;
import aor.paj.projetofinalbackend.dao.UserDao;
import aor.paj.projetofinalbackend.dto.InterestDto;
import aor.paj.projetofinalbackend.entity.InterestEntity;
import aor.paj.projetofinalbackend.entity.UserEntity;
import aor.paj.projetofinalbackend.mapper.InterestMapper;

import aor.paj.projetofinalbackend.security.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class InterestBean implements TagBean<InterestDto> {

    @EJB
    private UserDao userDao;

    @EJB
    private InterestDao interestDao;

    @Override
    public Long getUserIdFromToken(String token) {
        Claims claims = JwtUtil.validateToken(token);
        return claims.get("id", Long.class);
    }

    @Override
    public List<InterestDto> getAllAttributes() {
        try {
            List<InterestEntity> entities = interestDao.findAll();
            return entities.stream()
                    .map(InterestMapper::toDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    @Transactional
    public void addAttributes(List<InterestDto> dtos, String token) {
        Long creatorId = getUserIdFromToken(token);
        UserEntity creator = userDao.findUserById(creatorId);
        if (creator == null) {
            throw new IllegalArgumentException("Invalid creator ID");
        }

        for (InterestDto dto : dtos) {
            InterestEntity existingEntity = interestDao.findByName(dto.getName());
            if (existingEntity == null) {
                InterestEntity entity = InterestMapper.toEntity(dto);
                entity.setCreator(creator);
                interestDao.merge(entity);
                creator.getInterests().add(entity);
            } else {
                creator.getInterests().add(existingEntity);
            }
        }
        userDao.merge(creator);
    }
}
