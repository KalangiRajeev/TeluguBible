package com.ikalangirajeev.telugubiblemessages.ui.bible.app.chapters;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.FragmentNavigator;
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

    String bookName;
    int bookNumber;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bookName = getArguments().getString("BookName");
        bookNumber = getArguments().getInt("BookNumber");
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        chaptersViewModel =
                ViewModelProviders.of(this).get(ChaptersViewModel.class);
        View root = inflater.inflate(R.layout.fragment_chapters, container, false);

        chaptersViewModel.setBookNumber(bookNumber);
        chaptersViewModel.setBookName(bookName);

        recyclerView = root.findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 4);
        recyclerView.setLayoutManager(layoutManager);


        chaptersViewModel.getText().observe(getViewLifecycleOwner(), new Observer<List<Data>>() {
            @Override
            public void onChanged(List<Data> blogIndexList) {
                myRecyclerViewAdapter = new MyRecyclerViewAdapter(getActivity(), R.layout.card_view_chapters, blogIndexList);
                recyclerView.setAdapter(myRecyclerViewAdapter);

                myRecyclerViewAdapter.setOnItemClickListener(new MyRecyclerViewAdapter.OnItemClickListener() {
                    @Override
                    public void OnItemClick(Data blogIndex, int position) {

//                        FragmentNavigator.Extras extras = new FragmentNavigator.Extras.Builder()
//                                .addSharedElement(recyclerView.getChildAt(position), "chapters_verses")
//                                .build();

                        Bundle bundle = new Bundle();
                        bundle.putString("BookName", bookName);
                        bundle.putInt("BookNumber", bookNumber);
                        bundle.putInt("ChapterNumber",position);

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