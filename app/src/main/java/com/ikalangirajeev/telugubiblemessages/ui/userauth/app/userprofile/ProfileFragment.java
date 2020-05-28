package com.ikalangirajeev.telugubiblemessages.ui.userauth.app.userprofile;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.hbb20.CountryCodePicker;
import com.ikalangirajeev.telugubiblemessages.R;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static androidx.lifecycle.ViewModelProviders.of;

public class ProfileFragment extends Fragment {

    public static final int REQUEST_CODE_PICK_IMAGE = 24;

    private FirebaseAuth firebaseAuth;

    private NavController navController;

    private boolean isEmailNotVerified = true;
    private ProfileViewModel profileViewModel;

    private MaterialTextView headerTextView;
    private TextView textViewEmail;
    private TextView textViewEmailVerified;
    private TextInputLayout editTextProfileName;
    private TextInputLayout editTextPhoneNumber;
    private TextInputLayout editTextVerificationNumber;
    private Button buttonSubmit;
    private TextView textViewSkip;
    private CountryCodePicker countryCodePicker;
    private Button buttonVerifyPhone;
    private Bitmap bitmap;
    private String verificationId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSharedElementEnterTransition(TransitionInflater.from(getActivity()).inflateTransition(R.transition.sharedelement_slide));
        setSharedElementReturnTransition(TransitionInflater.from(getActivity()).inflateTransition(R.transition.sharedelement_slide));
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        profileViewModel =
                of(this).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        headerTextView = root.findViewById(R.id.headerTextView);
        textViewEmail = root.findViewById(R.id.textViewEmailProfile);
        textViewEmailVerified = root.findViewById(R.id.textViewEmailVerified);
        editTextProfileName = root.findViewById(R.id.editTextProfileName);
        countryCodePicker = root.findViewById(R.id.ccp);
        editTextPhoneNumber = root.findViewById(R.id.editTextPhoneNumber);
        buttonSubmit = root.findViewById(R.id.buttonSubmit);
        textViewSkip = root.findViewById(R.id.textViewSkip);
        editTextVerificationNumber = root.findViewById(R.id.editTextVerificationNumber);
        buttonVerifyPhone = root.findViewById(R.id.buttonVerifyPhone);

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            textViewEmail.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());

            if (FirebaseAuth.getInstance().getCurrentUser().isEmailVerified()) {
                textViewEmailVerified.setText(R.string.email_verified);
                isEmailNotVerified = false;

            } else {
                textViewEmailVerified.setTextColor(Color.RED);
                textViewEmailVerified.setText((getString(R.string.email_not_verified)));
            }

            if (FirebaseAuth.getInstance().getCurrentUser().getDisplayName() != null) {
                editTextProfileName.getEditText().setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
            }

            if (FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber() != null) {
                editTextPhoneNumber.getEditText().setText(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber().substring(3));
            }
        }


        textViewSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                navController.navigate(R.id.bibleFragment, bundle, new NavOptions.Builder()
                        .setPopUpTo(R.id.profileFragment, true)
                        .setEnterAnim(R.anim.slide_in_right)
                        .setExitAnim(R.anim.slide_out_left)
                        .setPopEnterAnim(R.anim.slide_in_left)
                        .setPopExitAnim(R.anim.slide_out_right)
                        .build());
            }
        });

        textViewEmailVerified.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEmailNotVerified) {
                    Bundle bundle = new Bundle();
                    bundle.putString("email", textViewEmail.getText().toString().trim());
                    navController.navigate(R.id.verifyEmailFragment, bundle, new NavOptions.Builder()
                            .setPopUpTo(R.id.profileFragment, true)
                            .setExitAnim(R.anim.slide_in_left)
                            .setExitAnim(R.anim.slide_out_right)
                            .setPopEnterAnim(R.anim.slide_in_right)
                            .setPopExitAnim(R.anim.slide_out_left)
                            .build());
                }
            }
        });


        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(editTextProfileName.getEditText().getText().toString().trim())) {
                    editTextProfileName.setError("Profile Name Required");
                    editTextProfileName.requestFocus();
                } else if (TextUtils.isEmpty(editTextPhoneNumber.getEditText().getText().toString().trim())) {
                    editTextPhoneNumber.setError("Phone Number Required");
                    editTextPhoneNumber.requestFocus();
                } else {
                    profileViewModel.setUserName(editTextProfileName.getEditText().getText().toString().trim());

                    profileViewModel.setUserProfileChangeRequestResults().observe(getViewLifecycleOwner(), new Observer<String>() {
                        @Override
                        public void onChanged(@Nullable String s) {
                            if (Objects.equals(s, "true")) {
                                Toast.makeText(getActivity(), "Profile Name Set", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();
                            }
                        }
                    });


                    String phoneNumber = "+"
                            + countryCodePicker.getSelectedCountryCode()
                            + editTextPhoneNumber.getEditText().getText().toString().trim();

                    PhoneAuthProvider.getInstance().verifyPhoneNumber(phoneNumber, 60, TimeUnit.SECONDS, getActivity(),
                            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                @Override
                                public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                    super.onCodeSent(verificationId, forceResendingToken);
                                    ProfileFragment.this.verificationId = verificationId;
                                }

                                @Override
                                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                    addPhoneNumber(phoneAuthCredential);
                                }

                                @Override
                                public void onVerificationFailed(@NonNull FirebaseException e) {
                                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                    );
                }
            }
        });

        buttonVerifyPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String verificationCode = Objects.requireNonNull(editTextVerificationNumber.getEditText()).getText().toString().trim();
                if (TextUtils.isEmpty(verificationCode)) {
                    editTextPhoneNumber.setError("Valid Verification Number required");
                    editTextPhoneNumber.requestFocus();
                } else {
                    addPhoneNumber(PhoneAuthProvider.getCredential(verificationId, verificationCode));
                }
            }
        });

        return root;
    }

    private void addPhoneNumber(PhoneAuthCredential credential) {
        profileViewModel.setPhoneNumber(credential);
        profileViewModel.setPhoneNumberResults().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                if (Objects.equals(s, "true")) {
                    Toast.makeText(getActivity(), R.string.phone_number_verified, Toast.LENGTH_LONG).show();
                    navController.navigate(R.id.bibleFragment, null, new NavOptions.Builder()
                            .setPopUpTo(R.id.profileFragment, true)
                            .setExitAnim(R.anim.slide_in_left)
                            .setExitAnim(R.anim.slide_out_right)
                            .setPopEnterAnim(R.anim.slide_in_right)
                            .setPopExitAnim(R.anim.slide_out_left)
                            .build());
                } else {
                    Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
    }
}