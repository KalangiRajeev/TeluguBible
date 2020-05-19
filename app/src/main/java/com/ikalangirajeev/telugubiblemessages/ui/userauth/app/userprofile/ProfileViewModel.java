package com.ikalangirajeev.telugubiblemessages.ui.userauth.app.userprofile;

import android.app.Activity;
import android.graphics.Bitmap;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class ProfileViewModel extends ViewModel {

    private FirebaseAuth firebaseAuth;
    private FirebaseStorage firebaseStorage;
    private MutableLiveData<String> mText;
    private PhoneAuthCredential phoneAuthCredential;
    private Bitmap bitmap;
    private Uri uri;

    private String userName;

    public ProfileViewModel() {
        mText = new MutableLiveData<>();
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPhoneNumber(PhoneAuthCredential phoneAuthCredential) {
        this.phoneAuthCredential = phoneAuthCredential;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }


    public LiveData<String> setUserProfileChangeRequestResults() {

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
//
//        if(bitmap!=null) {
//            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//            final StorageReference storageReference = firebaseStorage.getReference()
//                    .child("pics/" + Objects.requireNonNull(firebaseUser).getUid());
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
//            byte[] imageBytes = byteArrayOutputStream.toByteArray();
//
//            storageReference.putBytes(imageBytes).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
//                    if (task.isSuccessful()) {
//                        storageReference.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
//                            @Override
//                            public void onComplete(@NonNull Task<Uri> task) {
//                                uri = task.getResult();
//                            }
//                        });
//                        mText.setValue("Profile Picture Uploaded to File Storage");
//                    }
//                }
//            });
//            if(uri!=null){
//                firebaseUser.updateProfile(new UserProfileChangeRequest.Builder().setPhotoUri(uri).build());
//            }
//
//        }


            Objects.requireNonNull(firebaseUser).updateProfile(new UserProfileChangeRequest
                    .Builder().setDisplayName(userName.toUpperCase())
                    .build())
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        mText.setValue(String.valueOf(task.isSuccessful()));
                    } else {
                        mText.setValue(Objects.requireNonNull(task.getException()).getMessage());
                    }
                }
            });

        return mText;
    }


    public LiveData<String> setPhoneNumberResults() {

        Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).updatePhoneNumber(phoneAuthCredential).
                addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful() && !Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber()).isEmpty()) {
                            mText.setValue(String.valueOf(task.isSuccessful()));
                        } else {
                            mText.setValue(Objects.requireNonNull(task.getException()).getMessage());
                        }
                    }
                });
        return mText;
    }
}