package com.mihudev.loginapp.components.login;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.mihudev.loginapp.R;
import com.mihudev.loginapp.databinding.FragmentLoginBinding;

import static com.mihudev.loginapp.components.login.FirebaseLoginResponse.Status.EMPTY;

/**
 * The Login Fragment
 */
public class LoginFragment extends Fragment {

    /**
     * Empty constructor required
     */
    public LoginFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final FragmentLoginBinding fragmentLoginBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false);

        final LoginViewModel loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);

        fragmentLoginBinding.setLoginViewModel(loginViewModel);
        fragmentLoginBinding.setLifecycleOwner(this);

        loginViewModel.getFirebaseLoginManager().observe(this, new Observer<FirebaseLoginResponse>() {
            @Override
            public void onChanged(FirebaseLoginResponse firebaseLoginResponse) {
                switch (firebaseLoginResponse.getStatus()) {
                    case SUCCESS:
                        fragmentLoginBinding.progressBar.setVisibility(View.GONE);
                        Navigation.findNavController(getView()).navigate(R.id.action_loginFragment_to_homeFragment);
                        break;
                    case LOADING:
                        fragmentLoginBinding.progressBar.setVisibility(View.VISIBLE);
                        break;

                    case ERROR:
                        fragmentLoginBinding.progressBar.setVisibility(View.GONE);
                        showError(firebaseLoginResponse.getErrorMessage());
                        break;
                    case CANCELED:
                        fragmentLoginBinding.progressBar.setVisibility(View.GONE);
                        showError(firebaseLoginResponse.getErrorMessage());
                        break;
                }
                loginViewModel.getFirebaseLoginManager().getValue().setStatus(EMPTY);
            }
        });

        fragmentLoginBinding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginViewModel.signIn(getContext());
            }
        });

        fragmentLoginBinding.registerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(getView()).navigate(R.id.action_loginFragment_to_registerFragment);
            }
        });

        fragmentLoginBinding.recoverPasswordText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(getView()).navigate(R.id.action_loginFragment_to_recoverPasswordFragment);
            }
        });

        return fragmentLoginBinding.getRoot();
    }

    /**
     * Displays an error for the user
     *
     * @param errorMessage The error message
     */
    private void showError(String errorMessage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setMessage(errorMessage)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        builder.create().show();
    }
}
