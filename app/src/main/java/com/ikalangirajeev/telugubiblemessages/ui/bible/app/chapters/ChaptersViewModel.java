package com.ikalangirajeev.telugubiblemessages.ui.bible.app.chapters;

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

public class ChaptersViewModel extends AndroidViewModel {

    private static final String TAG = "ChaptersViewModel";

    private MutableLiveData<List<Data>> mBooks;
    List<Data> dataList;
    private int bookNumber;
    private String bookName;


    public ChaptersViewModel(@NonNull Application application) {
        super(application);
        mBooks = new MutableLiveData<>();
        dataList = new ArrayList<>();
    }

    public void setBookNumber(int bookNumber) {
        this.bookNumber = bookNumber;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public LiveData<List<Data>> getText() {

        Bible bible = null;
        try {
            bible = new MyAsyncTask(getApplication()).execute().get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        dataList.clear();
        for (int i = 0; i < bible.getBooks().get(bookNumber).getChapters().size(); i++) {
            Data data = new Data(String.valueOf(i + 1), String.valueOf("వచనములు " + bible.getBooks().get(bookNumber).getChapters().get(i).getVerses().size()), 10);
            dataList.add(data);
            // Log.d(TAG, data.getHeader() + " & " + data.getBody());
        }
        mBooks.setValue(dataList);
        return mBooks;
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
                InputStream inputStream = application.getAssets().open("new_telugu_bible.json");
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