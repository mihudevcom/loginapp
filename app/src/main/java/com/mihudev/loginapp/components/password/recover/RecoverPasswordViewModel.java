package com.mihudev.loginapp.components.password.recover;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * The Recover password fragment ViewModel. Responsible for the View of the Recover Password Fragment
 * and making the connection between the Recover Password View and Recover Passoword Manager
 */
public class RecoverPasswordViewModel extends ViewModel {

    /**
     * The email text live data displayed in the email EditText
     */
    public final MutableLiveData<String> email = new MutableLiveData<>();
    /**
     * The manager used to recover the user password
     */
    private final FirebaseRecoverPasswordManager firebaseRecoverPasswordManager = new FirebaseRecoverPasswordManager();

    /**
     * Constructor
     */
    public RecoverPasswordViewModel() {
    }

    /**
     * Calls the recoverPassword function from the manager
     *
     * @param context App context
     */
    public void recoverPassword(Context context) {
        firebaseRecoverPasswordManager.recoverPassword(context, this.email.getValue());
    }

    /**
     * @return The Recover Password Manager
     */
    public FirebaseRecoverPasswordManager getFirebaseRecoverPasswordManager() {
        return firebaseRecoverPasswordManager;
    }
}