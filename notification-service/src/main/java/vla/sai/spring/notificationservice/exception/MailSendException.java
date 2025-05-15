package vla.sai.spring.notificationservice.exception;


public class MailSendException extends RuntimeException {

    public MailSendException(String message) {
        super(message);
    }
}
