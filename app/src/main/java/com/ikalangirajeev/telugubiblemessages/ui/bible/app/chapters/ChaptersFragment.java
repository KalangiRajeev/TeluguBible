package com.ikalangirajeev.telugubiblemessages.ui.bible.app.chapters;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ikalangirajeev.telugubiblemessages.ui.bible.app.Data;
import com.ikalangirajeev.telugubiblemessages.ui.bible.app.MyRecyclerViewAdapter;
import com.ikalangirajeev.telugubiblemessages.R;

import java.util.List;

public class ChaptersFragment extends Fragment {

    private static final String TAG = "ChaptersFragment";

    private NavController navController;


    private RecyclerView recyclerView;
    private ChaptersViewModel chaptersViewModel;
    MyRecyclerViewAdapter myRecyclerViewAdapter;

    private String bibleSelected;
    private String bookName;
    private int bookNumber;
    private int chaptersCount;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bibleSelected = getArguments().getString("bible");
        bookName = getArguments().getString("BookName");
        bookNumber = getArguments().getInt("BookNumber");
        chaptersCount = getArguments().getInt("ChaptersCount");
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        chaptersViewModel =
                ViewModelProviders.of(this).get(ChaptersViewModel.class);
        View root = inflater.inflate(R.layout.fragment_chapters, container, false);


        recyclerView = root.findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 5);
        recyclerView.setLayoutManager(layoutManager);


        chaptersViewModel.getText(bibleSelected, bookNumber, chaptersCount).observe(getViewLifecycleOwner(), new Observer<List<Data>>() {
            @Override
            public void onChanged(List<Data> blogIndexList) {
                myRecyclerViewAdapter = new MyRecyclerViewAdapter(getActivity(), R.layout.card_view_chapters, blogIndexList);
                recyclerView.setAdapter(myRecyclerViewAdapter);

                myRecyclerViewAdapter.setOnItemClickListener(new MyRecyclerViewAdapter.OnItemClickListener() {
                    @Override
                    public void OnItemClick(Data blogIndex, int position) {

                        Bundle bundle = new Bundle();
                        bundle.putString("bible", bibleSelected);
                        bundle.putString("BookName", bookName);
                        bundle.putInt("BookNumber", bookNumber);
                        bundle.putInt("ChapterNumber",position+1);
                        bundle.putInt("ChapterCount", myRecyclerViewAdapter.getItemCount());

                        navController.navigate(R.id.action_chaptersFragment_to_versesFragment, bundle, null, null);
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