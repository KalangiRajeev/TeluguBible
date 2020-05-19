package com.ikalangirajeev.telugubiblemessages.ui.blogs.detailblog;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.ikalangirajeev.telugubiblemessages.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetailBlogFragment extends Fragment {

    private static final String TAG = "DetailBlogFragment";

    private DetailBlogViewModel detailBlogViewModel;
    private int commentsCounter = 0;
    private int dislikesCounter = 0;
    private String blogId;
    private String commentId;
    private boolean isLiked = false;
    private boolean isDisliked = false;
    private int likesCounter = 0;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        detailBlogViewModel =
                ViewModelProviders.of(this).get(DetailBlogViewModel.class);
        View root = inflater.inflate(R.layout.fragment_detail_blog, container, false);

        TextView textViewAuthorName = root.findViewById(R.id.textViewAuthorName);
        TextView textViewEmail = root.findViewById(R.id.textViewEmail);
        TextView textViewTimeStamp = root.findViewById(R.id.textViewTimeStamp);
        TextView textViewBlogTitle = root.findViewById(R.id.editTextBlogTitle);
        TextView textViewBlogDescription = root.findViewById(R.id.textViewBlogDescription);
        final TextView textViewLikesCounter = root.findViewById(R.id.textViewLikesCounter);
        final TextView textViewDisLikesCounter = root.findViewById(R.id.textViewDisLikesCounter);
        final TextView textViewCommentsCounter = root.findViewById(R.id.textViewCommentsCounter);
        final EditText editTextComment = root.findViewById(R.id.editTextComment);
        ImageButton imageButton = root.findViewById(R.id.textViewSubmit);

        final RecyclerView recyclerView = root.findViewById(R.id.recyclerView);

        if (getArguments() != null) {
            blogId = getArguments().getString("blogId");
            textViewAuthorName.setText(getArguments().getString("authorName"));
            textViewEmail.setText(getArguments().getString("authorEmail"));
            textViewTimeStamp.setText(getArguments().getString("timeStamp"));
            textViewBlogTitle.setText(getArguments().getString("blogTitle"));
            textViewBlogDescription.setText(getArguments().getString("blogDescription"));
            likesCounter = getArguments().getInt("blogLikes");
            textViewLikesCounter.setText(String.valueOf(likesCounter));
            dislikesCounter = getArguments().getInt("blogDislikes");
            textViewDisLikesCounter.setText(String.valueOf(dislikesCounter));
            commentsCounter = getArguments().getInt("blogComments");
            textViewCommentsCounter.setText(String.valueOf(commentsCounter));
        }

        textViewLikesCounter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (FirebaseAuth.getInstance().getCurrentUser() == null) {
                    textViewLikesCounter.setError("Only logged in User can Like");
                } if (isLiked){
                    textViewLikesCounter.setError("You can Like Only Once");
                } else {
                    isLiked = true;
                    if (isLiked) {
                        likesCounter += 1;
                    }
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("blogs2020");

                    databaseReference.child(blogId).child("likesCounter").setValue(likesCounter)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getActivity(), "Likes Counter Upated", Toast.LENGTH_SHORT).show();
                                        textViewLikesCounter.setText(String.valueOf(likesCounter));
                                    }
                                }
                            });

                }
            }
        });

        textViewDisLikesCounter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (FirebaseAuth.getInstance().getCurrentUser() == null) {
                    textViewDisLikesCounter.setError("Only logged in User can Dislike");
                } if (isDisliked) {
                    textViewDisLikesCounter.setError("You can Dislike Only Once");
                } else {
                    isDisliked = true;
                    if (isDisliked) {
                        dislikesCounter += 1;
                    }
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("blogs2020");

                    databaseReference.child(blogId).child("dislikesCounter").setValue(dislikesCounter)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getActivity(), "Dislikes Counter Upated", Toast.LENGTH_SHORT).show();
                                        textViewDisLikesCounter.setText(String.valueOf(dislikesCounter));
                                    }
                                }
                            });
                }
            }
        });


        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(editTextComment.getText().toString().trim())) {
                    editTextComment.setError("Comment can't be empty");
                    editTextComment.requestFocus();
                } else if (FirebaseAuth.getInstance().getCurrentUser() == null) {
                    editTextComment.setError("Only Logged In User can Comment");
                    Toast.makeText(getActivity(), "Only Logged In User can Comment", Toast.LENGTH_SHORT).show();
                } else {

                    Log.d(TAG, "onClick: " + blogId);

                    //SimpleDateFormat formatter = new SimpleDateFormat("yyMMddHHmmssZ");


                    String commentUser = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
                    String commentUserUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    String commentText = editTextComment.getText().toString().trim();

                    commentsCounter += 1;


                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("blogs2020");
                    commentId = databaseReference.push().getKey();
                    Date date = new Date();
                    String timeStamp = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.SHORT).format(date);
                    Comment comment = new Comment(commentId, commentUser, timeStamp ,commentUserUid, commentText);
                    Map<String, Object> mapComment = comment.toMap();

                    Map<String, Object> childUpdates = new HashMap<>();
                    childUpdates.put("/comments/" + commentId, mapComment);
                    childUpdates.put("commentsCounter", commentsCounter);


                    databaseReference.child(blogId).updateChildren(childUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getActivity(), "Comment Added", Toast.LENGTH_SHORT).show();
                                DetailBlogFragment.this.commentId = commentId;
                                editTextComment.setText("");
                                editTextComment.clearFocus();
                                textViewCommentsCounter.setText(String.valueOf(commentsCounter));
                            }
                        }
                    });

                }
            }
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("blogs2020");
        Query query = databaseReference.child(blogId).child("comments").orderByKey();

        FirebaseRecyclerOptions<Comment> firebaseRecyclerOptions =
                new FirebaseRecyclerOptions.Builder<Comment>()
                .setLifecycleOwner(getViewLifecycleOwner())
                .setQuery(query, Comment.class)
                .build();

        DetailedBlogRecyclerViewAdapter detailedBlogRecyclerViewAdapter =
                new DetailedBlogRecyclerViewAdapter(firebaseRecyclerOptions);

        recyclerView.setAdapter(detailedBlogRecyclerViewAdapter);





        detailBlogViewModel.getCommentsList(blogId).observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String comments) {

            }
        });

        return root;
    }
}