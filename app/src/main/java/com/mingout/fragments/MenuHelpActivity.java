package com.mingout.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import com.mingout.activities.R;
import com.mingout.util.ConnectionTask;
import com.mingout.util.ConnectionTaskSupportFragment;
import com.mingout.util.Constants;
import com.mingout.util.ResultJSON;

import org.json.JSONException;
import org.json.JSONObject;

public class MenuHelpActivity extends Fragment implements ResultJSON {
	WebView WV_text;
 	TextView helpTxt;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.activity_menu_help_layout, null);
		helpTxt = (TextView)view.findViewById(R.id.TV_title);
		helpTxt.setText("Help");

		WV_text = (WebView) view.findViewById(R.id.WV_text);

		getData();
		return view;

	}

	private void getData() {
		// TODO Auto-generated method stub

		JSONObject jsonobj;
		jsonobj = new JSONObject();
		try {
			// adding some keys
			jsonobj.put("appkey", "spiderman1450@gmail.com");
			jsonobj.put("session_token", Constants.SESSION_TOKEN);
			jsonobj.put("user_id", Constants.USER_ID);
			new ConnectionTaskSupportFragment(MenuHelpActivity.this, jsonobj).execute(Constants.API_HELP);
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

			if (jData.getString("status_code").equals("1")) {
				JSONObject jResponse = jData.getJSONObject("response");
				String text = jResponse.getString("text");

				byte[] decoded = Base64.decode(text.toString(), Base64.DEFAULT);
				String decodedString = new String(decoded, "UTF-8");

				final String mimeType = "text/html";
				final String encoding = "UTF-8";
				String html = decodedString.toString();
				WV_text.loadDataWithBaseURL("", html, mimeType, encoding, "");

			} else {
				Toast.makeText(getActivity(), jData.getString("status_message"),
						Toast.LENGTH_LONG).show();
			}
		} catch (Exception e) {
			// TODO: handle exception
			Log.e("Exception :", e.toString());
		}
	}
}
