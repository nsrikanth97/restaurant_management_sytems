package edu.neu.csye7374.dto;

import lombok.Data;

@Data
public class ResponseEntity<E> {

    private String message;

    private E data;

    private ReturnType responseStatus;

    StackTraceElement[] stackTraceElements;

    public ResponseEntity() {
    }

    public ResponseEntity(String message, E data, ReturnType responseStatus) {
        this.message = message;
        this.data = data;
        this.responseStatus = responseStatus;
    }
}
