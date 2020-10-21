package com.ikalangirajeev.telugubiblemessages.ui.bible.app.linkedrefs;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ikalangirajeev.telugubiblemessages.ui.roombible.BibleDatabase;
import com.ikalangirajeev.telugubiblemessages.ui.roombible.EnglishBible;
import com.ikalangirajeev.telugubiblemessages.ui.roombible.HindiBible;
import com.ikalangirajeev.telugubiblemessages.ui.roombible.HindiBibleDao;
import com.ikalangirajeev.telugubiblemessages.ui.roombible.KannadaBible;
import com.ikalangirajeev.telugubiblemessages.ui.roombible.KannadaBibleDao;
import com.ikalangirajeev.telugubiblemessages.ui.roombible.MalayalamBible;
import com.ikalangirajeev.telugubiblemessages.ui.roombible.MalayalamBibleDao;
import com.ikalangirajeev.telugubiblemessages.ui.roombible.TamilBible;
import com.ikalangirajeev.telugubiblemessages.ui.roombible.TamilBibleDao;
import com.ikalangirajeev.telugubiblemessages.ui.roombible.TeluguBible;
import com.ikalangirajeev.telugubiblemessages.ui.roombible.EnglishBibleDao;
import com.ikalangirajeev.telugubiblemessages.ui.roombible.TeluguBibleDao;

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

    public LiveData<List<LinkVerse>> getData(String bibleSelected, Integer verseId) {


        try {
            verseInfoList = new VerseInfoAsyncTask(getApplication()).execute(verseId).get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        switch (bibleSelected) {
            case "bible_english":
                try {
                    linkVerseList = new EnglishVersesAsyncTask(getApplication()).execute(verseInfoList).get();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "bible_tamil":
                try {
                    linkVerseList = new TamilVersesAsyncTask(getApplication()).execute(verseInfoList).get();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "bible_kannada":
                try {
                    linkVerseList = new KannadaVersesAsyncTask(getApplication()).execute(verseInfoList).get();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "bible_hindi":
                try {
                    linkVerseList = new HindiVersesAsyncTask(getApplication()).execute(verseInfoList).get();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "bible_malayalam":
                try {
                    linkVerseList = new MalayalamVersesAsyncTask(getApplication()).execute(verseInfoList).get();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            default:
                try {
                    linkVerseList = new TeluguVersesAsyncTask(getApplication()).execute(verseInfoList).get();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
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
                                chapterNumber = Integer.parseInt(splitString[1]) ;
                                verseNumber = Integer.parseInt(splitString[2]);
                                VerseInfo verseInfo = new VerseInfo(Integer.parseInt(linkVerseId), bookNumber, chapterNumber, verseNumber);
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

    private class TeluguVersesAsyncTask extends AsyncTask<List<VerseInfo>, Void, List<LinkVerse>> {
        Application application;
        List<LinkVerse> linkVerseList;
        List<VerseInfo> verseInfoList;
        TeluguBibleDao teluguBibleDao;
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


        public TeluguVersesAsyncTask(Application application) {
            this.application = application;
            BibleDatabase bibleDatabase = BibleDatabase.getBibleDatabase(application);
            teluguBibleDao = bibleDatabase.teluguBibleDao();
            linkVerseList = new ArrayList<>();
            verseInfoList = new ArrayList<>();
        }

        @Override
        protected List<LinkVerse> doInBackground(List<VerseInfo>... lists) {
            verseInfoList.addAll(lists[0]);


           for (VerseInfo verseInfo : verseInfoList) {

                TeluguBible verse = teluguBibleDao.getTeluguBibleVerse(verseInfo.getBookNumber(), verseInfo.getChapterNumber(), verseInfo.getVerseNumber());

                Integer chapterNumber = verse.getChapter();
                Integer verseNumber = verse.getVersecount();

                String verseHeader = books[verseInfo.getBookNumber()] + " " + chapterNumber + ":" + verseNumber;
                LinkVerse linkVerse = new LinkVerse(verseHeader, verse.getVerse());
                linkVerseList.add(linkVerse);
            }
            return linkVerseList;
        }
    }

    private class EnglishVersesAsyncTask extends AsyncTask<List<VerseInfo>, Void, List<LinkVerse>> {
        Application application;
        List<LinkVerse> linkVerseList;
        List<VerseInfo> verseInfoList;
        EnglishBibleDao englishBibleDao;
        private String books[] = {
                "Genesis", "Exodus", "Leviticus", "Numbers", "Deuteronomy", "Joshua",
                "Judges", "Ruth", "1 Samuel", "2 Samuel", "1 Kings", "2 Kings", "1 Chronicles",
                "2 Chronicles", "Ezra", "Nehemiah", "Esther", "Job", "Psalms", "Proverbs", "Ecclesiastes",
                "Song of Solomon", "Isaiah", "Jeremiah", "Lamentations", "Ezekiel", "Daniel",
                "Hosea", "Joel", "Amos", "Obadiah", "Jonah", "Micah", "Nahum", "Habakkuk", "Zephaniah",
                "Haggai", "Zechariah", "Malachi", "Matthew", "Mark", "Luke", "John", "Acts", "Romans",
                "1 Corinthians", "2 Corinthians", "Galatians", "Ephesians", "Philippians", "Colossians",
                "1 Thessalonians", "2 Thessalonians", "1 Timothy", "2 Timothy", "Titus", "Philemon",
                "Hebrews", "James", "1 Peter", "2 Peter", "1 John", "2 John", "3 John", "Jude", "Revelation"};


        public EnglishVersesAsyncTask(Application application) {
            this.application = application;
            BibleDatabase bibleDatabase = BibleDatabase.getBibleDatabase(application);
            englishBibleDao = bibleDatabase.englishBibleDao();
            linkVerseList = new ArrayList<>();
            verseInfoList = new ArrayList<>();
        }

        @Override
        protected List<LinkVerse> doInBackground(List<VerseInfo>... lists) {
            verseInfoList.addAll(lists[0]);


            for (VerseInfo verseInfo : verseInfoList) {

                EnglishBible verse = englishBibleDao.getEnglishBibleVerse(verseInfo.getBookNumber(), verseInfo.getChapterNumber(), verseInfo.getVerseNumber());

                Integer chapterNumber = verse.getChapter();
                Integer verseNumber = verse.getVersecount();

                String verseHeader = books[verseInfo.getBookNumber()] + " " + chapterNumber + ":" + verseNumber;
                LinkVerse linkVerse = new LinkVerse(verseHeader, verse.getVerse());
                linkVerseList.add(linkVerse);
            }
            return linkVerseList;
        }
    }

    private class TamilVersesAsyncTask extends AsyncTask<List<VerseInfo>, Void, List<LinkVerse>> {
        Application application;
        List<LinkVerse> linkVerseList;
        List<VerseInfo> verseInfoList;
        TamilBibleDao tamilBibleDao;
        private String books[] = {
                "ஆதியாகமம்", "யாத்திராகமம்", "லேவியராகமம்", "எண்ணாகமம்", "உபாகமம்", "யோசுவா", "நியாயாதிபதிகள்", "ரூத்", "1 சாமுவேல்",
                "2 சாமுவேல்", "1 இராஜாக்கள்", "2 இராஜாக்கள்", "1 நாளாகமம்", "2 நாளாகமம்", "எஸ்றா", "நெகேமியா", "எஸ்தர்", "யோபு",
                "சங்கீதம்", "நீதிமொழிகள்", "பிரசங்கி", "உன்னதப்பாட்டு", "ஏசாயா", "எரேமியா", "புலம்பல்", "எசேக்கியேல்", "தானியேல்", "ஓசியா",
                "யோவேல்", "ஆமோஸ்", "ஒபதியா", "யோனா", "மீகா", "நாகூம்", "ஆபகூக்", "செப்பனியா", "ஆகாய்", "சகரியா", "மல்கியா",
                "மத்தேயு", "மாற்கு", "லுூக்கா", "யோவான்", "அப்போஸ்தலர்", "ரோமர்", "1 கொரிந்தியர்", "2 கொரிந்தியர்", "கலாத்தியர்",
                "எபேசியர்", "பிலிப்பியர்", "கொலோசெயர்", "1 தெசலோனிக்கேயர்", "2 தெசலோனிக்கேயர்", "1 தீமோத்தேயு", "2 தீமோத்தேயு", "தீத்து",
                "பிலேமோன்", "எபிரெயர்", "யாக்கோபு", "1 பேதுரு", "2 பேதுரு", "1 யோவான்", "2 யோவான்", "3 யோவான்", "யூதா", "வெளி"};


        public TamilVersesAsyncTask(Application application) {
            this.application = application;
            BibleDatabase bibleDatabase = BibleDatabase.getBibleDatabase(application);
            tamilBibleDao = bibleDatabase.tamilBibleDao();
            linkVerseList = new ArrayList<>();
            verseInfoList = new ArrayList<>();
        }

        @Override
        protected List<LinkVerse> doInBackground(List<VerseInfo>... lists) {
            verseInfoList.addAll(lists[0]);


            for (VerseInfo verseInfo : verseInfoList) {

                TamilBible verse = tamilBibleDao.getTamilBibleVerse(verseInfo.getBookNumber(), verseInfo.getChapterNumber(), verseInfo.getVerseNumber());

                Integer chapterNumber = verseInfo.getChapterNumber();
                Integer verseNumber = verseInfo.getVerseNumber();

                String verseHeader = books[verseInfo.getBookNumber()] + " " + chapterNumber + ":" + verseNumber;
                LinkVerse linkVerse = new LinkVerse(verseHeader, verse.getVerse());
                linkVerseList.add(linkVerse);
            }
            return linkVerseList;
        }
    }

    private class KannadaVersesAsyncTask extends AsyncTask<List<VerseInfo>, Void, List<LinkVerse>> {
        Application application;
        List<LinkVerse> linkVerseList;
        List<VerseInfo> verseInfoList;
        KannadaBibleDao kannadaBibleDao;
        private String books[] = {
                "ಆದಿಕಾಂಡ", "ವಿಮೋಚನಕಾಂಡ", "ಯಾಜಕಕಾಂಡ", "ಅರಣ್ಯಕಾಂಡ", "ಧರ್ಮೋಪದೇಶಕಾಂಡ", "ಯೆಹೋಶುವ", "ನ್ಯಾಯಸ್ಥಾಪಕರು",
                "ರೂತಳು", "1 ಸಮುವೇಲನು", "2 ಸಮುವೇಲನು", "1 ಅರಸುಗಳು", "2 ಅರಸುಗಳು", "1 ಪೂರ್ವಕಾಲವೃತ್ತಾ", "2 ಪೂರ್ವಕಾಲವೃತ್ತಾ",
                "ಎಜ್ರನು", "ನೆಹೆಮಿಯ", "ಎಸ್ತೇರಳು", "ಯೋಬನು", "ಕೀರ್ತನೆಗಳು", "ಙ್ಞಾನೋಕ್ತಿಗಳು", "ಪ್ರಸಂಗಿ", "ಪರಮ ಗೀತ", "ಯೆಶಾಯ",
                "ಯೆರೆಮಿಯ", "ಪ್ರಲಾಪಗಳು", "ಯೆಹೆಜ್ಕೇಲನು", "ದಾನಿಯೇಲನು", "ಹೋಶೇ", "ಯೋವೇಲ", "ಆಮೋಸ", "ಓಬದ್ಯ", "ಯೋನ", "ಮಿಕ",
                "ನಹೂಮ", "ಹಬಕ್ಕೂಕ್ಕ", "ಚೆಫನ್ಯ", "ಹಗ್ಗಾಯ", "ಜೆಕರ್ಯ", "ಮಲಾಕಿಯ", "ಮತ್ತಾಯನು", "ಮಾರ್ಕನು", "ಲೂಕನು", "ಯೋಹಾನನು",
                "ಅಪೊಸ್ತಲರ ಕೃತ್ಯಗ", "ರೋಮಾಪುರದವರಿಗೆ", "1 ಕೊರಿಂಥದವರಿಗೆ", "2 ಕೊರಿಂಥದವರಿಗೆ", "ಗಲಾತ್ಯದವರಿಗೆ", "ಎಫೆಸದವರಿಗೆ",
                "ಫಿಲಿಪ್ಪಿಯವರಿಗೆ", "ಕೊಲೊಸ್ಸೆಯವರಿಗೆ", "1 ಥೆಸಲೊನೀಕದವರಿಗೆ", "2 ಥೆಸಲೊನೀಕದವರಿಗೆ", "1 ತಿಮೊಥೆಯನಿಗೆ", "2 ತಿಮೊಥೆಯನಿಗೆ",
                "ತೀತನಿಗೆ", "ಫಿಲೆಮೋನನಿಗೆ", "ಇಬ್ರಿಯರಿಗೆ", "ಯಾಕೋಬನು", "1 ಪೇತ್ರನು", "2 ಪೇತ್ರನು", "1 ಯೋಹಾನನು", "2 ಯೋಹಾನನು",
                "3 ಯೋಹಾನನು", "ಯೂದನು", "ಪ್ರಕಟನೆ"};

        public KannadaVersesAsyncTask(Application application) {
            this.application = application;
            BibleDatabase bibleDatabase = BibleDatabase.getBibleDatabase(application);
            kannadaBibleDao = bibleDatabase.kannadaBibleDao();
            linkVerseList = new ArrayList<>();
            verseInfoList = new ArrayList<>();
        }

        @Override
        protected List<LinkVerse> doInBackground(List<VerseInfo>... lists) {
            verseInfoList.addAll(lists[0]);


            for (VerseInfo verseInfo : verseInfoList) {

                KannadaBible verse = kannadaBibleDao.getKannadaBibleVerse(verseInfo.getBookNumber(), verseInfo.getChapterNumber(), verseInfo.getVerseNumber());

                Integer chapterNumber = verse.getChapter();
                Integer verseNumber = verse.getVersecount();

                String verseHeader = books[verseInfo.getBookNumber()] + " " + chapterNumber + ":" + verseNumber;
                LinkVerse linkVerse = new LinkVerse(verseHeader, verse.getVerse());
                linkVerseList.add(linkVerse);
            }
            return linkVerseList;
        }
    }

    private class HindiVersesAsyncTask extends AsyncTask<List<VerseInfo>, Void, List<LinkVerse>> {
        Application application;
        List<LinkVerse> linkVerseList;
        List<VerseInfo> verseInfoList;
        HindiBibleDao hindiBibleDao;
        private String books[] = {
                "उत्पत्ति","निर्गमन","लैव्यवस्था","गिनती","व्यवस्थाविवरण","यहोशू ",
                "न्यायियों","रूत","1 शमूएल","2 शमूएल","1 राजा","2 राजा",
                "1 इतिहास","2 इतिहास","एज्रा","नहेमायाह","एस्तेर","अय्यूब",
                "भजन संहिता","नीतिवचन ","सभोपदेशक","श्रेष्ठगीत","यशायाह","यिर्मयाह",
                "विलापगीत","यहेजकेल","दानिय्येल","होशे","योएल","आमोस","ओबद्दाह",
                "योना","मीका","नहूम","हबक्कूक","सपन्याह","हाग्गै","जकर्याह",
                "मलाकी","मत्ती","मरकुस","लूका","यूहन्ना","प्रेरितों के काम","रोमियो",
                "1 कुरिन्थियों","2 कुरिन्थियों","गलातियों","इफिसियों","फिलिप्पियों","कुलुस्सियों",
                "1 थिस्सलुनीकियों","2 थिस्सलुनीकियों","1 तीमुथियुस","2 तीमुथियुस","तीतुस",
                "फिलेमोन","इब्रानियों","याकूब","1 पतरस","2 पतरस","1 यूहन्ना","2 यूहन्ना",
                "3 यूहन्ना","यहूदा","प्रकाशित वाक्य"};


        public HindiVersesAsyncTask(Application application) {
            this.application = application;
            BibleDatabase bibleDatabase = BibleDatabase.getBibleDatabase(application);
            hindiBibleDao = bibleDatabase.hindiBibleDao();
            linkVerseList = new ArrayList<>();
            verseInfoList = new ArrayList<>();
        }

        @Override
        protected List<LinkVerse> doInBackground(List<VerseInfo>... lists) {
            verseInfoList.addAll(lists[0]);

            for (VerseInfo verseInfo : verseInfoList) {

                HindiBible verse = hindiBibleDao.getHindiBibleVerse(verseInfo.getBookNumber(), verseInfo.getChapterNumber(), verseInfo.getVerseNumber());

                Integer chapterNumber = verse.getChapter();
                Integer verseNumber = verse.getVersecount();

                String verseHeader = books[verseInfo.getBookNumber()] + " " + chapterNumber + ":" + verseNumber;
                LinkVerse linkVerse = new LinkVerse(verseHeader, verse.getVerse());
                linkVerseList.add(linkVerse);
            }
            return linkVerseList;
        }
    }

    private class MalayalamVersesAsyncTask extends AsyncTask<List<VerseInfo>, Void, List<LinkVerse>> {
        Application application;
        List<LinkVerse> linkVerseList;
        List<VerseInfo> verseInfoList;
        MalayalamBibleDao malayalamBibleDao;
        private String books[] = {"ഉല്പത്തി", "പുറപ്പാട്", "ലേവ്യപുസ്തകം", "സംഖ്യാപുസ്തകം", "ആവർത്തനം", "യോശുവ", "ന്യായാധിപന്മാർ",
                "രൂത്ത്", "1 ശമൂവേൽ", "2 ശമൂവേൽ", "1 രാജാക്കന്മാർ", "2 രാജാക്കന്മാർ", "1 ദിനവൃത്താന്തം",
                "2 ദിനവൃത്താന്തം", "എസ്രാ", "നെഹെമ്യാവു", "എസ്ഥേർ", "ഇയ്യോബ്", "സങ്കീർത്തനങ്ങൾ",
                "സദൃശ്യവാക്യങ്ങൾ", "സഭാപ്രസംഗി", "ഉത്തമഗീതം", "യെശയ്യാ", "യിരമ്യാവു", "വിലാപങ്ങൾ",
                "യെഹേസ്കേൽ", "ദാനീയേൽ", "ഹോശേയ", "യോവേൽ", "ആമോസ്", "ഓബദ്യാവു", "യോനാ",
                "മീഖാ", "നഹൂം", "ഹബക്കൂക്ക്", "സെഫന്യാവു", "ഹഗ്ഗായി", "സെഖര്യാവു", "മലാഖി", "മത്തായി",
                "മർക്കൊസ്", "ലൂക്കോസ്", "യോഹന്നാൻ", "പ്രവൃത്തികൾ", "റോമർ", "1 കൊരിന്ത്യർ", "2 കൊരിന്ത്യർ",
                "ഗലാത്യർ", "എഫെസ്യർ", "ഫിലിപ്പിയർ", "കൊലൊസ്സ്യർ", "1 തെസ്സലൊനീക്യർ", "2 തെസ്സലൊനീക്യർ",
                "1 തിമൊഥെയൊസ്", "2 തിമൊഥെയൊസ്", "തീത്തൊസ്", "ഫിലേമോൻ", "എബ്രായർ", "യാക്കോബ്",
                "1 പത്രൊസ്", "2 പത്രൊസ്", "1 യോഹന്നാൻ", "2 യോഹന്നാൻ", "3 യോഹന്നാൻ","യൂദാ", "വെളിപ്പാട്" };


        public MalayalamVersesAsyncTask(Application application) {
            this.application = application;
            BibleDatabase bibleDatabase = BibleDatabase.getBibleDatabase(application);
            malayalamBibleDao = bibleDatabase.malayalamBibleDao();
            linkVerseList = new ArrayList<>();
            verseInfoList = new ArrayList<>();
        }

        @Override
        protected List<LinkVerse> doInBackground(List<VerseInfo>... lists) {
            verseInfoList.addAll(lists[0]);

            for (VerseInfo verseInfo : verseInfoList) {

                MalayalamBible verse = malayalamBibleDao.getMalayalamBibleVerse(verseInfo.getBookNumber(), verseInfo.getChapterNumber(), verseInfo.getVerseNumber());

                Integer chapterNumber = verse.getChapter();
                Integer verseNumber = verse.getVersecount();

                String verseHeader = books[verseInfo.getBookNumber()] + " " + chapterNumber + ":" + verseNumber;
                LinkVerse linkVerse = new LinkVerse(verseHeader, verse.getVerse());
                linkVerseList.add(linkVerse);
            }
            return linkVerseList;
        }
    }
}
