package com.example.champbars;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class CommitActivity extends Activity implements OnClickListener {

	private Button bClys, bFirehaus, bJoes, bKams, bLegends, bMurphys,
			bRedLion, bWhiteHo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_commit);

		// cbBar stands for commit-button-bar_name
		bClys = (Button) findViewById(R.id.cbClys);
		bFirehaus = (Button) findViewById(R.id.cbFirehaus);
		bJoes = (Button) findViewById(R.id.cbJoes);
		bKams = (Button) findViewById(R.id.cbKams);
		bLegends = (Button) findViewById(R.id.cbLegends);
		bMurphys = (Button) findViewById(R.id.cbMurphys);
		bRedLion = (Button) findViewById(R.id.cbRedLion);
		bWhiteHo = (Button) findViewById(R.id.cbWhiteHo);

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
		getMenuInflater().inflate(R.menu.commit, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		if (v == bClys) {
			// TODO Send commit message to the server for this respective bar
			// and user
		} else if (v == bFirehaus) {
			// TODO Send commit message to the server for this respective bar
			// and user
		} else if (v == bJoes) {
			// TODO Send commit message to the server for this respective bar
			// and user
		} else if (v == bJoes) {
			// TODO Send commit message to the server for this respective bar
			// and user
		} else if (v == bKams) {
			// TODO Send commit message to the server for this respective bar
			// and user
		} else if (v == bLegends) {
			// TODO Send commit message to the server for this respective bar
			// and user
		} else if (v == bMurphys) {
			// TODO Send commit message to the server for this respective bar
			// and user
		} else if (v == bRedLion) {
			// TODO Send commit message to the server for this respective bar
			// and user
		} else if (v == bWhiteHo) {
			// TODO Send commit message to the server for this respective bar
			// and user
		}
	}

}
