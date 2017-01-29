package com.mingout.activities;

import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.google.gson.Gson;
import com.mingout.dialog.MyAlertDialog;
import com.mingout.models.FacebookDataModel;
import com.mingout.util.ConnectionTask;
import com.mingout.util.Constants;
import com.mingout.util.ResultJSON;
import com.mingout.util.Utilities;

public class SplashActivity extends Activity implements ResultJSON {

	Thread splashTread;
	SharedPreferences mPrefs;
	String tag = "com.mingout.activities.SplashActivity";
	FacebookDataModel facebookDataObj;
	String responseJson;
	AccessToken accessToken;
    String accessTokenStr;
	ImageView IV_centerBg, IV_logo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Fabric.with(this, new Crashlytics());
		Fabric.with(this, new Crashlytics());
		setContentView(R.layout.activity_splash_layout);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			Window window = getWindow();
			window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			window.setStatusBarColor(Color.rgb(255, 122, 0));
		}

		IV_centerBg = (ImageView) findViewById(R.id.IV_centerBg);
		IV_logo = (ImageView) findViewById(R.id.IV_logo);

		IV_centerBg.startAnimation(AnimationUtils.loadAnimation(SplashActivity.this, R.anim.rotate_right));
		IV_logo.startAnimation(AnimationUtils.loadAnimation(SplashActivity.this, R.anim.blink));

		FacebookSdk.sdkInitialize(getApplicationContext());

		mPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());



        facebookDataObj = new FacebookDataModel();

			Gson gson = new Gson();
			String json = mPrefs.getString("facebook_access_token", "");
			accessToken = gson.fromJson(json, AccessToken.class);

		splashTread = new Thread() {
			@Override
			public void run() {
				try {
					synchronized (this) {
						wait(1000);
					}

				} catch (InterruptedException e) {
				} finally {
					if (accessToken == null) {
						Intent i = new Intent();
						i.setClass(SplashActivity.this, MainActivity.class);
						startActivity(i);
						finish();
					} else if(accessToken != null && accessToken.getToken() != ""){
						GraphRequest request = GraphRequest.newMeRequest(accessToken,
							new GraphRequest.GraphJSONObjectCallback() {
								@Override
								public void onCompleted(JSONObject object, GraphResponse response) {
									if(object != null){
										Log.e("Facebook Response", response.toString());
                                        parseFacebookData(object.toString());
									}else{
                                        MyAlertDialog dialog = new MyAlertDialog(SplashActivity.this, "There is some thing wrong! Please try later.");
                                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                        dialog.show();
                                    }
								}
							});
						Bundle parameters = new Bundle();
						parameters.putString("fields", "id, first_name, last_name, link, email, location, gender, birthday");
						request.setParameters(parameters);
						request.executeAsync();
					}
				}
			}
		};
        if(Utilities.isConnected(SplashActivity.this)){
            splashTread.start();
        }else{
            MyAlertDialog dialog = new MyAlertDialog(SplashActivity.this, "Please connect to internet!");
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
        }
	}

	protected void parseFacebookData(String response) {
		// TODO Auto-generated method stub
		JSONObject profile;
		try {
			profile = new JSONObject(response);

			facebookDataObj.setName(profile.getString("first_name"));
			facebookDataObj.setEmail(profile.getString("email"));
			facebookDataObj.setImageUrl("https://graph.facebook.com/" + profile.getString("id") + "/picture?type=large");

			fetchAccountDetails(facebookDataObj.getEmail(), facebookDataObj.getImageUrl());
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private void fetchAccountDetails(String email, String picUrl) throws JSONException {
		// TODO Auto-generated method stub
		JSONObject jsonobj;
		jsonobj = new JSONObject();
		try {
			jsonobj.put("password", "123456");
			jsonobj.put("appkey", "spiderman1450@gmail.com");
			jsonobj.put("email", email);
			jsonobj.put("fb_image", picUrl);
			new ConnectionTask(this, jsonobj, false).execute(Constants.API_SIGNIN);
		} catch (JSONException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void UpdateResult(Object obj) {
		// TODO Auto-generated method stub
		try {
			String string = (String) obj;
			Log.e("Response :", string);
			JSONObject jData = new JSONObject(string);
			if(jData.has("status_code")){
				if (jData.getString("status_code").equals("1")) {
					JSONObject jResponse = (JSONObject) jData.getJSONObject("response");

					Constants.SESSION_TOKEN = jResponse.getString("session_token");
					Constants.USER_ID = jResponse.getString("user_id");
					Constants.USER_EMAIL = facebookDataObj.getEmail();
					Constants.USER_NAME = facebookDataObj.getName();

					Intent i = new Intent();
					i.setClass(SplashActivity.this, DashBoardActivity.class);
					i.putExtra("facebook data", facebookDataObj);
					startActivity(i);
					finish();
				} else {
					Toast.makeText(SplashActivity.this, jData.getString("status_message"), Toast.LENGTH_LONG).show();
				}
			} else {
				MyAlertDialog dialog = new MyAlertDialog(SplashActivity.this, "There is some thing wrong while fetching " + facebookDataObj.getEmail()
						+ " account detail! Please contact with MingOut Administrator!");
				dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
				dialog.show();
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
