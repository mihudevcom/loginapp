package com.mihudev.loginapp.components.password.change;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthRecentLoginRequiredException;
import com.google.firebase.auth.FirebaseUser;
import com.mihudev.loginapp.R;
import com.mihudev.loginapp.components.password.change.FirebaseChangePasswordResponse.Status;

/**
 * Change password manager responsible for changing the user password and providing status responses and messages
 * from the change password response
 */
public class FirebaseChangePasswordManager extends LiveData<FirebaseChangePasswordResponse> {

    /**
     * Current logged in user. Used for changing the password
     */
    private final FirebaseUser firebaseUser;

    /**
     * Constructor. We get the current FirebaseUser here.
     */
    public FirebaseChangePasswordManager() {
        super();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    }

    /**
     * tries to change the user password
     *
     * @param newPassword The password to be changed to
     */
    public void changePassword(final Context context, String newPassword) {
        setValue(new FirebaseChangePasswordResponse(Status.LOADING, ""));
        if (!isPasswordLengthGreaterThan5(newPassword)) {
            setValue(new FirebaseChangePasswordResponse(Status.ERROR, context.getString(R.string.error_new_password_too_short)));
        } else {
            firebaseUser.updatePassword(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        setValue(new FirebaseChangePasswordResponse(Status.SUCCESS, context.getString(R.string.password_changed_successfully)));
                    }
                }
            }).addOnCanceledListener(new OnCanceledListener() {
                @Override
                public void onCanceled() {
                    setValue(new FirebaseChangePasswordResponse(Status.CANCELED, context.getString(R.string.error_password_change_cancelled)));
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    if (e instanceof FirebaseAuthRecentLoginRequiredException) {
                        setValue(new FirebaseChangePasswordResponse(Status.ERROR, context.getString(R.string.error_log_out_and_login)));
                    }
                    if (e instanceof FirebaseNetworkException) {
                        setValue(new FirebaseChangePasswordResponse(Status.ERROR, context.getString(R.string.error_password_change_network)));
                    } else {
                        setValue(new FirebaseChangePasswordResponse(Status.ERROR, context.getString(R.string.error_unknown)));
                    }
                }
            });
        }
    }

    /**
     * @param password Password text
     * @return True is the password length is greater than 5. False otherwise
     */
    private boolean isPasswordLengthGreaterThan5(String password) {
        return password.length() > 5;
    }

}