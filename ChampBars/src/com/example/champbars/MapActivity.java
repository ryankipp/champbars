package com.example.champbars;

import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import com.example.champbars.*;

public class MapActivity extends FragmentActivity {

	private GoogleMap mMap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		setUpMapIfNeeded();

		GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(getApplicationContext());
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
			mMap = ((SupportMapFragment) getSupportFragmentManager()
					.findFragmentById(R.id.map)).getMap();
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
	private void setUpMap() {
		final LatLng KAMS = new LatLng(40.108103, -88.229688);
		final LatLng WHITEHO = new LatLng(40.109335,-88.23084);
		final LatLng LEGENDS = new LatLng(40.11062,-88.231148);
		final LatLng MURPHYS = new LatLng(40.110553,-88.230154);
		final LatLng CLYS = new LatLng(40.109621,-88.230386);
		final LatLng REDLION = new LatLng(40.109909,-88.235626);
		final LatLng FIREHAUS = new LatLng(40.109729,-88.230361);
		final LatLng JOES = new LatLng(40.109713,-88.231783);
		
		Bar bKams = new Bar("Kams", "618 E Daniel St, Champaign, IL 61820", 0, 0, KAMS,
				new MarkerOptions().position(KAMS)
				.title("Kams").snippet("")
				.icon(BitmapDescriptorFactory.fromResource(R.drawable.kams)));
		Bar bWhiteHo = new Bar("White Horse Inn", "510 E John St Champaign, IL 61820", 0, 0, WHITEHO,
				new MarkerOptions().position(WHITEHO)
				.title("White Horse Inn").snippet("")
				.icon(BitmapDescriptorFactory.fromResource(R.drawable.whiteho)));
		Bar bLegends = new Bar("Legends", "522 E Green St Champaign, IL 61820", 0, 0, LEGENDS,
				new MarkerOptions().position(LEGENDS)
				.title("Legends").snippet("")
				.icon(BitmapDescriptorFactory.fromResource(R.drawable.legends)));
		Bar bMurphys = new Bar("Murphy's", "604 E Green St Champaign, IL 61820", 0, 0, MURPHYS,
				new MarkerOptions().position(MURPHYS)
				.title("Murphy's").snippet("")
				.icon(BitmapDescriptorFactory.fromResource(R.drawable.murphys)));
		Bar bClys = new Bar("Clybourne", "706 S 6th St, Champaign, IL 61820", 0, 0, CLYS,
				new MarkerOptions().position(CLYS)
				.title("Clybourne").snippet("")
				.icon(BitmapDescriptorFactory.fromResource(R.drawable.clys)));
		Bar bRedLion = new Bar("Red Lion", "211 E Green St Champaign, IL 61820", 0, 0, REDLION,
				new MarkerOptions().position(REDLION)
				.title("Red Lion").snippet("")
				.icon(BitmapDescriptorFactory.fromResource(R.drawable.redlion)));
		Bar bFirehaus = new Bar("Firehaus", "708 S 6th St Champaign, IL 61820", 0, 0, FIREHAUS,
				new MarkerOptions().position(FIREHAUS)
				.title("Firehaus").snippet("")
				.icon(BitmapDescriptorFactory.fromResource(R.drawable.firehaus)));
		Bar bJoes = new Bar("Joe's", "706 South 5th Street, Champaign, IL 61820", 0, 0, JOES,
				new MarkerOptions().position(FIREHAUS)
				.title("Joe's").snippet("")
				.icon(BitmapDescriptorFactory.fromResource(R.drawable.joes)));
		
		Marker mKams = mMap.addMarker(bKams.markerOptions);
		Marker mWhiteHo = mMap.addMarker(bWhiteHo.markerOptions);
		Marker mLegends = mMap.addMarker(bLegends.markerOptions);
		Marker mMurphys = mMap.addMarker(bMurphys.markerOptions);
		Marker mClys = mMap.addMarker(bClys.markerOptions);
		Marker mRedLion = mMap.addMarker(bRedLion.markerOptions);
		Marker mFirehaus = mMap.addMarker(bFirehaus.markerOptions);
		Marker mJoes = mMap.addMarker(bJoes.markerOptions);
	}

	// TODO add markerOnClickListener for bar clicks

}
