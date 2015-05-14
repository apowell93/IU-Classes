/* LearnGuitarTuner.java
 * 
 * Activity for the tuner page of the application
 * 
 * Created By: Anthony Powell (anjopowe)
 * Created On: 3/3/2015
 * Last Modified By: Anthony Powell (anjopowe)
 * Last Modified On: 3/6/2015
 * Assignment/Project: A290 Android Development
 * Part Of: Final Project - Learn Guitar
 */

package edu.indiana.learnguitar;

import android.os.Bundle;
import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

public class LearnGuitarTuner extends Activity implements OnClickListener {

	private MediaPlayer mp; // initialize mediaplayer
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_learn_guitar_tuner);
		
		//set listeners for each button
		setVolumeControlStream(AudioManager.STREAM_MUSIC);
		findViewById(R.id.tunerButtonELow).setOnClickListener(this);
		findViewById(R.id.tunerButtonA).setOnClickListener(this);
		findViewById(R.id.tunerButtonD).setOnClickListener(this);
		findViewById(R.id.tunerButtonG).setOnClickListener(this);
		findViewById(R.id.tunerButtonB).setOnClickListener(this);
		findViewById(R.id.tunerButtonEHigh).setOnClickListener(this);
	}
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.learn_guitar_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    public void onClick(View v){
    	int resId;
    	switch (v.getId()){
    	
    		//handle each buttons click fxn - playing the correct mp3
	    	case R.id.tunerButtonELow: resId=R.raw.tunerelow;
	    	break;
	    	case R.id.tunerButtonA: resId=R.raw.tunera;
	    	break;
	    	case R.id.tunerButtonD: resId=R.raw.tunerd;
	    	break;
	    	case R.id.tunerButtonG: resId=R.raw.tunerg;
	    	break;
	    	case R.id.tunerButtonB: resId=R.raw.tunerb;
	    	break;
	    	case R.id.tunerButtonEHigh: resId=R.raw.tunerehigh;
	    	break;
	    	default: resId=R.raw.tunerelow;
	    	break;
    	}
    	if (mp != null) {
    		mp.release();
    	}
    	//strat MediaPlayer
    	mp = MediaPlayer.create(this, resId);
    	mp.start();
    }
}