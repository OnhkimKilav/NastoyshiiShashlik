package com.example.nastoyshiishashlik.api.model;

public class ApiError {
    private int error;
    private String message;
    private String field;

    public ApiError() {
    }

    public int getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public String getField() {
        return field;
    }
}
