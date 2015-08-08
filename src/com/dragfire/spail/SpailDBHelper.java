package com.dragfire.spail;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SpailDBHelper extends SQLiteOpenHelper
{
	Random randomId;
	private String username = "from@gmail.com", password = "frompasswd";
	public static final String DATABASE_NAME = "spail.db";
	public static final String SPAIl_TABLE_NAME = "undelivered_mails";
	public static final String SPAIL_PHONE_NUMBER = "phone_number";
	public static final String SPAIL_MSG_BODY = "msg_body";

	public SpailDBHelper(Context context)
		{

			super(context, DATABASE_NAME, null, 1);
		}

	@Override
	public void onCreate(SQLiteDatabase db)
	{
		Log.i("SPAIL", "DB table created!");
		// TODO Auto-generated method stub
		db.execSQL("create table undelivered_mails (serial integer primary key,phone_number text, msg_body text)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS " + SPAIl_TABLE_NAME);
		onCreate(db);
	}

	public boolean insertMessage(String phone, String msg)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put("serial", new Date().getTime());
		contentValues.put(SPAIL_PHONE_NUMBER, phone);
		contentValues.put(SPAIL_MSG_BODY, msg);
		db.insert(SPAIl_TABLE_NAME, null, contentValues);
		return true;
	}

	// public Cursor getData(int id){
	// SQLiteDatabase db = this.getReadableDatabase();
	// Cursor resW = db.rawQuery( "select * from contacts where id="+id+"", null
	// );
	// return res;
	// }
	//
	public int numberOfRows()
	{

		SQLiteDatabase db = this.getReadableDatabase();
		int numRows = (int) DatabaseUtils.queryNumEntries(db, SPAIl_TABLE_NAME);
		Log.i("SPAIL", "num_rows: " + numRows);
		return numRows;
	}

	public Integer deleteMessage(String id)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		return db.delete(SPAIl_TABLE_NAME, "serial = ? ", new String[] { id });
	}

	public void sendAllMail()
	{
		Log.i("SPAIL", "Sending all undelivered mails");

		new Thread(new Runnable()
		{

			@Override
			public void run()
			{
				String phone_number, msg;
				SQLiteDatabase db = SpailDBHelper.this.getReadableDatabase();
				Cursor res = db.rawQuery("select * from " + SPAIl_TABLE_NAME,
						null);
				res.moveToFirst();

				while (res.isAfterLast() == false)
				{
					phone_number = res.getString(res
							.getColumnIndex(SPAIL_PHONE_NUMBER));
					msg = res.getString(res.getColumnIndex(SPAIL_MSG_BODY));
					// TODO Auto-generated method stub
					if (InternetMonitorService.isOnline())
					{
						MS mailSender = new MS(username, password);

						Log.i("SPAIL", "Net on sending....");
						SpailUtil.MAIL_SENT = true;
						try
						{
							Log.i("SPAIL", "Message: " + msg);
							mailSender.sendMail(msg);
						} catch (Exception e)
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
							SpailUtil.MAIL_SENT = false;
						}
						if (SpailUtil.MAIL_SENT)
						{
							Log.i("SPAIL", "DB_HELPER: MESSAGE DELETED!");
							SpailDBHelper.this.deleteMessage(res.getString(res
									.getColumnIndex("serial")));
						}
					}
					Log.i("SPAIL", "P_NUM: " + phone_number);
					res.moveToNext();
				}
			}
		}).start();

	}

}
