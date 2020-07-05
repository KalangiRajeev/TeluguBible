package com.ikalangirajeev.telugubiblemessages.ui.dictionary;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ikalangirajeev.telugubiblemessages.R;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class    DictRecyclerViewAdapter extends RecyclerView.Adapter<DictRecyclerViewAdapter.MyViewHolder> {

    private Context context;
    private int layoutResource;
    private List<DictEngTel> dictList;
    private String searchString;

    public DictRecyclerViewAdapter(Context context, int layoutResource, List<DictEngTel> dictList, String searchString) {
        this.context = context;
        this.layoutResource = layoutResource;
        this.dictList = dictList;
        this.searchString = searchString;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(layoutResource, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(itemView);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        DictEngTel dictEngTel = dictList.get(position);
        holder.setData(dictEngTel);
    }

    @Override
    public int getItemCount() {
        return dictList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView textViewDictWord, textViewDictPos, textViewDictMeaning;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewDictWord = itemView.findViewById(R.id.textViewDictWord);
            textViewDictPos = itemView.findViewById(R.id.textViewDictPos);
            textViewDictMeaning = itemView.findViewById(R.id.textViewDictMeaning);
        }

        public void setData(DictEngTel dictEngTel){
            String dictWord = dictEngTel.getEng_word();
            this.textViewDictWord.setText(dictWord.substring(0,1).toUpperCase() + dictWord.substring(1).toLowerCase());
            this.textViewDictPos.setText(dictEngTel.getPos());


            String dictMeaning = dictEngTel.getMeaning();
            dictMeaning = dictMeaning.substring(0,1).toUpperCase()+dictMeaning.substring(1).toLowerCase();
            dictMeaning = dictMeaning.replaceAll(Pattern.quote("*"), dictEngTel.getEng_word().toLowerCase());
            Pattern pattern = Pattern.compile(searchString);
            Matcher matcher = pattern.matcher(dictMeaning);
            BackgroundColorSpan backgroundColorSpan = new BackgroundColorSpan(Color.YELLOW);
            ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.BLUE);
            Spannable spannable = new SpannableString(dictMeaning);
            while (matcher.find()){
                spannable.setSpan(foregroundColorSpan, matcher.start(), matcher.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannable.setSpan(backgroundColorSpan, matcher.start(), matcher.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            this.textViewDictMeaning.setText(spannable);
        }
    }
}
