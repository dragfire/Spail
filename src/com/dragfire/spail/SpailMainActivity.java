package com.dragfire.spail;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import android.R.integer;
import android.R.string;
import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SpailMainActivity extends Activity
{
	private Button sendButton;
	private SpailDBHelper maildDbHelper;
	private HashMap<String, String> un_msg;
	TextView latLabel, lonLabel, altLabel, speedLabel;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_spail_main);
		Log.i("SPAIL", "SPAIL Started");
		sendButton = (Button) findViewById(R.id.button1);
		latLabel = (TextView) findViewById(R.id.latlabel);
		lonLabel = (TextView) findViewById(R.id.lonlabel);
		altLabel = (TextView) findViewById(R.id.altLabel);
		speedLabel = (TextView) findViewById(R.id.speedLabel);

		sendButton.setOnClickListener(new Button.OnClickListener()
		{

			@Override
			public void onClick(View arg0)
			{
				Log.i("SPAIL", "Button Clicked");

				int i = 0;
				LocationService gps;
				while (++i < 3)
				{
					gps = new LocationService(
							getApplicationContext());
					latLabel.setText("Latitude: "+Double.toString(gps.latitude()));
					lonLabel.setText("Longitude: "+Double.toString(gps.longitude()));
					altLabel.setText("Altitude: "+Double.toString(gps.altitude()));
				}
				// new Thread(new Runnable()
				// {
				//
				// @Override
				// public void run()
				// {
				// if (InternetMonitorService.isOnline())
				// {
				// Log.i("SPAIL", "Internet connection available");
				// MS sender = new MS("lucy.jaa@gmail.com", "lucyjaa123");
				// try
				// {
				// sender.sendMail("Hello SPAIL");
				// } catch (Exception e)
				// {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// }
				// }
				// else {
				//
				// }
				// // TODO Auto-generated method stub
				//
				// }
				// }).start();

			}
		});
	}
}
