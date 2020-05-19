package com.ikalangirajeev.telugubiblemessages.ui.bible.app.verses;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.ikalangirajeev.telugubiblemessages.ui.bible.app.Bible;
import com.ikalangirajeev.telugubiblemessages.ui.bible.app.Data;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class VersesViewModel extends AndroidViewModel {

    private static final String TAG = "VersesViewModel";

    private MutableLiveData<List<Data>> mText;
    List<Data> blogIndexList;
    private int bookNumber;
    private int chapterNumber;

    public VersesViewModel(@NonNull Application application) {
        super(application);
        mText = new MutableLiveData<>();
        blogIndexList = new ArrayList<>();
    }

    public void setBookNumber(int bookNumber) {
        this.bookNumber = bookNumber;
    }

    public void setChapterNumber(int chapterNumber) {
        this.chapterNumber = chapterNumber;
    }

    public LiveData<List<Data>> getData() {

        Bible bible = null;
        try {
            bible = new MyAsyncTask(getApplication()).execute().get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        blogIndexList.clear();
        for (int i = 0; i < bible.getBooks().get(bookNumber).getChapters().get(chapterNumber).getVerses().size(); i++) {
            Data blogIndex = new Data(bible.getBooks().get(bookNumber).getChapters().get(chapterNumber).getVerses().get(i).getVerse(),
                    String.valueOf(i + 1) + "వ వచనము");
            blogIndexList.add(blogIndex);
        }

        mText.setValue(blogIndexList);
        return mText;
    }

    private static class MyAsyncTask extends AsyncTask<Void, Void, Bible> {

        Application application;

        public MyAsyncTask(Application application) {
            this.application = application;
        }

        @Override
        protected Bible doInBackground(Void... voids) {

            String json = null;
            try {
                InputStream inputStream = application.getAssets().open("telugu_bible.json");
                int size = inputStream.available();

                byte[] buffer = new byte[size];
                inputStream.read(buffer);
                inputStream.close();

                json = new String(buffer, "UTF-8");

            } catch (Exception e) {
                e.printStackTrace();
            }
            Gson gson = new Gson();
            Bible bible = gson.fromJson(json, Bible.class);
            return bible;
        }
    }
}