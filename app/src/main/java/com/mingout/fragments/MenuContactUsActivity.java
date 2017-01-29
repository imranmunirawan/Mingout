package com.mingout.fragments;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mingout.activities.R;
import com.mingout.dialog.ContactUsPurposeDialog;
import com.mingout.util.ConnectionTaskSupportFragment;
import com.mingout.util.Constants;
import com.mingout.util.ResultJSON;

import org.json.JSONException;
import org.json.JSONObject;

public class MenuContactUsActivity extends Fragment implements ResultJSON {

	EditText ET_name, ET_email, ET_contactPurpose, ET_subject, ET_description;
	TextView TV_cancel, TV_save;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.activity_menu_contact_us_layout, null);

		ET_name = (EditText) view.findViewById(R.id.ET_name);
		ET_email = (EditText) view.findViewById(R.id.ET_email);
		ET_contactPurpose = (EditText) view.findViewById(R.id.ET_contactPurpose);
		ET_subject = (EditText) view.findViewById(R.id.ET_subject);
		ET_description = (EditText) view.findViewById(R.id.ET_description);

		TV_cancel = (TextView) view.findViewById(R.id.TV_cancel);
		TV_save = (TextView) view.findViewById(R.id.TV_save);

		ET_name.setText(Constants.USER_NAME);
		ET_email.setText(Constants.USER_EMAIL);
		ET_contactPurpose.setKeyListener(null);
		ET_contactPurpose.setText(getResources().getString(R.string.feedback));

		TV_save.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				sendContactUsData();
				ET_subject.setText("");
				ET_description.setText("");
			}
		});
	//	TV_cancel.setOnClickListener(new OnClickListener() {

	//		@Override
	//		public void onClick(View arg0) {
	//			// TODO Auto-generated method stub
	//			getActivity().finish();
	//		}
	//	});

		ET_contactPurpose.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				ContactUsPurposeDialog dialog = new ContactUsPurposeDialog(
						getActivity(), ET_contactPurpose);
				dialog.getWindow().setBackgroundDrawable(
						new ColorDrawable(Color.TRANSPARENT));
				dialog.show();
			}
		});
		return view;
	}

	protected void sendContactUsData() {
		// TODO Auto-generated method stub
		String email = ET_email.getText().toString();
		if (TextUtils.isEmpty(ET_name.getText())) {
			ET_name.setError("Mandatory Field!");
			return;
		}
		if (TextUtils.isEmpty(ET_email.getText())) {
			ET_email.setError("Mandatory Field!");
			return;
		}
		if (!email.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")) {
			ET_email.setError("Enter valid email!");
			return;
		}
		if (TextUtils.isEmpty(ET_contactPurpose.getText())) {
			ET_contactPurpose.setError("Mandatory Field!");
			return;
		}
		if (TextUtils.isEmpty(ET_subject.getText())) {
			ET_subject.setError("Mandatory Field!");
			return;
		}
		if (TextUtils.isEmpty(ET_description.getText())) {
			ET_description.setError("Mandatory Field!");
			return;
		}

		JSONObject jsonobj;
		jsonobj = new JSONObject();
		try {
			// adding some keys
			jsonobj.put("appkey", "spiderman1450@gmail.com");
			jsonobj.put("session_token", Constants.SESSION_TOKEN);
			jsonobj.put("user_id", Constants.USER_ID);
			jsonobj.put("sender_email", ET_email.getText().toString());
			jsonobj.put("name", ET_name.getText().toString());
			jsonobj.put("reciever_email", Constants.CONTACT_RECEIVER_EMAIL);
			jsonobj.put("contact_purpose", ET_contactPurpose.getText().toString());
			jsonobj.put("subject", ET_subject.getText().toString());
			jsonobj.put("description", ET_description.getText().toString());
			new ConnectionTaskSupportFragment(MenuContactUsActivity.this, jsonobj).execute(Constants.API_CONTACT_US);
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
			Log.e("Contact Response", string);
			if (jData.has("status_code")) {
				Toast.makeText(getActivity(),
						"Contact details has been sent!", Toast.LENGTH_LONG)
						.show();
				//getActivity().finish();

			} else {
//				Toast.makeText(MenuContactUsActivity.this,
//						jData.getString("message"), Toast.LENGTH_LONG)
//						.show();
			}
			//getActivity().finish();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
