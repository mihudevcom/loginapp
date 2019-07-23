package com.mihudev.loginapp.components.login;


/**
 * The Firebase login response received from the server
 */
public class FirebaseLoginResponse {

    /**
     * The status code of the Login response
     */
    private  Status status;
    /**
     * The error message of the lggin response
     */
    private final String errorMessage;

    /**
     * FirebaseLoginresponse constructor
     *
     * @param status       The status code
     * @param errorMessage The error message
     */
    public FirebaseLoginResponse(Status status, String errorMessage) {
        this.status = status;
        this.errorMessage = errorMessage;
    }

    /**
     * @return The status code of the Login response
     */
    public Status getStatus() {
        return status;
    }

    /**
     * @return The error message of the Login response
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Set the status value
     * @param status status code
     */
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
