package com.ikalangirajeev.telugubiblemessages.ui.bible.app.books;

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

public class BooksViewModel extends AndroidViewModel {
    private static final String TAG = "BooksViewModel";

    private MutableLiveData<List<Data>> mText;
    List<Data> dataList = new ArrayList<>();

    private String books[] = {
            "ఆదికాండము", "నిర్గమకాండము", "లేవీయకాండము", "సంఖ్యాకాండము", "ద్వితీయోపదేశకాండమ", "యెహొషువ",
            "న్యాయాధిపతులు", "రూతు", "I సమూయేలు", "II సమూయేలు", "I రాజులు",
            "II రాజులు", "I దినవృత్తాంతములు", "II దినవృత్తాంతములు", "ఎజ్రా", "నెహెమ్యా", "ఎస్తేరు", "యోబు",
            "కీర్తనల గ్రంథము", "సామెతలు", "ప్రసంగి", "పరమగీతము", "యెషయా", "యిర్మీయా", "విలాపవాక్యములు", "యెహెజ్కేలు",
            "దానియేలు", "హొషేయ", "యోవేలు", "ఆమోసు", "ఓబద్యా", "యోనా", "మీకా", "నహూము", "హబక్కూకు", "జెఫన్యా",
            "హగ్గయి", "జెకర్యా", "మలాకీ", "మత్తయి సువార్త", "మార్కు సువార్త", "లూకా సువార్త", "యోహాను సువార్త", "అపొస్తలుల కార్యములు",
            "రోమీయులకు", "1 కొరింథీయులకు", "2 కొరింథీయులకు", "గలతీయులకు", "ఎఫెసీయులకు", "ఫిలిప్పీయులకు",
            "కొలొస్సయులకు", "1 థెస్సలొనీకయులకు", "2 థెస్సలొనీకయులకు", "1 తిమోతికి", "2 తిమోతికి", "తీతుకు", "ఫిలేమోనుకు",
            "హెబ్రీయులకు", "యాకోబు", "1 పేతురు", "2 పేతురు", "1 యోహాను", "2 యోహాను", "3 యోహాను", "యూదా", "ప్రకటన గ్రంథము"};


    public BooksViewModel(@NonNull Application application) {
        super(application);
        mText = new MutableLiveData<>();
    }

    public LiveData<List<Data>> getText() {

        Bible bible = null;
        try {
            bible = new MyAsyncTask(getApplication()).execute().get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        dataList.clear();

        for (int i = 0; i < bible.getBooks().size(); i++) {
            Data data = new Data(books[i], "అధ్యాయాలు " + String.valueOf(bible.getBooks().get(i).getChapters().size()), 10);
            dataList.add(data);
        }

        mText.setValue(dataList);
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