package com.example.champbars;

import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

public class MapActivity extends FragmentActivity {

	private GoogleMap mMap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		setUpMapIfNeeded();
		// Get the radius the user passed in from BarSearchActivity
		Intent intent = getIntent();
		Float radius = Float.valueOf(intent
				.getStringExtra(BarSearchActivity.RADIUS));

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
		mMap.setMyLocationEnabled(true);
		Location myLocation = null;
		while (myLocation == null) {
			myLocation = mMap.getMyLocation();
		}
		if (myLocation != null) {
			LatLng myLocationLatLng = new LatLng(myLocation.getLatitude(),
					myLocation.getLongitude());
			mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocationLatLng,
					15));
		}
	}

	// TODO add markerOnClickListener for bar clicks

}
