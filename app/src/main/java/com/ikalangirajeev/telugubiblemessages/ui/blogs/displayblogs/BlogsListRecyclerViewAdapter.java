package com.ikalangirajeev.telugubiblemessages.ui.blogs.displayblogs;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.ikalangirajeev.telugubiblemessages.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class BlogsListRecyclerViewAdapter extends FirebaseRecyclerAdapter<BlogIndex, BlogsListRecyclerViewAdapter.MyViewHolder> {

    private static final String TAG = "BlogsListRecyclerViewAd";
    private OnItemClickListener onItemClickListener;
    private int layoutResource;

    public BlogsListRecyclerViewAdapter(@NonNull FirebaseRecyclerOptions firebaseRecyclerOptions, int layoutResource) {
        super(firebaseRecyclerOptions);
        this.layoutResource = layoutResource;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(layoutResource, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }


    @Override
    protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull BlogIndex model) {
        holder.setData(model);
        holder.setListeners();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textViewAuthorName;
        TextView textViewEmail;
        TextView textViewTimeStamp;
        TextView textViewBlogTitle;
        TextView textViewLikesCounter;
        TextView textViewDisLikesCounter;
        TextView textViewCommentsCounter;
        TextView textViewBlogDescription;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewAuthorName = itemView.findViewById(R.id.textViewAuthorName);
            textViewEmail = itemView.findViewById(R.id.textViewEmail);
            textViewTimeStamp = itemView.findViewById(R.id.textViewTimeStamp);
            textViewBlogTitle = itemView.findViewById(R.id.textViewBlogTitle);
            textViewLikesCounter = itemView.findViewById(R.id.textViewLikesCounter);
            textViewDisLikesCounter = itemView.findViewById(R.id.textViewDisLikesCounter);
            textViewCommentsCounter = itemView.findViewById(R.id.textViewCommentsCounter);
            textViewBlogDescription = itemView.findViewById(R.id.textViewBlogDescription);
        }

        public void setData(BlogIndex blogIndex) {

            this.textViewAuthorName.setText(blogIndex.getAuthorName());
            this.textViewEmail.setText(blogIndex.getEmail());
            this.textViewTimeStamp.setText(blogIndex.getTimeStamp());
            this.textViewBlogTitle.setText(blogIndex.getBlogTitle());
            this.textViewBlogDescription.setText(blogIndex.getBlogDescription());
            this.textViewLikesCounter.setText(String.valueOf(blogIndex.getLikesCounter()));
            this.textViewDisLikesCounter.setText(String.valueOf(blogIndex.getDislikesCounter()));
            this.textViewCommentsCounter.setText(String.valueOf(blogIndex.getCommentsCounter()));
        }

        public void setListeners() {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (onItemClickListener != null && position != RecyclerView.NO_POSITION) {
                        onItemClickListener.OnItemClick(getItem(position), position);
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void OnItemClick(BlogIndex blogIndex, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
