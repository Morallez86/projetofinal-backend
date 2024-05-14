package aor.paj.projetofinalbackend.entity;


import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "configuration")
public class ConfigurationEntity implements Serializable{

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "time_out")
    private int timeOut;

    public ConfigurationEntity() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(int timeOut) {
        this.timeOut = timeOut;
    }

    @Override
    public String toString() {
        return "ConfigurationEntity{" +
                "id=" + id +
                ", tokenExpirationTime=" + timeOut +
                '}';
    }
}


