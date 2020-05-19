package com.ikalangirajeev.telugubiblemessages.ui.userauth.app.registration;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrationViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private FirebaseAuth firebaseAuth;

    private String email;
    private String password;

    public RegistrationViewModel() {
        mText = new MutableLiveData<>();
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LiveData<String> getText() {

        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    if(firebaseAuth.getCurrentUser()!=null) {
                        firebaseAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    mText.setValue(String.valueOf(task.isSuccessful()));
                                }
                            }
                        });
                    }
                } else {
                    mText.setValue(task.getException().getMessage());
                }
            }
        });

        return mText;
    }
}