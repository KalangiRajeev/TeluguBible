package com.ikalangirajeev.telugubiblemessages.ui.bible.app.verses;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
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

public class VersesViewModel extends AndroidViewModel {

    private static final String TAG = "VersesViewModel";


    private List<Data> dataList;
    private MutableLiveData<List<Data>> mutableLiveData;

    private int bookNumber;
    private int chapterNumber;
    private Application application;

    public VersesViewModel(@NonNull Application application) {
        super(application);
        dataList = new ArrayList<>();
        mutableLiveData = new MutableLiveData<>();
    }

    public LiveData<List<Data>> getData(String bibleSelected, String bookName, int bookNumber, int chapterNumber) throws ExecutionException, InterruptedException {

        if (bibleSelected.equals("bible_english")) {
           List<EnglishBible> englishBibleList = new EnglishAsyncTask(application).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, bookNumber, chapterNumber).get();
           for (EnglishBible verse : englishBibleList){
               Data data = new Data(bookName + " " + verse.getChapter() + ":" + verse.getVersecount(), verse.getVerse(), verse.getVerseid());
               dataList.add(data);
           }
        } else if (bibleSelected.equals("bible_tamil")){
            List<TamilBible> tamilBibleList = new TamilAsyncTask(application).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, bookNumber, chapterNumber).get();
            for (TamilBible verse : tamilBibleList){
                Data data = new Data(bookName + " " + verse.getChapter() + ":" + verse.getVersecount(), verse.getVerse(), verse.getVerseid());
                dataList.add(data);
            }
        } else if (bibleSelected.equals("bible_kannada")){
            List<KannadaBible> kannadaBibleList = new KannadaAsyncTask(application).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, bookNumber, chapterNumber).get();
            for (KannadaBible verse : kannadaBibleList){
                Data data = new Data(bookName + " " + verse.getChapter() + ":" + verse.getVersecount(), verse.getVerse(), verse.getVerseid());
                dataList.add(data);
            }
        } else if (bibleSelected.equals("bible_hindi")){
            List<HindiBible> hindiBibleList = new HindiAsyncTask(application).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, bookNumber, chapterNumber).get();
            for (HindiBible verse : hindiBibleList){
                Data data = new Data(bookName + " " + verse.getChapter() + ":" + verse.getVersecount(), verse.getVerse(), verse.getVerseid());
                dataList.add(data);
            }
        } else if (bibleSelected.equals("bible_malayalam")){
            List<MalayalamBible> malayalamBibleList = new MalayalamAsyncTask(application).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, bookNumber, chapterNumber).get();
            for (MalayalamBible verse : malayalamBibleList){
                Data data = new Data(bookName + " " + verse.getChapter() + ":" + verse.getVersecount(), verse.getVerse(), verse.getVerseid());
                dataList.add(data);
            }
        } else {
            List<TeluguBible> teluguBibleList = new TeluguAsyncTask(application).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, bookNumber, chapterNumber).get();
            for (TeluguBible verse : teluguBibleList){
                Data data = new Data(bookName + " " + verse.getChapter() + ":" + verse.getVersecount(), verse.getVerse(), verse.getVerseid());
                dataList.add(data);
            }
        }

        mutableLiveData.setValue(dataList);
        return mutableLiveData;
    }

    private static class EnglishAsyncTask extends AsyncTask<Integer, Void, List<EnglishBible>>{
        private EnglishBibleDao englishBibleDao;

        public EnglishAsyncTask (Application application){
            BibleDatabase bibleDatabase = BibleDatabase.getBibleDatabase(application);
            englishBibleDao = bibleDatabase.englishBibleDao();
        }
        @Override
        protected List<EnglishBible> doInBackground(Integer... integers) {
            return englishBibleDao.getEnglishBibleList(integers[0], integers[1]);
        }
    }

    private static class TamilAsyncTask extends AsyncTask<Integer, Void, List<TamilBible>>{
        private TamilBibleDao tamilBibleDao;

        public TamilAsyncTask (Application application){
            BibleDatabase bibleDatabase = BibleDatabase.getBibleDatabase(application);
            tamilBibleDao = bibleDatabase.tamilBibleDao();
        }
        @Override
        protected List<TamilBible> doInBackground(Integer... integers) {
            return tamilBibleDao.getTamilBibleList(integers[0], integers[1]);
        }
    }
    private static class KannadaAsyncTask extends AsyncTask<Integer, Void, List<KannadaBible>>{
        private KannadaBibleDao kannadaBibleDao;

        public KannadaAsyncTask (Application application){
            BibleDatabase bibleDatabase = BibleDatabase.getBibleDatabase(application);
            kannadaBibleDao = bibleDatabase.kannadaBibleDao();
        }
        @Override
        protected List<KannadaBible> doInBackground(Integer... integers) {
            return kannadaBibleDao.getKannadaBibleList(integers[0], integers[1]);
        }
    }
    private static class TeluguAsyncTask extends AsyncTask<Integer, Void, List<TeluguBible>>{
        private TeluguBibleDao teluguBibleDao;

        public TeluguAsyncTask (Application application){
            BibleDatabase bibleDatabase = BibleDatabase.getBibleDatabase(application);
            teluguBibleDao = bibleDatabase.teluguBibleDao();
        }
        @Override
        protected List<TeluguBible> doInBackground(Integer... integers) {
            return teluguBibleDao.getTeluguBibleList(integers[0], integers[1]);
        }
    }
    private static class HindiAsyncTask extends AsyncTask<Integer, Void, List<HindiBible>>{
        private HindiBibleDao hindiBibleDao;

        public HindiAsyncTask (Application application){
            BibleDatabase bibleDatabase = BibleDatabase.getBibleDatabase(application);
            hindiBibleDao = bibleDatabase.hindiBibleDao();
        }
        @Override
        protected List<HindiBible> doInBackground(Integer... integers) {
            return hindiBibleDao.getHindiBibleList(integers[0], integers[1]);
        }
    }
    private static class MalayalamAsyncTask extends AsyncTask<Integer, Void, List<MalayalamBible>>{
        private MalayalamBibleDao malayalamBibleDao;

        public MalayalamAsyncTask (Application application){
            BibleDatabase bibleDatabase = BibleDatabase.getBibleDatabase(application);
            malayalamBibleDao = bibleDatabase.malayalamBibleDao();
        }
        @Override
        protected List<MalayalamBible> doInBackground(Integer... integers) {
            return malayalamBibleDao.getMalayalamBibleList(integers[0], integers[1]);
        }
    }
}