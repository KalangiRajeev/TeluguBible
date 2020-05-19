package com.ikalangirajeev.telugubiblemessages.ui.blogs.userblogs;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ikalangirajeev.telugubiblemessages.ui.blogs.displayblogs.BlogIndex;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UserBlogsViewModel extends ViewModel {

    private static final String TAG = "BlogsViewModel";


    private MutableLiveData<String> mText;



    public UserBlogsViewModel() {
        mText = new MutableLiveData<>();

    }

    public LiveData<String> getBlogIndex(String userUid) {
        mText.setValue(userUid);
        return mText;
    }
}