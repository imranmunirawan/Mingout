package com.mingout.fragments;

import org.json.JSONException;
import org.json.JSONObject;
import com.mingout.util.ConnectionTask;
import com.mingout.util.Constants;
import com.mingout.util.ResultJSON;
import android.app.Fragment;
import android.util.Log;

public class JoinChatFragment extends Fragment implements ResultJSON {

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Constants.JOIN_CHAT_FLAG = true;
		getData(Constants.USER_LAT, Constants.USER_LONG);
	}

	private void getData(String Lat, String Long) {
		// TODO Auto-generated method stub

		JSONObject jsonobj;
		jsonobj = new JSONObject();
		try {
			// adding some keys
			jsonobj.put("appkey", "spiderman1450@gmail.com");
			jsonobj.put("session_token", Constants.SESSION_TOKEN);
			jsonobj.put("user_id", Constants.USER_ID);
			jsonobj.put("profile_id", Constants.QR_LOGIN_PROFILE_ID);
			jsonobj.put("lat", Lat);
			jsonobj.put("lon", Long);
			jsonobj.put("qr_code", Constants.QR_CODE);

			if (Constants.JOIN_CHAT_FLAG) new ConnectionTask(JoinChatFragment.this, jsonobj, false).execute(Constants.API_JOIN_CHAT);
		} catch (JSONException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void UpdateResult(Object obj) {
		// TODO Auto-generated method stub
		if (Constants.JOIN_CHAT_FLAG)
			getData(Constants.USER_LAT, Constants.USER_LONG);
		Log.e("Methord called :", "Join Chat");

	}

	public void StartJoinChat() {
		Constants.JOIN_CHAT_FLAG = true;
		getData(Constants.USER_LAT, Constants.USER_LONG);
	}
	
	public boolean isJoinChatRunning(){
		return Constants.JOIN_CHAT_FLAG;
	}

}