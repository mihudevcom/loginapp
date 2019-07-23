package com.mihudev.loginapp.components.password.recover;

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
import com.mihudev.loginapp.databinding.FragmentRecoverPasswordBinding;

import static com.mihudev.loginapp.components.password.recover.FirebaseRecoverPasswordResponse.Status.EMPTY;

/**
 * The Recover Password Fragment
 */
public class RecoverPasswordFragment extends Fragment {

    /**
     * Empty constructor required
     */
    public RecoverPasswordFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        final FragmentRecoverPasswordBinding fragmentRecoverPasswordBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_recover_password, container, false);

        final RecoverPasswordViewModel recoverPasswordViewModel = ViewModelProviders.of(this).get(RecoverPasswordViewModel.class);

        fragmentRecoverPasswordBinding.setRecoverPasswordViewModel(recoverPasswordViewModel);
        fragmentRecoverPasswordBinding.setLifecycleOwner(this);

        recoverPasswordViewModel.getFirebaseRecoverPasswordManager().observe(this, new Observer<FirebaseRecoverPasswordResponse>() {
            @Override
            public void onChanged(FirebaseRecoverPasswordResponse firebaseRecoverPasswordResponse) {
                switch (firebaseRecoverPasswordResponse.getStatus()) {
                    case SUCCESS:
                        fragmentRecoverPasswordBinding.progressBar.setVisibility(View.GONE);
                        showMessage(firebaseRecoverPasswordResponse.getErrorMessage());
                        break;
                    case LOADING:
                        fragmentRecoverPasswordBinding.progressBar.setVisibility(View.VISIBLE);
                        break;

                    case ERROR:
                        fragmentRecoverPasswordBinding.progressBar.setVisibility(View.GONE);
                        showError(firebaseRecoverPasswordResponse.getErrorMessage());
                        break;
                    case CANCELED:
                        fragmentRecoverPasswordBinding.progressBar.setVisibility(View.GONE);
                        showError(firebaseRecoverPasswordResponse.getErrorMessage());
                        break;
                }
                recoverPasswordViewModel.getFirebaseRecoverPasswordManager().getValue().setStatus(EMPTY);
            }
        });

        fragmentRecoverPasswordBinding.recoverPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recoverPasswordViewModel.recoverPassword(getContext());
            }
        });

        return fragmentRecoverPasswordBinding.getRoot();
    }

    /**
     * Displays an error for the user
     *
     * @param errorMessage The error message
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
