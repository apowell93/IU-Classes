/* FirstRealAppAboutActivity.java
 * 
 * Activity for the about page of the application
 * 
 * Created By: Anthony Powell (anjopowe)
 * Created On: 2/17/2015
 * Last Modified By: Anthony Powell (anjopowe)
 * Last Modified On: 2/19/2015
 * Assignment/Project: A290 Android Development
 * Part Of: Project 3 - A290FirstRealApplication
 */

package edu.indiana.a290firstrealapplication;

import android.app.Activity;
import android.os.Bundle;

public class FirstRealAppAboutActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		//uses layout created in activity_first_real_app_about.xml
		setContentView(R.layout.activity_first_real_app_about);
	}
}
