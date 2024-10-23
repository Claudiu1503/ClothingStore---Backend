package com.backend.clothingstore.errorHeandler;

public class ErrorResponse {
    private String error;

    // Constructor
    public ErrorResponse(String error) {
        this.error = error;
    }

    // Getter
    public String getError() {
        return error;
    }

    // Setter
    public void setError(String error) {
        this.error = error;
    }
}
