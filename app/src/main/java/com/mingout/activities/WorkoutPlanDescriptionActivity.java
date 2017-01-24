package com.mingout.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class WorkoutPlanDescriptionActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_workout_plan_description_layout);
		
		TextView textView2 = (TextView)findViewById(R.id.textView2);
		
		try{
			textView2.setText(getIntent().getStringExtra("desc"));
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
