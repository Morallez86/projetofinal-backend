package aor.paj.projetofinalbackend.entity;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * ConfigurationEntity represents a configuration setting in the application.
 *
 * @author João Morais
 * @author Ricardo Elias
 */
@Entity
@Table(name = "configuration")
public class ConfigurationEntity implements Serializable{

    private static final long serialVersionUID = 1L;

    /**
     * The unique identifier for the configuration.
     */
    @Id
    @Column(name = "id")
    private int id;

    /**
     * The timeout setting for the configuration.
     */
    @Column(name = "time_out")
    private int timeOut;

    /**
     * Default constructor for ConfigurationEntity.
     */
    public ConfigurationEntity() {
    }

    /**
     * Retrieves the configuration ID.
     *
     * @return the configuration ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Retrieves the timeout setting.
     *
     * @return the timeout setting.
     */
    public int getTimeOut() {
        return timeOut;
    }

    /**
     * Sets the timeout setting.
     *
     * @param timeOut the new timeout setting.
     */
    public void setTimeOut(int timeOut) {
        this.timeOut = timeOut;
    }

    /**
     * Returns a string representation of the ConfigurationEntity.
     *
     * @return a string representation of the ConfigurationEntity.
     */
    @Override
    public String toString() {
        return "ConfigurationEntity{" +
                "id=" + id +
                ", tokenExpirationTime=" + timeOut +
                '}';
    }
}
