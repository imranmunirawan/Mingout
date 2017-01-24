package com.mingout.util;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Fragment;

public class UpdateProfileData {

	String userId, profileId, columnName, value, sessionToken;
	Fragment frag;

	public UpdateProfileData(String userId, String profileId,
			String columnName, String value, String sessionToken, Fragment f) {
		this.userId = userId;
		this.profileId = profileId;
		this.columnName = columnName;
		this.value = value;
		this.sessionToken = sessionToken;
		this.frag = f;

	}

	public void setValue(String value) {
		this.value = value;
	}

	public void setProfileId(String profileId) {
		this.profileId = profileId;
	}

	public void getData() {
		// TODO Auto-generated method stub

		JSONObject jsonobj;
		jsonobj = new JSONObject();
		try {
			// adding some keys
			jsonobj.put("appkey", "spiderman1450@gmail.com");
			jsonobj.put("session_token", Constants.SESSION_TOKEN);
			jsonobj.put("user_id", userId);
			jsonobj.put("profile_id", profileId);
			jsonobj.put("column", columnName);
			jsonobj.put("value", value);
			new ConnectionTask(frag, jsonobj)
					.execute(Constants.API_PROFILE_UPDATE);
		} catch (JSONException ex) {
			ex.printStackTrace();
		}
	}

}
