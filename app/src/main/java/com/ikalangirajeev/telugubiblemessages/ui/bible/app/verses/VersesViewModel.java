package com.ikalangirajeev.telugubiblemessages.ui.bible.app.verses;

import android.app.Application;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.core.os.HandlerCompat;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ikalangirajeev.telugubiblemessages.ui.bible.app.Data;
import com.ikalangirajeev.telugubiblemessages.ui.roombible.BibleDatabase;
import com.ikalangirajeev.telugubiblemessages.ui.roombible.EnglishBible;
import com.ikalangirajeev.telugubiblemessages.ui.roombible.EnglishBibleDao;
import com.ikalangirajeev.telugubiblemessages.ui.roombible.HindiBible;
import com.ikalangirajeev.telugubiblemessages.ui.roombible.HindiBibleDao;
import com.ikalangirajeev.telugubiblemessages.ui.roombible.KannadaBible;
import com.ikalangirajeev.telugubiblemessages.ui.roombible.KannadaBibleDao;
import com.ikalangirajeev.telugubiblemessages.ui.roombible.MalayalamBible;
import com.ikalangirajeev.telugubiblemessages.ui.roombible.MalayalamBibleDao;
import com.ikalangirajeev.telugubiblemessages.ui.roombible.TamilBible;
import com.ikalangirajeev.telugubiblemessages.ui.roombible.TamilBibleDao;
import com.ikalangirajeev.telugubiblemessages.ui.roombible.TeluguBible;
import com.ikalangirajeev.telugubiblemessages.ui.roombible.TeluguBibleDao;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class VersesViewModel extends AndroidViewModel {

    private static final String TAG = "VersesViewModel";
    private ExecutorService executorService = Executors.newFixedThreadPool(10);
    private Handler handler = HandlerCompat.createAsync(Looper.getMainLooper());

    private List<Data> dataList;
    private MutableLiveData<List<Data>> mutableLiveData;
    private Application application;

    public VersesViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
        dataList = new ArrayList<>();
        mutableLiveData = new MutableLiveData<>();
    }

    public LiveData<List<Data>> getData(String bibleSelected, String bookName, int bookNumber, int chapterNumber) throws ExecutionException, InterruptedException {
        dataList.clear();

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                BibleDatabase bibleDatabase = BibleDatabase.getBibleDatabase(application);

                switch (bibleSelected) {
                    case "bible_english":
                        EnglishBibleDao englishBibleDao = bibleDatabase.englishBibleDao();
                        List<EnglishBible> englishBibleList = englishBibleDao.getEnglishBibleList(bookNumber, chapterNumber);
                        for (EnglishBible verse : englishBibleList) {
                            Data data = new Data(bookName + " " + verse.getChapter() + ":" + verse.getVersecount(), verse.getVerse(), verse.getVerseid());
                            dataList.add(data);
                        }
                        break;
                    case "bible_tamil":
                        TamilBibleDao tamilBibleDao = bibleDatabase.tamilBibleDao();
                        List<TamilBible> tamilBibleList = tamilBibleDao.getTamilBibleList(bookNumber, chapterNumber);
                        for (TamilBible verse : tamilBibleList) {
                            Data data = new Data(bookName + " " + verse.getChapter() + ":" + verse.getVersecount(), verse.getVerse(), verse.getVerseid());
                            dataList.add(data);
                        }
                        break;
                    case "bible_kannada":
                        KannadaBibleDao kannadaBibleDao = bibleDatabase.kannadaBibleDao();
                        List<KannadaBible> kannadaBibleList = kannadaBibleDao.getKannadaBibleList(bookNumber, chapterNumber);
                        for (KannadaBible verse : kannadaBibleList) {
                            Data data = new Data(bookName + " " + verse.getChapter() + ":" + verse.getVersecount(), verse.getVerse(), verse.getVerseid());
                            dataList.add(data);
                        }
                        break;
                    case "bible_hindi":
                        HindiBibleDao hindiBibleDao = bibleDatabase.hindiBibleDao();
                        List<HindiBible> hindiBibleList = hindiBibleDao.getHindiBibleList(bookNumber, chapterNumber);
                        for (HindiBible verse : hindiBibleList) {
                            Data data = new Data(bookName + " " + verse.getChapter() + ":" + verse.getVersecount(), verse.getVerse(), verse.getVerseid());
                            dataList.add(data);
                        }
                        break;
                    case "bible_malayalam":
                        MalayalamBibleDao malayalamBibleDao = bibleDatabase.malayalamBibleDao();
                        List<MalayalamBible> malayalamBibleList = malayalamBibleDao.getMalayalamBibleList(bookNumber, chapterNumber);
                        for (MalayalamBible verse : malayalamBibleList) {
                            Data data = new Data(bookName + " " + verse.getChapter() + ":" + verse.getVersecount(), verse.getVerse(), verse.getVerseid());
                            dataList.add(data);
                        }
                        break;
                    default:
                        TeluguBibleDao teluguBibleDao = bibleDatabase.teluguBibleDao();
                        List<TeluguBible> teluguBibleList = teluguBibleDao.getTeluguBibleList(bookNumber, chapterNumber);
                        for (TeluguBible verse : teluguBibleList) {
                            Data data = new Data(bookName + " " + verse.getChapter() + ":" + verse.getVersecount(), verse.getVerse(), verse.getVerseid());
                            dataList.add(data);
                        }
                        break;
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        mutableLiveData.setValue(dataList);
                    }
                });
            }
        });
        return mutableLiveData;
    }
}