package com.mingout.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mingout.adapters.GymWorkPlanListAdapter;
import com.mingout.models.workoutPlanModel;
import com.mingout.util.ConnectionTask;
import com.mingout.util.Constants;
import com.mingout.util.ResultJSON;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class GymWorkPlanActivity extends Activity implements ResultJSON {

	ListView lv;
	GymWorkPlanListAdapter adapter;
	TextView TV_addWorkoutPlan;
	String deviceNumber;
	TextView TV_title;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gym_work_plan);

		lv = (ListView) findViewById(R.id.listView1);
		TV_addWorkoutPlan = (TextView) findViewById(R.id.TV_addWorkoutPlan);
		TV_title = (TextView) findViewById(R.id.TV_title);

		try {
			deviceNumber = getIntent().getStringExtra("device number");
			TV_title.setText("Device Number : " + deviceNumber);
			getWorkoutPlanData(true);
		} catch (Exception e) {
			// TODO: handle exception
		}

		TV_addWorkoutPlan.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent();
				i.setClass(GymWorkPlanActivity.this,
						GymWorkPlanEditActivity.class);
				i.putExtra("device number", deviceNumber);
				startActivity(i);
			}
		});

	}

	private void getWorkoutPlanData(boolean progressbarFlag) {
		// TODO Auto-generated method stub
		JSONObject jsonobj;
		jsonobj = new JSONObject();
		try {
			// adding some keys
			jsonobj.put("appkey", "spiderman1450@gmail.com");
			jsonobj.put("session_token", Constants.SESSION_TOKEN);
			jsonobj.put("user_id", Constants.USER_ID);
			jsonobj.put("device_number", deviceNumber);

			new ConnectionTask(this, jsonobj, progressbarFlag)
					.execute(Constants.API_GET_GYM_WORKOUT_PLAN);
		} catch (JSONException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void UpdateResult(Object obj) {
		// TODO Auto-generated method stub
		Constants.List_workOutPlan = new ArrayList<workoutPlanModel>();
		try {
			String string = (String) obj;
			Log.e("Response", string);
			JSONObject jData = new JSONObject(string);
			if (jData.getString("status_code").equals("1")) {
				JSONArray jResponse = jData.getJSONArray("response");
				for (int i = 0; i < jResponse.length(); i++) {
					JSONObject jSon = (JSONObject) jResponse.get(i);

					workoutPlanModel objj = new workoutPlanModel();
					objj.setSets(jSon.getString("sets"));
					objj.setReps(jSon.getString("reps"));
					objj.setWeight(jSon.getString("weight"));
					objj.setRest(jSon.getString("rest"));
					objj.setDate(jSon.getString("date"));
					objj.setPid(jSon.getString("plan_id"));
					objj.setDeviceName(jSon.getString("device_number"));

					Constants.List_workOutPlan.add(objj);
				}
				if (Constants.List_workOutPlan.size() != 0
						&& Constants.List_workOutPlan_devices != null) {
					adapter = new GymWorkPlanListAdapter(
							GymWorkPlanActivity.this,
							R.layout.item_workplan_layout,
							Constants.List_workOutPlan);
					lv.setAdapter(adapter);
				}

			} else {
				Toast.makeText(GymWorkPlanActivity.this,
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
		getWorkoutPlanData(false);
	}
}
