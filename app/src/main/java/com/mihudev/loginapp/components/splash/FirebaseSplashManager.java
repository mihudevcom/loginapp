package com.mihudev.loginapp.components.splash;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Splash manager responsible getting the current user
 */
class FirebaseSplashManager {

    /**
     * @return The current FirebaseUser and can be null
     */
    public FirebaseUser getCurrentUser() {
        return FirebaseAuth.getInstance().getCurrentUser();
    }
}
