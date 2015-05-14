/* LearnGuitarScales.java
 * 
 * Activity for the scales home page of the application
 * 
 * Created By: Anthony Powell (anjopowe)
 * Created On: 3/4/2015
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

public class LearnGuitarScales extends Activity implements OnItemClickListener {


	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_learn_guitar_scales);
		
		//listView that displays the individual scales
		ListView scaleView = (ListView) findViewById(R.id.ScaleView);
		
		//this string contains the id's of the different scales - new ones can be added later on
		String[] scalesList = { "Chromatic Scale", "Major Scale - Position 1", "Major Scale - Position 2", "Major Scale - Position 3", "Minor Pentatonic Scale - Position 1", "Minor Pentatonic Scale - Position 2", "Major Pentatonic Scale - Position 1"};
		
		
		//adapter for reading the array values and putting into listview
	//	ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, 
	//			android.R.layout.simple_expandable_list_item_1, scalesList);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, 
				R.layout.listitem, scalesList);
				
		//sets listView1 to use adapter
		scaleView.setAdapter(adapter);
		scaleView.setOnItemClickListener(this);

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
		//open the scales individual page and pass in the scale selected
		Intent i = new Intent(this, LearnGuitarIndScale.class);
		i.putExtra(LearnGuitarIndScale.SPOS, position);
		startActivity(i);
		
	}
	
}
