package com.example.champbars;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;


public class MainScreen extends Activity {
	//The bar objects that will be called upon throughout the app
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_screen);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/** Called when the user clicks the 'Bar Search' button */
	public void barSearch(View view) {
		Intent intent = new Intent(this, MapActivity.class);
		startActivity(intent);
	}

	/** Called when the user clicks the 'Commit' button*/
	public void commit(View view) {
		Intent intent = new Intent(this, CommitActivity.class);
		startActivity(intent);
	}
	
	/** Called when the user clicks the 'Check In' button*/
	public void checkIn(View view){
		Intent intent = new Intent(this, CheckInActivity.class);
		startActivity(intent);
	}
	

}
