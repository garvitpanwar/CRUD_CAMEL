package com.garvit.provider.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResponseMessage {
    @JsonProperty("message")
    private String message;

    public ResponseMessage() {
    }

    public ResponseMessage(String message) {
        this.message = message;
    }

    // Getters and setters...
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}