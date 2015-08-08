package com.dragfire.spail;

import android.os.AsyncTask;
import android.util.Log;

public class MSender extends AsyncTask<Void, Void, Void>
{
	private String username, password, subject, body, sender, recipient;

	public MSender(String u, String p, String subject, String sender,String r)
		{
			this.username = u;
			this.password = p;
			this.subject = subject;
			this.sender = sender;
			this.recipient = r;
			// TODO Auto-generated constructor stub
		}

	@Override
	public Void doInBackground(Void... s)
	{
		Log.i("MSender", "Sending Mail");
		
		try
		{
			MS me = new MS(this.username, this.password);
			me.sendMail(body);
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
}
