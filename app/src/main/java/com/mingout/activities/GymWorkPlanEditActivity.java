package com.mingout.activities;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mingout.dialog.GymWorkPlanCalenderDialog;
import com.mingout.dialog.GymWorkPlanNumbersDialog;
import com.mingout.dialog.MyAlertDialog;
import com.mingout.models.workoutPlanModel;
import com.mingout.util.ConnectionTask;
import com.mingout.util.Constants;
import com.mingout.util.ResultJSON;

public class GymWorkPlanEditActivity extends Activity implements ResultJSON {

	Button B_delete;
	TextView TV_saveButton, TV_title, ET_deviceNumber, ET_sets, ET_reps,
			ET_rest, ET_date;
	EditText ET_weight;
	boolean delWorkOutFlag = false, editWorkOutFlag = false;
	String deviceNumber;
	workoutPlanModel workPlanObject;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		setContentView(R.layout.activity_gym_work_plan_edit);

		B_delete = (Button) findViewById(R.id.B_delete);

		TV_saveButton = (TextView) findViewById(R.id.TV_saveButton);
		TV_title = (TextView) findViewById(R.id.TV_title);

		ET_deviceNumber = (TextView) findViewById(R.id.ET_deviceNumber);
		ET_sets = (TextView) findViewById(R.id.ET_sets);
		ET_reps = (TextView) findViewById(R.id.ET_reps);
		ET_weight = (EditText) findViewById(R.id.ET_weight);
		ET_rest = (TextView) findViewById(R.id.ET_rest);
		ET_date = (TextView) findViewById(R.id.ET_date);

		try {
			editWorkOutFlag = getIntent().getBooleanExtra("edit flag", false);
			TV_title.setText("Device Number : "
					+ getIntent().getStringExtra("device number"));
			ET_deviceNumber
					.setText(getIntent().getStringExtra("device number"));
		} catch (Exception e) {
			// TODO: handle exception
		}

		if (editWorkOutFlag) {

			try {
				workPlanObject = (workoutPlanModel) getIntent()
						.getSerializableExtra("data");
				TV_title.setText("Device Number : "
						+ workPlanObject.getDeviceName());
				ET_deviceNumber.setText(workPlanObject.getDeviceName());
			} catch (Exception e) {
				// TODO: handle exception

			}

			TV_saveButton.setBackgroundResource(R.drawable.save);
			B_delete.setVisibility(TextView.VISIBLE);

			ET_sets.setText(workPlanObject.getSets());
			ET_reps.setText(workPlanObject.getReps());
			ET_weight.setText(workPlanObject.getWeight());
			ET_rest.setText(workPlanObject.getRest());
			ET_date.setText(workPlanObject.getDate());

		}

		ET_sets.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				GymWorkPlanNumbersDialog dialog = new GymWorkPlanNumbersDialog(
						GymWorkPlanEditActivity.this, ET_sets);
				dialog.getWindow().setBackgroundDrawable(
						new ColorDrawable(Color.TRANSPARENT));
				dialog.show();
			}
		});

		ET_rest.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				GymWorkPlanNumbersDialog dialog = new GymWorkPlanNumbersDialog(
						GymWorkPlanEditActivity.this, ET_rest);
				dialog.getWindow().setBackgroundDrawable(
						new ColorDrawable(Color.TRANSPARENT));
				dialog.show();
			}
		});

		ET_date.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				GymWorkPlanCalenderDialog dialog = new GymWorkPlanCalenderDialog(
						GymWorkPlanEditActivity.this, ET_date);
				dialog.getWindow().setBackgroundDrawable(
						new ColorDrawable(Color.TRANSPARENT));
				dialog.show();
			}
		});

		ET_reps.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				GymWorkPlanNumbersDialog dialog = new GymWorkPlanNumbersDialog(
						GymWorkPlanEditActivity.this, ET_reps);
				dialog.getWindow().setBackgroundDrawable(
						new ColorDrawable(Color.TRANSPARENT));
				dialog.show();
			}
		});

		TV_saveButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				setAddWorkPlanData();
			}
		});
		B_delete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				delWorkoutPlan();
			}
		});
	}

	protected void setAddWorkPlanData() {
		// TODO Auto-generated method stub
		if (!editWorkOutFlag) {
			if (TextUtils.isEmpty(ET_sets.getText())) {
				ET_sets.setError("Mendatory field!");
				return;
			}
			if (TextUtils.isEmpty(ET_reps.getText())) {
				ET_reps.setError("Mendatory field!");
				return;
			}
			if (TextUtils.isEmpty(ET_weight.getText())) {
				ET_weight.setError("Mendatory field!");
				return;
			}
			if (TextUtils.isEmpty(ET_rest.getText())) {
				ET_rest.setError("Mendatory field!");
				return;
			}
			if (TextUtils.isEmpty(ET_date.getText())) {
				ET_date.setError("Mendatory field!");
				return;
			}
			JSONObject jsonobj;
			jsonobj = new JSONObject();
			try {

				// adding some keys
				jsonobj.put("appkey", "spiderman1450@gmail.com");
				jsonobj.put("session_token", Constants.SESSION_TOKEN);
				jsonobj.put("user_id", Constants.USER_ID);
				jsonobj.put("device_number", ET_deviceNumber.getText()
						.toString());
				jsonobj.put("sets", ET_sets.getText().toString());
				jsonobj.put("reps", ET_reps.getText().toString());
				jsonobj.put("weight", ET_weight.getText().toString());
				jsonobj.put("rest", ET_rest.getText().toString());
				jsonobj.put("date", ET_date.getText().toString());

				new ConnectionTask(this, jsonobj)
						.execute(Constants.API_ADD_GYM_WORKOUT_PLAN);
			} catch (JSONException ex) {
				ex.printStackTrace();
			}
		} else {
			if (TextUtils.isEmpty(ET_sets.getText())) {
				ET_sets.setError("Mendatory field!");
				return;
			}
			if (TextUtils.isEmpty(ET_reps.getText())) {
				ET_reps.setError("Mendatory field!");
				return;
			}
			if (TextUtils.isEmpty(ET_weight.getText())) {
				ET_weight.setError("Mendatory field!");
				return;
			}
			if (TextUtils.isEmpty(ET_rest.getText())) {
				ET_rest.setError("Mendatory field!");
				return;
			}
			if (TextUtils.isEmpty(ET_date.getText())) {
				ET_date.setError("Mendatory field!");
				return;
			}
			JSONObject jsonobj;
			jsonobj = new JSONObject();
			try {
				// adding some keys

					jsonobj.put("appkey", "spiderman1450@gmail.com");
					jsonobj.put("session_token", Constants.SESSION_TOKEN);
					jsonobj.put("user_id", Constants.USER_ID);
					jsonobj.put("device_number", workPlanObject.getDeviceName());
					jsonobj.put("plan_id", workPlanObject.getPid());
					jsonobj.put("sets", ET_sets.getText().toString());
					jsonobj.put("reps", ET_reps.getText().toString());
					jsonobj.put("weight", ET_weight.getText().toString());
					jsonobj.put("rest", ET_rest.getText().toString());
					jsonobj.put("date", ET_date.getText().toString());

				new ConnectionTask(this, jsonobj).execute(Constants.API_UPDATE_GYM_WORKOUT_PLAN);
			} catch (JSONException ex) {
				ex.printStackTrace();
			}
		}
	}

	@Override
	public void UpdateResult(Object obj) {
		// TODO Auto-generated method stub
		try {
			String string = (String) obj;
			Log.e("Edit Response :", string);
			JSONObject jData = new JSONObject(string);
			if (jData.getString("status_code").equals("1")) {
				if (!delWorkOutFlag) {
					if (!editWorkOutFlag) {
						Toast.makeText(GymWorkPlanEditActivity.this,
								"Gym Work plan added successfully!",
								Toast.LENGTH_LONG).show();
					} else {
						Toast.makeText(GymWorkPlanEditActivity.this,
								"Gym Work plan has been updated successfully!",
								Toast.LENGTH_LONG).show();
					}
				} else {
					delWorkOutFlag = false;
					Toast.makeText(GymWorkPlanEditActivity.this,
							"Gym work plan deleted successfully!",
							Toast.LENGTH_LONG).show();
				}
				finish();
			} else if (jData.getString("status_code").equals("1002")) {
				MyAlertDialog dialog = new MyAlertDialog(
						GymWorkPlanEditActivity.this,
						"Device Number already exist");
				dialog.getWindow().setBackgroundDrawable(
						new ColorDrawable(Color.TRANSPARENT));
				dialog.show();
			} else {
				Toast.makeText(GymWorkPlanEditActivity.this,
						jData.getString("status_message"), Toast.LENGTH_LONG)
						.show();
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void delWorkoutPlan() {
		JSONObject jsonobj;
		jsonobj = new JSONObject();
		try {
			// adding some keys
			delWorkOutFlag = true;
			jsonobj.put("appkey", "spiderman1450@gmail.com");
			jsonobj.put("session_token", Constants.SESSION_TOKEN);
			jsonobj.put("user_id", Constants.USER_ID);
			jsonobj.put("device_number", workPlanObject.getDeviceName());
			jsonobj.put("plan_id", workPlanObject.getPid());

			new ConnectionTask(this, jsonobj)
					.execute(Constants.API_DEL_GYM_WORKOUT_PLAN);
		} catch (JSONException ex) {
			ex.printStackTrace();
		}
	}

}
