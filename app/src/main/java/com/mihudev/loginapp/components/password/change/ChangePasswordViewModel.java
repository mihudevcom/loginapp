package com.mihudev.loginapp.components.password.change;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * The Change password fragment ViewModel. Responsible for the View of the Change Password Fragment
 * and making the connection between the Change Password View and Change Password Manager
 */
public class ChangePasswordViewModel extends ViewModel {

    /**
     * The new password text live data displayed in the newPassword EditText
     */
    public final MutableLiveData<String> newPassword = new MutableLiveData<>();
    /**
     * Change Password Manager used for changing the user password
     */
    private final FirebaseChangePasswordManager firebaseChangePasswordManager = new FirebaseChangePasswordManager();


    /**
     * Constructor
     */
    public ChangePasswordViewModel() {
    }

    /**
     * Used to ask the Change Password manager to change this users password
     */
    public void changePassword(Context context) {
        firebaseChangePasswordManager.changePassword(context, newPassword.getValue());
    }

    /**
     * @return The Change Password Manager
     */
    public FirebaseChangePasswordManager getFirebaseChangePasswordManager() {
        return firebaseChangePasswordManager;
    }
}