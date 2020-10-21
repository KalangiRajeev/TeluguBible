package com.ikalangirajeev.telugubiblemessages.ui.userauth.app.login;

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
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.FragmentNavigator;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ikalangirajeev.telugubiblemessages.R;

public class LoginFragment extends Fragment {

    private FirebaseAuth firebaseAuth;

    private NavController navController;
    private MaterialTextView headerTextView;
    private LoginViewModel loginViewModel;
    private MaterialTextView textViewSkipLogin;
    private MaterialTextView textViewRegistration;
    private MaterialTextView textViewForgotPassword;
    private TextInputLayout editTextEmail;
    private TextInputLayout editTextPassword;
    private Button buttonLogin;
    private ProgressBar progressBar;

    String email;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        email = getArguments().getString("email");
        if(email.equals("none")){
            email = "";
        }
        setSharedElementEnterTransition(TransitionInflater.from(getActivity()).inflateTransition(R.transition.sharedelement_slide));
        setSharedElementReturnTransition(TransitionInflater.from(getActivity()).inflateTransition(R.transition.sharedelement_slide));
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        View root = inflater.inflate(R.layout.fragment_login, container, false);

        headerTextView = root.findViewById(R.id.headerTextView);
        editTextEmail = root.findViewById(R.id.text_email);
        editTextPassword = root.findViewById(R.id.edit_text_password);
        buttonLogin = root.findViewById(R.id.button_login);
        progressBar = root.findViewById(R.id.progressbar_login);
        textViewRegistration = root.findViewById(R.id.text_view_registration);
        textViewForgotPassword = root.findViewById(R.id.text_view_forget_password);

        if (email != null && !email.equals("None")) {
            editTextEmail.getEditText().setText(email);
        }

        textViewForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                if(!TextUtils.isEmpty(editTextEmail.getEditText().getText().toString().trim())) {
                    bundle.putString("email", editTextEmail.getEditText().getText().toString().trim());
                }
                navController.navigate(R.id.action_loginFragment_to_resetPasswordFragment, bundle);
            }
        });

        textViewRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                if(!TextUtils.isEmpty(editTextEmail.getEditText().getText().toString().trim())) {
                    bundle.putString("email", editTextEmail.getEditText().getText().toString().trim());
                }
                navController.navigate(R.id.action_loginFragment_to_registrationFragment, bundle);
            }
        });


        textViewSkipLogin = root.findViewById(R.id.text_view_skip_login);
        textViewSkipLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentNavigator.Extras extras = new FragmentNavigator.Extras.Builder()
                        .addSharedElement(textViewSkipLogin, textViewSkipLogin.getTransitionName())
                        .build();
                navController.navigate(R.id.action_loginFragment_to_bibleFragment);
            }
        });

        editTextPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        });


        buttonLogin.setOnClickListener(new View.OnClickListener() {
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
                } else if (TextUtils.isEmpty(editTextPassword.getEditText().getText().toString().trim()) || editTextPassword.getEditText().getText().toString().trim().length() < 6) {
                    editTextPassword.setError("password needs six characters at least");
                    editTextPassword.requestFocus();
                    return;
                } else {
                    loginViewModel.setEmail(editTextEmail.getEditText().getText().toString().trim());
                    loginViewModel.setPassword(editTextPassword.getEditText().getText().toString().trim());

                    loginViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
                        @Override
                        public void onChanged(@Nullable String s) {
                            if (s.equals("true")) {
                                Toast.makeText(getActivity(), "User Login Successful", Toast.LENGTH_LONG).show();

                                FragmentNavigator.Extras extras = new FragmentNavigator.Extras.Builder()
                                        .addSharedElement(buttonLogin, buttonLogin.getTransitionName())
                                        .build();

                                Bundle bundle = new Bundle();
                                bundle.putString("email", editTextEmail.getEditText().getText().toString().trim());
                                navController.navigate(R.id.action_loginFragment_to_bibleFragment, bundle);
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