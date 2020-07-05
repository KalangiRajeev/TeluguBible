package com.ikalangirajeev.telugubiblemessages.ui.bible.app.books;

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

public class BooksViewModel extends AndroidViewModel {
    private static final String TAG = "BooksViewModel";


    private String englishBooks[] = {
            "Genesis", "Exodus", "Leviticus", "Numbers", "Deuteronomy", "Joshua",
            "Judges", "Ruth", "1 Samuel", "2 Samuel", "1 Kings", "2 Kings", "1 Chronicles",
            "2 Chronicles", "Ezra", "Nehemiah", "Esther", "Job", "Psalms", "Proverbs", "Ecclesiastes",
            "Song of Solomon", "Isaiah", "Jeremiah", "Lamentations", "Ezekiel", "Daniel",
            "Hosea", "Joel", "Amos", "Obadiah", "Jonah", "Micah", "Nahum", "Habakkuk", "Zephaniah",
            "Haggai", "Zechariah", "Malachi", "Matthew", "Mark", "Luke", "John", "Acts", "Romans",
            "1 Corinthians", "2 Corinthians", "Galatians", "Ephesians", "Philippians", "Colossians",
            "1 Thessalonians", "2 Thessalonians", "1 Timothy", "2 Timothy", "Titus", "Philemon",
            "Hebrews", "James", "1 Peter", "2 Peter", "1 John", "2 John", "3 John", "Jude", "Revelation"};
    private String teluguBooks[] = {
            "ఆదికాండము", "నిర్గమకాండము", "లేవీయకాండము", "సంఖ్యాకాండము", "ద్వితీయోపదేశకాండమ", "యెహొషువ",
            "న్యాయాధిపతులు", "రూతు", "I సమూయేలు", "II సమూయేలు", "I రాజులు",
            "II రాజులు", "I దినవృత్తాంతములు", "II దినవృత్తాంతములు", "ఎజ్రా", "నెహెమ్యా", "ఎస్తేరు", "యోబు గ్రంథము",
            "కీర్తనల గ్రంథము", "సామెతలు", "ప్రసంగి", "పరమగీతము", "యెషయా గ్రంథము", "యిర్మీయా", "విలాపవాక్యములు", "యెహెజ్కేలు",
            "దానియేలు", "హొషేయ", "యోవేలు", "ఆమోసు", "ఓబద్యా", "యోనా", "మీకా", "నహూము", "హబక్కూకు", "జెఫన్యా",
            "హగ్గయి", "జెకర్యా", "మలాకీ", "మత్తయి సువార్త", "మార్కు సువార్త", "లూకా సువార్త", "యోహాను సువార్త", "అపొస్తలుల కార్యములు",
            "రోమీయులకు", "1 కొరింథీయులకు", "2 కొరింథీయులకు", "గలతీయులకు", "ఎఫెసీయులకు", "ఫిలిప్పీయులకు",
            "కొలొస్సయులకు", "1 థెస్సలొనీకయులకు", "2 థెస్సలొనీకయులకు", "1 తిమోతికి", "2 తిమోతికి", "తీతుకు", "ఫిలేమోనుకు",
            "హెబ్రీయులకు", "యాకోబు", "1 పేతురు", "2 పేతురు", "1 యోహాను", "2 యోహాను", "3 యోహాను", "యూదా", "ప్రకటన గ్రంథము"};
    private String tamilBooks[] = {
            "ஆதியாகமம்", "யாத்திராகமம்", "லேவியராகமம்", "எண்ணாகமம்", "உபாகமம்", "யோசுவா", "நியாயாதிபதிகள்", "ரூத்", "1 சாமுவேல்",
            "2 சாமுவேல்", "1 இராஜாக்கள்", "2 இராஜாக்கள்", "1 நாளாகமம்", "2 நாளாகமம்", "எஸ்றா", "நெகேமியா", "எஸ்தர்", "யோபு",
            "சங்கீதம்", "நீதிமொழிகள்", "பிரசங்கி", "உன்னதப்பாட்டு", "ஏசாயா", "எரேமியா", "புலம்பல்", "எசேக்கியேல்", "தானியேல்", "ஓசியா",
            "யோவேல்", "ஆமோஸ்", "ஒபதியா", "யோனா", "மீகா", "நாகூம்", "ஆபகூக்", "செப்பனியா", "ஆகாய்", "சகரியா", "மல்கியா",
            "மத்தேயு", "மாற்கு", "லுூக்கா", "யோவான்", "அப்போஸ்தலர்", "ரோமர்", "1 கொரிந்தியர்", "2 கொரிந்தியர்", "கலாத்தியர்",
            "எபேசியர்", "பிலிப்பியர்", "கொலோசெயர்", "1 தெசலோனிக்கேயர்", "2 தெசலோனிக்கேயர்", "1 தீமோத்தேயு", "2 தீமோத்தேயு", "தீத்து",
            "பிலேமோன்", "எபிரெயர்", "யாக்கோபு", "1 பேதுரு", "2 பேதுரு", "1 யோவான்", "2 யோவான்", "3 யோவான்", "யூதா", "வெளி"};
    private String kannadaBooks[] =  {
            "ಆದಿಕಾಂಡ", "ವಿಮೋಚನಕಾಂಡ", "ಯಾಜಕಕಾಂಡ", "ಅರಣ್ಯಕಾಂಡ", "ಧರ್ಮೋಪದೇಶಕಾಂಡ", "ಯೆಹೋಶುವ", "ನ್ಯಾಯಸ್ಥಾಪಕರು",
            "ರೂತಳು", "1 ಸಮುವೇಲನು", "2 ಸಮುವೇಲನು", "1 ಅರಸುಗಳು", "2 ಅರಸುಗಳು", "1 ಪೂರ್ವಕಾಲವೃತ್ತಾ", "2 ಪೂರ್ವಕಾಲವೃತ್ತಾ",
            "ಎಜ್ರನು", "ನೆಹೆಮಿಯ", "ಎಸ್ತೇರಳು", "ಯೋಬನು", "ಕೀರ್ತನೆಗಳು", "ಙ್ಞಾನೋಕ್ತಿಗಳು", "ಪ್ರಸಂಗಿ", "ಪರಮ ಗೀತ", "ಯೆಶಾಯ",
            "ಯೆರೆಮಿಯ", "ಪ್ರಲಾಪಗಳು", "ಯೆಹೆಜ್ಕೇಲನು", "ದಾನಿಯೇಲನು", "ಹೋಶೇ", "ಯೋವೇಲ", "ಆಮೋಸ", "ಓಬದ್ಯ", "ಯೋನ", "ಮಿಕ",
            "ನಹೂಮ", "ಹಬಕ್ಕೂಕ್ಕ", "ಚೆಫನ್ಯ", "ಹಗ್ಗಾಯ", "ಜೆಕರ್ಯ", "ಮಲಾಕಿಯ", "ಮತ್ತಾಯನು", "ಮಾರ್ಕನು", "ಲೂಕನು", "ಯೋಹಾನನು",
            "ಅಪೊಸ್ತಲರ ಕೃತ್ಯಗ", "ರೋಮಾಪುರದವರಿಗೆ", "1 ಕೊರಿಂಥದವರಿಗೆ", "2 ಕೊರಿಂಥದವರಿಗೆ", "ಗಲಾತ್ಯದವರಿಗೆ", "ಎಫೆಸದವರಿಗೆ",
            "ಫಿಲಿಪ್ಪಿಯವರಿಗೆ", "ಕೊಲೊಸ್ಸೆಯವರಿಗೆ", "1 ಥೆಸಲೊನೀಕದವರಿಗೆ", "2 ಥೆಸಲೊನೀಕದವರಿಗೆ", "1 ತಿಮೊಥೆಯನಿಗೆ", "2 ತಿಮೊಥೆಯನಿಗೆ",
            "ತೀತನಿಗೆ", "ಫಿಲೆಮೋನನಿಗೆ", "ಇಬ್ರಿಯರಿಗೆ", "ಯಾಕೋಬನು", "1 ಪೇತ್ರನು", "2 ಪೇತ್ರನು", "1 ಯೋಹಾನನು", "2 ಯೋಹಾನನು",
            "3 ಯೋಹಾನನು", "ಯೂದನು", "ಪ್ರಕಟನೆ"};
    private String hindiBooks[] = {
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
            "3 यूहन्ना","यहूदा","प्रकाशित वाक्य" };
    private String malayalamBooks [] = {"ഉല്പത്തി", "പുറപ്പാട്", "ലേവ്യപുസ്തകം", "സംഖ്യാപുസ്തകം", "ആവർത്തനം", "യോശുവ", "ന്യായാധിപന്മാർ",
            "രൂത്ത്", "1 ശമൂവേൽ", "2 ശമൂവേൽ", "1 രാജാക്കന്മാർ", "2 രാജാക്കന്മാർ", "1 ദിനവൃത്താന്തം",
            "2 ദിനവൃത്താന്തം", "എസ്രാ", "നെഹെമ്യാവു", "എസ്ഥേർ", "ഇയ്യോബ്", "സങ്കീർത്തനങ്ങൾ",
            "സദൃശ്യവാക്യങ്ങൾ", "സഭാപ്രസംഗി", "ഉത്തമഗീതം", "യെശയ്യാ", "യിരമ്യാവു", "വിലാപങ്ങൾ",
            "യെഹേസ്കേൽ", "ദാനീയേൽ", "ഹോശേയ", "യോവേൽ", "ആമോസ്", "ഓബദ്യാവു", "യോനാ",
            "മീഖാ", "നഹൂം", "ഹബക്കൂക്ക്", "സെഫന്യാവു", "ഹഗ്ഗായി", "സെഖര്യാവു", "മലാഖി", "മത്തായി",
            "മർക്കൊസ്", "ലൂക്കോസ്", "യോഹന്നാൻ", "പ്രവൃത്തികൾ", "റോമർ", "1 കൊരിന്ത്യർ", "2 കൊരിന്ത്യർ",
            "ഗലാത്യർ", "എഫെസ്യർ", "ഫിലിപ്പിയർ", "കൊലൊസ്സ്യർ", "1 തെസ്സലൊനീക്യർ", "2 തെസ്സലൊനീക്യർ",
            "1 തിമൊഥെയൊസ്", "2 തിമൊഥെയൊസ്", "തീത്തൊസ്", "ഫിലേമോൻ", "എബ്രായർ", "യാക്കോബ്",
            "1 പത്രൊസ്", "2 പത്രൊസ്", "1 യോഹന്നാൻ", "2 യോഹന്നാൻ", "3 യോഹന്നാൻ","യൂദാ", "വെളിപ്പാട്" };

    private Integer chaptersCount;
    private List<Data> dataList;
    private MutableLiveData<List<Data>> mLiveDataList;
    private Application application;

    public BooksViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
        dataList = new ArrayList<>();
        mLiveDataList = new MutableLiveData<>();
    }

    public LiveData<List<Data>> getText(String bibleSelected) {
        if (bibleSelected.equals("bible_english")) {
            dataList.clear();
            for (int i = 0; i < englishBooks.length; i++) {
                try {
                    chaptersCount = new EnglishBibleAsyncTask(application).execute(i).get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Data data = new Data(englishBooks[i], chaptersCount + " Chapters", chaptersCount);
                dataList.add(data);
            }
        } else if (bibleSelected.equals("bible_tamil")){
            dataList.clear();
            for (int i = 0; i < tamilBooks.length; i++) {
                try {
                    chaptersCount = new TamilBibleAsyncTask(application).execute(i).get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Data data = new Data(tamilBooks[i], chaptersCount + " அத்தியாயங்கள்", chaptersCount);
                dataList.add(data);
            }
        } else if (bibleSelected.equals("bible_kannada")){
            dataList.clear();
            for (int i = 0; i < kannadaBooks.length; i++) {
                try {
                    chaptersCount = new KannadaBibleAsyncTask(application).execute(i).get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Data data = new Data(kannadaBooks[i], chaptersCount + " ಅಧ್ಯಾಯಗಳು", chaptersCount);
                dataList.add(data);
            }
        } else if (bibleSelected.equals("bible_hindi")){
            dataList.clear();
            for (int i=0; i< hindiBooks.length; i++) {
                try {
                    chaptersCount = new HindiBibleAsyncTask(application).execute(i).get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Data data = new Data(hindiBooks[i], chaptersCount + " अध्याय", chaptersCount);
                dataList.add(data);
            }
        } else if (bibleSelected.equals("bible_malayalam")){
            dataList.clear();
            for (int i=0; i< malayalamBooks.length; i++) {
                try {
                    chaptersCount = new MalayalamBibleAsyncTask(application).execute(i).get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Data data = new Data(malayalamBooks[i], chaptersCount + " അധ്യായങ്ങൾ", chaptersCount);
                dataList.add(data);
            }
        } else {
            dataList.clear();
            for (int i = 0; i < teluguBooks.length; i++) {
                try {
                    chaptersCount = new TeluguBibleAsyncTask(application).execute(i).get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Data data = new Data(teluguBooks[i], chaptersCount + " అధ్యాయాలు", chaptersCount);
                dataList.add(data);
            }
        }
        mLiveDataList.setValue(dataList);
        return mLiveDataList;
    }

    private static class EnglishBibleAsyncTask extends AsyncTask<Integer, Void, Integer>{

        private EnglishBibleDao englishBibleDao;
        private Integer chaptersCount;

        public EnglishBibleAsyncTask(Application application) {
            BibleDatabase bibleDatabase = BibleDatabase.getBibleDatabase(application);
            englishBibleDao = bibleDatabase.englishBibleDao();
        }

        @Override
        protected Integer doInBackground(Integer... integers) {
            chaptersCount = englishBibleDao.getChaptersCount(integers[0]);
            return chaptersCount;
        }
    }
    private static class TeluguBibleAsyncTask extends AsyncTask<Integer, Void, Integer>{
        private TeluguBibleDao teluguBibleDao;
        private Integer chaptersCount;

        public TeluguBibleAsyncTask(Application application) {
            BibleDatabase bibleDatabase = BibleDatabase.getBibleDatabase(application);
            teluguBibleDao = bibleDatabase.teluguBibleDao();
        }

        @Override
        protected Integer doInBackground(Integer... integers) {
            chaptersCount = teluguBibleDao.getChaptersCount(integers[0]);
            return chaptersCount;
        }
    }
    private static class TamilBibleAsyncTask extends AsyncTask<Integer, Void, Integer>{
        private TamilBibleDao tamilBibleDao;
        private Integer chaptersCount;

        public TamilBibleAsyncTask(Application application) {
            BibleDatabase bibleDatabase = BibleDatabase.getBibleDatabase(application);
            tamilBibleDao = bibleDatabase.tamilBibleDao();
        }

        @Override
        protected Integer doInBackground(Integer... integers) {
            chaptersCount = tamilBibleDao.getChaptersCount(integers[0]);
            return chaptersCount;
        }
    }
    private static class KannadaBibleAsyncTask extends AsyncTask<Integer, Void, Integer>{
        private KannadaBibleDao kannadaBibleDao;
        private Integer chaptersCount;

        public KannadaBibleAsyncTask(Application application) {
            BibleDatabase bibleDatabase = BibleDatabase.getBibleDatabase(application);
            kannadaBibleDao = bibleDatabase.kannadaBibleDao();
        }

        @Override
        protected Integer doInBackground(Integer... integers) {
            chaptersCount = kannadaBibleDao.getChaptersCount(integers[0]);
            return chaptersCount;
        }
    }

    private static class HindiBibleAsyncTask extends AsyncTask<Integer, Void, Integer>{
        private HindiBibleDao hindiBibleDao;
        private Integer chaptersCount;

        public HindiBibleAsyncTask(Application application) {
            BibleDatabase bibleDatabase = BibleDatabase.getBibleDatabase(application);
            hindiBibleDao = bibleDatabase.hindiBibleDao();
        }

        @Override
        protected Integer doInBackground(Integer... integers) {
            chaptersCount = hindiBibleDao.getChaptersCount(integers[0]);
            return chaptersCount;
        }
    }
    private static class MalayalamBibleAsyncTask extends AsyncTask<Integer, Void, Integer>{
        private MalayalamBibleDao malayalamBibleDao;
        private Integer chaptersCount;

        public MalayalamBibleAsyncTask(Application application) {
            BibleDatabase bibleDatabase = BibleDatabase.getBibleDatabase(application);
            malayalamBibleDao = bibleDatabase.malayalamBibleDao();
        }

        @Override
        protected Integer doInBackground(Integer... integers) {
            chaptersCount = malayalamBibleDao.getChaptersCount(integers[0]);
            return chaptersCount;
        }
    }
}