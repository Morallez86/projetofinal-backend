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

/**
 * Bean responsible for handling operations related to interests, such as fetching all interests,
 * adding interests to a user, and removing interests from a user.
 *
 * @see InterestDao
 * @see UserDao
 * @see InterestDto
 * @see InterestEntity
 * @see UserEntity
 * @see InterestMapper
 * @see JwtUtil
 * @see Claims
 *
 * @author João Morais
 * @author Ricardo Elias
 */
@ApplicationScoped
public class InterestBean implements TagBean<InterestDto> {

    @EJB
    UserDao userDao;

    @EJB
    InterestDao interestDao;

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
     * Retrieves all interests from the database.
     *
     * @return a list of InterestDto representing all interests
     */
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

    /**
     * Adds a list of interests to the user identified by the provided JWT token.
     *
     * @param dtos the list of InterestDto to add
     * @param token the JWT token identifying the user
     */
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

    /**
     * Removes a list of interests from the user identified by the provided JWT token.
     *
     * @param interestIds the list of interest IDs to remove
     * @param token the JWT token identifying the user
     */
    @Override
    @Transactional
    public void removeAttributes(List<Long> interestIds, String token) {
        Long userId = getUserIdFromToken(token);
        UserEntity user = userDao.findUserById(userId);
        if (user == null) {
            throw new IllegalArgumentException("Invalid user ID");
        }

        for (Long interestId : interestIds) {
            InterestEntity interestEntity = interestDao.findById(interestId);
            if (interestEntity != null) {
                user.getInterests().remove(interestEntity);
            }
        }
        userDao.merge(user);
    }
}
