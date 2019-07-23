package com.mihudev.loginapp.components.register;

/**
 * The Firebase register response received from the server
 */
public class FirebaseRegisterResponse {

    /**
     * The status code of the register response
     */
    private Status status;
    /**
     * The error message of the register response
     */
    private final String errorMessage;

    /**
     * FirebaseregisterResponse constructor
     *
     * @param status       The status code
     * @param errorMessage The error message
     */
    public FirebaseRegisterResponse(Status status, String errorMessage) {
        this.status = status;
        this.errorMessage = errorMessage;
    }

    /**
     * @return The status code of the Register response
     */
    public Status getStatus() {
        return status;
    }

    /**
     * @return The error message of the Register response
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    public void setStatus(Status status) {
        this.status = status;
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
