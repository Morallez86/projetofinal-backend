package aor.paj.projetofinalbackend.dto;

import aor.paj.projetofinalbackend.utils.CustomLocalDateTimeDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.xml.bind.annotation.XmlElement;

import java.time.LocalDateTime;

/**
 * TaskEndDateDto is a Data Transfer Object (DTO) class representing the planned starting date of a task.
 *
 * @author João Morais
 * @author Ricardo Elias
 */
public class TaskEndDateDto {

    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime plannedStartingDate;

    /**
     * Default constructor for TaskEndDateDto.
     */
    public TaskEndDateDto() {
    }

    /**
     * Constructor for TaskEndDateDto that initializes the planned starting date.
     *
     * @param plannedStartingDate the planned starting date of the task.
     */
    public TaskEndDateDto(LocalDateTime plannedStartingDate) {
        this.plannedStartingDate = plannedStartingDate;
    }

    /**
     * Retrieves the planned starting date of the task.
     *
     * @return the planned starting date of the task.
     */
    @XmlElement
    public LocalDateTime getPlannedStartingDate() {
        return plannedStartingDate;
    }

    /**
     * Sets the planned starting date of the task.
     *
     * @param plannedStartingDate the planned starting date to set.
     */
    public void setPlannedStartingDate(LocalDateTime plannedStartingDate) {
        this.plannedStartingDate = plannedStartingDate;
    }
}
