package com.ikalangirajeev.telugubiblemessages.ui.blogs.displayblogs;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.firebase.ui.database.FirebaseRecyclerOptions;


public class BlogsViewModel extends ViewModel {

    private MutableLiveData<String> mText;


    public BlogsViewModel() {
        mText = new MutableLiveData<>();
    }

    public LiveData<String> getBlogIndex(String searchName) {

        mText.setValue(searchName);
        return mText;
    }
}