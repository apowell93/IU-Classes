/* LearnGuitarChord.java
 * 
 * Activity for the chords home page of the application
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
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.content.Intent;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class LearnGuitarChords extends Activity implements OnItemClickListener {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_learn_guitar_chords);
		
		//listView that displays the individual lessons
		ListView chordView = (ListView) findViewById(R.id.ChordView);
		
		//this string contains the id's of the different lessons - new ones can be added later on
		String[] chordsList = { "A Major", "A minor", "C Major", "D Major", "D minor", "E Major", "E minor", "F Major", "G Major"};
		
		
		//adapter for reading the array values and putting into listview
	//	ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, 
	//			android.R.layout.simple_expandable_list_item_1, chordsList);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, 
				R.layout.listitem, chordsList);
				
		//sets listView1 to use adapter
		chordView.setAdapter(adapter);
		chordView.setOnItemClickListener(this);

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
    
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		//open the chords' individual page and pass in the chord selected
		Intent i = new Intent(this, LearnGuitarIndChord.class);
		i.putExtra(LearnGuitarIndChord.CPOS, position);
		startActivity(i);
	}
	
}
