package com.example.champbars;

import com.google.android.gms.maps.model.LatLng;

/*
 * An object that holds the relevant information on a particular bar
 */
public class Bar {
	private String address; // Address in the form
							// "100 Green Street, Champaign, IL, 61820"
	private int numMen;
	private int numWomen;
	private LatLng geoLocation;
	//TODO: Add either a new class of a string array for 'Bar Specials'
	
	public LatLng getGeoLocation() {
		return geoLocation;
	}

	public void setGeoLocation(LatLng geoLocation) {
		this.geoLocation = geoLocation;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getNumMen() {
		return numMen;
	}

	public void setNumMen(int numMen) {
		this.numMen = numMen;
	}

	public int getNumWomen() {
		return numWomen;
	}

	public void setNumWomen(int numWomen) {
		this.numWomen = numWomen;
	}
}
