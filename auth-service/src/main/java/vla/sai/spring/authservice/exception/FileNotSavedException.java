package vla.sai.spring.authservice.exception;

public class FileNotSavedException extends RuntimeException {
    public FileNotSavedException(String message) {super(message);}
    public FileNotSavedException(String message, Throwable cause) {super(message, cause);}
}
