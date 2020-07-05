package com.ikalangirajeev.telugubiblemessages.ui.bible.app.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ikalangirajeev.telugubiblemessages.R;

import java.util.List;

public class SearchFragment extends Fragment {

    private SearchViewModel searchViewModel;
    private RecyclerView recyclerView;
    private NavController navController;
    private SearchRecyclerViewAdapter searchRecyclerViewAdapter;
    private TextView textViewSearchCount;
    private Button buttonSearchDict;

    String search, bibleSelected;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bibleSelected = getArguments().getString("bible");
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

                searchRecyclerViewAdapter.setOnItemClickListener(new SearchRecyclerViewAdapter.OnItemClickListener() {
                    @Override
                    public void OnItemClick(SearchData searchData, int position) {
                        Bundle bundle = new Bundle();
                        bundle.putString("bible", bibleSelected);
                        bundle.putString("BookName", searchData.getBookName());
                        bundle.putInt("BookNumber", searchData.getBookNumber());
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