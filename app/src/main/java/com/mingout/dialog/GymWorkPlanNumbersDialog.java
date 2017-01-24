package com.mingout.dialog;

import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.mingout.activities.R;

public class GymWorkPlanNumbersDialog extends Dialog implements
		View.OnClickListener {

	Button B_select;
	Context context;
	Spinner spinner1;
	ArrayAdapter<String> spinnerAdapter;
	List<String> genderList;
	String selectedGender = null;
	int startFrom;
	TextView editText;

	public GymWorkPlanNumbersDialog(Context context, TextView editText) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.editText = editText;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dialog_gym_work_plan_numbers_layout);

		B_select = (Button) findViewById(R.id.B_select);
		spinner1 = (Spinner) findViewById(R.id.spinner1);

		genderList = new ArrayList<String>();

		for (int i = 1; i < 101; i++) {
			genderList.add(String.valueOf(i));
		}

		spinnerAdapter = new ArrayAdapter<String>(context,
				R.layout.item_dialog_drop_list_layout, R.id.text1, genderList);

		B_select.setOnClickListener(this);
		spinner1.setAdapter(spinnerAdapter);

		spinner1.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				editText.setText(genderList.get(arg2).toString());
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {

		case R.id.B_select:
			dismiss();
			break;

		}
	}

}
