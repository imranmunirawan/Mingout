package com.mingout.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.mingout.util.UpdateProfileData;

public class MenuSettingsActivity extends Activity {
	String dataString;
	UpdateProfileData updateProfileObject;

	TextView TV_save;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu_settings_layout);

		TV_save = (TextView) findViewById(R.id.TV_save);

		TV_save.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				finish();
			}
		});


	}
}
