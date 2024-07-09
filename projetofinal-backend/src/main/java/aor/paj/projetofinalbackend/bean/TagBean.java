package aor.paj.projetofinalbackend.bean;

import java.util.List;

/**
 * Generic interface for handling tags (such as interests and skills).
 * Provides methods to get user ID from token, retrieve all attributes, add attributes,
 * and remove attributes.
 *
 * @param <D> the type of the DTO (Data Transfer Object) that represents the tag
 *
 * @see aor.paj.projetofinalbackend.bean.InterestBean
 * @see aor.paj.projetofinalbackend.bean.SkillBean
 *
 * @author João Morais
 * @author Ricardo Elias
 */
public interface TagBean<D> {

    /**
     * Retrieves the user ID from the provided JWT token.
     *
     * @param token the JWT token
     * @return the user ID extracted from the token
     */
    Long getUserIdFromToken(String token);

    /**
     * Retrieves all attributes (tags) from the system.
     *
     * @return a list of DTOs representing all attributes
     */
    List<D> getAllAttributes();

    /**
     * Adds a list of attributes (tags) to the user identified by the provided JWT token.
     *
     * @param dtos the list of DTOs to add
     * @param token the JWT token identifying the user
     */
    void addAttributes(List<D> dtos, String token);

    /**
     * Removes a list of attributes (tags) from the user identified by the provided JWT token.
     *
     * @param ids the list of IDs of the attributes to remove
     * @param token the JWT token identifying the user
     */
    void removeAttributes(List<Long> ids, String token);
}
