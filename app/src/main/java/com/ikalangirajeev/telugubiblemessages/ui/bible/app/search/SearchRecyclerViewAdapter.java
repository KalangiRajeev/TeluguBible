package com.ikalangirajeev.telugubiblemessages.ui.bible.app.search;


import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchRecyclerViewAdapter extends RecyclerView.Adapter<SearchRecyclerViewAdapter.MyViewHolder> {

    private static final String TAG = "MyRecyclerViewAdapter";
    private Context context;
    private List<SearchData> dataList = new ArrayList<>();
    private LayoutInflater layoutInflater;
    private OnRVItemClickListener onRVItemClickListener;
    private int layoutResource;
    private String searchString;

    public SearchRecyclerViewAdapter(Context context, int layoutResource, List<SearchData> dataList, String searchString) {
        this.context = context;
        this.layoutResource = layoutResource;
        layoutInflater = LayoutInflater.from(context);
        this.dataList = dataList;
        this.searchString = searchString;
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

        SearchData searchData = dataList.get(position);
        holder.setData(searchData);
        holder.setListeners();
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

        public void setData(SearchData data) {
            this.headerTextView.setText(data.getHeader());
            Pattern pattern = Pattern.compile(searchString);
            Matcher matcher = pattern.matcher(data.getBody());
            BackgroundColorSpan backgroundColorSpan = new BackgroundColorSpan(Color.YELLOW);
            ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.BLUE);
            Spannable spannable = new SpannableString(data.getBody());
            while(matcher.find()){
                spannable.setSpan(foregroundColorSpan, matcher.start(), matcher.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannable.setSpan(backgroundColorSpan, matcher.start(), matcher.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            this.bodyTextView.setText(spannable);
        }

        public void setListeners() {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (onRVItemClickListener != null && position != RecyclerView.NO_POSITION) {
                        onRVItemClickListener.OnRVItemClick(dataList.get(position), position);
                    }
                }
            });
        }
    }
    public interface OnRVItemClickListener {
        void OnRVItemClick(SearchData searchData, int position);
    }

    public void setOnRVItemClickListener(OnRVItemClickListener onRVItemClickListener) {
        this.onRVItemClickListener = onRVItemClickListener;
    }
}
