package aor.paj.projetofinalbackend.bean;

import java.util.List;

public interface TagBean<D> {
    Long getUserIdFromToken(String token);
    List<D> getAllAttributes();
    void addAttributes(List<D> dtos, String token);

    void removeAttributes(List<D> dtos, String token);
}