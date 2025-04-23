package vla.sai.spring.analyticsservice.exception;

public class FileNotSavedException extends RuntimeException {
    public FileNotSavedException(String message) {super(message);}
    public FileNotSavedException(String message, Throwable cause) {super(message, cause);}
}
