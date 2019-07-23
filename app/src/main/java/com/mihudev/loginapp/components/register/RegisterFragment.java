package com.mihudev.loginapp.components.register;


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
import com.mihudev.loginapp.databinding.FragmentRegisterBinding;

import static com.mihudev.loginapp.components.register.FirebaseRegisterResponse.Status.EMPTY;

/**
 * The Register fragment
 */
public class RegisterFragment extends Fragment {

    /**
     * Empty constructor required
     */
    public RegisterFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final FragmentRegisterBinding fragmentRegisterBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_register, container, false);

        final RegisterViewModel registerViewModel = ViewModelProviders.of(this).get(RegisterViewModel.class);

        fragmentRegisterBinding.setRegisterViewModel(registerViewModel);
        fragmentRegisterBinding.setLifecycleOwner(this);

        registerViewModel.getFirebaseRegisterManager().observe(this, new Observer<FirebaseRegisterResponse>() {
            @Override
            public void onChanged(FirebaseRegisterResponse firebaseRegisterResponse) {
                switch (firebaseRegisterResponse.getStatus()) {
                    case SUCCESS:
                        fragmentRegisterBinding.progressBar.setVisibility(View.GONE);
                        Navigation.findNavController(getView()).navigate(R.id.action_registerFragment_to_homeFragment);
                        break;
                    case LOADING:
                        fragmentRegisterBinding.progressBar.setVisibility(View.VISIBLE);
                        break;

                    case ERROR:
                        fragmentRegisterBinding.progressBar.setVisibility(View.GONE);
                        showError(firebaseRegisterResponse.getErrorMessage());
                        break;
                    case CANCELED:
                        fragmentRegisterBinding.progressBar.setVisibility(View.GONE);
                        showError(firebaseRegisterResponse.getErrorMessage());
                        break;
                }
                registerViewModel.getFirebaseRegisterManager().getValue().setStatus(EMPTY);
            }
        });

        fragmentRegisterBinding.registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerViewModel.register(getContext());
            }
        });

        return fragmentRegisterBinding.getRoot();
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

