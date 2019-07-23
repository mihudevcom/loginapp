package com.mihudev.loginapp.components.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.mihudev.loginapp.R;
import com.mihudev.loginapp.databinding.FragmentHomeBinding;

/**
 * The Home Fragment View
 */
public class HomeFragment extends Fragment {

    /**
     * Empty constructor required
     */
    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final FragmentHomeBinding fragmentHomeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);

        final HomeViewModel homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);

        fragmentHomeBinding.setHomeViewModel(homeViewModel);
        fragmentHomeBinding.setLifecycleOwner(this);

        fragmentHomeBinding.logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homeViewModel.logOut();
                Navigation.findNavController(getView()).navigate(R.id.action_homeFragment_to_loginFragment);
            }
        });

        fragmentHomeBinding.changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(getView()).navigate(R.id.action_homeFragment_to_changePasswordFragment);
            }
        });

        return fragmentHomeBinding.getRoot();
    }
}
