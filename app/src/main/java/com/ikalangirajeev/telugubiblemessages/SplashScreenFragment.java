package com.ikalangirajeev.telugubiblemessages;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SplashScreenFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SplashScreenFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final long SPLASH_SREEN_TIME = 5000;
    private int currentApiVersion;

    private ImageView imageView;
    private AnimatorSet animatorSet;

    private FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SplashScreenFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SplashScreenFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SplashScreenFragment newInstance(String param1, String param2) {
        SplashScreenFragment fragment = new SplashScreenFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        ((MainActivity)getActivity()).getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_splash_screen, container, false);

        imageView = root.findViewById(R.id.splashScreenImage);

        animatorSet = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(), R.animator.splash_screen_animator);
        animatorSet.setTarget(imageView);
        animatorSet.start();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                if (firebaseUser == null) {
                    navController.navigate(R.id.loginFragment, null, new NavOptions.Builder()
                    .setPopUpTo(R.id.splashScreenFragment, true)
                            .setEnterAnim(R.anim.slide_in_right)
                            .setExitAnim(R.anim.slide_out_left)
                            .setPopEnterAnim(R.anim.slide_in_left)
                            .setPopExitAnim(R.anim.slide_out_right)
                    .build());
                } else if (!firebaseUser.isEmailVerified()) {

                    firebaseUser.getDisplayName();
                    firebaseUser.getEmail();
                    firebaseUser.isEmailVerified();
                    firebaseUser.getPhoneNumber();

                    Bundle bundle = new Bundle();
                    bundle.putString("email", firebaseUser.getEmail());
                    Toast.makeText(getContext(), firebaseUser.getEmail(), Toast.LENGTH_LONG).show();
                    navController.navigate(R.id.verifyEmailFragment, bundle, new NavOptions.Builder()
                            .setPopUpTo(R.id.splashScreenFragment, true)
                            .setEnterAnim(R.anim.slide_in_right)
                            .setExitAnim(R.anim.slide_out_left)
                            .setPopEnterAnim(R.anim.slide_in_left)
                            .setPopExitAnim(R.anim.slide_out_right).build());
                } else {
                    navController.navigate(R.id.bibleFragment, null, new NavOptions.Builder()
                            .setPopUpTo(R.id.splashScreenFragment, true)
                            .setEnterAnim(R.anim.slide_in_right)
                            .setExitAnim(R.anim.slide_out_left)
                            .setPopEnterAnim(R.anim.slide_in_left)
                            .setPopExitAnim(R.anim.slide_out_right)
                            .build());
                }
            }
        }, SPLASH_SREEN_TIME);

        return root;
    }
}
