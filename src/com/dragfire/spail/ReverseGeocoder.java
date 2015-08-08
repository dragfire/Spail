package com.dragfire.spail;

import java.util.List;
import java.util.Locale;

import android.app.ProgressDialog;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;

public class ReverseGeocoder extends AsyncTask<Void, Void, String>
{
	public AsyncResponse delegate = null;
	private ProgressDialog pDialog;
	public Context geocoderContext;
	private double latitude, longitude;
	public String locationName;
	private List<Address> listAddresses;

	public ReverseGeocoder(double lat, double lon)
		{
			this.latitude = lat;
			this.longitude = lon;
			// TODO Auto-generated constructor stub
		}

	@Override
	protected void onPreExecute()
	{

	}

	@Override
	protected String doInBackground(Void... s)
	{

		try
		{
			Geocoder geocoder = new Geocoder(geocoderContext,
					Locale.getDefault());
			String city = "unknown";
			listAddresses = geocoder.getFromLocation(this.latitude,
					this.longitude, 1);
			if (!listAddresses.get(0).getLocality().isEmpty())
				city = listAddresses.get(0).getLocality();

			if (null != listAddresses && listAddresses.size() > 0)
			{
				locationName = city + ", "
						+ listAddresses.get(0).getSubAdminArea() + "\n"
						+ listAddresses.get(0).getAdminArea() + " "
						+ listAddresses.get(0).getAddressLine(1);
				return locationName;
			}

		} catch (Exception e)
		{
		}
		return "";
	}

	@Override
	protected void onPostExecute(String res)
	{
		delegate.processFinish(res);
	}
}
