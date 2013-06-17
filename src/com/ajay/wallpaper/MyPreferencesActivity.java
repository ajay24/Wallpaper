package com.ajay.wallpaper;

import android.os.Bundle;
import android.app.Activity;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;
import android.view.Menu;
import android.widget.Toast;

public class MyPreferencesActivity extends PreferenceActivity {

   
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        addPreferencesFromResource(R.xml.prefs);

        Preference numberofPointsPreference = getPreferenceScreen().findPreference("numberOfPoints");
        
        numberofPointsPreference.setOnPreferenceChangeListener(numberCheckListener);
    }

    Preference.OnPreferenceChangeListener numberCheckListener=new OnPreferenceChangeListener() {
		
		public boolean onPreferenceChange(Preference preference, Object newValue) {
			 if (newValue != null && newValue.toString().length() > 0
			          && newValue.toString().matches("\\d*")) {
			        return true;
			      }
			      // If now create a message to the user
			      Toast.makeText(MyPreferencesActivity.this, "Invalid Input", Toast.LENGTH_SHORT).show();
			      return false;
		}
	};
}
