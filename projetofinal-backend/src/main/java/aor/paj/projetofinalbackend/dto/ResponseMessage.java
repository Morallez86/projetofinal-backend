package aor.paj.projetofinalbackend.dto;

/**
 * ResponseMessage represents a simple DTO class used for sending a response message.
 *
 * @author João Morais
 * @author Ricardo Elias
 */
public class ResponseMessage {
    private String message;

    /**
     * Constructs a ResponseMessage object with a specified message.
     *
     * @param message the message to be encapsulated.
     */
    public ResponseMessage(String message) {
        this.message = message;
    }

    /**
     * Sets the message for the ResponseMessage object.
     *
     * @param message the message to set.
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Retrieves the message encapsulated in the ResponseMessage object.
     *
     * @return the encapsulated message.
     */
    public String getMessage() {
        return message;
    }
}
