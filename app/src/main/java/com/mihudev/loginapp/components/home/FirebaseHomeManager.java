package com.mihudev.loginapp.components.home;

import com.google.firebase.auth.FirebaseAuth;

/**
 * The Firebase Home manager responsible for parsing and retrieving data on the Home screen
 */
class FirebaseHomeManager {

    /**
     * Logs out the FirebaseUser
     */
    void logOut() {
        FirebaseAuth.getInstance().signOut();
    }
}
