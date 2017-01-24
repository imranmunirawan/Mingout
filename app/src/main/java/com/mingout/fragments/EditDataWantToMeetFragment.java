package com.mingout.fragments;

import org.json.JSONObject;

import com.mingout.activities.R;
import com.mingout.activities.EditBusinessSocialDataActivity.OnSaveButtonPressListner;
import com.mingout.util.Constants;
import com.mingout.util.ResultJSON;
import com.mingout.util.UpdateProfileData;

import android.app.Fragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

public class EditDataWantToMeetFragment extends Fragment implements
		OnSaveButtonPressListner, ResultJSON {

	EditText editText1;
	String dataString;
	UpdateProfileData updateProfileObject;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(
				R.layout.fragment_edit_want_to_meet_layout, null);
		editText1 = (EditText) view.findViewById(R.id.editText1);

		try {
			dataString = getArguments().getString("data");
			//dataString = Constants.S_WANT_TO_MEET;
			editText1.setText(dataString);
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
				Toast.makeText(getActivity(), "Want To Meet has been updated!",
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
		if (!TextUtils.isEmpty(editText1.getText())) {
			updateProfileObject = new UpdateProfileData(Constants.USER_ID,
					Constants.PROFILE_ID_SOCIAL, "want_to_me", editText1
							.getText().toString(), Constants.SESSION_TOKEN,
					EditDataWantToMeetFragment.this);
			Constants.S_WANT_TO_MEET = editText1.getText().toString();
			editText1.setText(Constants.S_WANT_TO_MEET);
			updateProfileObject.getData();
		}
	}
}
