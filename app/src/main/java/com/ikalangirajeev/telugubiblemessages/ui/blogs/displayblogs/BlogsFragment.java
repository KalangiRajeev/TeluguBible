package com.ikalangirajeev.telugubiblemessages.ui.blogs.displayblogs;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.ikalangirajeev.telugubiblemessages.R;

import java.util.List;

public class BlogsFragment extends Fragment {

    private NavController navController;

    private BlogsViewModel blogsViewModel;
    private RecyclerView recyclerView;
    BlogsListRecyclerViewAdapter blogsListRecyclerViewAdapter;
    private String searchName;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        searchName = getArguments().getString("SearchBlogs");
        if (searchName.equals("None")) {
            searchName = "";
        } else {
            searchName = searchName.toUpperCase().trim();
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        blogsViewModel =
                ViewModelProviders.of(this).get(BlogsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_blogs, container, false);

        recyclerView = root.findViewById(R.id.recyclerViewBlogsList);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        blogsViewModel.getBlogIndex(searchName).observe(getViewLifecycleOwner(), new Observer<String>() {
           @Override
           public void onChanged(String searchName) {

               DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("blogs2020");
               Query query = databaseReference.orderByChild("authorName").startAt(searchName).endAt(searchName + "\uf8ff")
                       .limitToLast(500);

               FirebaseRecyclerOptions<BlogIndex> firebaseRecyclerOptions =
                       new FirebaseRecyclerOptions.Builder<BlogIndex>()
                               .setLifecycleOwner(getViewLifecycleOwner())
                               .setQuery(query, BlogIndex.class)
                               .build();

               blogsListRecyclerViewAdapter =
                       new BlogsListRecyclerViewAdapter(firebaseRecyclerOptions, R.layout.card_view_blogs);

               recyclerView.setAdapter(blogsListRecyclerViewAdapter);
               blogsListRecyclerViewAdapter.setOnItemClickListener(new BlogsListRecyclerViewAdapter.OnItemClickListener() {
                   @Override
                   public void OnItemClick(BlogIndex blogIndex, int position) {

                       Bundle bundle = new Bundle();
                       bundle.putString("blogId", blogIndex.getBlogId());
                       bundle.putString("authorName", blogIndex.getAuthorName());
                       bundle.putString("authorEmail", blogIndex.getEmail());
                       bundle.putString("timeStamp", blogIndex.getTimeStamp());
                       bundle.putString("blogTitle", blogIndex.getBlogTitle());
                       bundle.putString("blogDescription", blogIndex.getBlogDescription());
                       bundle.putInt("blogLikes", blogIndex.getLikesCounter());
                       bundle.putInt("blogDislikes", blogIndex.getDislikesCounter());
                       bundle.putInt("blogComments", blogIndex.getCommentsCounter());

                       navController.navigate(R.id.action_blogsFragment_to_detailBlogFragment, bundle);
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