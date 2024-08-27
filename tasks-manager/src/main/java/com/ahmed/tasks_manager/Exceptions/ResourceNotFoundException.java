package com.ahmed.tasks_manager.Exceptions;

public class ResourceNotFoundException extends RuntimeException {
    private String message;
    public ResourceNotFoundException(String projectNotFound) {
        this.message= projectNotFound;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
