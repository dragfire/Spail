package com.dragfire.spail;

import java.security.Security;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Security;
import java.util.Properties;
import java.util.prefs.Preferences;

public class MS extends Authenticator
{
	private String mailhost = "smtp.gmail.com";
	private String username, password;
	private Session session;
	static
	{
		Security.addProvider(new JSSEProvider());
	}

	public MS(String user, String password)
		{
			super();
			this.username = user;
			this.password = password;
		}

	public synchronized void sendMail(String body) throws Exception
	{
		try
		{
			Properties props = System.getProperties();
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", mailhost);
			props.put("mail.smtp.user", username);
			props.put("mail.smtp.password", password);
			props.put("mail.smtp.port", "587");
			props.put("mail.smtp.auth", "true");
			props.put("mail.debug", "true");

			session = Session.getDefaultInstance(props,
					new Authenticator()
					{
						protected PasswordAuthentication getPasswordAuthentication()
						{
							return new PasswordAuthentication(username,
									password);
						}
					});
			MimeMessage message = new MimeMessage(session);
			Address fromAddress = new InternetAddress(username);
			Address toAddress = new InternetAddress("xyz@gmail.com");

			message.setFrom(fromAddress);
			message.setRecipient(Message.RecipientType.TO, toAddress);

			message.setSubject("#SPAIL#");
			message.setText(body);
			Transport transport = session.getTransport("smtp");
			transport.connect(mailhost, username, password);
			message.saveChanges();
			Transport.send(message);
			transport.close();

		} catch (Exception e)
		{
			// TODO: handle exception
			
		}
	}

	public class ByteArrayDataSource implements DataSource
	{
		private byte[] data;
		private String type;

		public ByteArrayDataSource(byte[] data, String type)
			{
				super();
				this.data = data;
				this.type = type;
			}

		public ByteArrayDataSource(byte[] data)
			{
				super();
				this.data = data;
			}

		public void setType(String type)
		{
			this.type = type;
		}

		public String getContentType()
		{
			if (type == null)
				return "application/octet-stream";
			else
				return type;
		}

		public InputStream getInputStream() throws IOException
		{
			return new ByteArrayInputStream(data);
		}

		public String getName()
		{
			return "ByteArrayDataSource";
		}

		public OutputStream getOutputStream() throws IOException
		{
			throw new IOException("Not Supported");
		}
	}
}
