package com.ikalangirajeev.telugubiblemessages.ui.bible.app.chapters;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.core.os.HandlerCompat;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ikalangirajeev.telugubiblemessages.ui.bible.app.Data;
import com.ikalangirajeev.telugubiblemessages.ui.roombible.BibleDatabase;
import com.ikalangirajeev.telugubiblemessages.ui.roombible.EnglishBibleDao;
import com.ikalangirajeev.telugubiblemessages.ui.roombible.HindiBibleDao;
import com.ikalangirajeev.telugubiblemessages.ui.roombible.KannadaBibleDao;
import com.ikalangirajeev.telugubiblemessages.ui.roombible.MalayalamBibleDao;
import com.ikalangirajeev.telugubiblemessages.ui.roombible.TamilBibleDao;
import com.ikalangirajeev.telugubiblemessages.ui.roombible.TeluguBibleDao;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChaptersViewModel extends AndroidViewModel {

    private static final String TAG = "ChaptersViewModel";

    private MutableLiveData<List<Data>> mDataList;
    private Integer versesCount;
    private List<Data> dataList;
    private Application application;

    private ExecutorService executorService = Executors.newFixedThreadPool(10);
    private Handler handler = HandlerCompat.createAsync(Looper.getMainLooper());

    public ChaptersViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
        mDataList = new MutableLiveData<>();
        dataList = new ArrayList<>();
    }


    public LiveData<List<Data>> getText(String bibleSelected, int bookNumber, int chaptersCount) {
        dataList.clear();

        executorService.execute(new Runnable() {
            @Override
            public void run() {

                switch (bibleSelected) {
                    case "bible_english":
                        for (int i = 1; i <= chaptersCount; i++) {
                            BibleDatabase bibleDatabase = BibleDatabase.getBibleDatabase(application);
                            EnglishBibleDao englishBibleDao = bibleDatabase.englishBibleDao();
                            versesCount = englishBibleDao.getVersesCount(bookNumber, i);
                            Data data = new Data(String.valueOf(i), versesCount + " Verses");
                            dataList.add(data);
                        }
                        break;
                    case "bible_tamil":
                        for (int i = 1; i <= chaptersCount; i++) {
                            BibleDatabase bibleDatabase = BibleDatabase.getBibleDatabase(application);
                            TamilBibleDao tamilBibleDao = bibleDatabase.tamilBibleDao();
                            versesCount = tamilBibleDao.getVersesCount(bookNumber, i);
                            Data data = new Data(String.valueOf(i), versesCount + " வசனங்கள்");
                            dataList.add(data);
                        }
                        break;
                    case "bible_kannada":
                        for (int i = 1; i <= chaptersCount; i++) {
                            BibleDatabase bibleDatabase = BibleDatabase.getBibleDatabase(application);
                            KannadaBibleDao kannadaBibleDao = bibleDatabase.kannadaBibleDao();
                            versesCount = kannadaBibleDao.getVersesCount(bookNumber, i);
                            Data data = new Data(String.valueOf(i), versesCount + " ಪದ್ಯಗಳು");
                            dataList.add(data);
                        }
                        break;
                    case "bible_hindi":
                        for (int i = 1; i <= chaptersCount; i++) {
                            BibleDatabase bibleDatabase = BibleDatabase.getBibleDatabase(application);
                            HindiBibleDao hindiBibleDao = bibleDatabase.hindiBibleDao();
                            versesCount = hindiBibleDao.getVersesCount(bookNumber, i);
                            Data data = new Data(String.valueOf(i), "   " + versesCount + " छंद   ");
                            dataList.add(data);
                        }
                        break;
                    case "bible_malayalam":

                        for (int i = 1; i <= chaptersCount; i++) {
                            BibleDatabase bibleDatabase = BibleDatabase.getBibleDatabase(application);
                            MalayalamBibleDao malayalamBibleDao = bibleDatabase.malayalamBibleDao();
                            versesCount = malayalamBibleDao.getVersesCount(bookNumber, i);
                            Data data = new Data(String.valueOf(i), versesCount + " വാക്യങ്ങൾ");
                            dataList.add(data);
                        }
                        break;
                    default:
                        for (int i = 1; i <= chaptersCount; i++) {
                            BibleDatabase bibleDatabase = BibleDatabase.getBibleDatabase(application);
                            TeluguBibleDao teluguBibleDao = bibleDatabase.teluguBibleDao();
                            versesCount = teluguBibleDao.getVersesCount(bookNumber, i);
                            Data data = new Data(String.valueOf(i), versesCount + " వచనాలు");
                            dataList.add(data);
                        }
                        break;
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        mDataList.setValue(dataList);
                    }
                });
            }
        });
        return mDataList;
    }
}