package com.example.champbars;

import com.example.champbars.MapActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class BarClickedActivity extends Activity 
{
	private Button checkinButton;
	private Button commitButton;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bar_clicked);
		
		// get the parameters passed int to this activity
		Bundle param = getIntent().getExtras();
		String barName = param.getString("bar_name");
		Bar barClicked = getBarFromTitle(barName);
		
		// set up checkin button and callback
        checkinButton = (Button) findViewById(R.id.buttonCheckIn);
        checkinButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            	Intent intent = new Intent(BarClickedActivity.this, CheckInActivity.class);
        		startActivity(intent);
            }
        });
        
        // set up bar commit button and callback
        commitButton = (Button) findViewById(R.id.buttonCommit);
        commitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            	Intent intent = new Intent(BarClickedActivity.this, CommitActivity.class);
        		startActivity(intent);
            }
        });
		
        // set up the screen with the correct Bar information
		TextView title = (TextView)findViewById(R.id.barName);
		TextView address = (TextView)findViewById(R.id.barAddress);
		
		title.setText(barClicked.getName());
		address.setText(barClicked.getAddress());
	}
	
	/**
	 * Function to get the bar selected in mapactivity by the title passed in
	 * @param title name of bar passed in to this activity from mapactivity
	 * @return
	 */
	public Bar getBarFromTitle(String title)
	{
		if(title.equals("Joe's"))
			return MapActivity.bJoes;
		else if(title.equals("Red Lion"))
			return MapActivity.bRedLion;
		else if(title.equals("Kams"))
			return MapActivity.bKams;
		else if(title.equals("White Horse Inn"))
			return MapActivity.bWhiteHo;
		else if(title.equals("Legends"))
			return MapActivity.bLegends;
		else if(title.equals("Murphy's"))
			return MapActivity.bMurphys;
		else if(title.equals("Clybourne"))
			return MapActivity.bClys;
		else if(title.equals("Firehaus"))
			return MapActivity.bFirehaus;
		return null;
	}
}
