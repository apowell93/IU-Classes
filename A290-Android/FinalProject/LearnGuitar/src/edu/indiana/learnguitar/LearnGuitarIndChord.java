/* LearnGuitarIndChord.java
 * 
 * Activity for the individual chords page of the application
 * 
 * Created By: Anthony Powell (anjopowe)
 * Created On: 3/3/2015
 * Last Modified By: Anthony Powell (anjopowe)
 * Last Modified On: 3/6/2015
 * Assignment/Project: A290 Android Development
 * Part Of: Final Project - Learn Guitar
 */

package edu.indiana.learnguitar;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class LearnGuitarIndChord extends Activity {

	public static final String CPOS = "edu.indiana.learnguitar.cPosition";
	public static final int DEFAULT_CHORD = 0;
	
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       
        //layout params for objects within layout
        LayoutParams lparams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        
        /*data for chords - START*/
        int chordPosition = getIntent().getIntExtra(CPOS, DEFAULT_CHORD);
        String title;
        ImageView chordDiagram = new ImageView(this);
        if (chordPosition == 0) {
        	//A Major
        	title = "A Major";
        	chordDiagram.setImageResource(R.drawable.amajor);
        	chordDiagram.setLayoutParams(lparams);
        	chordDiagram.setId(View.generateViewId());
        } else if (chordPosition == 1) {
        	//A minor
        	title = "A minor";
        	chordDiagram.setImageResource(R.drawable.aminor);
        	chordDiagram.setLayoutParams(lparams);
        	chordDiagram.setId(View.generateViewId());
        } else if (chordPosition == 2) {
        	//C Major
        	title = "C Major";
        	chordDiagram.setImageResource(R.drawable.cmajor);
        	chordDiagram.setLayoutParams(lparams);
        	chordDiagram.setId(View.generateViewId());
        } else if (chordPosition == 3) {
        	//D Major
        	title = "D Major";
        	chordDiagram.setImageResource(R.drawable.dmajor);
        	chordDiagram.setLayoutParams(lparams);
        	chordDiagram.setId(View.generateViewId());
        } else if (chordPosition == 4) {
        	//D minor
        	title = "D minor";
        	chordDiagram.setImageResource(R.drawable.dminor);
        	chordDiagram.setLayoutParams(lparams);
        	chordDiagram.setId(View.generateViewId());
        } else if (chordPosition == 5) {
        	//E Major
        	title = "E Major";
        	chordDiagram.setImageResource(R.drawable.emajor);
        	chordDiagram.setLayoutParams(lparams);
        	chordDiagram.setId(View.generateViewId());
        } else if (chordPosition == 6) {
        	//E minor
        	title = "E minor";
        	chordDiagram.setImageResource(R.drawable.eminor);
        	chordDiagram.setLayoutParams(lparams);
        	chordDiagram.setId(View.generateViewId());
        } else if (chordPosition == 7) {
        	//F Major
        	title = "F Major";
        	chordDiagram.setImageResource(R.drawable.fmajor);
        	chordDiagram.setLayoutParams(lparams);
        	chordDiagram.setId(View.generateViewId());
        } else if (chordPosition == 8) {
        	//G Major
        	title = "G Major";
        	chordDiagram.setImageResource(R.drawable.gmajor);
        	chordDiagram.setLayoutParams(lparams);
        	chordDiagram.setId(View.generateViewId());
        } else {
        	//defaults to A Major
        	title = "A Major";
        	chordDiagram.setImageResource(R.drawable.amajor);
        	chordDiagram.setLayoutParams(lparams);
        	chordDiagram.setId(View.generateViewId());
        }

        //END of data for chords

		//initialize layout of activity
		LinearLayout layout = new LinearLayout(this);
		layout.setOrientation(LinearLayout.VERTICAL);
		layout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		layout.setGravity(Gravity.CENTER);
		layout.setBackgroundColor(Color.parseColor("#f7daa7"));
		layout.setPadding(10, 10, 10, 10);
		//set title of page
		setTitle(title);
		//add chord diagram to layout
		layout.addView(chordDiagram);
		
		//set content view to the layout
		setContentView(layout);
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
	
}
