package com.lrony.iread.presentation.settings;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;

import com.lrony.iread.R;

/**
 * Created by Lrony on 18-5-22.
 */
public class SettingsFragment extends PreferenceFragment {

    private static final String TAG = "SettingFragment";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_settings);
    }

    @Override
    public void onStart() {
        super.onStart();
        register();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    private void register() {
//        Preference nightPref = findPreference(R.string.pref_key_mode_night);
//        nightPref.setOnPreferenceClickListener(preference -> {
//            boolean isNight = PreferencesHelper.getInstance().isNightMode();
//            Logcat.d(TAG, isNight);
//            AppCompatActivity activity = (AppCompatActivity) getActivity();
//            PreferencesHelper.getInstance().setNightMode(isNight);
//            if (isNight) {
//                activity
//                        .getDelegate()
//                        .setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//            } else {
//                activity
//                        .getDelegate()
//                        .setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//            }
//            activity.recreate();
//            return true;
//        });
    }

    private Preference findPreference(int id) {
        return findPreference(getString(id));
    }
}
