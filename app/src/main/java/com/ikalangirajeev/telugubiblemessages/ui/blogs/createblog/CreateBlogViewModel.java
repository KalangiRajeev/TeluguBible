package com.ikalangirajeev.telugubiblemessages.ui.blogs.createblog;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class CreateBlogViewModel extends ViewModel {
    public static final String BLOG_DB_NODE = "blogs2020";

    private MutableLiveData<String> mText;
    private CreateBlog createBlog;

    public CreateBlogViewModel() {
        mText = new MutableLiveData<>();
    }


    public LiveData<String> getCreateBlog(CreateBlog createBlog) {

        Map<String, Object> mapBlog = createBlog.toMap();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        Map<String, Object> childrenUpdates = new HashMap<>();
        childrenUpdates.put("/blogs2020/" + createBlog.getBlogId(), mapBlog);

        databaseReference.updateChildren(childrenUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    mText.setValue("Posted Successfully");
                } else {
                    mText.setValue(task.getException().getMessage());
                }
            }
        });
        return mText;
    }

}