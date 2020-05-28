package com.ikalangirajeev.telugubiblemessages.ui.userauth.app.registration;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.transition.Transition;
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

public class RegistrationFragment extends Fragment {

    private static final String TAG = "RegistrationFragment";

    private MaterialTextView headerTextView;
    private RegistrationViewModel registrationViewModel;
    private NavController navController;
    private TextInputLayout editTextEmail;
    private TextInputLayout editTextPassword;
    private TextInputLayout editTextConfirmPassword;
    private Button buttonRegister;
    private TextView textViewSignin;


    private String email;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        email = getArguments().getString("email");
        if (email.equalsIgnoreCase("none")) {
            email = "";
        }
        setSharedElementEnterTransition(TransitionInflater.from(getActivity()).inflateTransition(R.transition.sharedelement_slide));
        setSharedElementReturnTransition(TransitionInflater.from(getActivity()).inflateTransition(R.transition.sharedelement_slide));
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        registrationViewModel =
                ViewModelProviders.of(this).get(RegistrationViewModel.class);
        View root = inflater.inflate(R.layout.fragment_register, container, false);

        headerTextView = root.findViewById(R.id.headerTextView);
        editTextPassword = root.findViewById(R.id.edit_text_register_password);
        editTextEmail = root.findViewById(R.id.edit_text_register_email);
        editTextConfirmPassword = root.findViewById(R.id.edit_text_register_confirm_password);
        buttonRegister = root.findViewById(R.id.button_register);
        textViewSignin = root.findViewById(R.id.text_view_login);

        textViewSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("email", editTextEmail.getEditText().getText().toString().trim());

                NavOptions navOptions = new NavOptions.Builder()
                        .setPopUpTo(R.id.registrationFragment, true)
                        .setEnterAnim(R.anim.slide_in_left).setExitAnim(R.anim.slide_out_right)
                        .setPopEnterAnim(R.anim.slide_in_right).setPopExitAnim(R.anim.slide_out_left)
                        .build();
                navController.navigate(R.id.loginFragment, bundle, navOptions);
            }
        });


        if (email != null) {
            editTextEmail.getEditText().setText(email);
        }

        editTextConfirmPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        });

        buttonRegister.setOnClickListener(new View.OnClickListener() {
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
                    editTextPassword.setError("password needs 6 characters");
                    editTextPassword.requestFocus();
                    return;
                } else if (!editTextPassword.getEditText().getText().toString().trim().equals(editTextConfirmPassword.getEditText().getText().toString().trim())) {
                    editTextConfirmPassword.setError("confirm password doesn't match with entry password");
                    editTextConfirmPassword.requestFocus();
                    return;
                } else {
                    registrationViewModel.setEmail(editTextEmail.getEditText().getText().toString().trim());
                    registrationViewModel.setPassword(editTextEmail.getEditText().getText().toString().trim());

                    registrationViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
                        @Override
                        public void onChanged(@Nullable String s) {
                            if (s.equals("true")) {
                                Toast.makeText(getActivity(), "User Registration Successful. Verify email", Toast.LENGTH_LONG).show();

                                FragmentNavigator.Extras extras = new FragmentNavigator.Extras.Builder()
                                        .addSharedElement(headerTextView, "header_of_page")
                                        .addSharedElement(buttonRegister, "button_se")
                                        .build();
                                Bundle bundle = new Bundle();
                                bundle.putString("email", editTextEmail.getEditText().getText().toString().trim());
                                NavOptions navOptions = new NavOptions.Builder()
                                        .setPopUpTo(R.id.registrationFragment, true)
                                        .setEnterAnim(R.anim.slide_in_left).setExitAnim(R.anim.slide_out_right)
                                        .setPopEnterAnim(R.anim.slide_in_right).setPopExitAnim(R.anim.slide_out_left)
                                        .build();
                                navController.navigate(R.id.loginFragment, bundle, navOptions, extras);
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