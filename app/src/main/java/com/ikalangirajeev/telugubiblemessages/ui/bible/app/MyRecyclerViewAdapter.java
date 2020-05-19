package com.ikalangirajeev.telugubiblemessages.ui.bible.app;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ikalangirajeev.telugubiblemessages.R;

import java.util.ArrayList;
import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder> {

    private static final String TAG = "MyRecyclerViewAdapter";
    private Context context;
    private List<Data> blogIndexList = new ArrayList<>();
    private LayoutInflater layoutInflater;
    private OnItemClickListener onItemClickListener;
    private int layoutResource;
    private int highLightPosition;

    public MyRecyclerViewAdapter(Context context, int layoutResource, List<Data> blogIndexList) {
        this.context = context;
        this.layoutResource = layoutResource;
        layoutInflater = LayoutInflater.from(context);
        this.blogIndexList = blogIndexList;
        setHasStableIds(true);
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = layoutInflater.inflate(layoutResource, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Data blogIndex = blogIndexList.get(position);
        holder.setData(blogIndex);
        holder.setListeners();

    }

    public Data getDataAt(int position){
        return blogIndexList.get(position);
    }

    public void setHighlightColor(int position){
        this.highLightPosition = position;
    }

    @Override
    public int getItemCount() {
        return blogIndexList.size();
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

        TextView headerTextView;
        TextView bodyTextView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            headerTextView = itemView.findViewById(R.id.textViewBooks);
            bodyTextView = itemView.findViewById(R.id.textViewChapters);
        }

        public void setData(Data blogIndex) {
            this.headerTextView.setText(blogIndex.getHeader());
            this.bodyTextView.setText(blogIndex.getBody());
        }

        public void setListeners() {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (onItemClickListener != null && position != RecyclerView.NO_POSITION) {
                        onItemClickListener.OnItemClick(blogIndexList.get(position), position);

                        Log.d(TAG, String.valueOf(blogIndexList.get(position).getHeader()));
                    }
                }
            });
        }
    }
    public interface OnItemClickListener {
        void OnItemClick(Data blogIndex, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
