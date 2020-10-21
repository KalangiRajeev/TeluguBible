package com.ikalangirajeev.telugubiblemessages.ui.bible.app.verses;


import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.os.HandlerCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.ikalangirajeev.telugubiblemessages.R;
import com.ikalangirajeev.telugubiblemessages.ui.bible.app.Data;

import java.util.ArrayList;
import java.util.List;

public class VersesRecyclerViewAdapter extends RecyclerView.Adapter<VersesRecyclerViewAdapter.MyViewHolder> {

    private static final String TAG = "MyRecyclerViewAdapter";
    private Context context;
    private List<Data> dataList = new ArrayList<>();
    private LayoutInflater layoutInflater;
    private OnItemClickListener onItemClickListener;
    private int layoutResource;
    private int highLightPosition = RecyclerView.NO_POSITION;

    public VersesRecyclerViewAdapter(Context context, int layoutResource) {
        this.context = context;
        this.layoutResource = layoutResource;
        layoutInflater = LayoutInflater.from(context);
        setHasStableIds(true);
    }

    public void setDataList(List<Data> dataList) {
        this.dataList = dataList;
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
        if (highLightPosition == position) {
            holder.setAnimation(position);
            highLightPosition = RecyclerView.NO_POSITION;
        }
//        holder.setListeners();
    }

    public void setHighLightPosition(int highLightPosition) {
        this.highLightPosition = highLightPosition;
    }

    public Data getDataAt(int position) {
        return dataList.get(position);
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
        TextView linkedRefs;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            headerTextView = itemView.findViewById(R.id.textViewBooks);
            bodyTextView = itemView.findViewById(R.id.textViewChapters);
            linkedRefs = itemView.findViewById(R.id.linkedRefs);
        }

        public void setData(Data data) {
            this.headerTextView.setText(data.getHeader());
            this.bodyTextView.setText(data.getBody());
            this.linkedRefs.setText(String.valueOf(data.getRefLink()));
        }

        public void setAnimation(int position) {
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.slide_in_left);
            animation.setDuration(1000);
            animation.setInterpolator(new AnticipateOvershootInterpolator());
            itemView.startAnimation(animation);
        }

//        public void setListeners() {
//            itemView.setOnLongClickListener(new View.OnLongClickListener() {
//                @Override
//                public boolean onLongClick(View v) {
//
//                    int position = getAdapterPosition();
//
//                    MyViewHolder.this.headerTextView.setBackgroundColor(Color.CYAN);
//
//                    if (onItemClickListener != null && position != RecyclerView.NO_POSITION) {
//                        onItemClickListener.OnItemClick(dataList.get(position), position);
//
//                        Log.d(TAG, String.valueOf(dataList.get(position).getHeader()));
//
//                    }
//                    return false;
//                }
//            });
//        }
    }


    public interface OnItemClickListener {
        void OnItemClick(Data data, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
