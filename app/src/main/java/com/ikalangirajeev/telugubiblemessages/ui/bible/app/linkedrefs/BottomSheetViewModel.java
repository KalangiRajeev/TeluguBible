package com.ikalangirajeev.telugubiblemessages.ui.bible.app.linkedrefs;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.ikalangirajeev.telugubiblemessages.ui.bible.app.Bible;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BottomSheetViewModel extends AndroidViewModel {

    private MutableLiveData<List<LinkVerse>> linkedVersesList;
    private List<LinkVerse> linkVerseList;
    private List<VerseInfo> verseInfoList;


    public BottomSheetViewModel(@NonNull Application application) {
        super(application);
        linkVerseList = new ArrayList<>();
        linkedVersesList = new MutableLiveData<>();
    }

    public LiveData<List<LinkVerse>> getData(Integer verseId) {


        try {
            verseInfoList = new VerseInfoAsyncTask(getApplication()).execute(verseId).get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            linkVerseList = new LinkVersesAsyncTask(getApplication()).execute(verseInfoList).get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        linkedVersesList.setValue(linkVerseList);
        return linkedVersesList;
    }

    private class VerseInfoAsyncTask extends AsyncTask<Integer, Void, List<VerseInfo>> {
        Application application;
        List<VerseInfo> verseInfoList;
        String jsonCrossReferences;
        String[] bookEngNames = {"GEN", "EXO", "LEV", "NUM", "DEU", "JOS", "JDG", "RUT", "1SA", "2SA", "1KI", "2KI",
                "1CH", "2CH", "EZR", "NEH", "EST", "JOB", "PSA", "PRO", "ECC", "SOS", "ISA", "JER", "LAM", "EZE", "DAN",
                "HOS", "JOE", "AMO", "OBA", "JON", "MIC", "NAH", "HAB", "ZEP", "HAG", "ZEC", "MAL", "MAT", "MAR", "LUK", "JOH", "ACT",
                "ROM", "1CO", "2CO", "GAL", "EPH", "PHP", "COL", "1TH", "2TH", "1TI", "2TI", "TIT", "PHM", "HEB", "JAM",
                "1PE", "2PE", "1JO", "2JO", "3JO", "JDE", "REV"};

        public VerseInfoAsyncTask(Application application) {
            this.application = application;
            verseInfoList = new ArrayList<>();
        }

        @Override
        protected List<VerseInfo> doInBackground(Integer... integers) {
            String linkVerseId, linkVerseInfo;
            Integer bookNumber = 0, chapterNumber, verseNumber;
            try {
                InputStream inputStream = application.getAssets().open("cross_references.json");
                byte[] bytes = new byte[inputStream.available()];
                inputStream.read(bytes);
                inputStream.close();
                jsonCrossReferences = new String(bytes, "UTF-8");
            } catch (Exception e) {
                e.printStackTrace();
            }

            JSONParser jsonParser = new JSONParser();
            try {
                JSONObject jsonObject = (JSONObject) jsonParser.parse(jsonCrossReferences);
                Iterator keys = jsonObject.keySet().iterator();
                while (keys.hasNext()) {
                    String key = (String) keys.next();
                    if (key.equals(String.valueOf(integers[0]))) {
                        JSONObject jsonLinkedRefObject = (JSONObject) jsonObject.get(key);
                        JSONObject jsonLinkedRefs = null;
                        if (jsonLinkedRefObject != null) {
                            jsonLinkedRefs = (JSONObject) jsonLinkedRefObject.get("r");
                        }
                        Iterator linkedRefKeys = null;
                        if (jsonLinkedRefs != null) {
                            linkedRefKeys = jsonLinkedRefs.keySet().iterator();
                        }
                        if (linkedRefKeys != null) {
                            while (linkedRefKeys.hasNext()) {
                                linkVerseId = (String) linkedRefKeys.next();
                                linkVerseInfo = (String) jsonLinkedRefs.get(linkVerseId);
                                String[] splitString = new String[0];
                                if (linkVerseInfo != null) {
                                    splitString = linkVerseInfo.trim().split("\\s+");
                                }

                                for (int i = 0; i < bookEngNames.length; i++) {
                                    if (splitString[0].equalsIgnoreCase(bookEngNames[i])) {
                                        bookNumber = i;
                                        break;
                                    }
                                }
                                chapterNumber = Integer.parseInt(splitString[1]) - 1;
                                verseNumber = Integer.parseInt(splitString[2]) - 1;
                                VerseInfo verseInfo = new VerseInfo(Integer.parseInt(linkVerseId), bookNumber,
                                        chapterNumber, verseNumber);
                                verseInfoList.add(verseInfo);
                            }
                        }
                        break;
                    }
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return verseInfoList;
        }
    }

    private class LinkVersesAsyncTask extends AsyncTask<List<VerseInfo>, Void, List<LinkVerse>> {
        Application application;
        List<LinkVerse> linkVerseList;
        List<VerseInfo> verseInfoList;
        String jsonBibleString;
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

        public LinkVersesAsyncTask(Application application) {
            this.application = application;
            linkVerseList = new ArrayList<>();
            verseInfoList = new ArrayList<>();
        }

        @Override
        protected List<LinkVerse> doInBackground(List<VerseInfo>... lists) {
            verseInfoList.addAll(lists[0]);

            try {
                InputStream inputStream = application.getAssets().open("new_telugu_bible.json");
                byte[] bytes = new byte[inputStream.available()];
                inputStream.read(bytes);
                inputStream.close();
                jsonBibleString = new String(bytes, "UTF-8");
            } catch (Exception e) {
                e.printStackTrace();
            }

            Gson gson = new Gson();
            Bible bible = gson.fromJson(jsonBibleString, Bible.class);
//            Log.d("Bible", "doInBackground: " + bible.toString());
            for (VerseInfo verseInfo : verseInfoList) {

                String verseBody = bible.getBooks().get(verseInfo.getBookNumber())
                        .getChapters().get(verseInfo.getChapterNumber())
                        .getVerses().get(verseInfo.getVerseNumber())
                        .getVerse();

                String chapterNumber = String.valueOf(verseInfo.getChapterNumber() + 1);
                String verseNumber = String.valueOf(verseInfo.getVerseNumber() + 1);

                String verseHeader = books[verseInfo.getBookNumber()] + " " + chapterNumber + ":" + verseNumber;
                LinkVerse linkVerse = new LinkVerse(verseHeader, verseBody);
                linkVerseList.add(linkVerse);
            }
            return linkVerseList;
        }
    }
}
