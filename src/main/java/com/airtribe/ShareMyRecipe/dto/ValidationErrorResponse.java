package com.airtribe.ShareMyRecipe.dto;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class ValidationErrorResponse {
    private String message;
    private LocalDateTime timeStamp;
    private Map<String, String> fieldErrors = new HashMap<>();

    public void addFieldErrors(String field, String errorMessage) {
        this.fieldErrors.put(field, errorMessage);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Map<String, String> getFieldErrors() {
        return fieldErrors;
    }

    public void setFieldErrors(Map<String, String> fieldErrors) {
        this.fieldErrors = fieldErrors;
    }
}
