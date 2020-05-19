package com.ikalangirajeev.telugubiblemessages.ui.bible.app.verses;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

import com.ikalangirajeev.telugubiblemessages.R;
import com.ikalangirajeev.telugubiblemessages.ui.bible.app.Data;

import java.util.List;

public class VersesFragment extends Fragment {

    private VersesViewModel versesViewModel;
    private RecyclerView recyclerView;
    private VersesRecyclerViewAdapter myRecyclerViewAdapter;
    private NavController navController;


    private String bookName;
    private int bookNumber;
    private int chapterNumber;
    private int verseNumber;
    private int highlightVerseNumber;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.bookName = getArguments().getString("BookName");
        this.bookNumber = getArguments().getInt("BookNumber");
        this.chapterNumber = getArguments().getInt("ChapterNumber");
        this.verseNumber = getArguments().getInt("VerseNumber");
        this.highlightVerseNumber = getArguments().getInt("HighlightVerseNumber");

    }



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        versesViewModel =
                ViewModelProviders.of(this).get(VersesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_verses, container, false);

        versesViewModel.setBookNumber(bookNumber);
        versesViewModel.setChapterNumber(chapterNumber);

        recyclerView = root.findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        final RecyclerView.SmoothScroller smoothScroller = new LinearSmoothScroller(getActivity()){
            @Override
            protected int getVerticalSnapPreference() {
                return LinearSmoothScroller.SNAP_TO_START;
            }
        };
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT |ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {


                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                if(ItemTouchHelper.LEFT == direction){
                    Toast.makeText(getActivity(), "Swiped to LEFT", Toast.LENGTH_LONG).show();

                } else if (ItemTouchHelper.RIGHT == direction){
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    int verseNumber = viewHolder.getAdapterPosition() + 1;
                    sendIntent.putExtra(Intent.EXTRA_TEXT, bookName + " " + (chapterNumber + 1) + ":" + verseNumber + "\n"
                            + myRecyclerViewAdapter.getDataAt(viewHolder.getAdapterPosition()).getHeader());

                    sendIntent.setType("text/plain");
                    Intent shareIntent = Intent.createChooser(sendIntent, null);
                    startActivity(shareIntent);

                    Toast.makeText(getActivity(), "Share...", Toast.LENGTH_LONG).show();
                }
            }
        }).attachToRecyclerView(recyclerView);



        versesViewModel.getData().observe(getViewLifecycleOwner(), new Observer<List<Data>>() {
            @Override
            public void onChanged(List<Data> dataList) {
                myRecyclerViewAdapter = new VersesRecyclerViewAdapter(getActivity(), R.layout.card_view_verses, dataList);
                recyclerView.setAdapter(myRecyclerViewAdapter);
                recyclerView.scrollToPosition(highlightVerseNumber);
                myRecyclerViewAdapter.setHighlightColor(highlightVerseNumber);

                myRecyclerViewAdapter.setOnItemClickListener(new VersesRecyclerViewAdapter.OnItemClickListener() {
                    @Override
                    public void OnItemClick(Data blogIndex, int position) {

                    }
                });
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