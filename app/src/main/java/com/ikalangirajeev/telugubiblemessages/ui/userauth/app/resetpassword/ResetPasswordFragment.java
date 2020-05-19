package com.ikalangirajeev.telugubiblemessages.ui.userauth.app.resetpassword;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.transition.TransitionInflater;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.FragmentNavigator;

import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;
import com.ikalangirajeev.telugubiblemessages.R;

public class ResetPasswordFragment extends Fragment {

    private ResetPasswordViewModel resetPasswordViewModel;
    private NavController navController;

    private MaterialTextView headerTextView;
    private TextView textViewBackToLogin;
    private TextInputLayout editTextEmail;
    private Button buttonResetPassword;
    private Button buttonVerifyEmail;
    private ProgressBar progressBar;


    private String email;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        email = getArguments().getString("email");
        if (email.equalsIgnoreCase("None")) {
            email = "";
        }
        setSharedElementEnterTransition(TransitionInflater.from(getActivity()).inflateTransition(R.transition.sharedelement_slide));
        setSharedElementReturnTransition(TransitionInflater.from(getActivity()).inflateTransition(R.transition.sharedelement_slide));
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        resetPasswordViewModel =
                ViewModelProviders.of(this).get(ResetPasswordViewModel.class);
        View root = inflater.inflate(R.layout.fragment_resetpassword, container, false);

        headerTextView = root.findViewById(R.id.headerTextView);
        textViewBackToLogin = root.findViewById(R.id.text_view_backto_login);
        editTextEmail = root.findViewById(R.id.editTextResetPassword);
        buttonResetPassword = root.findViewById(R.id.button_resetpassword);
        progressBar = root.findViewById(R.id.progressbar_resetpassword);

        textViewBackToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("email", editTextEmail.getEditText().getText().toString().trim());

                NavOptions navOptions = new NavOptions.Builder()
                        .setLaunchSingleTop(true)
                        .setEnterAnim(R.anim.slide_in_left).setExitAnim(R.anim.slide_out_right)
                        .setPopEnterAnim(R.anim.slide_in_right).setPopExitAnim(R.anim.slide_out_left)
                        .build();
                navController.navigate(R.id.loginFragment, bundle, navOptions);
            }
        });


        if (email != null) {
            editTextEmail.getEditText().setText(email);
        }

        editTextEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        });

        buttonResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(editTextEmail.getEditText().getText().toString().trim())) {
                    editTextEmail.setError("e-mail required");
                    editTextEmail.requestFocus();
                    return;
                } else if (!Patterns.EMAIL_ADDRESS.matcher(editTextEmail.getEditText().getText().toString().trim()).matches()) {
                    editTextEmail.setError("Valid e-mail required");
                    editTextEmail.requestFocus();
                    return;
                } else {
                    resetPasswordViewModel.setEmail(editTextEmail.getEditText().getText().toString().trim());
                    resetPasswordViewModel.getPasswordResetEmailResults().observe(getViewLifecycleOwner(), new Observer<String>() {
                        @Override
                        public void onChanged(String s) {
                            if (s.equals("true")) {
                                Toast.makeText(getActivity(), "Password reset link sent. Verify email!", Toast.LENGTH_LONG).show();

                                FragmentNavigator.Extras extras = new FragmentNavigator.Extras.Builder()
                                        .addSharedElement(headerTextView, "header_of_page")
                                        .addSharedElement(buttonResetPassword, "button_se")
                                        .build();

                                Bundle bundle = new Bundle();
                                bundle.putString("email", editTextEmail.getEditText().getText().toString().trim());
                                navController.navigate(R.id.loginFragment, bundle, new NavOptions.Builder()
                                        .setLaunchSingleTop(true)
                                        .setEnterAnim(R.anim.slide_in_left).setExitAnim(R.anim.slide_out_right)
                                        .setPopEnterAnim(R.anim.slide_in_right).setPopExitAnim(R.anim.slide_out_left)
                                        .build(), extras);
                            } else {
                                Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });


        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
    }

}
