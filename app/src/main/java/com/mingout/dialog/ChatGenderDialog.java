package com.mingout.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.mingout.activities.R;
import com.mingout.util.Constants;

import java.util.ArrayList;
import java.util.List;

public class ChatGenderDialog extends Dialog implements
		View.OnClickListener {
	Button B_select;
	Context context;
	Spinner spinner1;
	ArrayAdapter<String> spinnerAdapter;
	List<String> genderList;
	Fragment fragment;
	String selectedGender = null;
	Activity activity;

	public ChatGenderDialog(Context context, Fragment frag) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.fragment = frag;
	}

	public ChatGenderDialog(Context context, Activity activity) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.activity = activity;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dialog_chat_gender_layoyt);

		B_select = (Button) findViewById(R.id.B_select);
		spinner1 = (Spinner) findViewById(R.id.spinner1);

		genderList = new ArrayList<String>();

		genderList.add(context.getResources().getString(R.string.any));
		genderList.add(context.getResources().getString(R.string.male));
		genderList.add(context.getResources().getString(R.string.female));

		spinnerAdapter = new ArrayAdapter<String>(context,
				android.R.layout.simple_list_item_1, genderList);

		B_select.setOnClickListener(this);
		spinner1.setAdapter(spinnerAdapter);

		spinner1.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				selectedGender = genderList.get(arg2).toString();
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
			if (selectedGender != null) {
				if(fragment != null){
					((GenderListner) fragment).setGender(selectedGender);
				}else if(activity != null){
                    Constants.MATCHES_SETTINGS_GENDER = selectedGender;
					((GenderListner) activity).setGender(selectedGender);
				}

				dismiss();
			}
			break;

		}
	}

	public interface GenderListner {
		void setGender(String gender);
	}
}
