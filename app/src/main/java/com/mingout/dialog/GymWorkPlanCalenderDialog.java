package com.mingout.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.mingout.activities.R;

public class GymWorkPlanCalenderDialog extends Dialog implements
		View.OnClickListener {
	Button Bok, Bcancel;
	Activity context;
	TextView tv;
	DatePicker datePicker;

	public GymWorkPlanCalenderDialog(Activity context, TextView tv) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.tv = tv;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dialog_gym_work_plan_calender_layout);

		Bok = (Button) findViewById(R.id.button1);
		Bcancel = (Button) findViewById(R.id.button2);
		datePicker = (DatePicker) findViewById(R.id.datePicker1);

		Bok.setOnClickListener(this);
		Bcancel.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {

		case R.id.button1:
			String date = String.valueOf(datePicker.getDayOfMonth()),
			month = String.valueOf(datePicker.getMonth() + 1),
			year = String.valueOf(datePicker.getYear());

			tv.setText(date + "/" + month + "/" + year);
			dismiss();
			break;
		case R.id.button2:
			dismiss();
			break;

		}
	}
}
