package com.ikalangirajeev.telugubiblemessages.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.preference.PreferenceFragmentCompat;

import com.ikalangirajeev.telugubiblemessages.R;

public class SettingsFragment extends PreferenceFragmentCompat {

    private static final String TAG = "SettingsFragment";
    public static final String PREF_SELECTED_BIBLE = "bibleSelected";
    SharedPreferences prefs;
    NavController navController;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);

        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());

        prefs.registerOnSharedPreferenceChangeListener(new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                if(SettingsFragment.PREF_SELECTED_BIBLE.equals(key)) {
                    String bibleSelected = sharedPreferences.getString(key, "bible_english");
                    Log.d(TAG, "onSharedPreferenceChanged: " + bibleSelected + " : " + key);
                    Toast.makeText(getActivity().getApplicationContext(), bibleSelected, Toast.LENGTH_LONG).show();
                    Bundle bundle = new Bundle();
                    bundle.putString(SettingsFragment.PREF_SELECTED_BIBLE, bibleSelected);
                    navController.navigate(R.id.bibleFragment, bundle, new NavOptions.Builder()
                            .setPopUpTo(R.id.settingsFragment, true)
                            .setEnterAnim(R.anim.slide_in_right)
                            .setExitAnim(R.anim.slide_out_left)
                            .setPopEnterAnim(R.anim.slide_in_left)
                            .setPopExitAnim(R.anim.slide_out_right)
                            .build());
                }
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
    }
}