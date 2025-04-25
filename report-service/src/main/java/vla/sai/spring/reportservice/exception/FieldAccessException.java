package vla.sai.spring.reportservice.exception;

public class FieldAccessException extends RuntimeException {
    public FieldAccessException(String field, Throwable cause) {
        super("Failed access to field: %s".formatted(field), cause);
    }
}
