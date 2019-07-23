package com.mihudev.loginapp.components.login;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


/**
 * The Login fragment ViewModel. Responsible for the View of the Login Fragment
 * and making the connection between the Login View and Login Manager
 */
public class LoginViewModel extends ViewModel {

    /**
     * The email text live data displayed in the email EditText
     */
    public final MutableLiveData<String> email = new MutableLiveData<>();
    /**
     * The password text live data displayed in the password EditText
     */
    public final MutableLiveData<String> password = new MutableLiveData<>();
    /**
     * The Login manager used for signing in
     */
    private final FirebaseLoginManager firebaseLoginManager = new FirebaseLoginManager();

    /**
     * The LoginViewModel constructor
     */
    public LoginViewModel() {
    }

    /**
     * Calls the signInWithEmailAndPassword function from the Login Manager
     */
    public void signIn(Context context) {
        firebaseLoginManager.signInWithEmailAndPassword(context, email.getValue(), password.getValue());
    }

    /**
     * @return The Login Manager
     */
    public FirebaseLoginManager getFirebaseLoginManager() {
        return firebaseLoginManager;
    }


}
