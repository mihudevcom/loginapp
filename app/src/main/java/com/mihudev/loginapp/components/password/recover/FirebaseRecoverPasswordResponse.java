package com.mihudev.loginapp.components.password.recover;

/**
 * The Firebase recover password response received from the server
 */
public class FirebaseRecoverPasswordResponse {

    /**
     * The error message of the recover password response
     */
    private final String errorMessage;
    /**
     * The status code of the recover password response
     */
    private Status status;

    /**
     * Constructor
     *
     * @param status       The status code
     * @param errorMessage The error message
     */
    public FirebaseRecoverPasswordResponse(Status status, String errorMessage) {
        this.status = status;
        this.errorMessage = errorMessage;
    }

    /**
     * @return The status of the recover password response
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Set the response status
     *
     * @param status status code
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * @return The error message of the recover password response
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * An enum with the possible types of status codes
     */
    enum Status {
        EMPTY,
        SUCCESS,
        LOADING,
        CANCELED,
        ERROR
    }


}
