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

public class EditDataLookingForFragment extends Fragment implements
		OnSaveButtonPressListner, ResultJSON {

	RadioButton radioButton1, radioButton2, radioButton3;
	String dataString;
	UpdateProfileData updateProfileObject;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_edit_looking_for_layout,
				null);
		radioButton1 = (RadioButton) view.findViewById(R.id.radioButton1);
		radioButton2 = (RadioButton) view.findViewById(R.id.radioButton2);
		radioButton3 = (RadioButton) view.findViewById(R.id.radioButton3);

		try {
			dataString = getArguments().getString("data");
			//dataString = Constants.S_LOOKING;
			if (dataString.equals("male") || dataString.equals("Male")) {
				radioButton1.setChecked(true);
			} else if (dataString.equals("female")
					|| dataString.equals("Female")) {
				radioButton2.setChecked(true);
			} else {
				radioButton3.setChecked(true);
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
			System.out.println(obj.toString());
			String string = (String) obj;
			JSONObject jData = new JSONObject(string);
			if (jData.getString("status_code").equals("1")) {
				JSONObject jResponse = (JSONObject) jData
						.getJSONObject("response");
				Toast.makeText(getActivity(), "Looking For has been updated!",
						Toast.LENGTH_SHORT).show();
				getActivity().finish();
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.toString());
		}
	}

	@Override
	public void saveButtonPressed() {
		// TODO Auto-generated method stub
		updateProfileObject = new UpdateProfileData(Constants.USER_ID,
				Constants.PROFILE_ID_SOCIAL, "looking_for", "",
				Constants.SESSION_TOKEN, EditDataLookingForFragment.this);
		if (radioButton1.isChecked()) {
			updateProfileObject.setValue("Male");
			Constants.S_LOOKING = "Male";
		} else if (radioButton2.isChecked()) {
			updateProfileObject.setValue("Female");
			Constants.S_LOOKING = "Female";
		} else {
			updateProfileObject.setValue("Not Looking");
			Constants.S_LOOKING = "Not Looking";
		}
		updateProfileObject.getData();
	}
}
