package aor.paj.projetofinalbackend.dto;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class EditTaskResult {

    private TaskDto taskDto;

    private int index;

    public EditTaskResult() {
    }

    public EditTaskResult(TaskDto taskDto, int index) {
        this.taskDto = taskDto;
        this.index = index;
    }

    @XmlElement
    public TaskDto getTaskDto() {
        return taskDto;
    }
    @XmlElement

    public int getIndex() {
        return index;
    }

    public void setTaskDto(TaskDto taskDto) {
        this.taskDto = taskDto;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
