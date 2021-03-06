/* LearnGuitarPrefsActivity.java
 * 
 * Activity for the prefs/settings page of the application
 * 
 * Created By: Anthony Powell (anjopowe)
 * Created On: 3/2/2015
 * Last Modified By: Anthony Powell (anjopowe)
 * Last Modified On: 3/6/2015
 * Assignment/Project: A290 Android Development
 * Part Of: Final Project - Learn Guitar
 */

package edu.indiana.learnguitar;

import android.os.Bundle;
import android.preference.PreferenceActivity;

//prefs activity displays the settings page using options from settings.xml
public class LearnGuitarPrefsActivity extends PreferenceActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.settings);
	}
}