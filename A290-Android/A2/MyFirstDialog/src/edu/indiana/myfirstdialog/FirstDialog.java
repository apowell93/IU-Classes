/* FirstDialog.java
 * 
 * File is for the main activity running in this program
 * 
 * Created By: Anthony Powell (anjopowe)
 * Created On: 2/12/2015
 * Last Modified By: Anthony Powell (anjopowe)
 * Last Modified On: 2/12/2015
 * Assignment/Project: A290 Android Development
 * Part Of: Project 2 - My First Dialog
 */

package edu.indiana.myfirstdialog;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

/* Added these 5 libraries to have access to all we need for dialog and such*/
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Toast;


public class FirstDialog extends Activity {
	
	/* This sets up our "list" to be viewed in the dialog and tracks which is selected*/
	CharSequence[] items = { "Google", "Apple", "Microsoft" };
	boolean[] itemsChecked = new boolean [items.length];
	
    
	/* While deprecated, showDialog is the easiest way to get this to work here */
	@SuppressWarnings("deprecation")
	public void onClick(View v) {
	/*This is a deprecated option, but we are going to use it anyway
	* We do this with the "Quick Fix" to suppress the warning*/
	showDialog(0);
	}
	
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		  /*Using a simple switch-case construct to control the creation
		  * and behaviour of our Dialog box*/
		  case 0:
			  return new AlertDialog.Builder(this)
			  .setIcon(R.drawable.ic_launcher)
			  /*This creates the dialog with the basic features of "OK"
			   * and "Cancel buttons as well as a title */
			  .setTitle("Select one or more options below:")
			  /*Each action or button click, requires a ClickListener
		 	   * so we have to enable or create one so it is ready to
		 	   * react to user input*/
			  .setPositiveButton("OK",
					  new DialogInterface.OnClickListener() {
				        public void onClick(DialogInterface dialog, int whichButton)
				        {
				          /*Using Toast to briefly display that "OK" button was clicked*/
				          Toast.makeText(getBaseContext(),
				          "OK clicked!", Toast.LENGTH_SHORT).show();
				        }
			  		  }
		      )
		      .setNegativeButton("Cancel",
		    		  new DialogInterface.OnClickListener() {
		    	  	    public void onClick(DialogInterface dialog, int whichButton)
		    	  	    {
		    	  	      /*Using Toast to briefly display that "Cancel" button was clicked*/
		    	  	      Toast.makeText(getBaseContext(),
		    	  	      "Cancel clicked!", Toast.LENGTH_SHORT).show();
		    	  	    }
		      		  }
		      )
		      .setMultiChoiceItems(items, itemsChecked,
		    		  new DialogInterface.OnMultiChoiceClickListener() {
		    	        public void onClick(DialogInterface dialog,
		    	        int which, boolean isChecked) {
		    	        	/*Using our boolean to confirm the state of each of our
		    	        	 * 3 options, set in our item list, and then using
		    	        	 * Toast to briefly display that each one has been "checked"
		    	        	 * or "unchecked*/
		    	        	Toast.makeText(getBaseContext(),
		    	        	items[which] + (isChecked ? " checked!":" unchecked!"),
		    	        	Toast.LENGTH_SHORT).show();
		    	        }
		      		  }
		      )
		      /*We need to actually create this entire "case" with these properties, once
		       * the button is clicked*/
		      .create();
		}
		/*No return values are expected, so we return null*/
		return null;
	}
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.firstdialog_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.first_dialog, menu);
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
