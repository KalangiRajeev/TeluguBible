package com.ikalangirajeev.telugubiblemessages.ui.userauth.app.verifyemail;

import android.os.Bundle;
import android.text.TextUtils;
import android.transition.TransitionInflater;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

import com.google.android.material.textview.MaterialTextView;
import com.ikalangirajeev.telugubiblemessages.MainActivity;
import com.ikalangirajeev.telugubiblemessages.R;

public class VerifyEmailFragment extends Fragment {

    private VerifyEmailViewModel verifyEmailViewModel;
    private NavController navController;

    private MaterialTextView headerTextView;
    private TextView textViewBackToLogin;
    private TextView textViewVerifyEmail;
    private Button buttonResetPassword;
    private Button buttonVerifyEmail;
    private ProgressBar progressBar;


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

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        verifyEmailViewModel =
                ViewModelProviders.of(this).get(VerifyEmailViewModel.class);
        View root = inflater.inflate(R.layout.fragment_verifyemail, container, false);

        headerTextView = root.findViewById(R.id.headerTextView);
        textViewBackToLogin = root.findViewById(R.id.text_view_backto_login);
        textViewVerifyEmail = root.findViewById(R.id.textViewVerifyEmail);
        buttonResetPassword = root.findViewById(R.id.buttonVerifyEmail);
        progressBar = root.findViewById(R.id.progressbar_resetpassword);

        textViewBackToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("email", textViewVerifyEmail.getText().toString().trim());

                NavOptions navOptions = new NavOptions.Builder()
                        .setLaunchSingleTop(true)
                        .setEnterAnim(R.anim.slide_in_left).setExitAnim(R.anim.slide_out_right)
                        .setPopEnterAnim(R.anim.slide_in_right).setPopExitAnim(R.anim.slide_out_left)
                        .build();
                navController.navigate(R.id.loginFragment, bundle, navOptions);
            }
        });



        if (email != null) {
            Toast.makeText(getActivity(), email, Toast.LENGTH_LONG).show();
            textViewVerifyEmail.setText(email);
        }



        buttonResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(textViewVerifyEmail.getText().toString().trim())) {
                    textViewVerifyEmail.setError("e-mail required");
                    textViewVerifyEmail.requestFocus();
                    return;
                } else if (!Patterns.EMAIL_ADDRESS.matcher(textViewVerifyEmail.getText().toString().trim()).matches()) {
                    textViewVerifyEmail.setError("Valid e-mail required");
                    textViewVerifyEmail.requestFocus();
                    return;
                } else {
                    verifyEmailViewModel.setEmail(textViewVerifyEmail.getText().toString().trim());
                    verifyEmailViewModel.getVerifyEmailResults().observe(getViewLifecycleOwner(), new Observer<String>() {
                        @Override
                        public void onChanged(String s) {
                            if(s.equals("true")){
                                Toast.makeText(getActivity(), "Verify e-mail link sent. Check your e-mail", Toast.LENGTH_LONG).show();

                                FragmentNavigator.Extras extras = new FragmentNavigator.Extras.Builder()
                                        .addSharedElement(headerTextView, "header_of_page")
                                        .addSharedElement(buttonVerifyEmail, "button_se")
                                        .build();
                                NavOptions navOptions = new NavOptions.Builder()
                                        .setLaunchSingleTop(true)
                                        .setEnterAnim(R.anim.slide_in_right).setExitAnim(R.anim.slide_out_left)
                                        .setPopEnterAnim(R.anim.slide_in_left).setPopExitAnim(R.anim.slide_out_right)
                                        .build();
                                Bundle bundle = new Bundle();
                                bundle.putString("email", textViewVerifyEmail.getText().toString().trim());
                                navController.navigate(R.id.bibleFragment, bundle, navOptions, extras);

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
