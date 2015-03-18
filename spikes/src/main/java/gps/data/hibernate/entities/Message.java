package gps.data.hibernate.entities;

/**
 * Created by Sony on 18/03/2015.
 */
public class Message {
    private Long idMessage;
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getIdMessage() {
        return idMessage;
    }

    public void setIdMessage(Long idMessage) {
        this.idMessage = idMessage;
    }
}
