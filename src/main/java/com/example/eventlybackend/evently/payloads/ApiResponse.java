package com.example.eventlybackend.evently.payloads;

/**
 * The ApiResponse class represents the response object for API calls.
 */
public class ApiResponse {
    private String message;
    private boolean success;
    private int id;

    /**
     * Constructs an ApiResponse object with the specified message and success status.
     *
     * @param message the response message
     * @param success the success status of the response
     */
    public ApiResponse(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    /**
     * Constructs an ApiResponse object with the specified message, success status, and ID.
     *
     * @param message the response message
     * @param success the success status of the response
     * @param id      the ID associated with the response
     */
    public ApiResponse(String message, boolean success, int id) {
        this.message = message;
        this.success = success;
        this.id = id;
    }

    /**
     * Returns the message of the ApiResponse.
     *
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the message of the ApiResponse.
     *
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Returns the success status of the ApiResponse.
     *
     * @return the success status
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Sets the success status of the ApiResponse.
     *
     * @param success the success status to set
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * Returns the ID associated with the ApiResponse.
     *
     * @return the ID
     */
    public int getId() {
        return id;
    }
}
