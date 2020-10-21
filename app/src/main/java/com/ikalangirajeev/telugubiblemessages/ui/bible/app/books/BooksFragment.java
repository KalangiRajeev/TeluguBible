package com.ikalangirajeev.telugubiblemessages.ui.bible.app.books;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.transition.Explode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.core.os.HandlerCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ikalangirajeev.telugubiblemessages.R;
import com.ikalangirajeev.telugubiblemessages.ui.SettingsFragment;
import com.ikalangirajeev.telugubiblemessages.ui.bible.app.Data;
import com.ikalangirajeev.telugubiblemessages.ui.bible.app.MyRecyclerViewAdapter;


import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BooksFragment extends Fragment {

    private static final String TAG = "BooksFragment";

    private NavController navController;

    private BooksViewModel booksViewModel;
    private RecyclerView recyclerView;
    private MyRecyclerViewAdapter myRecyclerViewAdapter;
    private String bibleSelected;
    private SharedPreferences prefs;
    private boolean isTablet;
    private View view;

    ExecutorService executorService = Executors.newFixedThreadPool(4);
    Handler handler = HandlerCompat.createAsync(Looper.getMainLooper());

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        isTablet = getActivity().getApplicationContext().getResources().getBoolean(R.bool.isTablet);

        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        bibleSelected = getArguments().getString("bibleSelected", prefs.getString(SettingsFragment.PREF_SELECTED_BIBLE, "bible_english"));
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        super.onPrepareOptionsMenu(menu);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        booksViewModel =
                new ViewModelProvider(this).get(BooksViewModel.class);

        if (isTablet) {
            view = inflater.inflate(R.layout.master_detail_nav_container, container, false);
        } else {
            view = inflater.inflate(R.layout.fragment_books, container, false);
        }

        recyclerView = view.findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 2,
                GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        myRecyclerViewAdapter = new MyRecyclerViewAdapter(getActivity(), R.layout.card_view_books);
        recyclerView.setAdapter(myRecyclerViewAdapter);


        booksViewModel.getText(bibleSelected).observe(getViewLifecycleOwner(), new Observer<List<Data>>() {
            @Override
            public void onChanged(final List<Data> data) {

                myRecyclerViewAdapter.setDataList(data);

                myRecyclerViewAdapter.setOnRyVwItemClickListener(new MyRecyclerViewAdapter.OnRyVwItemClickListener() {
                    @Override
                    public void OnRyVwItemClick(Data data, int position) {
                        Bundle bundle = new Bundle();
                        bundle.putString("bibleSelected", bibleSelected);
                        bundle.putString("BookName", data.getHeader());
                        bundle.putInt("BookNumber", position);
                        bundle.putInt("ChaptersCount", data.getRefLink());
                        navController.navigate(R.id.action_bibleFragment_to_chaptersFragment, bundle);
                    }
                });
            }

        });

        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (isTablet) {
            navController = Navigation.findNavController(getActivity(), R.id.navigation_master_detail);
        } else {
            navController = Navigation.findNavController(view);
        }
    }
}