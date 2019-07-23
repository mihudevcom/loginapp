package com.mihudev.loginapp.components.password.change;

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

import com.mihudev.loginapp.R;
import com.mihudev.loginapp.databinding.FragmentChangePasswordBinding;

import static com.mihudev.loginapp.components.password.change.FirebaseChangePasswordResponse.Status.EMPTY;

/**
 * The ChangePassword Fragment
 */
public class ChangePasswordFragment extends Fragment {

    /**
     * Empty constructor
     */
    public ChangePasswordFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final FragmentChangePasswordBinding fragmentChangePasswordBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_change_password, container, false);

        final ChangePasswordViewModel changePasswordViewModel = ViewModelProviders.of(this).get(ChangePasswordViewModel.class);

        fragmentChangePasswordBinding.setChangePasswordViewModel(changePasswordViewModel);
        fragmentChangePasswordBinding.setLifecycleOwner(this);

        changePasswordViewModel.getFirebaseChangePasswordManager().observe(this, new Observer<FirebaseChangePasswordResponse>() {
            @Override
            public void onChanged(FirebaseChangePasswordResponse firebaseChangePasswordResponse) {
                switch (firebaseChangePasswordResponse.getStatus()) {
                    case SUCCESS:
                        fragmentChangePasswordBinding.progressBar.setVisibility(View.GONE);
                        showMessage(firebaseChangePasswordResponse.getErrorMessage());
                        break;
                    case LOADING:
                        fragmentChangePasswordBinding.progressBar.setVisibility(View.VISIBLE);
                        break;

                    case ERROR:
                        fragmentChangePasswordBinding.progressBar.setVisibility(View.GONE);
                        showError(firebaseChangePasswordResponse.getErrorMessage());
                        break;
                    case CANCELED:
                        fragmentChangePasswordBinding.progressBar.setVisibility(View.GONE);
                        showError(firebaseChangePasswordResponse.getErrorMessage());
                        break;
                }
                changePasswordViewModel.getFirebaseChangePasswordManager().getValue().setStatus(EMPTY);
            }
        });

        fragmentChangePasswordBinding.changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changePasswordViewModel.changePassword(getContext());
            }
        });

        return fragmentChangePasswordBinding.getRoot();
    }

    /**
     * Displays an error
     *
     * @param errorMessage The error message text to be displayed
     */
    private void showError(String errorMessage) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setMessage(errorMessage)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        builder.create().show();
    }

    /**
     * Displays a message
     *
     * @param message The message text to be displayed
     */
    private void showMessage(String message) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setMessage(message)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        builder.create().show();
    }
}
