package aor.paj.projetofinalbackend.dto;

import aor.paj.projetofinalbackend.utils.SkillType;

public class SkillDto {

    private Long Id;
    private String name;
    private SkillType type;

    public SkillDto() {
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SkillType getType() {
        return type;
    }

    public void setType(SkillType type) {
        this.type = type;
    }
}
