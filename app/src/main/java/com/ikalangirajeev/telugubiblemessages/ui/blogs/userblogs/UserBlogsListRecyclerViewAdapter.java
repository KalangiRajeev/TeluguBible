package com.ikalangirajeev.telugubiblemessages.ui.blogs.userblogs;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.ikalangirajeev.telugubiblemessages.R;
import com.ikalangirajeev.telugubiblemessages.ui.blogs.displayblogs.BlogIndex;

public class UserBlogsListRecyclerViewAdapter extends FirebaseRecyclerAdapter<BlogIndex, UserBlogsListRecyclerViewAdapter.MyViewHolder> {

    private static final String TAG = "BlogsListRecyclerViewAd";
    private OnItemClickListener onItemClickListener;
    private int layoutResource;


    public UserBlogsListRecyclerViewAdapter(@NonNull FirebaseRecyclerOptions firebaseRecyclerOptions, int layoutResource) {
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
        TextView textViewDelete;

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
            textViewDelete = itemView.findViewById(R.id.textViewDelete);
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

            textViewDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getSnapshots().getSnapshot(getAdapterPosition()).getRef().removeValue()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful())
                                        Toast.makeText(itemView.getContext(), "Item Deleted", Toast.LENGTH_LONG).show();
                                    else
                                        Toast.makeText(itemView.getContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                }
                            });
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
