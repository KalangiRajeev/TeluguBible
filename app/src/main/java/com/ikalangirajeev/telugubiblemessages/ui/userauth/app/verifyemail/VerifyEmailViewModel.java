package com.ikalangirajeev.telugubiblemessages.ui.userauth.app.verifyemail;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class VerifyEmailViewModel extends ViewModel {
    // TODO: Implement the ViewModel

    private MutableLiveData<String> mText;
    private FirebaseAuth firebaseAuth;

    private String email;

    public VerifyEmailViewModel() {
        mText = new MutableLiveData<>();
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public LiveData<String> getVerifyEmailResults() {

        firebaseAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    mText.setValue(String.valueOf(task.isSuccessful()));
                } else {
                    mText.setValue(task.getException().getMessage());
                }
            }
        });

        return mText;
    }
}
