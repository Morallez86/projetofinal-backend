package aor.paj.projetofinalbackend.dto;

import aor.paj.projetofinalbackend.utils.CustomLocalDateTimeDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.xml.bind.annotation.XmlElement;

import java.time.LocalDateTime;

public class TaskEndDateDto {

    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime plannedStartingDate;

    public TaskEndDateDto() {
    }

    @XmlElement
    public LocalDateTime getPlannedStartingDate() {
        return plannedStartingDate;
    }

    public void setPlannedEndingDate(LocalDateTime plannedStartingDate) {
        this.plannedStartingDate = plannedStartingDate;
    }
}
