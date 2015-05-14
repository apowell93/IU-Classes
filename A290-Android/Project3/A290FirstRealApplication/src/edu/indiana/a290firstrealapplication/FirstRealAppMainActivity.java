/* FirstRealAppMainActivity.java
 * 
 * Main activity for the application
 * Displays the home screen
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.content.Intent;
import android.view.View.OnClickListener;
import android.view.MenuInflater;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;

public class FirstRealAppMainActivity extends Activity implements OnClickListener {

	//for logging/debugging
	private static final String TAG= "FirstRealAppMainActivity";
	
	public void onClick(View v) {
		
		//handles each case based on which button is clicked
		switch (v.getId()) {
		
		//displays about screen - start about activity
		case R.id.AboutButton:
			Intent i = new Intent(this, FirstRealAppAboutActivity.class);
			startActivity(i);
		break;
		
		//starts new game
		case R.id.NewButton:
			openNewGameDialog();
		break;
		
		//exit application
		case R.id.ExitButton:
			finish();
		break;
		
		}
	}
	
	//interface for selecting difficulty after "NewGame" is clicked
	private void openNewGameDialog() {
		new AlertDialog.Builder(this)
			.setTitle(R.string.NewGameTitle)
			/*If you have an icon you added, and you want to have it appear as part
			 * of this dialog, add this next line*/
			.setIcon(R.drawable.ic_launcher)
			.setItems(R.array.difficulty,
			new android.content.DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialoginterface,
					int i) {
				startGame(i);
				}
			})
	    	.show();
	}
	
	//start FirstRealAppGameActivity to start a new game
	private void startGame(int i){
		Log.d(TAG, "Clicked on " + i);
		Intent intent = new Intent(this, FirstRealAppGameActivity.class);
		intent.putExtra(FirstRealAppGameActivity.KEY_DIFFICULTY, i);
		startActivity(intent);
	}
	
	
	//allows buttons to be clicked with an action using a ClickListener
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_real_app_main);
        
        View ContinueButton = findViewById(R.id.ContinueButton);
        ContinueButton.setOnClickListener(this);
        
        View NewButton = findViewById(R.id.NewButton);
        NewButton.setOnClickListener(this);
        
        View AboutButton = findViewById(R.id.AboutButton);
        AboutButton.setOnClickListener(this);
        
        View ExitButton = findViewById(R.id.ExitButton);
        ExitButton.setOnClickListener(this);
    }

    //for displaying setting/options
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	super.onCreateOptionsMenu(menu);
    	MenuInflater inflater = getMenuInflater();
    	inflater.inflate(R.menu.menu_first_real_app_settings, menu);
    	return true;
    }

    //opens settings page - start prefs activity
    @Override
    public boolean onOptionsItemSelected (MenuItem item){
    	switch (item.getItemId()) {
    	case R.id.SettingsMenu:
    		startActivity (new Intent(this, FirstRealAppPrefsActivity.class));
    		return true;
    		/*We can add more items to this switch-case construct, if and when we need them*/
    	}
    	return false;
    }
}
