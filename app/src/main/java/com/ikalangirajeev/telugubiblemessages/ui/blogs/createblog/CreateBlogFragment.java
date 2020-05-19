package com.ikalangirajeev.telugubiblemessages.ui.blogs.createblog;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.ikalangirajeev.telugubiblemessages.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class CreateBlogFragment extends Fragment {

    private static final String TAG = "CreateBlogFragment";

    private CreateBlogViewModel createBlogViewModel;
    private NavController navController;


    private TextView textViewAuthorName;
    private TextView textViewEmail;
    private TextInputLayout editTextBlogTitle;
    private TextInputLayout editTextBlogDescription;
    private Button buttonSubmit;

    private String authorName;
    private String authorEmail;
    private String authorUid;
    private String blogId;
    private String timeStamp;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        createBlogViewModel =
                ViewModelProviders.of(this).get(CreateBlogViewModel.class);
        View root = inflater.inflate(R.layout.fragment_create_blog, container, false);

        textViewAuthorName = root.findViewById(R.id.textViewAuthorName);
        textViewEmail = root.findViewById(R.id.textViewEmail);
        editTextBlogTitle = root.findViewById(R.id.editTextBlogTitle);
        editTextBlogDescription = root.findViewById(R.id.textViewBlogDescription);
        buttonSubmit = root.findViewById(R.id.buttonSubmit);

        if(FirebaseAuth.getInstance().getCurrentUser()!=null) {
            authorUid = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
            authorName = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getDisplayName();
            authorEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        } else {
            Toast.makeText(getActivity(), "Only Loggedin user can Post", Toast.LENGTH_SHORT).show();
            navController.navigate(R.id.bibleFragment, null, new NavOptions.Builder()
                    .setEnterAnim(R.anim.slide_in_right)
                    .setExitAnim(R.anim.slide_out_left)
                    .setPopEnterAnim(R.anim.slide_in_left)
                    .setPopExitAnim(R.anim.slide_out_right)
                    .build());
        }
        textViewAuthorName.setText(authorName);
        textViewEmail.setText(authorEmail);

        //SimpleDateFormat formatter = new SimpleDateFormat("yyMMddHHmmssZ");
        Date date = new Date();
        timeStamp = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.SHORT).format(date);

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(editTextBlogTitle.getEditText().getText().toString().trim())) {
                    editTextBlogTitle.setError("Title can't be empty");
                    editTextBlogTitle.requestFocus();
                } else if (TextUtils.isEmpty(editTextBlogDescription.getEditText().getText().toString().trim())) {
                    editTextBlogDescription.setError("Description can't be empty");
                    editTextBlogTitle.requestFocus();
                } else {

                    Log.d(TAG, "onCreate: " + blogId + "\n" + authorUid + "\n"
                            + authorName + "\n"
                            + authorEmail + "\n"
                            + editTextBlogTitle.getEditText().getText().toString().trim() + "\n"
                            + editTextBlogDescription.getEditText().getText().toString().trim());

                    blogId = FirebaseDatabase.getInstance().getReference("blogs2020").push().getKey();

                    CreateBlog createBlog = new CreateBlog(blogId,
                            authorUid,
                            authorName,
                            timeStamp,
                            authorEmail,
                            editTextBlogTitle.getEditText().getText().toString().trim(),
                            editTextBlogDescription.getEditText().getText().toString().trim(),
                            0, 0, 0);

                    createBlogViewModel.getCreateBlog(createBlog).observe(getViewLifecycleOwner(), new Observer<String>() {
                        @Override
                        public void onChanged(@Nullable String s) {
                                Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();
                                navController.navigate(R.id.userBlogsFragment, null, new NavOptions.Builder()
                                        .setEnterAnim(R.anim.slide_in_right)
                                        .setExitAnim(R.anim.slide_out_left)
                                        .setPopEnterAnim(R.anim.slide_in_left)
                                        .setPopExitAnim(R.anim.slide_out_right)
                                        .build());
                        }
                    });
                }
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