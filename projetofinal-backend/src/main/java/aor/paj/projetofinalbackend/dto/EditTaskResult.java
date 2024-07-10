package aor.paj.projetofinalbackend.dto;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

/**
 * Data Transfer Object (DTO) representing the result of editing a task.
 * This class is used to transfer the editing of tasks between different layers of the application.
 *
 * @author João Morais
 * @author Ricardo Elias
 */
@XmlRootElement
public class EditTaskResult {

    private TaskDto taskDto;
    private int index;

    /**
     * Default constructor.
     */
    public EditTaskResult() {
    }

    /**
     * Constructor with parameters.
     *
     * @param taskDto the task DTO.
     * @param index   the index of the task in the list.
     */
    public EditTaskResult(TaskDto taskDto, int index) {
        this.taskDto = taskDto;
        this.index = index;
    }

    /**
     * Gets the task DTO.
     *
     * @return the task DTO.
     */
    @XmlElement
    public TaskDto getTaskDto() {
        return taskDto;
    }

    /**
     * Gets the index of the task in the list.
     *
     * @return the index of the task in the list.
     */
    @XmlElement
    public int getIndex() {
        return index;
    }

    /**
     * Sets the task DTO.
     *
     * @param taskDto the task DTO to set.
     */
    public void setTaskDto(TaskDto taskDto) {
        this.taskDto = taskDto;
    }

    /**
     * Sets the index of the task in the list.
     *
     * @param index the index to set.
     */
    public void setIndex(int index) {
        this.index = index;
    }
}
