package com.mingout.fragments;

import org.json.JSONObject;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.Toast;

import com.mingout.activities.EditBusinessSocialDataActivity.OnSaveButtonPressListner;
import com.mingout.activities.R;
import com.mingout.util.Constants;
import com.mingout.util.ResultJSON;
import com.mingout.util.UpdateProfileData;

public class EditDataSexFragment extends Fragment implements
		OnSaveButtonPressListner, ResultJSON {

	RadioButton radioButton1, radioButton2;
	String dataString;
	UpdateProfileData updateProfileObject;
	int fragmentFlag;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_edit_sex_layout, null);

		radioButton1 = (RadioButton) view.findViewById(R.id.radioButton1);
		radioButton2 = (RadioButton) view.findViewById(R.id.radioButton2);

		try {
			//if(fragmentFlag == Constants.SOCIAL_FRAGMENT_FLAG){
			//	dataString = Constants.S_GENDER;
			//}
			//dataString = Constants.B_GENDER;
			dataString = getArguments().getString("data");
			fragmentFlag = getArguments().getInt("frag flag");
			if (dataString.equals("male") || dataString.equals("Male")) {
				radioButton1.setChecked(true);
				radioButton2.setChecked(false);
			} else {
				radioButton1.setChecked(false);
				radioButton2.setChecked(true);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		return view;
	}

	@Override
	public void UpdateResult(Object obj) {
		// TODO Auto-generated method stub
		try {

			String string = (String) obj;
			JSONObject jData = new JSONObject(string);
			if (jData.getString("status_code").equals("1")) {
				JSONObject jResponse = (JSONObject) jData
						.getJSONObject("response");
				Toast.makeText(getActivity(), "Gender has been updated!",
						Toast.LENGTH_SHORT).show();
				getActivity().finish();
			}
		} catch (Exception e) {
			// TODO: handle exception

		}

	}

	@Override
	public void saveButtonPressed() {
		// TODO Auto-generated method stub
		switch (fragmentFlag) {
		case Constants.BUSINESS_FRAGMENT_FLAG:
			updateProfileObject = new UpdateProfileData(Constants.USER_ID,
					Constants.PROFILE_ID_BUSINESS, "gender", "",
					Constants.SESSION_TOKEN, EditDataSexFragment.this);
			if (radioButton1.isChecked()) {
				updateProfileObject.setValue("Male");
				Constants.B_GENDER = "Male";
			} else {
				updateProfileObject.setValue("Female");
				Constants.B_GENDER = "Female";
			}
			updateProfileObject.getData();
			break;

		case Constants.SOCIAL_FRAGMENT_FLAG:
			updateProfileObject = new UpdateProfileData(Constants.USER_ID,
					Constants.PROFILE_ID_SOCIAL, "gender", "",
					Constants.SESSION_TOKEN, EditDataSexFragment.this);
			if (radioButton1.isChecked()) {
				updateProfileObject.setValue("Male");
				Constants.S_GENDER = "Male";
			} else {
				updateProfileObject.setValue("Female");
				Constants.S_GENDER = "Female";
			}
			updateProfileObject.getData();
			break;

		}
	}
}
