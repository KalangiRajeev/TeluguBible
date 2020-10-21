package com.ikalangirajeev.telugubiblemessages.ui.bible.app;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ikalangirajeev.telugubiblemessages.R;

import java.util.ArrayList;
import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder> {

    private static final String TAG = "MyRecyclerViewAdapter";
    private Context context;
    private List<Data> dataList = new ArrayList<>();
    private LayoutInflater layoutInflater;
    private OnRyVwItemClickListener onRyVwItemClickListener;
    private int layoutResource;
    private int highLightPosition;

    public MyRecyclerViewAdapter(Context context, int layoutResource) {
        this.context = context;
        this.layoutResource = layoutResource;
        layoutInflater = LayoutInflater.from(context);
        setHasStableIds(true);
    }

    public void setDataList(List<Data> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
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

        Data data = dataList.get(position);
        holder.setData(data);
        holder.setListeners();

    }

    public Data getDataAt(int position){
        return dataList.get(position);
    }

    public void setHighlightColor(int position){
        this.highLightPosition = position;
    }

    @Override
    public int getItemCount() {
        return dataList.size();
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

        public void setData(Data data) {
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.slide_in_up);
            animation.setDuration(1000);
            animation.setInterpolator(new AnticipateOvershootInterpolator());
            itemView.startAnimation(animation);

            this.headerTextView.setText(data.getHeader());
            this.bodyTextView.setText(data.getBody());
        }

        public void setListeners() {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (onRyVwItemClickListener != null && position != RecyclerView.NO_POSITION) {
                        onRyVwItemClickListener.OnRyVwItemClick(dataList.get(position), position);
                    }
                }
            });
        }
    }
    public interface OnRyVwItemClickListener {
        void OnRyVwItemClick(Data blogIndex, int position);
    }

    public void setOnRyVwItemClickListener(OnRyVwItemClickListener onRyVwItemClickListener) {
        this.onRyVwItemClickListener = onRyVwItemClickListener;
    }
}
