/* FirstRealAppPrefsActivity.java
 * 
 * Activity to display settings page
 * 
 * Created By: Anthony Powell (anjopowe)
 * Created On: 2/17/2015
 * Last Modified By: Anthony Powell (anjopowe)
 * Last Modified On: 2/19/2015
 * Assignment/Project: A290 Android Development
 * Part Of: Project 3 - A290FirstRealApplication
 */

package edu.indiana.a290firstrealapplication;

import android.os.Bundle;
import android.preference.PreferenceActivity;


//prefs activity displays the settings page using options from settings.xml
public class FirstRealAppPrefsActivity extends PreferenceActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.settings);
	}
}
