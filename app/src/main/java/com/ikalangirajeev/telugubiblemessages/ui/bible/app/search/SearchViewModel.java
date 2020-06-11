package com.ikalangirajeev.telugubiblemessages.ui.bible.app.search;

import android.app.Application;
import android.graphics.Color;
import android.os.AsyncTask;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.ikalangirajeev.telugubiblemessages.ui.bible.app.Bible;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchViewModel extends AndroidViewModel {

    private static final String TAG = "SearchViewModel";

    private MutableLiveData<List<SearchData>> mText;
    private List<SearchData> searchDataList;
    private String searchableString;

    public SearchViewModel(@NonNull Application application) {
        super(application);
        mText = new MutableLiveData<>();
        searchDataList = new ArrayList<>();
    }

    public void setSearchableString(String searchableString) {
        this.searchableString = searchableString;
    }

    public LiveData<List<SearchData>> getSearchDataList() {


        searchDataList.clear();
        try {
            searchDataList = new MyAsyncTask(getApplication()).execute(searchableString).get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        mText.setValue(searchDataList);

        return mText;
    }

    private static class MyAsyncTask extends AsyncTask<String, Void, List<SearchData>> {

        List<SearchData> searchDataList;

        private String books[] = {
                "ఆదికాండము", "నిర్గమకాండము", "లేవీయకాండము", "సంఖ్యాకాండము", "ద్వితీయోపదేశకాండమ", "యెహొషువ",
                "న్యాయాధిపతులు", "రూతు", "సమూయేలు మొదటి గ్రంథము", "సమూయేలు రెండవ గ్రంథము", "రాజులు మొదటి గ్రంథము",
                "రాజులు రెండవ గ్రంథము", "దినవృత్తాంతములు మొదటి గ్రంథము", "దినవృత్తాంతములు రెండవ గ్రంథము", "ఎజ్రా", "నెహెమ్యా", "ఎస్తేరు", "యోబు గ్రంథము",
                "కీర్తనల గ్రంథము", "సామెతలు", "ప్రసంగి", "పరమగీతము", "యెషయా గ్రంథము", "యిర్మీయా", "విలాపవాక్యములు", "యెహెజ్కేలు",
                "దానియేలు", "హొషేయ", "యోవేలు", "ఆమోసు", "ఓబద్యా", "యోనా", "మీకా", "నహూము", "హబక్కూకు", "జెఫన్యా",
                "హగ్గయి", "జెకర్యా", "మలాకీ", "మత్తయి సువార్త", "మార్కు సువార్త", "లూకా సువార్త", "యోహాను సువార్త", "అపొస్తలుల కార్యములు",
                "రోమీయులకు", "1 కొరింథీయులకు", "2 కొరింథీయులకు", "గలతీయులకు", "ఎఫెసీయులకు", "ఫిలిప్పీయులకు",
                "కొలొస్సయులకు", "1 థెస్సలొనీకయులకు", "2 థెస్సలొనీకయులకు", "1 తిమోతికి", "2 తిమోతికి", "తీతుకు", "ఫిలేమోనుకు",
                "హెబ్రీయులకు", "యాకోబు", "1 పేతురు", "2 పేతురు", "1 యోహాను", "2 యోహాను", "3 యోహాను", "యూదా", "ప్రకటన గ్రంథము"};

        Application application;
        String searchableString;

        public MyAsyncTask(Application application) {
            this.application = application;
            searchDataList = new ArrayList<>();
        }

        @Override
        protected List<SearchData> doInBackground(String... strings) {

            searchDataList.clear();
            searchableString = strings[0];

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


            Pattern pattern = Pattern.compile(searchableString);
            BackgroundColorSpan backgroundColorSpan = new BackgroundColorSpan(Color.YELLOW);
            ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.BLUE);

            for (int i = 0; i < bible.getBooks().size(); i++) {
                for (int j = 0; j < bible.getBooks().get(i).getChapters().size(); j++) {
                    for (int k = 0; k < bible.getBooks().get(i).getChapters().get(j).getVerses().size(); k++) {

                        Matcher matcher = pattern.matcher(bible.getBooks().get(i).getChapters().get(j).
                                getVerses().get(k).getVerse());

                        while (matcher.find()) {
                            Spannable spannable = new SpannableString(bible.getBooks().get(i).getChapters().get(j).
                                    getVerses().get(k).getVerse());
                                spannable.setSpan(foregroundColorSpan, matcher.start(), matcher.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                spannable.setSpan(backgroundColorSpan, matcher.start(), matcher.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                            int chapterNumber = j + 1;
                            int verseNumber = k + 1;

                            SearchData searchData = new SearchData(spannable, books[i] + " " +
                                    chapterNumber + ":" + verseNumber);

                            searchData.setBookName(books[i]);
                            searchData.setBookNumber(i);
                            searchData.setChapterNumber(j);
                            searchData.setVerseNumber(k);

                            searchDataList.add(searchData);

                        }
                    }
                }
            }
            return searchDataList;
        }
    }
}