package com.ikalangirajeev.telugubiblemessages.ui.bible.app.search;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.core.os.HandlerCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ikalangirajeev.telugubiblemessages.R;
import com.ikalangirajeev.telugubiblemessages.ui.SettingsFragment;
import com.ikalangirajeev.telugubiblemessages.ui.roombible.BibleDatabase;
import com.ikalangirajeev.telugubiblemessages.ui.roombible.EnglishBibleDao;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SearchFragment extends Fragment {

    private SearchViewModel searchViewModel;
    private RecyclerView recyclerView;
    private NavController navController;
    private SearchRecyclerViewAdapter searchRecyclerViewAdapter;
    private TextView textViewSearchCount;
    private Button buttonSearchDict;

    String search, bibleSelected;
    ExecutorService executorService = Executors.newSingleThreadExecutor();
    Handler handler = HandlerCompat.createAsync(Looper.getMainLooper());

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        bibleSelected = getArguments().getString("bibleSelected", prefs.getString(SettingsFragment.PREF_SELECTED_BIBLE, "bible_english"));
        search = getArguments().getString("SearchData");
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        searchViewModel =
                ViewModelProviders.of(this).get(SearchViewModel.class);
        View root = inflater.inflate(R.layout.fragment_search, container, false);

        textViewSearchCount = root.findViewById(R.id.textViewSearchCount);
        buttonSearchDict = root.findViewById(R.id.searchDict);
        recyclerView = root.findViewById(R.id.recyclerView);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        searchViewModel.getSearchDataList(bibleSelected, search).observe(getViewLifecycleOwner(), new Observer<List<SearchData>>() {
            @Override
            public void onChanged(List<SearchData> searchData) {
                searchRecyclerViewAdapter = new SearchRecyclerViewAdapter(getActivity(), R.layout.card_view_verses, searchData, search);
                recyclerView.setAdapter(searchRecyclerViewAdapter);
                textViewSearchCount.setText(searchData.size() + " Results");

                searchRecyclerViewAdapter.setOnRVItemClickListener(new SearchRecyclerViewAdapter.OnRVItemClickListener() {
                    @Override
                    public void OnRVItemClick(SearchData searchData, int position) {
                        Bundle bundle = new Bundle();
                        bundle.putString("bibleSelected", bibleSelected);
                        bundle.putString("BookName", searchData.getBookName());
                        bundle.putInt("BookNumber", searchData.getBookNumber());

                        executorService.execute(new Runnable() {
                            @Override
                            public void run() {
                                BibleDatabase bibleDatabase = BibleDatabase.getBibleDatabase(getActivity().getApplicationContext());
                                EnglishBibleDao englishBibleDao = bibleDatabase.englishBibleDao();
                                Integer chapterCount = englishBibleDao.getChaptersCount(searchData.getBookNumber());
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        bundle.putInt("ChapterCount", chapterCount);
                                    }
                                });
                            }
                        });
                        bundle.putInt("ChapterNumber", searchData.getChapternumber());
                        bundle.putInt("HighlightVerseNumber", searchData.getVerseNumber());
                        navController.navigate(R.id.action_searchFragment_to_versesFragment, bundle);
                    }
                });
            }
        });


        buttonSearchDict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("SearchDict", search);
                navController.navigate(R.id.dictFragment, bundle, new NavOptions.Builder()
                        .setPopUpTo(R.id.searchFragment, true)
                        .setEnterAnim(R.anim.slide_in_right)
                        .setExitAnim(R.anim.slide_out_left)
                        .setPopEnterAnim(R.anim.slide_in_left)
                        .setPopExitAnim(R.anim.slide_out_right)
                        .build());
            }
        });

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
    }
}