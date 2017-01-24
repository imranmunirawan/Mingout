package com.mingout.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.mingout.activities.MenuSettingsActivity;
import com.mingout.activities.R;
import com.mingout.fragments.MenuContactUsActivity;
import com.mingout.fragments.MenuHelpActivity;

public class OptionsDialog extends Dialog implements
		View.OnClickListener {
	Button B_contactUs, B_settings, B_help;
	Context context;

	public OptionsDialog(Context context) {
		super(context);
		this.context = context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.options_layout);

		B_contactUs = (Button) findViewById(R.id.B_contactUs);
		B_help = (Button) findViewById(R.id.B_help);
		B_settings = (Button) findViewById(R.id.B_settings);

		B_contactUs.setOnClickListener(this);
		B_help.setOnClickListener(this);
		B_settings.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
            case R.id.B_contactUs:
                Intent i = new Intent();
                i.setClass(context, MenuContactUsActivity.class);
                context.startActivity(i);
                break;

            case R.id.B_help:
                Intent iii = new Intent();
                iii.setClass(context, MenuHelpActivity.class);
                context.startActivity(iii);
                break;

            case R.id.B_settings:
                Intent ii = new Intent();
                ii.setClass(context, MenuSettingsActivity.class);
                context.startActivity(ii);
                break;
		}
	}
}
