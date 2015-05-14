/* FirstRealAppGameActivity.java
 * 
 * Activity for the actual game to display
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
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

public class FirstRealAppGameActivity extends Activity {
	
	//for logging/debuging
	private static final String TAG = "FirstRealAppGameActivity";
	
	public static final String KEY_DIFFICULTY = "edu.indiana.a290firstrealapplication.difficulty";
	
	//for declaring the selected difficulty
	public static final int DIFFICULTY_EASY = 0;
	public static final int DIFFICULTY_MEDIUM = 1;
	public static final int DIFFICULTY_HARD = 2;
	
	
	//strings identifying the different puzzles starting boards
	 private final String easyPuzzle =
			 "360000000004230800000004200" +
			 "070460003820000014500013020" +
			 "001900000007048300000000045";
	private final String mediumPuzzle =
			 "650000070000506000014000005" +
			 "007009000002314700000700800" +
			 "500000630000201000030000097";
	private final String hardPuzzle =
			 "009000000080605020501078000" +
			 "000000700706040102004000000" +
			 "000720903090301080000000600";
	
	//will contain the starting and updated puzzle state
	private int puzzle[];
	
	private FirstRealAppPuzzleView puzzleView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//creates the puzzle based on the selected difficulty
		super.onCreate(savedInstanceState);
		Log.d(TAG, "onCreate");
		int diff = getIntent().getIntExtra(KEY_DIFFICULTY, DIFFICULTY_EASY);
		puzzle = getPuzzle(diff);
		calculateUsedTiles();
		puzzleView = new FirstRealAppPuzzleView(this);
		setContentView(puzzleView);
		puzzleView.requestFocus();
	}
	
	private final int used[][][] = new int[9][9][];
	
	protected int[] getUsedTiles(int x, int y) {
		return used[x][y];
	}
	
	private void calculateUsedTiles() {
		for (int x = 0; x < 9; x++) {
			for (int y = 0; y < 9; y++) {
				used[x][y] = calculateUsedTiles(x, y);
				//have more work to do here for both
				//LogCat and toPuzzleString
			}
		}
	}
	
	private int[] calculateUsedTiles(int x, int y) {
		int c[] = new int[9];
		//check horizontal tiles
		for (int i = 0; i < 9; i++) {
			if (i == x)
				continue;
			int t = getTile(i, y);
			if (t != 0)
				c[t - 1] = t;
		}
		//check vertical tiles
		for (int i = 0; i < 9; i++) {
			if (i == y)
				continue;
			int t = getTile(i, x);
			if (t != 0)
				c[t - 1] = t;
		}
		//check 3x3 block
		int startx = (x / 3) * 3;
		int starty = (y / 3) * 3;
		for (int i = startx; i < startx + 3; i++) {
			for (int j = starty; j < starty + 3; j++) {
				if (i == x && j == y)
					continue;
				int t = getTile(i, j);
				if (t != 0)
					c[t - 1] = t;
			}
		}
		
		//compress the array to save some space
		int nused = 0;
		for (int t : c) {
			if (t != 0)
				nused++;
		}
		int c1[] = new int[nused];
		nused = 0;
		for (int t : c) {
			if (t != 0)
				c1[nused++] = t;
		}
		return c1;
	}
	
	/** Given a difficulty level, come up with a new puzzle */
	private int[] getPuzzle(int diff) {
		String puz;
		// TODO: Continue last game
		switch (diff) {
		
			case DIFFICULTY_HARD:
			 puz = hardPuzzle;
			break;
			 
			case DIFFICULTY_MEDIUM:
			 puz = mediumPuzzle;
			break;
			
			case DIFFICULTY_EASY:
			 default:
			   puz = easyPuzzle;
			break;
		}
	    return fromPuzzleString(puz);
	 }
	/** Convert a puzzle string into an array */
	 static protected int[] fromPuzzleString(String string) {
		 int[] puz = new int[string.length()];
		 for (int i = 0; i < puz.length; i++) {
			 puz[i] = string.charAt(i) - '0';
		 }
		 return puz;
	 }
	
	 /** Return the tile at the given coordinates */
	 private int getTile(int x, int y) {
		 return puzzle[y * 9 + x];
	 }
	 
	/** Return a string for the tile at the given coordinates */
	 protected String getTileString(int x, int y) {
		 int v = getTile(x, y);
		 if (v == 0)
			 return "";
		 else
			 return String.valueOf(v);
	 }
	
}
