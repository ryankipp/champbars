package com.example.champbars;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class BarSearchActivity extends Activity {
	public final static String RADIUS = "com.example.champbars.RADIUS";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bar_search);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.bar_search, menu);
		return true;
	}
	
	/** Called when the user clicks the Submit button
	 * Tells MapActivity the radius within to search for bars */
	public void radiusBarSearch(View view) {
		Intent intent = new Intent(this, MapActivity.class);
		
		//Grab the radius that the user entered and pass it to the new activity after appending it to the intent
		EditText editText = (EditText) findViewById(R.id.radius);
	    String radius = editText.getText().toString();
	    intent.putExtra(RADIUS, radius);
		startActivity(intent);
	}

}
