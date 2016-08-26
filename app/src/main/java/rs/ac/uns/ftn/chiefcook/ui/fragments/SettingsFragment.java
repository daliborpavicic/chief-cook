package rs.ac.uns.ftn.chiefcook.ui.fragments;

import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;

import rs.ac.uns.ftn.chiefcook.R;

/**
 * Created by daliborp on 26.8.16..
 */
public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle bundle, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);

        getActivity().setTitle(R.string.menu_title_settings);
    }
}
