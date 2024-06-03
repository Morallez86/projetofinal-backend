package aor.paj.projetofinalbackend.dao;

import aor.paj.projetofinalbackend.entity.UserEntity;
import aor.paj.projetofinalbackend.entity.UserProjectEntity;
import jakarta.ejb.Stateless;

@Stateless
public class UserProjectDao extends AbstractDao<UserProjectEntity> {

    private static final long serialVersionUID = 1L;

    public UserProjectDao() {
        super(UserProjectEntity.class);
    }
}