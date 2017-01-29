package com.mingout.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mingout.activities.DashBoardActivity;
import com.mingout.activities.ProviewActivity;
import com.mingout.activities.R;
import com.mingout.dialog.MyAlertDialog;
import com.mingout.models.FacebookDataModel;
import com.mingout.models.QRscanLoginModel;
import com.mingout.util.ConnectionTaskSupportFragment;
import com.mingout.util.Constants;
import com.mingout.util.GPSHelper;
import com.mingout.util.ResultJSON;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DashboardSocialFragment extends Fragment implements ResultJSON {

	//Button B_profile, B_photo;
	ImageView IV_qrCode, IV_logo, IV_bgQr;
	TextView TV_name, TV_age, TV_interest, TV_ageDivider;
	//LinearLayout LL_profile, LL_photo;
	LinearLayout LL_business_bg;
	FacebookDataModel facebookDataObj;
	boolean qrVerificationFlag = false;
	String scannedQRCode, Lat = null, Long = null;
	SharedPreferences mPrefs;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_dashboard_social_layout, null);
		Constants.FRAGMENT_FLAG = Constants.SOCIAL_FRAGMENT_FLAG;
		//B_profile = (Button) view.findViewById(R.id.B_profile);
		//B_photo = (Button) view.findViewById(R.id.B_photo);

		IV_qrCode = (ImageView) view.findViewById(R.id.IV_qrCode);
		IV_logo = (ImageView) view.findViewById(R.id.IV_logo);
		IV_bgQr = (ImageView) view.findViewById(R.id.IV_bgQr);

		TV_name = (TextView) view.findViewById(R.id.TV_name);
		TV_age = (TextView) view.findViewById(R.id.TV_age);
		TV_interest = (TextView) view.findViewById(R.id.TV_interest);
		TV_ageDivider = (TextView) view.findViewById(R.id.TV_ageDivider);

	//	LL_profile = (LinearLayout) view.findViewById(R.id.LL_profile);
	//	LL_photo = (LinearLayout) view.findViewById(R.id.LL_photo);
        LL_business_bg = (LinearLayout) view.findViewById(R.id.LL_business_bg);

		mPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
		IV_bgQr.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.rotate_right));
        final DashBoardActivity dashBoardActivity = (DashBoardActivity) getActivity();

        GPSHelper gps = new GPSHelper(getActivity());
		gps.getMyLocation();
		if (gps.isGPSenabled()) {
			Lat = String.valueOf(gps.getLatitude());
			Long = String.valueOf(gps.getLongitude());
		}

		try {
			facebookDataObj = (FacebookDataModel) getArguments().getSerializable("facebook data");
		//	Constants.facebookProfileImage = facebookDataObj.getImageUrl();
			Picasso.with(getActivity()).load(Constants.PROFILE_SOCIAL_image).fit().centerCrop().into(IV_logo);
			TV_name.setText(facebookDataObj.getName());
		//	TV_age.setText(facebookDataObj.get);
			Log.e("Name " , Constants.S_Name);
		} catch (Exception e) {}

		IV_qrCode.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
            // TODO Auto-generated method stub
            try {
                GPSHelper gps = new GPSHelper(getActivity());
                gps.getMyLocation();
                if (gps.isGPSenabled()) {
                    Lat = String.valueOf(gps.getLatitude());
                    Long = String.valueOf(gps.getLongitude());
                    Log.e("Lat :", String.valueOf(gps.getLatitude()));
                    Log.e("Long :", String.valueOf(gps.getLongitude()));

                    new com.google.zxing.integration.android.IntentIntegrator(getActivity()).initiateScan();
                } else {
                    MyAlertDialog dialog = new MyAlertDialog(getActivity(), getResources().getString(R.string.gps_error_message));
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
			}
		});

        LL_business_bg.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                dashBoardActivity.showBusinessFragment();
				Constants.CURRENT_FRAGMENT_FLAG = Constants.BUSINESS_FRAGMENT_FLAG;
			}
		});

		return view;
	}

    public void receiveScannedData(Intent data){
		if(data != null){
			scannedQRCode = data.getStringExtra("SCAN_RESULT");
			verifyQr(scannedQRCode, Lat, Long);
		}
    }

	public void setQrUpdate(String qrCode) {
		JSONObject jsonobj;
		jsonobj = new JSONObject();
		Constants.QR_LOGIN_PROFILE_ID = Constants.PROFILE_ID_SOCIAL;
		try {
			jsonobj.put("appkey", Constants.APP_KEY);
			jsonobj.put("profile_id", Constants.QR_LOGIN_PROFILE_ID);
			jsonobj.put("session_token", Constants.SESSION_TOKEN);
			jsonobj.put("user_id", Constants.USER_ID);
			jsonobj.put("qr_code", qrCode);
			new ConnectionTaskSupportFragment(DashboardSocialFragment.this, jsonobj)
					.execute(Constants.API_UPDATE_STATUS);
		} catch (JSONException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void UpdateResult(Object obj) {
		// TODO Auto-generated method stub
		try {
			String string = (String) obj;
			if (!qrVerificationFlag) {
				JSONObject jData = new JSONObject(string);
				if (jData.getString("status_code").equals("1")) {

					Constants.USER_LAT = Lat;
					Constants.USER_LONG = Long;

					QRscanLoginModel Qrobj = new QRscanLoginModel();
					Qrobj.setUserId(Constants.USER_ID);
					Qrobj.setProfileId(Constants.QR_LOGIN_PROFILE_ID);
					Qrobj.setQrCode(Constants.QR_CODE);
					Qrobj.setSessionToken(Constants.SESSION_TOKEN);
					Qrobj.setUserLat(Constants.USER_LAT);
					Qrobj.setUserLong(Constants.USER_LONG);

					SharedPreferences.Editor editor = mPrefs.edit();
					Gson gson = new Gson();
					String json = gson.toJson(Qrobj);
					editor.putString("QRModelObject", json);
					editor.commit();

					Intent i = new Intent();
					i.setClass(getActivity(), ProviewActivity.class);
					i.putExtra("fragment flag", 2);
					startActivity(i);

					getActivity().finish();

				} else {
					Toast.makeText(getActivity(),
							jData.getString("status_message"),
							Toast.LENGTH_LONG).show();
				}
			} else {
				qrVerificationFlag = false;
				Log.e("Response :", string);
				if (!string.equals("false")) {
					JSONArray jData = new JSONArray(string);
					JSONObject jsonObj = (JSONObject) jData.get(0);
					String distance = jsonObj.getString("distance");

					if (Float.valueOf(distance) > 0) {
						Constants.QR_CODE = scannedQRCode;
						setQrUpdate(Constants.QR_CODE);
					} else {
						MyAlertDialog dialog = new MyAlertDialog(getActivity(),
								"You are not in location range! please try another QR");
						dialog.getWindow().setBackgroundDrawable(
								new ColorDrawable(Color.TRANSPARENT));
						dialog.show();
					}
				} else {
					MyAlertDialog dialog = new MyAlertDialog(getActivity(),
							"Unable to reach server! please try again");
					dialog.getWindow().setBackgroundDrawable(
							new ColorDrawable(Color.TRANSPARENT));
					dialog.show();
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void verifyQr(String qrCode, String Lat, String Long) {
		JSONObject jsonobj;
		jsonobj = new JSONObject();
		try {
			qrVerificationFlag = true;
			jsonobj.put("qr", qrCode);
			jsonobj.put("lat", Lat);
			jsonobj.put("long", Long);
			new ConnectionTaskSupportFragment(DashboardSocialFragment.this, jsonobj).execute(Constants.API_QR_LOCATION_VERIFY);
		} catch (JSONException ex) {
			ex.printStackTrace();
		}
	}

	public void updateImage() {
		// TODO Auto-generated method stub
		Picasso.with(getActivity()).load(Constants.PROFILE_SOCIAL_image).fit().centerCrop().into(IV_logo);
	}

	public void setAge(int ageFromDOB) {
		// TODO Auto-generated method stub
		// TV_ageDivider.setVisibility(View.VISIBLE);
		 TV_age.setText(String.valueOf(ageFromDOB));
	}
	//public interface OnSaveButtonPressListner {
	//	public void saveButtonPressed();
//
	//}

}
