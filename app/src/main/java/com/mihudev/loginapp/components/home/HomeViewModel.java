package com.mihudev.loginapp.components.home;

import androidx.lifecycle.ViewModel;

/**
 * The Home fragment ViewModel. Responsible for the View of the Home Fragment
 * and making the connection between the Home View and Home Manager
 */
public class HomeViewModel extends ViewModel {

    /**
     * Used to parse data on this screen
     */
    private final FirebaseHomeManager firebaseHomeManager;

    /**
     * HomeViewModel constructor
     */
    public HomeViewModel() {
        firebaseHomeManager = new FirebaseHomeManager();
    }

    /**
     * Calls the FirebaseHomeManager logOut() function
     */
    public void logOut() {
        firebaseHomeManager.logOut();
    }
}
