package com.mihudev.loginapp.components.password.change;

/**
 * The change password request response received from the server
 */
public class FirebaseChangePasswordResponse {

    /**
     * The error message for the response
     */
    private final String errorMessage;
    /**
     * The status code of the response
     */
    private Status status;

    /**
     * Constructor
     *
     * @param status       The status code of the response
     * @param errorMessage The error message for the request
     */
    public FirebaseChangePasswordResponse(Status status, String errorMessage) {
        this.status = status;
        this.errorMessage = errorMessage;
    }

    /**
     * @return The status code of the response
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Set the status value
     *
     * @param status status code
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * @return the response error message
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * /**
     * * An enum with the possible types of status codes
     */
    enum Status {
        EMPTY,
        SUCCESS,
        LOADING,
        CANCELED,
        ERROR
    }
}
