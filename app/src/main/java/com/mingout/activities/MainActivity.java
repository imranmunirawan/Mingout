package com.mingout.activities;

import org.json.JSONException;
import org.json.JSONObject;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.gson.Gson;
import com.mingout.dialog.RegistrationAgeDialog;
import com.mingout.dialog.RegistrationAgeDialog.DialogAgeListener;
import com.mingout.models.FacebookDataModel;
import com.mingout.util.ConnectionTask;
import com.mingout.util.Constants;
import com.mingout.util.ResultJSON;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

@SuppressWarnings("ALL")
public class MainActivity extends Activity implements ResultJSON, DialogAgeListener {

    String responseJson, userDOB = null, tag = "com.mingout.activities.MainActivity";
    FacebookDataModel facebookDataObj;
    SharedPreferences mPrefs;
    private static String APP_ID = String.valueOf(R.string.facebook_app_id);
    boolean singinFlag = false;
    LoginButton loginButton;
    CallbackManager callbackManager;
    ImageView IV_logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // FacebookSdk.sdkInitialize(getApplicationContext());
        // AppEventsLogger.activateApp(this);
        loginButton = (LoginButton) findViewById(R.id.login_button);
        IV_logo = (ImageView) findViewById(R.id.IV_logo);

        loginButton.setReadPermissions("email", "user_status");
        callbackManager = CallbackManager.Factory.create();

        facebookDataObj = new FacebookDataModel();
        mPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                AccessToken accessToken = loginResult.getAccessToken();

                SharedPreferences.Editor prefsEditor = mPrefs.edit();
                Gson gson = new Gson();
                String json = gson.toJson(accessToken);
                prefsEditor.putString("facebook_access_token", json);

                prefsEditor.commit();

                GraphRequest request = GraphRequest.newMeRequest(accessToken,
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                parseFacebookData(object.toString());
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id, first_name, last_name, link, email, location, gender, birthday");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {}

            @Override
            public void onError(FacebookException exception) {}
        });
    }


    protected void parseFacebookData(String response) {
        // TODO Auto-generated method stub
        JSONObject profile;
        IV_logo.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.blink));
        try {
            profile = new JSONObject(response);
            try {
                facebookDataObj.setName(profile.getString("first_name"));
            } catch (Exception e) {

            }
            try {
                facebookDataObj.setLastName(profile.getString("last_name"));
            } catch (Exception e) {

            }
            try {
                facebookDataObj.setEmail(profile.getString("email"));
            } catch (Exception e) {

            }
            try {
                facebookDataObj.setGender(profile.getString("gender"));
            } catch (Exception e) {

            }
            // try {
            //     facebookDataObj.setBirthday(profile.getString("birthday"));
            // } catch (Exception e) {
//
            // }
            facebookDataObj.setImageUrl("https://graph.facebook.com/" + profile.getString("id") + "/picture?type=large");
            getSigninData();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void getData(String email, String picUrl, String DOB)
            throws JSONException {
        JSONObject jsonobj;
        jsonobj = new JSONObject();
        try {
            singinFlag = false;
            jsonobj.put("p", "123456");
            jsonobj.put("f", facebookDataObj.getName());
            jsonobj.put("e", email);
            jsonobj.put("l", facebookDataObj.getLastName());
            jsonobj.put("g", facebookDataObj.getGender());
            jsonobj.put("d", DOB);
            jsonobj.put("c", "not nvailable");
            new ConnectionTask(this, jsonobj).execute(Constants.API_REGISTRATION);
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void UpdateResult(Object obj) {
        // TODO Auto-generated method stub
        try {
            String string = (String) obj;
            JSONObject jData = new JSONObject(string);
            JSONObject jResponse = (JSONObject) jData.getJSONObject("response");
            if (singinFlag) {
                if (jData.has("status_code")) {
                    singinFlag = false;
                    Constants.SESSION_TOKEN     = jResponse.getString("session_token");
                    Constants.USER_ID            = jResponse.getString("user_id");
                    Constants.USER_EMAIL         = facebookDataObj.getEmail();
                    Constants.USER_NAME         = facebookDataObj.getName();

                    Toast.makeText(MainActivity.this,
                            Constants.USER_NAME , Toast.LENGTH_LONG ).show();
                    Intent i = new Intent(MainActivity.this, DashBoardActivity.class);
                    i.putExtra("facebook data", facebookDataObj);
                    startActivity(i);
                    finish();
                } else {
                    RegistrationAgeDialog dialog = new RegistrationAgeDialog(MainActivity.this);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.show();
                }
            } else {
                if (jData.getString("status_code").equals("1")){
                    Constants.SESSION_TOKEN = jResponse.getString("session_token");
                    Constants.USER_ID = jResponse.getString("user_id");
                    Constants.USER_EMAIL = facebookDataObj.getEmail();
                    Constants.USER_NAME = facebookDataObj.getName();

                    Intent i = new Intent();
                    i.setClass(MainActivity.this, DashBoardActivity.class);
                    i.putExtra("facebook_data", facebookDataObj);
                    startActivity(i);
                    finish();
                } else if (jData.getString("status_code").equals("2")) {
                    getSigninData();
                } else {
                    Toast.makeText(MainActivity.this, jData.getString("status_message"), Toast.LENGTH_LONG).show();
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            Log.e("Exception", e.toString());
        }
    }

    private void getSigninData() throws JSONException {
        // TODO Auto-generated method stub
        JSONObject jsonobj;
        jsonobj = new JSONObject();
        try {
            singinFlag = true;
            jsonobj.put("password", "123456");
            jsonobj.put("appkey", "spiderman1450@gmail.com");
            jsonobj.put("email", facebookDataObj.getEmail());
            jsonobj.put("fb_image", facebookDataObj.getImageUrl());
            //  AppConstents.USER_AGE = facebookDataObj.getAge();

            new ConnectionTask(this, jsonobj).execute(Constants.API_SIGNIN);

        } catch (JSONException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void AgeListner(String age) {
        userDOB = age;
        try {
            getData(facebookDataObj.getEmail(), facebookDataObj.getImageUrl(), userDOB);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
