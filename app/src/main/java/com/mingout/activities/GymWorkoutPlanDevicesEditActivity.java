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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mingout.dialog.GymWorkOutDeviceDialog;
import com.mingout.dialog.GymWorkOutDeviceDialog.BodyPartListner;
import com.mingout.dialog.MyAlertDialog;
import com.mingout.util.ConnectionTask;
import com.mingout.util.Constants;
import com.mingout.util.ResultJSON;

public class GymWorkoutPlanDevicesEditActivity extends Activity implements
		ResultJSON, BodyPartListner {

	Button B_delete;
	int editFlag;
	TextView TV_saveButton;
	EditText ET_bodyPart, ET_deviceNum, ET_seat, ET_desc;
	boolean delWorkOutFlag = false, editWorkOutFlag = false;
	String planId, deviceId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gym_workout_plan_edit_layout);

		B_delete = (Button) findViewById(R.id.B_delete);

		TV_saveButton = (TextView) findViewById(R.id.TV_saveButton);

		ET_bodyPart = (EditText) findViewById(R.id.ET_bodyPart);
		ET_deviceNum = (EditText) findViewById(R.id.ET_deviceNum);
		ET_seat = (EditText) findViewById(R.id.ET_seat);
		ET_desc = (EditText) findViewById(R.id.ET_desc);

		try {
			editFlag = getIntent().getIntExtra("editflag", 0);
			deviceId = getIntent().getStringExtra("id");
			Log.e("Device_ID :", deviceId);
			planId = getIntent().getStringExtra("device");

			ET_bodyPart.setText(getIntent().getStringExtra("body part"));
			ET_deviceNum.setText(getIntent().getStringExtra("device"));
			ET_seat.setText(getIntent().getStringExtra("sets"));
			ET_desc.setText(getIntent().getStringExtra("desc"));

			editWorkOutFlag = getIntent().getBooleanExtra("edit flag", false);
		} catch (Exception e) {
			// TODO: handle exception
		}

		if (editWorkOutFlag) {
			TV_saveButton.setBackgroundResource(R.drawable.save);
			B_delete.setVisibility(TextView.VISIBLE);
			ET_deviceNum.setKeyListener(null);

		}

		ET_bodyPart.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				GymWorkOutDeviceDialog dialog = new GymWorkOutDeviceDialog(
						GymWorkoutPlanDevicesEditActivity.this);
				dialog.getWindow().setBackgroundDrawable(
						new ColorDrawable(Color.TRANSPARENT));
				dialog.show();
			}
		});

		ET_deviceNum.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (editWorkOutFlag) {
					MyAlertDialog dialog = new MyAlertDialog(
							GymWorkoutPlanDevicesEditActivity.this,
							"Device Number cannot changed");
					dialog.getWindow().setBackgroundDrawable(
							new ColorDrawable(Color.TRANSPARENT));
					dialog.show();
				}
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
			if (TextUtils.isEmpty(ET_bodyPart.getText())) {
				ET_bodyPart.setError("Mendatory field!");
				return;
			}
			if (TextUtils.isEmpty(ET_deviceNum.getText())) {
				ET_deviceNum.setError("Mendatory field!");
				return;
			}
			if (TextUtils.isEmpty(ET_seat.getText())) {
				ET_seat.setError("Mendatory field!");
				return;
			}

			JSONObject jsonobj;
			jsonobj = new JSONObject();
			try {

				// adding some keys
				jsonobj.put("appkey", "spiderman1450@gmail.com");
				jsonobj.put("session_token", Constants.SESSION_TOKEN);
				jsonobj.put("user_id", Constants.USER_ID);
				jsonobj.put("device_number", ET_deviceNum.getText().toString());
				jsonobj.put("seat_number", ET_seat.getText().toString());
				jsonobj.put("body_part", ET_bodyPart.getText().toString());
				jsonobj.put("description", ET_desc.getText().toString() + " ");
				// jsonobj.put("rest", "123");
				// jsonobj.put("date", "123");
				new ConnectionTask(this, jsonobj)
						.execute(Constants.API_ADD_GYM_WORKOUT_DEVICE);
			} catch (JSONException ex) {
				ex.printStackTrace();
			}
		} else {
			if (TextUtils.isEmpty(ET_bodyPart.getText())) {
				ET_bodyPart.setError("Mendatory field!");
				return;
			}
			if (TextUtils.isEmpty(ET_deviceNum.getText())) {
				ET_deviceNum.setError("Mendatory field!");
				return;
			}
			if (TextUtils.isEmpty(ET_seat.getText())) {
				ET_seat.setError("Mendatory field!");
				return;
			}

			JSONObject jsonobj;
			jsonobj = new JSONObject();
			try {
				// adding some keys
				jsonobj.put("appkey", "spiderman1450@gmail.com");
				jsonobj.put("session_token", Constants.SESSION_TOKEN);
				jsonobj.put("user_id", Constants.USER_ID);
				jsonobj.put("gym_device_id", deviceId);
				jsonobj.put("device_number", planId);
				jsonobj.put("seat_number", ET_seat.getText().toString());
				jsonobj.put("body_part", ET_bodyPart.getText().toString());
				jsonobj.put("description", ET_desc.getText().toString());
				// jsonobj.put("rest", "123");
				// jsonobj.put("date", "123");
				new ConnectionTask(this, jsonobj)
						.execute(Constants.API_UPDATE_GYM_WORKOUT_DEVICE);
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
						Toast.makeText(GymWorkoutPlanDevicesEditActivity.this,
								"Gym Work plan added successfully!",
								Toast.LENGTH_LONG).show();
					} else {
						Toast.makeText(GymWorkoutPlanDevicesEditActivity.this,
								"Gym Work plan has been updated successfully!",
								Toast.LENGTH_LONG).show();
					}
				} else {
					delWorkOutFlag = false;
					Toast.makeText(GymWorkoutPlanDevicesEditActivity.this,
							"Gym work plan deleted successfully!",
							Toast.LENGTH_LONG).show();
				}
				finish();
			} else if (jData.getString("status_code").equals("1002")) {
				MyAlertDialog dialog = new MyAlertDialog(
						GymWorkoutPlanDevicesEditActivity.this,
						"Device Number already exist");
				dialog.getWindow().setBackgroundDrawable(
						new ColorDrawable(Color.TRANSPARENT));
				dialog.show();
			} else {
				Toast.makeText(GymWorkoutPlanDevicesEditActivity.this,
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
			jsonobj.put("device_number", planId);
			new ConnectionTask(this, jsonobj)
					.execute(Constants.API_DEL_GYM_WORKOUT_DEVICE);
		} catch (JSONException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void setBodyPart(String bodyPart) {
		// TODO Auto-generated method stub
		ET_bodyPart.setText(bodyPart);
	}

}
