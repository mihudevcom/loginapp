package com.mihudev.loginapp.components.splash;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.mihudev.loginapp.R;
import com.mihudev.loginapp.databinding.FragmentSplashBinding;

/**
 * The Splash Fragment
 */
public class SplashFragment extends Fragment {

    /**
     * Used for displaying the splash screen 2 seconds
     */
    private Handler handler;

    private Runnable waitRunnable;

    /**
     * Empty constructor required
     */
    public SplashFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final FragmentSplashBinding fragmentSplashBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_splash, container, false);

        final SplashViewModel splashViewModel = ViewModelProviders.of(this).get(SplashViewModel.class);

        fragmentSplashBinding.setSplashViewModel(splashViewModel);
        fragmentSplashBinding.setLifecycleOwner(this);

        //show either the Home screen or Login screen after 2 seconds
        handler = new Handler();
        waitRunnable = new Runnable() {

            @Override
            public void run() {
                if (splashViewModel.getCurrentUser() == null) {
                    Navigation.findNavController(getView()).navigate(R.id.action_splashFragment_to_loginFragment);
                } else {
                    Navigation.findNavController(getView()).navigate(R.id.action_splashFragment_to_homeFragment);
                }
            }
        };
        handler.postDelayed(waitRunnable, 2000);

        return fragmentSplashBinding.getRoot();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(waitRunnable);
    }
}
