package aor.paj.projetofinalbackend.utils;

import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Utility class for converting between lists and sets using mapping functions.
 *
 * @author João Morais
 * @author Ricardo Elias
 */
public class ListConverter {

    /**
     * Converts a list of entities to a list of DTOs using a mapper function.
     *
     * @param entitySet The list of entities to convert.
     * @param mapperFunction The function to map each entity to a DTO.
     * @param <E> The type of entities in the input list.
     * @param <D> The type of DTOs in the output list.
     * @return A list of DTOs converted from the input list of entities.
     */
    public static <E, D> List<D> convertSetToList(List<E> entitySet, Function<E, D> mapperFunction) {
        return entitySet.stream()
                .map(mapperFunction)
                .collect(Collectors.toList());
    }

    /**
     * Converts a list of DTOs to a set of entities using a mapper function.
     *
     * @param dtoList The list of DTOs to convert.
     * @param mapperFunction The function to map each DTO to an entity.
     * @param <E> The type of entities in the output set.
     * @param <D> The type of DTOs in the input list.
     * @return A set of entities converted from the input list of DTOs.
     */
    public static <E, D> Set<D> convertListToSet(List<E> dtoList, Function<E, D> mapperFunction) {
        return dtoList.stream()
                .map(mapperFunction)
                .collect(Collectors.toSet());
    }
}
