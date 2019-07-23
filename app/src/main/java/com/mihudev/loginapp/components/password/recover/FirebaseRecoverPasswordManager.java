package com.mihudev.loginapp.components.password.recover;

import android.content.Context;
import android.util.Patterns;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.mihudev.loginapp.R;
import com.mihudev.loginapp.components.password.recover.FirebaseRecoverPasswordResponse.Status;

/**
 * Recover password manager responsible of recovering the user password and providing status responses and messages
 * from the Recover password response
 */
public class FirebaseRecoverPasswordManager extends LiveData<FirebaseRecoverPasswordResponse> {

    /**
     * Used to access the sendPasswordResetEmail function
     */
    private final FirebaseAuth firebaseAuth;

    /**
     * Constructor
     */
    public FirebaseRecoverPasswordManager() {
        super();
        firebaseAuth = FirebaseAuth.getInstance();
    }

    /**
     * tries to recover the user password
     *
     * @param email The user email
     */
    public void recoverPassword(final Context context, String email) {
        setValue(new FirebaseRecoverPasswordResponse(Status.LOADING, ""));
        if (email == null || !isEmailValid(email)) {
            setValue(new FirebaseRecoverPasswordResponse(Status.ERROR, context.getString(R.string.error_invalid_email)));
        } else {
            firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        setValue(new FirebaseRecoverPasswordResponse(Status.SUCCESS, context.getString(R.string.password_reset_success)));
                    }
                }
            }).addOnCanceledListener(new OnCanceledListener() {
                @Override
                public void onCanceled() {
                    setValue(new FirebaseRecoverPasswordResponse(Status.CANCELED, context.getString(R.string.error_rcover_password_cancelled)));
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    if (e instanceof FirebaseNetworkException) {
                        setValue(new FirebaseRecoverPasswordResponse(Status.ERROR, context.getString(R.string.error_password_recover_network)));
                    } else if (e instanceof FirebaseAuthInvalidUserException) {
                        String errorCode =
                                ((FirebaseAuthInvalidUserException) e).getErrorCode();

                        if (errorCode.equals("ERROR_USER_NOT_FOUND")) {
                            setValue(new FirebaseRecoverPasswordResponse(Status.ERROR, context.getString(R.string.error_no_matching_account)));
                        } else if (errorCode.equals("ERROR_USER_DISABLED")) {
                            setValue(new FirebaseRecoverPasswordResponse(Status.ERROR, context.getString(R.string.error_user_disabled)));
                        }
                    } else {
                        setValue(new FirebaseRecoverPasswordResponse(Status.ERROR, context.getString(R.string.error_unknown)));
                    }
                }
            });
        }
    }

    /**
     * @param email Email text
     * @return True if the email format is valid. False otherwise
     */
    private boolean isEmailValid(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
