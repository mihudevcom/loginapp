package com.mihudev.loginapp.components.register;

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
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.mihudev.loginapp.R;

/**
 * Register manager responsible for registering the user and providing status responses and messages
 * from the Register response
 */
public class FirebaseRegisterManager extends LiveData<FirebaseRegisterResponse> {

    /**
     * Used to access the createUserWithEmailAndPassword function
     */
    private final FirebaseAuth firebaseAuth;

    /**
     * Constructor
     */
    public FirebaseRegisterManager() {
        super();
        firebaseAuth = FirebaseAuth.getInstance();
    }

    /**
     * Tries to register the user
     *
     * @param email    user email
     * @param password user password
     */
    public void registerWithEmailAndPassword(final Context context, String email, String password) {
        setValue(new FirebaseRegisterResponse(FirebaseRegisterResponse.Status.LOADING, ""));
        if (email == null || !isEmailValid(email)) {
            setValue(new FirebaseRegisterResponse(FirebaseRegisterResponse.Status.ERROR, context.getString(R.string.error_invalid_email)));
        } else if (password == null || !isPasswordLengthGreaterThan5(password)) {
            setValue(new FirebaseRegisterResponse(FirebaseRegisterResponse.Status.ERROR, context.getString(R.string.error_password_too_short)));
        } else {
            setValue(new FirebaseRegisterResponse(FirebaseRegisterResponse.Status.LOADING, null));
            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        setValue(new FirebaseRegisterResponse(FirebaseRegisterResponse.Status.SUCCESS, null));
                    }
                }
            }).addOnCanceledListener(new OnCanceledListener() {
                @Override
                public void onCanceled() {
                    setValue(new FirebaseRegisterResponse(FirebaseRegisterResponse.Status.SUCCESS, context.getString(R.string.error_sign_up_cacelled)));
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    if (e instanceof FirebaseAuthUserCollisionException) {
                        setValue(new FirebaseRegisterResponse(FirebaseRegisterResponse.Status.ERROR, context.getString(R.string.error_sign_up_email_already_exists)));
                    } else if (e instanceof FirebaseNetworkException) {
                        setValue(new FirebaseRegisterResponse(FirebaseRegisterResponse.Status.ERROR, context.getString(R.string.error_sign_up_network)));
                    } else {
                        setValue(new FirebaseRegisterResponse(FirebaseRegisterResponse.Status.ERROR, context.getString(R.string.error_unknown)));
                    }
                }
            });
        }
    }

    /**
     * @param email Email text
     * @return True if the email format is correct. False otherwise
     */
    private boolean isEmailValid(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    /**
     * @param password Password text
     * @return True if the password length is greater than 5. False otherwise
     */
    private boolean isPasswordLengthGreaterThan5(String password) {
        return password.length() > 5;
    }

}

