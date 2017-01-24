package com.mingout.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.mingout.activities.R;

public class MyAlertDialog extends Dialog implements
		View.OnClickListener {
	Button Bok;
	TextView TVmessage;
	Activity context;
	String message, title;
	boolean gpsSettingFlag = false;

	public MyAlertDialog(Activity context, String message) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.message = message;
	}

	public MyAlertDialog(Activity context, String message, String title) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.message = message;
		this.title = title;

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dialog_alert_layout);

		TVmessage = (TextView) findViewById(R.id.TV_message);
		Bok = (Button) findViewById(R.id.B_ok);

		TVmessage.setText(message);

		if (message
				.equals("Please ON GPS! It is mandatory to get current location of user to give services otherwise enter your location manualy")) {
			gpsSettingFlag = true;
		}
		if (message
				.equals("Please ON GPS! It is mandatory to get current location of user to give services!")) {
			gpsSettingFlag = true;
		}

		Bok.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {

		case R.id.B_ok:

			if (gpsSettingFlag) {
				Intent intent = new Intent(
						Settings.ACTION_LOCATION_SOURCE_SETTINGS);
				context.startActivity(intent);
				dismiss();
			} else {
				dismiss();
			}
			break;
		}
	}

}
