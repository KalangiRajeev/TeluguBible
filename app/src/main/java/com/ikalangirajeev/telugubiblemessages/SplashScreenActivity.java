package com.ikalangirajeev.telugubiblemessages;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.transition.Explode;
import android.transition.Transition;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SplashScreenActivity extends AppCompatActivity {

    public static final int SPLASH_SREEN_TIME = 5000;

    private ImageView imageView;
    private TextView textView;
    private Animation imageAnimation, textAnimation;
    private AnimatorSet animatorSet;
    private ConstraintLayout splashSreenRoot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        imageView = findViewById(R.id.splashScreenImage);
        textView = findViewById(R.id.splashTextView);
        splashSreenRoot = findViewById(R.id.splashScreenRoot);

//        imageAnimation = AnimationUtils.loadAnimation(this, R.anim.scale_down_top);
        textAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_in_left);

        animatorSet = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.splash_screen_animator);
        animatorSet.setTarget(splashSreenRoot);
//        animatorSet.setTarget(textView);
        animatorSet.start();

//        imageView.setAnimation(imageAnimation);
//        textView.setAnimation(textAnimation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);

                Pair[] pairs = new Pair[1];
                pairs[0] = new Pair<View, String>(splashSreenRoot, "rootTransition");
                ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(SplashScreenActivity.this, pairs);
                startActivity(intent, activityOptions.toBundle());

                finish();
            }
        }, SPLASH_SREEN_TIME);

    }
}
