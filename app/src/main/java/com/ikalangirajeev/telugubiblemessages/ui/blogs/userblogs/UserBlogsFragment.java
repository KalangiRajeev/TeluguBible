package com.ikalangirajeev.telugubiblemessages.ui.blogs.userblogs;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.ikalangirajeev.telugubiblemessages.R;
import com.ikalangirajeev.telugubiblemessages.ui.blogs.displayblogs.BlogIndex;
import com.ikalangirajeev.telugubiblemessages.ui.blogs.displayblogs.BlogsListRecyclerViewAdapter;

import java.util.Collections;
import java.util.List;

public class UserBlogsFragment extends Fragment {

    private NavController navController;

    private UserBlogsViewModel userBlogsViewModel;
    private RecyclerView recyclerView;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        userBlogsViewModel =
                ViewModelProviders.of(this).get(UserBlogsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_user_blogs, container, false);

        recyclerView = root.findViewById(R.id.recyclerViewBlogsList);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);


        userBlogsViewModel.getBlogIndex(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String userUid) {
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("blogs2020");

                Query query = databaseReference.orderByChild("authorUid")
                        .equalTo(userUid);

                FirebaseRecyclerOptions<BlogIndex> firebaseRecyclerOptions =
                        new FirebaseRecyclerOptions.Builder<BlogIndex>()
                                .setLifecycleOwner(getViewLifecycleOwner())
                                .setQuery(query, BlogIndex.class)
                                .build();

                UserBlogsListRecyclerViewAdapter userBlogsListRecyclerViewAdapter =
                        new UserBlogsListRecyclerViewAdapter(firebaseRecyclerOptions, R.layout.card_view_user_blogs);

                recyclerView.setAdapter(userBlogsListRecyclerViewAdapter);

                userBlogsListRecyclerViewAdapter.setOnItemClickListener(new UserBlogsListRecyclerViewAdapter.OnItemClickListener() {
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

                        navController.navigate(R.id.detailBlogFragment, bundle, new NavOptions.Builder()
                                .setEnterAnim(R.anim.slide_in_right)
                                .setExitAnim(R.anim.slide_out_left)
                                .setPopEnterAnim(R.anim.slide_in_left)
                                .setPopExitAnim(R.anim.slide_out_right)
                                .build());
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