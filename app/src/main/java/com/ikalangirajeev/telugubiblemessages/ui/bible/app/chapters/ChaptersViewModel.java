package com.ikalangirajeev.telugubiblemessages.ui.bible.app.chapters;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
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
import java.util.concurrent.ExecutionException;

public class ChaptersViewModel extends AndroidViewModel {

    private static final String TAG = "ChaptersViewModel";

    private MutableLiveData<List<Data>> mDataList;
    private Integer versesCount;
    private List<Data> dataList;
    private Application application;


    public ChaptersViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
        mDataList = new MutableLiveData<>();
        dataList = new ArrayList<>();

    }


    public LiveData<List<Data>> getText(String bibleSelected, int bookNumber, int chaptersCount) {

        if (bibleSelected.equals("bible_english")) {
            dataList.clear();
            for (int i = 1; i <= chaptersCount; i++) {
                try {
                    versesCount = new EnglishBibleAsyncTask(application).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, bookNumber, i).get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Data data = new Data(String.valueOf(i),  versesCount +" Verses" );
                dataList.add(data);
            }
        } else if (bibleSelected.equals("bible_tamil")) {
            dataList.clear();
            for (int i = 1; i <= chaptersCount; i++) {
                try {
                    versesCount = new TamilBibleAsyncTask(application).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, bookNumber, i).get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Data data = new Data(String.valueOf(i),  versesCount + " வசனங்கள்");
                dataList.add(data);
            }
        } else if (bibleSelected.equals("bible_kannada")){
            dataList.clear();
            for (int i = 1; i <= chaptersCount; i++){
                try {
                    versesCount = new KannadaBibleAsyncTask(application).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, bookNumber, i).get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Data data = new Data(String.valueOf(i), versesCount + " ಪದ್ಯಗಳು" );
                dataList.add(data);
            }
        } else if (bibleSelected.equals("bible_hindi")){
            dataList.clear();
            for (int i = 1; i <= chaptersCount; i++){
                try {
                    versesCount = new HindiBibleAsyncTask(application).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, bookNumber, i).get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Data data = new Data(String.valueOf(i), "   " + versesCount + " छंद   " );
                dataList.add(data);
            }
        } else if (bibleSelected.equals("bible_malayalam")){
            dataList.clear();
            for (int i = 1; i <= chaptersCount; i++){
                try {
                    versesCount = new MalayalamBibleAsyncTask(application).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, bookNumber, i).get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Data data = new Data(String.valueOf(i),  versesCount + " വാക്യങ്ങൾ" );
                dataList.add(data);
            }
        } else {
           dataList.clear();
           for (int i = 1; i <= chaptersCount;i++){
               try {
                   versesCount = new TeluguBibleAsyncTask(application).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, bookNumber, i).get();
               } catch (ExecutionException e) {
                   e.printStackTrace();
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
               Data data = new Data(String.valueOf(i), versesCount + " వచనాలు");
               dataList.add(data);
           }
        }

        mDataList.setValue(dataList);
        return mDataList;
    }

    private static class EnglishBibleAsyncTask extends AsyncTask<Integer, Void, Integer> {

        private EnglishBibleDao englishBibleDao;
        private Integer versesCount;

        public EnglishBibleAsyncTask(Application application) {
            BibleDatabase bibleDatabase = BibleDatabase.getBibleDatabase(application);
            englishBibleDao = bibleDatabase.englishBibleDao();
        }
        @Override
        protected Integer doInBackground(Integer... integers) {
            versesCount = englishBibleDao.getVersesCount(integers[0], integers[1]);
            return versesCount;
        }
    }
    private static class TamilBibleAsyncTask extends AsyncTask<Integer, Void, Integer> {

        private TamilBibleDao tamilBibleDao;
        private Integer versesCount;

        public TamilBibleAsyncTask(Application application) {
            BibleDatabase bibleDatabase = BibleDatabase.getBibleDatabase(application);
            tamilBibleDao = bibleDatabase.tamilBibleDao();
        }
        @Override
        protected Integer doInBackground(Integer... integers) {
            versesCount = tamilBibleDao.getVersesCount(integers[0], integers[1]);
            return versesCount;
        }
    }
    private static class KannadaBibleAsyncTask extends AsyncTask<Integer, Void, Integer> {

        private KannadaBibleDao kannadaBibleDao;
        private Integer versesCount;

        public KannadaBibleAsyncTask(Application application) {
            BibleDatabase bibleDatabase = BibleDatabase.getBibleDatabase(application);
            kannadaBibleDao = bibleDatabase.kannadaBibleDao();
        }
        @Override
        protected Integer doInBackground(Integer... integers) {
            versesCount = kannadaBibleDao.getVersesCount(integers[0], integers[1]);
            return versesCount;
        }
    }
    private static class TeluguBibleAsyncTask extends AsyncTask<Integer, Void, Integer> {

        private TeluguBibleDao teluguBibleDao;
        private Integer versesCount;

        public TeluguBibleAsyncTask(Application application) {
            BibleDatabase bibleDatabase = BibleDatabase.getBibleDatabase(application);
            teluguBibleDao = bibleDatabase.teluguBibleDao();
        }
        @Override
        protected Integer doInBackground(Integer... integers) {
            versesCount = teluguBibleDao.getVersesCount(integers[0], integers[1]);
            return versesCount;
        }
    }
    private static class HindiBibleAsyncTask extends AsyncTask<Integer, Void, Integer> {

        private HindiBibleDao hindiBibleDao;
        private Integer versesCount;

        public HindiBibleAsyncTask(Application application) {
            BibleDatabase bibleDatabase = BibleDatabase.getBibleDatabase(application);
            hindiBibleDao = bibleDatabase.hindiBibleDao();
        }
        @Override
        protected Integer doInBackground(Integer... integers) {
            versesCount = hindiBibleDao.getVersesCount(integers[0], integers[1]);
            return versesCount;
        }
    }

    private static class MalayalamBibleAsyncTask extends AsyncTask<Integer, Void, Integer> {

        private MalayalamBibleDao malayalamBibleDao;
        private Integer versesCount;

        public MalayalamBibleAsyncTask(Application application) {
            BibleDatabase bibleDatabase = BibleDatabase.getBibleDatabase(application);
            malayalamBibleDao = bibleDatabase.malayalamBibleDao();
        }
        @Override
        protected Integer doInBackground(Integer... integers) {
            versesCount = malayalamBibleDao.getVersesCount(integers[0], integers[1]);
            return versesCount;
        }
    }
}