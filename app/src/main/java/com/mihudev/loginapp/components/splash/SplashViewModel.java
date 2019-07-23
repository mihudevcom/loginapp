package com.mihudev.loginapp.components.splash;

import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseUser;

/**
 * The Splash fragment ViewModel. Responsible for the View of the Splash Fragment
 * and making the connection between the Splash View and Splash Manager
 */
public class SplashViewModel extends ViewModel {

    /**
     * The manager used to get the current user
     */
    private final FirebaseSplashManager firebaseSplashManager;

    /**
     * Constructor
     */
    public SplashViewModel() {
        firebaseSplashManager = new FirebaseSplashManager();
    }

    /**
     * Calls the getCurrentUser from the Manager
     *
     * @return The current FirebaseUser. Can be null.
     */
    public FirebaseUser getCurrentUser() {
        return firebaseSplashManager.getCurrentUser();
    }
}
