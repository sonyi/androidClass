package com.preferencesdemo;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;
import android.widget.Toast;

public class SettingActivity extends PreferenceActivity {
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.setting);
		
		ListPreference lpFavorite = (ListPreference) findPreference("favorite");
		lpFavorite.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
			
			@Override
			public boolean onPreferenceChange(Preference preference, Object newValue) {
				Toast.makeText(SettingActivity.this, "兴趣发生了变更", Toast.LENGTH_SHORT).show();
				return true;
			}
		});
	}
}
