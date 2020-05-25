package com.ikalangirajeev.telugubiblemessages.ui.bible.app.verses;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.ikalangirajeev.telugubiblemessages.ui.bible.app.Bible;
import com.ikalangirajeev.telugubiblemessages.ui.bible.app.Data;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class VersesViewModel extends AndroidViewModel {

    private static final String TAG = "VersesViewModel";

    private MutableLiveData<List<Data>> mText;
    List<Data> dataList;
    private int bookNumber;
    private int chapterNumber;
    Integer refLinks = 0;

    public VersesViewModel(@NonNull Application application) {
        super(application);
        mText = new MutableLiveData<>();
        dataList = new ArrayList<>();
    }

    public void setBookNumber(int bookNumber) {
        this.bookNumber = bookNumber;
    }

    public void setChapterNumber(int chapterNumber) {
        this.chapterNumber = chapterNumber;
    }

    public LiveData<List<Data>> getData() throws ExecutionException, InterruptedException {

        dataList.clear();
        try {
            dataList = new BibleAsyncTask(getApplication()).execute(new Integer[]{bookNumber, chapterNumber}).get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        mText.setValue(dataList);
        return mText;
    }

    private static class BibleAsyncTask extends AsyncTask<Integer, Void, List<Data>> {

        Application application;
        Bible bible = null;

        public BibleAsyncTask(Application application) {
            this.application = application;
        }

        @Override
        protected List<Data> doInBackground(Integer... integers) {
            List<Data> dataList = new ArrayList<>();
            String jsonBibleString = null;
            String jsonRefLinksString = null;
            try {
                InputStream inputStream = application.getAssets().open("new_telugu_bible.json");
                int size = inputStream.available();

                byte[] buffer = new byte[size];
                inputStream.read(buffer);
                inputStream.close();

                jsonBibleString = new String(buffer, "UTF-8");

            } catch (Exception e) {
                e.printStackTrace();
            }
            Gson gson = new Gson();
            Bible bible = gson.fromJson(jsonBibleString, Bible.class);


            for (int i = 0; i < bible.getBooks().get(integers[0]).getChapters().get(integers[1]).getVerses().size(); i++) {

                Integer verseId = bible.getBooks().get(integers[0]).getChapters().get(integers[1]).getVerses().get(i).getVerseId();
                String header = bible.getBooks().get(integers[0]).getChapters().get(integers[1]).getVerses().get(i).getVerse();
                String body = (i + 1) + "వ వచనము";

                Data data = new Data(header, body, verseId);

                dataList.add(data);
            }
            return dataList;
        }

    }
}