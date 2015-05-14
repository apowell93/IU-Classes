/* LearnGuitarLessons.java
 * 
 * Activity for the lessons home page of the application
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


public class LearnGuitarLessons extends Activity implements OnItemClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_learn_guitar_lessons);
		
		//listView that displays the individual lessons
		ListView lessonView = (ListView) findViewById(R.id.LessonView);
		
		//this string contains the id's of the different lessons - new ones can be added later on
		String[] lessonsList = { "Lesson 1 - Introduction", "Lesson 2 - First Chords"};
		
		
		//adapter for reading the array values and putting into listview
	//	ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, 
	//	android.R.layout.simple_expandable_list_item_1, lessonsList);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, 
				R.layout.listitem, lessonsList);
				
		//sets listView1 to use adapter
		lessonView.setAdapter(adapter);
		lessonView.setOnItemClickListener(this);

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
		
		//string containing the name of the lesson that was clicked
		//String lessonText = parent.getItemAtPosition(position).toString();
		
		//open the lessons individual page and pass in the lesson selected
		Intent i = new Intent(this, LearnGuitarIndLesson.class);
	//	i.putExtra(LearnGuitarIndLesson.LESSON, lessonText);
		i.putExtra(LearnGuitarIndLesson.LPOS, position);
		startActivity(i);
		
	}
	
	
}
