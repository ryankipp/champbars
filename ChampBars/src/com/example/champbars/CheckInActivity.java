package com.example.champbars;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class CheckInActivity extends Activity implements OnClickListener{

	private Button bClys, bFirehaus, bJoes, bKams, bLegends, bMurphys,
	bRedLion, bWhiteHo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_check_in);
		
		// cibBar stands for check-in-button-Bar
				bClys = (Button) findViewById(R.id.cibClys);
				bFirehaus = (Button) findViewById(R.id.cibFirehaus);
				bJoes = (Button) findViewById(R.id.cibJoes);
				bKams = (Button) findViewById(R.id.cibKams);
				bLegends = (Button) findViewById(R.id.cibLegends);
				bMurphys = (Button) findViewById(R.id.cibMurphys);
				bRedLion = (Button) findViewById(R.id.cibRedLion);
				bWhiteHo = (Button) findViewById(R.id.cibWhiteHo);

				bClys.setOnClickListener(this);
				bFirehaus.setOnClickListener(this);
				bJoes.setOnClickListener(this);
				bKams.setOnClickListener(this);
				bLegends.setOnClickListener(this);
				bMurphys.setOnClickListener(this);
				bRedLion.setOnClickListener(this);
				bWhiteHo.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.check_in, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		if (v == bClys) {
			// TODO Send checkin message to the server for this respective bar
			// and user
		} else if (v == bFirehaus) {
			// TODO Send checkin message to the server for this respective bar
			// and user
		} else if (v == bJoes) {
			// TODO Send checkin message to the server for this respective bar
			// and user
		} else if (v == bJoes) {
			// TODO Send checkin message to the server for this respective bar
			// and user
		} else if (v == bKams) {
			// TODO Send checkin message to the server for this respective bar
			// and user
		} else if (v == bLegends) {
			// TODO Send checkin message to the server for this respective bar
			// and user
		} else if (v == bMurphys) {
			// TODO Send checkin message to the server for this respective bar
			// and user
		} else if (v == bRedLion) {
			// TODO Send checkin message to the server for this respective bar
			// and user
		} else if (v == bWhiteHo) {
			// TODO Send checkin message to the server for this respective bar
			// and user
		}
		
	}

}
