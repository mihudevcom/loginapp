package com.mihudev.loginapp.components.login;

import android.content.Context;
import android.util.Patterns;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.mihudev.loginapp.R;

/**
 * Login manager responsible of loging in the user and providing status responses and messages
 * from the Login response
 */
public class FirebaseLoginManager extends LiveData<FirebaseLoginResponse> {

    /**
     * Used for loging in the user and getting the response from the server
     */
    private final FirebaseAuth firebaseAuth;

    /**
     * The main constructor for FirebaseLoginManager
     */
    public FirebaseLoginManager() {
        super();
        firebaseAuth = FirebaseAuth.getInstance();
    }

    /**
     * Tries to login the user
     *
     * @param email    The user email
     * @param password The user password
     */
    public void signInWithEmailAndPassword(final Context context, String email, String password) {

        if (email == null || !isEmailValid(email)) {
            setValue(new FirebaseLoginResponse(FirebaseLoginResponse.Status.ERROR, context.getString(R.string.error_invalid_email)));
        } else if (password == null || !isPasswordLengthGreaterThan5(password)) {
            setValue(new FirebaseLoginResponse(FirebaseLoginResponse.Status.ERROR, context.getString(R.string.error_password_too_short)));
        } else {
            setValue(new FirebaseLoginResponse(FirebaseLoginResponse.Status.LOADING, null));
            firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        setValue(new FirebaseLoginResponse(FirebaseLoginResponse.Status.SUCCESS, null));
                    }
                }
            }).addOnCanceledListener(new OnCanceledListener() {
                @Override
                public void onCanceled() {
                    setValue(new FirebaseLoginResponse(FirebaseLoginResponse.Status.CANCELED, context.getString(R.string.error_login_cancelled)));
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    if (e instanceof FirebaseNetworkException) {
                        setValue(new FirebaseLoginResponse(FirebaseLoginResponse.Status.ERROR, context.getString(R.string.error_sign_in_network)));
                    } else if (e instanceof FirebaseAuthInvalidCredentialsException) {
                        setValue(new FirebaseLoginResponse(FirebaseLoginResponse.Status.ERROR, context.getString(R.string.error_invalid_password)));
                    } else if (e instanceof FirebaseAuthInvalidUserException) {
                        String errorCode =
                                ((FirebaseAuthInvalidUserException) e).getErrorCode();

                        if (errorCode.equals("ERROR_USER_NOT_FOUND")) {
                            setValue(new FirebaseLoginResponse(FirebaseLoginResponse.Status.ERROR, context.getString(R.string.error_no_matching_account)));
                        } else if (errorCode.equals("ERROR_USER_DISABLED")) {
                            setValue(new FirebaseLoginResponse(FirebaseLoginResponse.Status.ERROR, context.getString(R.string.error_user_disabled)));
                        }
                    } else {
                        setValue(new FirebaseLoginResponse(FirebaseLoginResponse.Status.ERROR, context.getString(R.string.error_unknown)));
                    }
                }
            });
        }

    }

    /**
     * Checks if the email is valid
     *
     * @param email Email to be checked
     * @return true if the email is a valid one
     */
    private boolean isEmailValid(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    /**
     * Checks if the password is at least 6 characters long
     *
     * @param password The password to be checked
     * @return true if the password is valid
     */
    private boolean isPasswordLengthGreaterThan5(String password) {
        return password.length() > 5;
    }
}
