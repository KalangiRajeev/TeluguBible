package com.ikalangirajeev.telugubiblemessages.ui.dictionary;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

public class DictViewModel extends AndroidViewModel {
    private static final String TAG = "DictViewModel";
    private LiveData<List<DictEngTel>> mdictEngTelList;
    private List<DictEngTel> dictList;

    private DictDao dictDao;

    public DictViewModel(@NonNull Application application) {
        super(application);
        DictDatabase dictDatabase = DictDatabase.getDictDatabase(application);
        dictDao = dictDatabase.dictDao();
    }

    public LiveData<List<DictEngTel>> getDictionary(String searchQuery){
        mdictEngTelList = dictDao.getDictEngTelList(searchQuery);
        return mdictEngTelList;
    }
}
