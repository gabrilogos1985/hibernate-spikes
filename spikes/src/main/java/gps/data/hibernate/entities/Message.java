package gps.data.hibernate.entities;

/**
 * Created by Sony on 18/03/2015.
 */
public class Message {
    private Long idMessage;
    private String message;
    private String text;
    private Message nextMessage;

    public Message(String text) {
        this.text = text;
    }

    public Message() {
    }

    public Message getNextMessage() {
        return nextMessage;
    }

    public void setNextMessage(Message nextMessage) {
        this.nextMessage = nextMessage;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

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
