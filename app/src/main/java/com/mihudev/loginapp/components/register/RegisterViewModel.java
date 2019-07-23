package com.mihudev.loginapp.components.register;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


/**
 * The Register fragment ViewModel. Responsible for the View of the Register Fragment
 * and making the connection between the Register View and Register Manager
 */
public class RegisterViewModel extends ViewModel {
    /**
     * The email text live data displayed in the email EditText
     */
    public final MutableLiveData<String> email = new MutableLiveData<>();
    /**
     * The password text live data displayed in the password EditText
     */
    public final MutableLiveData<String> password = new MutableLiveData<>();
    /**
     * Manager used to register the user
     */
    private final FirebaseRegisterManager firebaseRegisterManager = new FirebaseRegisterManager();

    /**
     * Constructor
     */
    public RegisterViewModel() {
    }

    /**
     * Calls the registerWithEmailAndPassword from the Register Manager
     */
    public void register(Context context) {
        firebaseRegisterManager.registerWithEmailAndPassword(context, email.getValue(), password.getValue());
    }

    /**
     * @return The Register Manager
     */
    public FirebaseRegisterManager getFirebaseRegisterManager() {
        return firebaseRegisterManager;
    }
}
