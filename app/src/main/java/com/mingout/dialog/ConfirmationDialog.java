package com.mingout.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.mingout.activities.R;

public class ConfirmationDialog extends Dialog implements
		View.OnClickListener {
	Button Byes, Bno;
	TextView TVmessage;
	Activity context;
	String message;

	public ConfirmationDialog(Activity context, String message) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.message = message;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dialog_confirmation_layout);

		TVmessage = (TextView) findViewById(R.id.TV_message);
		Byes = (Button) findViewById(R.id.button1);
		Bno = (Button) findViewById(R.id.button2);

		TVmessage.setText(message);
		Byes.setOnClickListener(this);
		Bno.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {

		case R.id.button1:
			((ConfirmationDialogListner) context)
					.confirmationDialogYesButtonPressed();
			break;

		case R.id.button2:
			this.dismiss();
			break;
		}
	}

	public interface ConfirmationDialogListner {
		void confirmationDialogYesButtonPressed();
	}

}
