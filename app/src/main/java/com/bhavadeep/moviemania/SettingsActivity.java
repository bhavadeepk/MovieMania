package com.bhavadeep.moviemania;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.widget.Toast;

import java.util.List;
import java.util.prefs.Preferences;

/**
 * A {@link PreferenceActivity} that presents a set of application settings.
 * <p>
 * See <a href="http://developer.android.com/design/patterns/settings.html">
 * Android Design: Settings</a> for design guidelines and the <a
 * href="http://developer.android.com/guide/topics/ui/settings.html">Settings
 * API Guide</a> for more information on developing a Settings UI.
 */
public class SettingsActivity extends PreferenceActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Add 'general' preferences, defined in the XML file
        getFragmentManager().beginTransaction().replace(android.R.id.content,
                new PrefFragment()).commit();
        PreferenceManager.setDefaultValues(SettingsActivity.this, R.xml.settings, false);

        // For all preferences, attach an OnPreferenceChangeListener so the UI summary can be
        // updated when the preference changes.

    }


    public static class PrefFragment
            extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            // Load the preferences from an XML resource
            addPreferencesFromResource(R.xml.settings);
            Preference sortPreference = (Preference) findPreference("sort_key");


            Preference.OnPreferenceChangeListener listener = new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    String stringValue = newValue.toString();
                    ListPreference listPreference = (ListPreference) preference;
                    int prefIndex = listPreference.findIndexOfValue(stringValue);
                    if (prefIndex >= 0) {
                        preference.setSummary(listPreference.getEntries()[prefIndex]);
                    }
                    getActivity().onBackPressed();
                    return true;
                }
            };
            sortPreference.setOnPreferenceChangeListener(listener);


        }


    }
}
