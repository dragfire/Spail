package com.dragfire.spail;

import android.location.Location;

public interface AsyncResponse
{
	public void processFinish(Location l);
	public void processFinish(String r);
}
