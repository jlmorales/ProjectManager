package com.projectmanager.Exceptions;

public class ProjectNotFoundExceptionResponse {
    private String message;

    public ProjectNotFoundExceptionResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
