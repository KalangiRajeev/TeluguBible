package com.ikalangirajeev.telugubiblemessages.ui.blogs.detailblog;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.ikalangirajeev.telugubiblemessages.R;

public class DetailedBlogRecyclerViewAdapter extends FirebaseRecyclerAdapter<Comment, DetailedBlogRecyclerViewAdapter.ReceivedViewHolder> {

    private static final String TAG = "DetailedBlogRecyclerViewAdapter";


    public DetailedBlogRecyclerViewAdapter(FirebaseRecyclerOptions firebaseRecyclerOptions) {
        super(firebaseRecyclerOptions);
    }


    @NonNull
    @Override
    public ReceivedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_blog_comments, parent, false);
            return new ReceivedViewHolder(view);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position)
    {
        return position;
    }

    @Override
    protected void onBindViewHolder(@NonNull ReceivedViewHolder holder, int position, @NonNull Comment comment) {
        holder.setData(comment);
    }


    class ReceivedViewHolder extends RecyclerView.ViewHolder {
        MaterialTextView textViewName;
        MaterialTextView textViewTimeStamp;
        MaterialTextView textViewComment;
        public ReceivedViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewTimeStamp = itemView.findViewById(R.id.textViewTimeStamp);
            textViewComment = itemView.findViewById(R.id.textViewComment);
        }

        public void setData(Comment comment) {
            this.textViewName.setText(comment.getCommentUser() + ", ");
            this.textViewTimeStamp.setText(comment.getTimeStamp());
            this.textViewComment.setText(comment.getCommentText());
        }
    }

    /* class SentViewHolder extends RecyclerView.ViewHolder {

        MaterialTextView textViewName;
        MaterialTextView textViewTimeStamp;
        MaterialTextView textViewComment;

        public SentViewHolder(@NonNull View view) {
            super(view);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewTimeStamp = itemView.findViewById(R.id.textViewTimeStamp);
            textViewComment = itemView.findViewById(R.id.textViewComment);
        }

        public void setData(Comment comment) {
            this.textViewName.setText(comment.getCommentUser() + ", ");
            this.textViewTimeStamp.setText(comment.getTimeStamp());
            this.textViewComment.setText(comment.getCommentText());
        }

    }*/
}
