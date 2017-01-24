package com.mingout.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

public class BillboardAddDetailActivity extends Activity {

	EditText editText1;
	TextView TV_save;
	int editFlag;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_billboard_add_detail_layout);
		TV_save = (TextView) findViewById(R.id.TV_save);
		editText1 = (EditText) findViewById(R.id.editText1);

		try {
			editText1.setText(getIntent().getStringExtra("data"));
			editFlag = getIntent().getIntExtra("edit flag", 0);

		} catch (Exception e) {
			// TODO: handle exception
		}
		if (String.valueOf(editFlag).equals("1")) {
			TV_save.setVisibility(View.INVISIBLE);
			editText1.setKeyListener(null);
		}

		TV_save.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (!TextUtils.isEmpty(editText1.getText())) {
					Bundle conData = new Bundle();
					conData.putString("data", editText1.getText().toString());
					Intent intent = new Intent();
					intent.putExtras(conData);
					setResult(RESULT_OK, intent);
					finish();
				}
			}
		});
	}

}
