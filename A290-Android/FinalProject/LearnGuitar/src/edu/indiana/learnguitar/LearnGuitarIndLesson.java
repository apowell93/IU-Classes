/* LearnGuitarIndLessons.java
 * 
 * Activity for the individual lessons page of the application
 * 
 * Created By: Anthony Powell (anjopowe)
 * Created On: 3/3/2015
 * Last Modified By: Anthony Powell (anjopowe)
 * Last Modified On: 3/6/2015
 * Assignment/Project: A290 Android Development
 * Part Of: Final Project - Learn Guitar
 */

package edu.indiana.learnguitar;


import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class LearnGuitarIndLesson extends Activity {

	public static final String LPOS = "edu.indiana.learnguitar.lPosition";

	public static final int DEFAULT_LESSON = 0;
	
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //layout params for objects within layout
        LayoutParams lparams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        
        /*data for lessons - START*/
        //add new array and data for new page following this format
        int lessonPosition = getIntent().getIntExtra(LPOS, DEFAULT_LESSON);
        ArrayList<String> lessonStrings = new ArrayList<String>();
        ArrayList<ImageView> lessonImages = new ArrayList<ImageView>();
        //for determining whether imageView or textView is next in layout
        ArrayList<String> lessonTypes = new ArrayList<String>();
        
        String title;
        if (lessonPosition == 0) {
        	//lesson 1 layout
        	title = "Lesson 1 - Introduction";
	    	//images for layout - displayed as ImageView's
	    	Drawable amajor = getResources().getDrawable( R.drawable.amajor );
	    	ImageView lesson1Image1 = new ImageView(this);
	    	lesson1Image1.setImageDrawable(amajor);
	    	lesson1Image1.setLayoutParams(lparams);
	    	lesson1Image1.setId(View.generateViewId());
	    	Drawable tabexample = getResources().getDrawable( R.drawable.tabexample);
	      	ImageView lesson1Image2 = new ImageView(this);
	    	lesson1Image2.setImageDrawable(tabexample);
	    	lesson1Image2.setId(View.generateViewId());
	    	lesson1Image2.setLayoutParams(lparams);
	
	    	//layout order for lesson 1
	    	lessonStrings.add(getString(R.string.Lesson1Text1));
	    	lessonStrings.add(getString(R.string.Lesson1Text2));
	    	lessonStrings.add(getString(R.string.Lesson1Text3));
	    	lessonImages.add(lesson1Image1);
	    	lessonStrings.add(getString(R.string.Lesson1Text4));
	    	lessonImages.add(lesson1Image2);
	    	lessonStrings.add(getString(R.string.Lesson1Text5));
	    	lessonStrings.add(getString(R.string.Lesson1Text6));
	    	//layout types order for building final layout  & differentiating between imageview and textview
	    	lessonTypes.add("string");
	    	lessonTypes.add("string");
	    	lessonTypes.add("string");
	    	lessonTypes.add("image");
	    	lessonTypes.add("string");
	    	lessonTypes.add("image");
	    	lessonTypes.add("string");
	    	lessonTypes.add("string");
	    	
	    	
        } else if (lessonPosition == 1) {
        	//lesson 2 layout
        	title = "Lesson 2 - First Chords";
	    	//images for layout - displayed as ImageView's
	    	Drawable chordE = getResources().getDrawable( R.drawable.emajor );
	    	ImageView lesson2Image1 = new ImageView(this);
	    	lesson2Image1.setImageDrawable(chordE);
	    	lesson2Image1.setLayoutParams(lparams);
	    	lesson2Image1.setId(View.generateViewId());
	    	lesson2Image1.setPadding(0, 10, 0, 10);
	    	Drawable chordD = getResources().getDrawable( R.drawable.dmajor);
	      	ImageView lesson2Image2 = new ImageView(this);
	    	lesson2Image2.setImageDrawable(chordD);
	    	lesson2Image2.setId(View.generateViewId());
	    	lesson2Image2.setLayoutParams(lparams);
	    	lesson2Image2.setPadding(0, 10, 0, 10);
	    	Drawable chordA = getResources().getDrawable( R.drawable.amajor);
	      	ImageView lesson2Image3 = new ImageView(this);
	    	lesson2Image3.setImageDrawable(chordA);
	    	lesson2Image3.setId(View.generateViewId());
	    	lesson2Image3.setLayoutParams(lparams);
	    	lesson2Image3.setPadding(0, 10, 0, 10);
        	
	    	//add strings and images to arrays
	    	lessonStrings.add(getString(R.string.Lesson2Text1));
	    	lessonStrings.add(getString(R.string.Lesson2Text2));
	    	lessonImages.add(lesson2Image1);
	    	lessonImages.add(lesson2Image2);
	    	lessonImages.add(lesson2Image3);
	    	lessonStrings.add(getString(R.string.Lesson2Text3));
	    	lessonStrings.add(getString(R.string.Lesson2Text4));
	    	lessonStrings.add(getString(R.string.Lesson2Text5));
	    	lessonStrings.add(getString(R.string.Lesson2Text6));
	    	//layout types order for building final layout  & differentiating between imageview and textview
	    	lessonTypes.add("string");
	    	lessonTypes.add("string");
	    	lessonTypes.add("image");
	    	lessonTypes.add("image");
	    	lessonTypes.add("image");
	    	lessonTypes.add("string");
	    	lessonTypes.add("string");
	    	lessonTypes.add("string");
	    	lessonTypes.add("string");
	    	
        } else {
        	title = "ERROR"; //should never happen - initializes "title" variable for 'else' to avoid error below
        }
    	/*data for lessons - END*/

		//initialize layout of activity
        ScrollView scroll = new ScrollView(this);
		scroll.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		scroll.setBackgroundColor(Color.parseColor("#f7daa7"));
		LinearLayout layout = new LinearLayout(this);
		layout.setOrientation(LinearLayout.VERTICAL);
		layout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		layout.setGravity(Gravity.CENTER);
		layout.setBackgroundColor(Color.parseColor("#f7daa7"));
		
		layout.setPadding(10, 10, 10, 10);

		//set title
		setTitle(title);
		//set remainder of layout
		int s = 0; //current string array position
		int i = 0; //current image array position
		for (int p = 0; p < lessonTypes.size(); p++) {
			if (lessonTypes.get(p).equals("string")) { //then textview will be added to layout
				TextView text = new TextView(this);
		        text.setLayoutParams(lparams);
				text.setTextAppearance(this, android.R.attr.textAppearanceMedium);
			    text.setText(lessonStrings.get(s));
			    text.setTextColor(Color.parseColor("#8b2300"));
			    text.setTypeface(null, Typeface.BOLD);
				layout.addView(text);
				s++;
			} else { //if its not text, it'll be an image
				layout.addView(lessonImages.get(i));
				i++;
			}
		}
		scroll.addView(layout);
		setContentView(scroll);
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
