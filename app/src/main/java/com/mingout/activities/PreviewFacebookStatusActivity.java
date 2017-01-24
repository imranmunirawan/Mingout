package com.mingout.activities;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.google.gson.Gson;
import com.mingout.util.Constants;

public class PreviewFacebookStatusActivity extends Activity {

	SharedPreferences mPrefs;
	EditText editText1;
	TextView TV_send, TV_exit;
	AccessToken accessToken;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_preview_facebook_status_layout);

		editText1 = (EditText) findViewById(R.id.editText1);
		TV_send = (TextView) findViewById(R.id.TV_send);
		TV_exit = (TextView) findViewById(R.id.TV_exit);

        Gson gson = new Gson();
        String json = mPrefs.getString("facebook_access_token", "");
        accessToken = gson.fromJson(json, AccessToken.class);

		TV_send.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (!TextUtils.isEmpty(editText1.getText())) {
                    Bundle params = new Bundle();
                    params.putString("message", editText1.getText().toString() + " via MingOut");
                    //params.putString("place", Constants.FACEBOOK_CHECK_IN);
                    new GraphRequest(accessToken, "/me/feed", params, HttpMethod.POST,
                            new GraphRequest.Callback() {
                                public void onCompleted(GraphResponse response) {
                                    Toast.makeText(PreviewFacebookStatusActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                                }
                            }
                    ).executeAsync();
				}
			}
		});
		TV_exit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}
}
