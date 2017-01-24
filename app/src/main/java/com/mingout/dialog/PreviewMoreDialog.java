package com.mingout.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.mingout.activities.BillboardActivity;
import com.mingout.activities.GymWorkPlanDevicesActivity;
import com.mingout.activities.PreviewFacebookStatusActivity;
import com.mingout.activities.PreviewHistoryActivity;
import com.mingout.activities.R;
import com.mingout.fragments.MenuContactUsActivity;

public class PreviewMoreDialog extends Dialog implements
		View.OnClickListener {
	Button B_billboard, B_help, B_gymWorkOutPlan, B_contactUs,
			B_facebookStatus;
	Context context;

	public PreviewMoreDialog(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context = context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dialog_preview_more_layout);

		B_billboard = (Button) findViewById(R.id.B_billboard);
		B_help = (Button) findViewById(R.id.B_help);
		B_gymWorkOutPlan = (Button) findViewById(R.id.B_gymWorkOutPlan);
		B_contactUs = (Button) findViewById(R.id.B_contactUs);
		B_facebookStatus = (Button) findViewById(R.id.B_facebookStatus);

		B_billboard.setOnClickListener(this);
		B_help.setOnClickListener(this);
		B_gymWorkOutPlan.setOnClickListener(this);
		B_contactUs.setOnClickListener(this);
		B_facebookStatus.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {

		case R.id.B_billboard:

			Intent i = new Intent();
			i.setClass(context, BillboardActivity.class);
			context.startActivity(i);

			break;
		case R.id.B_help:
			Intent ixx = new Intent();
			ixx.setClass(context, PreviewHistoryActivity.class);
			context.startActivity(ixx);
			break;
		case R.id.B_gymWorkOutPlan:
			Intent ii = new Intent();
			ii.setClass(context, GymWorkPlanDevicesActivity.class);
			context.startActivity(ii);
			break;
		case R.id.B_contactUs:
			Intent ix = new Intent();
			ix.setClass(context, MenuContactUsActivity.class);
			context.startActivity(ix);
			break;

		case R.id.B_facebookStatus:
			Intent iii = new Intent();
			iii.setClass(context, PreviewFacebookStatusActivity.class);
			context.startActivity(iii);
			break;
		}
	}
}
