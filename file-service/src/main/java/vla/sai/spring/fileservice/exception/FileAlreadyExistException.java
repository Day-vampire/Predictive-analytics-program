package vla.sai.spring.fileservice.exception;

public class FileAlreadyExistException extends RuntimeException {
    public FileAlreadyExistException(String message) {
        super(message);
    }
}

