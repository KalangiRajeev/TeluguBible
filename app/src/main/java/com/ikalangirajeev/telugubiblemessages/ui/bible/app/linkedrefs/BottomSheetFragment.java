package com.ikalangirajeev.telugubiblemessages.ui.bible.app.linkedrefs;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.ikalangirajeev.telugubiblemessages.R;
import com.ikalangirajeev.telugubiblemessages.ui.SettingsFragment;

import java.util.List;

public class BottomSheetFragment extends BottomSheetDialogFragment {

    private static final String TAG = "BottomSheetfragment";
    private BottomSheetViewModel bottomSheetViewModel;
    private TextView textViewHeader, textViewBody, linkedRefsCount;
    private RecyclerView recyclerView;
    private BottomSheetRecyclerViewAdapter bottomSheetRecyclerViewAdapter;


    private String bookName, verseBody, bibleSelected;
    private int bookNumber;
    private int chapterNumber;
    private int verseNumber;
    private int verseId;
    private SharedPreferences prefs;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        bibleSelected = getArguments().getString("bibleSelected", prefs.getString(SettingsFragment.PREF_SELECTED_BIBLE, "bible_english"));
        bookName = (getArguments().getString("bookName")!= null) ? getArguments().getString("bookName") : "";
        verseBody = (getArguments().getString("verseBody")!= null) ? getArguments().getString("verseBody") : "";
        bookNumber = getArguments().getInt("bookNumber");
        chapterNumber = getArguments().getInt("chapterNumber");
        verseNumber=getArguments().getInt("verseNumber");
        verseId = getArguments().getInt("verseId");
        Log.d(TAG, "onCreate: " + verseId);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        bottomSheetViewModel = new ViewModelProvider(this).get(BottomSheetViewModel.class);

        View view = inflater.inflate(R.layout.bottom_sheet, container, false);

        textViewHeader = view.findViewById(R.id.textViewHeader);
        textViewBody = view.findViewById(R.id.textViewBody);
        linkedRefsCount = view.findViewById(R.id.linkedRefsCount);
        recyclerView = view.findViewById(R.id.recyclerView);


        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        bottomSheetRecyclerViewAdapter = new BottomSheetRecyclerViewAdapter(getActivity(), R.layout.card_view_bottomsheet);
        recyclerView.setAdapter(bottomSheetRecyclerViewAdapter);

        textViewHeader.setText(bookName + " " + (chapterNumber) + ":" + (verseNumber + 1));
        textViewBody.setText(verseBody);

        bottomSheetViewModel.getData(bibleSelected, verseId).observe(getViewLifecycleOwner(), new Observer<List<LinkVerse>>() {
            @Override
            public void onChanged(List<LinkVerse> linkVerses) {
                bottomSheetRecyclerViewAdapter.setLinkVerseList(linkVerses);
                linkedRefsCount.setText(bottomSheetRecyclerViewAdapter.getItemCount() + " Cross References " );
            }
        });
        return view;
    }

}

