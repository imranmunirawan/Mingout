package com.mingout.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.mingout.activities.R;
import com.mingout.activities.ReviewItemDetailActivity;
import com.mingout.models.ChatRoomUserListModel;
import com.mingout.util.Constants;

public class ChatMoreDialog extends Dialog implements
		View.OnClickListener {
	Button Bok;
	ChatRoomUserListModel secondUserData;
	Activity context;

	public ChatMoreDialog(Activity context, ChatRoomUserListModel secondUserData) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.secondUserData = secondUserData;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dialog_chat_more_layout);

		Bok = (Button) findViewById(R.id.B_ok);

		Bok.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {

		case R.id.B_ok:
			Intent i = new Intent();
			i.setClass(context, ReviewItemDetailActivity.class);
			if (secondUserData.getType().equals("B")) {
				i.putExtra("Fragment Flag", Constants.SOCIAL_FRAGMENT_FLAG);
			} else {
				i.putExtra("Fragment Flag", Constants.BUSINESS_FRAGMENT_FLAG);
			}
			i.putExtra("profile id", secondUserData.getProfileId());
			i.putExtra("detail flag", 1);
			context.startActivity(i);
			break;
		}
	}

}
