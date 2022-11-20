package com.example.backend221.Exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class FileStoreException extends RuntimeException {
    public FileStoreException(String message) {
        super(message);
    }
    public FileStoreException(String message, Throwable cause) {
        super(message, cause);
    }
}
