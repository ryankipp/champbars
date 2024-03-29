package com.example.champbars;

import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class MapActivity extends FragmentActivity implements OnInfoWindowClickListener{

	private GoogleMap mMap;
	public static Bar 	bKams, 
						bWhiteHo, 
						bLegends, 
						bMurphys, 
						bClys, 
						bRedLion, 
						bFirehaus, 
						bJoes;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		
		setUpMapIfNeeded();

		GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());
	}

	@Override
	protected void onResume() {
		super.onResume();
		setUpMapIfNeeded();
	}

	private void setUpMapIfNeeded() {
		// Do a null check to confirm that we have not already instantiated the
		// map.
		if (mMap == null) {
			// Try to obtain the map from the SupportMapFragment.
			mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
			
			// Check if we were successful in obtaining the map.
			if (mMap != null) {
				setUpMap();
			}
		}
	}

	/**
	 * This is where we can add markers or lines, add listeners or move the
	 * camera. In this case, we just add a marker near Africa. This should only
	 * be called once and when we are sure that {@link #mMap} is not null.
	 */
	private void setUpMap() 
	{
		// set up listener for when ballon of bars is clicked to start new activity
		mMap.setOnInfoWindowClickListener(this);
		
		LatLng KAMS = new LatLng(40.108103, -88.229688);
		LatLng WHITEHO = new LatLng(40.109335, -88.23084);
		LatLng LEGENDS = new LatLng(40.11062, -88.231148);
		LatLng MURPHYS = new LatLng(40.110553, -88.230154);
		LatLng CLYS = new LatLng(40.109621, -88.230386);
		LatLng REDLION = new LatLng(40.109909, -88.235626);
		LatLng FIREHAUS = new LatLng(40.109729, -88.230361);
		LatLng JOES = new LatLng(40.109713, -88.231783);

		bKams = new Bar("Kams", "618 E Daniel St, Champaign, IL 61820", 0, 0,
				KAMS, new MarkerOptions()
						.position(KAMS)
						.title("Kams")
						.snippet("")
						.icon(BitmapDescriptorFactory
								.fromResource(R.drawable.kams)));
		bWhiteHo = new Bar("White Horse Inn",
				"510 E John St Champaign, IL 61820", 0, 0, WHITEHO,
				new MarkerOptions()
						.position(WHITEHO)
						.title("White Horse Inn")
						.snippet("")
						.icon(BitmapDescriptorFactory
								.fromResource(R.drawable.whiteho)));
		bLegends = new Bar("Legends", "522 E Green St Champaign, IL 61820", 0,
				0, LEGENDS, new MarkerOptions()
						.position(LEGENDS)
						.title("Legends")
						.snippet("")
						.icon(BitmapDescriptorFactory
								.fromResource(R.drawable.legends)));
		bMurphys = new Bar("Murphy's", "604 E Green St Champaign, IL 61820", 0,
				0, MURPHYS, new MarkerOptions()
						.position(MURPHYS)
						.title("Murphy's")
						.snippet("")
						.icon(BitmapDescriptorFactory
								.fromResource(R.drawable.murphys)));
		bClys = new Bar("Clybourne", "706 S 6th St, Champaign, IL 61820", 0, 0,
				CLYS, new MarkerOptions()
						.position(CLYS)
						.title("Clybourne")
						.snippet("")
						.icon(BitmapDescriptorFactory
								.fromResource(R.drawable.clys)));
		bRedLion = new Bar("Red Lion", "211 E Green St Champaign, IL 61820", 0,
				0, REDLION, new MarkerOptions()
						.position(REDLION)
						.title("Red Lion")
						.snippet("")
						.icon(BitmapDescriptorFactory
								.fromResource(R.drawable.redlion)));
		bFirehaus = new Bar("Firehaus", "708 S 6th St Champaign, IL 61820", 0,
				0, FIREHAUS, new MarkerOptions()
						.position(FIREHAUS)
						.title("Firehaus")
						.snippet("")
						.icon(BitmapDescriptorFactory
								.fromResource(R.drawable.firehaus)));
		bJoes = new Bar("Joe's", "706 South 5th Street, Champaign, IL 61820",
				0, 0, JOES, new MarkerOptions()
						.position(JOES)
						.title("Joe's")
						.snippet("")
						.icon(BitmapDescriptorFactory
								.fromResource(R.drawable.joes)));

		// add the markers to the map
		Marker mKams = mMap.addMarker(bKams.markerOptions);
		Marker mWhiteHo = mMap.addMarker(bWhiteHo.markerOptions);
		Marker mLegends = mMap.addMarker(bLegends.markerOptions);
		Marker mMurphys = mMap.addMarker(bMurphys.markerOptions);
		Marker mClys = mMap.addMarker(bClys.markerOptions);
		Marker mRedLion = mMap.addMarker(bRedLion.markerOptions);
		Marker mFirehaus = mMap.addMarker(bFirehaus.markerOptions);
		Marker mJoes = mMap.addMarker(bJoes.markerOptions);
	}

	/**
	 * Function to handle Bar Marker Clicks - spawns a new activity
	 * @param marker the bar icon that was clicked
	 */
	@Override
	public void onInfoWindowClick(Marker marker) 
	{
		Intent intent = new Intent(this, BarClickedActivity.class);
		String barName = marker.getTitle();

		intent.putExtra("bar_name", barName);
		
        startActivity(intent);
	}
}
