package com.mingout.activities;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.mingout.adapters.PreviewWorkoutPlanListAdapter;
import com.mingout.models.WorkOutDevicesModel;
import com.mingout.util.ConnectionTask;
import com.mingout.util.Constants;
import com.mingout.util.ResultJSON;

public class GymWorkPlanDevicesActivity extends Activity implements ResultJSON {
	ListView lv;
	PreviewWorkoutPlanListAdapter adapter;
	TextView TV_addWorkoutPlan;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gym_work_plan_layout);

		lv = (ListView) findViewById(R.id.listView1);
		TV_addWorkoutPlan = (TextView) findViewById(R.id.TV_addWorkoutPlan);

		getWorkoutPlanData();

		TV_addWorkoutPlan.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent();
				i.setClass(GymWorkPlanDevicesActivity.this,
						GymWorkoutPlanDevicesEditActivity.class);
				startActivity(i);
			}
		});

		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent i = new Intent();
				i.setClass(GymWorkPlanDevicesActivity.this,
						GymWorkPlanActivity.class);
				i.putExtra("device number", Constants.List_workOutPlan_devices
						.get(arg2).getDevice_number());
				startActivity(i);
			}
		});
	}

	private void getWorkoutPlanData() {
		// TODO Auto-generated method stub
		JSONObject jsonobj;
		jsonobj = new JSONObject();
		try {
			// adding some keys
			jsonobj.put("appkey", "spiderman1450@gmail.com");
			jsonobj.put("session_token", Constants.SESSION_TOKEN);
			jsonobj.put("user_id", Constants.USER_ID);
			new ConnectionTask(this, jsonobj)
					.execute(Constants.API_GET_GYM_DEVICES);
		} catch (JSONException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void UpdateResult(Object obj) {
		// TODO Auto-generated method stub
		Constants.List_workOutPlan_devices = new ArrayList<WorkOutDevicesModel>();
		try {
			String string = (String) obj;
			Log.e("Response", string);
			JSONObject jData = new JSONObject(string);
			if (jData.getString("status_code").equals("1")) {
				JSONArray jResponse = jData.getJSONArray("response");
				for (int i = 0; i < jResponse.length(); i++) {
					JSONObject jSon = (JSONObject) jResponse.get(i);

					WorkOutDevicesModel objj = new WorkOutDevicesModel();
					objj.setGym_device_id(jSon.getString("gym_device_id"));
					objj.setDevice_number(jSon.getString("device_number"));
					objj.setBody_part(jSon.getString("body_part"));
					objj.setDevice_seat(jSon.getString("device_seat"));
					objj.setDescription(jSon.getString("description"));
					objj.setTime_stamp(jSon.getString("time_stamp"));
					objj.setStatus(jSon.getString("status"));

					Constants.List_workOutPlan_devices.add(objj);
				}
				if (Constants.List_workOutPlan_devices.size() != 0
						&& Constants.List_workOutPlan_devices != null) {
					adapter = new PreviewWorkoutPlanListAdapter(
							GymWorkPlanDevicesActivity.this,
							R.layout.item_workoutplan_preview_layout,
							Constants.List_workOutPlan_devices);
					lv.setAdapter(adapter);
				}

			} else {
				Toast.makeText(GymWorkPlanDevicesActivity.this,
						"Session has been expired!", Toast.LENGTH_LONG).show();
			}
		} catch (Exception e) {
			// TODO: handle exception
			Log.e("Exception :", e.toString());
		}
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		getWorkoutPlanData();

	}
}
