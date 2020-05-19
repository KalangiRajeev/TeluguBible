package com.ikalangirajeev.telugubiblemessages.ui.blogs.detailblog;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ikalangirajeev.telugubiblemessages.ui.blogs.displayblogs.BlogIndex;

import java.util.ArrayList;
import java.util.List;

public class DetailBlogViewModel extends ViewModel {

    private static final String TAG = "DetailBlogViewModel";


    private MutableLiveData<String> mText;


    public DetailBlogViewModel() {
        mText = new MutableLiveData<>();

    }



    public LiveData<String> getCommentsList(String userId) {


       return mText;
    }
}