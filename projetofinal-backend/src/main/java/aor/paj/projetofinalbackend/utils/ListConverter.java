package aor.paj.projetofinalbackend.utils;

import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ListConverter {
    public static <E, D> List<D> convertSetToList(List<E> entitySet, Function<E, D> mapperFunction) {
        return entitySet.stream()
                .map(mapperFunction)
                .collect(Collectors.toList());
    }

    public static <E, D> Set<D> convertListToSet(List<E> dtoList, Function<E, D> mapperFunction) {
        return dtoList.stream()
                .map(mapperFunction)
                .collect(Collectors.toSet());
    }
}
