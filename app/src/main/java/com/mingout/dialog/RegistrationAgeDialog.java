package com.mingout.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;

import com.mingout.activities.R;

public class RegistrationAgeDialog extends Dialog implements
		View.OnClickListener {
	Button Bok;
	Activity context;
	DatePicker datePicker1;

	public RegistrationAgeDialog(Activity context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context = context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dialog_registration_age_layout);

		Bok = (Button) findViewById(R.id.B_ok);
		datePicker1 = (DatePicker) findViewById(R.id.datePicker1);
		
		

		Bok.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.B_ok:
			((DialogAgeListener) context).AgeListner(String.valueOf(datePicker1
					.getYear())
					+ "-"
					+ String.valueOf(datePicker1.getMonth() + 1)
					+ "-"
					+ String.valueOf(datePicker1.getDayOfMonth()));
			break;
		}
	}

	public interface DialogAgeListener {
		public void AgeListner(String age);
	}
}