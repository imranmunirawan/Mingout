package com.mingout.fragments;

import org.json.JSONObject;

import com.mingout.activities.EditBusinessSocialDataActivity.OnSaveButtonPressListner;
import com.mingout.activities.R;
import com.mingout.models.ReviewBusinessDataModel;
import com.mingout.models.ReviewSocialDataModel;
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

public class EditDataMyPhraseFragment extends Fragment implements
		OnSaveButtonPressListner, ResultJSON {

	int lockFrag = 0;
	EditText editText1;
	String dataString;
	int fragmentFlag;
	UpdateProfileData updateProfileObject;
	ReviewSocialDataModel socialModel;
	ReviewBusinessDataModel businessModel;
	EditProfileBusinessFragment editProfileBusinessFragment;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_edit_my_phase_layout,
				null);
		editText1 = (EditText) view.findViewById(R.id.editText1);
		socialModel = new ReviewSocialDataModel();
		businessModel = new ReviewBusinessDataModel();
		try {
			lockFrag = getArguments().getInt("frag lock");
			dataString = getArguments().getString("data");

			fragmentFlag = getArguments().getInt("frag flag");
			if(fragmentFlag == Constants.SOCIAL_FRAGMENT_FLAG){
				//dataString = Constants.S_MY_PHRASE;
				editText1.setText(Constants.S_MY_PHRASE);
			}else{
				editText1.setText(Constants.B_MY_PHRASE);
			}
			//dataString = Constants.B_MY_PHRASE;
			//editText1.setText(Constants.B_MY_PHRASE);
			//editText1.setText(dataString);
		} catch (Exception e) {
			// TODO: handle exception
		}

		if (String.valueOf(lockFrag).equals(
				String.valueOf(Constants.FLAG_LOCK_FRAGMENT))) {
			editText1.setKeyListener(null);
			editText1.setText(dataString);
			editText1.setHint("");
		}

		return view;
	}

	@Override
	public void saveButtonPressed() {
		// TODO Auto-generated method stub
		if (!TextUtils.isEmpty(editText1.getText())) {
			switch (fragmentFlag) {
			case Constants.BUSINESS_FRAGMENT_FLAG:
				updateProfileObject = new UpdateProfileData(Constants.USER_ID,
						Constants.PROFILE_ID_BUSINESS, "my_phrase", editText1
								.getText().toString(), Constants.SESSION_TOKEN,
						EditDataMyPhraseFragment.this);
				businessModel.setMyPhrase(editText1.getText().toString());

				updateProfileObject.getData();
				break;

			case Constants.SOCIAL_FRAGMENT_FLAG:
				updateProfileObject = new UpdateProfileData(Constants.USER_ID,
						Constants.PROFILE_ID_SOCIAL, "my_phrase", editText1
								.getText().toString(), Constants.SESSION_TOKEN,
						EditDataMyPhraseFragment.this);
				//Constants.S_MY_PHRASE = editText1.getText().toString();
				socialModel.setMyPhrase(editText1.getText().toString());
				//editProfileBusinessFragment.refresh();
				updateProfileObject.getData();
				break;

			}
		}

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
				Toast.makeText(getActivity(), "My Phrase has been updated!",
						Toast.LENGTH_SHORT).show();
							}
		} catch (Exception e) {
			// TODO: handle exception

		}

	}

}
