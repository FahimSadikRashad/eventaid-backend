package com.example.eventlybackend.evently.payloads;

public class ApiResponse {
    private String message;
    private boolean success;
    private int  id;

    public ApiResponse(String message, boolean success) {
        this.message = message;
        this.success = success;
    }
    public ApiResponse(String message, boolean success,int id) {
        this.message = message;
        this.success = success;
        this.id=id;
    }
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
