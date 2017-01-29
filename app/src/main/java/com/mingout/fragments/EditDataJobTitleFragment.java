package com.mingout.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.mingout.activities.EditBusinessSocialDataActivity.OnSaveButtonPressListner;
import com.mingout.activities.R;
import com.mingout.models.ReviewBusinessDataModel;
import com.mingout.util.Constants;
import com.mingout.util.ResultJSON;
import com.mingout.util.UpdateProfileData;

import org.json.JSONObject;

public class EditDataJobTitleFragment extends Fragment implements
		OnSaveButtonPressListner, ResultJSON {

	EditText editText1;
	String dataString;
	UpdateProfileData updateProfileObject;
	ReviewBusinessDataModel businessModel;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_edit_job_title_layout,
				null);

		editText1 = (EditText) view.findViewById(R.id.editText1);
		businessModel = new ReviewBusinessDataModel();
		try {
			//dataString = Constants.B_JOB_TITLE;
			dataString = getArguments().getString("data");
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
				JSONObject jResponse = jData
						.getJSONObject("response");
				Toast.makeText(getActivity(), "Job Title has been updated!",
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
					Constants.PROFILE_ID_BUSINESS, "job_title", editText1
							.getText().toString(), Constants.SESSION_TOKEN,
					EditDataJobTitleFragment.this);
			businessModel.setCompany(editText1.getText().toString());
			updateProfileObject.getData();
		}
	}
}
