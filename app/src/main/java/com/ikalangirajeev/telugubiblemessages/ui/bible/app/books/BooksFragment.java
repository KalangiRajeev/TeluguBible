package com.ikalangirajeev.telugubiblemessages.ui.bible.app.books;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ikalangirajeev.telugubiblemessages.R;
import com.ikalangirajeev.telugubiblemessages.ui.bible.app.Data;
import com.ikalangirajeev.telugubiblemessages.ui.bible.app.MyRecyclerViewAdapter;


import java.util.List;

public class BooksFragment extends Fragment {

    private static final String TAG = "BooksFragment";

    private NavController navController;

    private BooksViewModel booksViewModel;
    private RecyclerView recyclerView;
    private MyRecyclerViewAdapter myRecyclerViewAdapter;
    private String bibleSelected;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        bibleSelected = getArguments().getString("bible");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        booksViewModel =
                ViewModelProviders.of(this).get(BooksViewModel.class);
        View view = inflater.inflate(R.layout.fragment_books, container, false);


        recyclerView = view.findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(),2,
                GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        booksViewModel.getText(bibleSelected).observe(getViewLifecycleOwner(), new Observer<List<Data>>() {
            @Override
            public void onChanged(List<Data> blogIndexList) {
                myRecyclerViewAdapter = new MyRecyclerViewAdapter(getActivity(), R.layout.card_view_books, blogIndexList);
                recyclerView.setAdapter(myRecyclerViewAdapter);

                myRecyclerViewAdapter.setOnItemClickListener(new MyRecyclerViewAdapter.OnItemClickListener() {
                    @Override
                    public void OnItemClick(Data data, int position) {
//                        FragmentNavigator.Extras extras = new FragmentNavigator.Extras.Builder()
//                                .addSharedElement(recyclerView.getChildAt(position), "books_chapters")
//                                .build();

                        Bundle bundle = new Bundle();
                        bundle.putString("bible", bibleSelected);
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
        navController = Navigation.findNavController(view);
    }
}