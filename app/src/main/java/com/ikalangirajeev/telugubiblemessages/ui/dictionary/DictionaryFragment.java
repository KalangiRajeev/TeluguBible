package com.ikalangirajeev.telugubiblemessages.ui.dictionary;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ikalangirajeev.telugubiblemessages.R;

import java.util.List;

public class DictionaryFragment extends Fragment {

    private DictViewModel dictViewModel;
    private RecyclerView recyclerView;
    private DictRecyclerViewAdapter dictRecyclerViewAdapter;

    private String searchQuery;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        searchQuery = getArguments().getString("SearchDict");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        dictViewModel = new ViewModelProvider(this).get(DictViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dictionary, container, false);

        recyclerView = root.findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        dictViewModel.getDictionary(searchQuery).observe(getViewLifecycleOwner(), new Observer<List<DictEngTel>>() {
            @Override
            public void onChanged(List<DictEngTel> dictEngTels) {
                dictRecyclerViewAdapter = new DictRecyclerViewAdapter(getActivity(), R.layout.card_view_dictionary_items, dictEngTels, searchQuery);
                recyclerView.setAdapter(dictRecyclerViewAdapter);
            }
        });

        return root;
    }
}
