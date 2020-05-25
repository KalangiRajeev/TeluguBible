package com.ikalangirajeev.telugubiblemessages.ui.bible.app.linkedrefs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.rpc.Help;
import com.ikalangirajeev.telugubiblemessages.R;

import java.util.ArrayList;
import java.util.List;

public class BottomSheetRecyclerViewAdapter extends RecyclerView.Adapter<BottomSheetRecyclerViewAdapter.MyViewHolder> {

    private Context context;
    private int layoutResource;
    private LayoutInflater layoutInflater;
    private List<LinkVerse> linkVerseList;
    public BottomSheetRecyclerViewAdapter(Context context, int layoutResource, List<LinkVerse> linkVerseList) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.layoutResource = layoutResource;
        this.linkVerseList = linkVerseList;
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
        LinkVerse linkVerse = linkVerseList.get(position);
        holder.setData(linkVerse);
    }

    @Override
    public int getItemCount() {
        return linkVerseList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView headerTextView, bodyTextView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            headerTextView = itemView.findViewById(R.id.textViewHeader);
            bodyTextView = itemView.findViewById(R.id.textViewBody);
        }

        public void setData(LinkVerse linkVerse) {
            headerTextView.setText(linkVerse.getHeader());
            bodyTextView.setText(linkVerse.getBody());
        }
    }
}
