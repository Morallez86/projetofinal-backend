package aor.paj.projetofinalbackend.pojo;

/**
 * POJO class representing a response message with a single string message.
 *
 * @author João Morais
 * @author Ricardo Elias
 */
public class ResponseMessage {
    private String message;

    /**
     * Constructs a new ResponseMessage object with the specified message.
     *
     * @param message The message string to be encapsulated in the ResponseMessage.
     */
    public ResponseMessage(String message) {
        this.message = message;
    }

    /**
     * Sets the message for the ResponseMessage object.
     *
     * @param message The message string to set.
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Retrieves the message encapsulated in the ResponseMessage object.
     *
     * @return The message string.
     */
    public String getMessage() {
        return message;
    }
}
