package com.mingout.fragments;

import org.json.JSONObject;

import com.mingout.activities.R;
import com.mingout.activities.EditBusinessSocialDataActivity.OnSaveButtonPressListner;
import com.mingout.models.ReviewBusinessDataModel;
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

public class EditDataCompanyFragment extends Fragment implements
		OnSaveButtonPressListner, ResultJSON {

	int lockFrag = 0;
	EditText editText1;
	String dataString;
	UpdateProfileData updateProfileObject;
	ReviewBusinessDataModel businessModel;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_edit_company_layout,
				null);
		editText1 = (EditText) view.findViewById(R.id.editText1);
		businessModel = new ReviewBusinessDataModel();
		try {
			lockFrag = getArguments().getInt("frag lock");
			dataString = getArguments().getString("data");
			//dataString = Constants.B_COMPANY;
			editText1.setText(dataString);
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
	public void UpdateResult(Object obj) {
		// TODO Auto-generated method stub
		try {
			System.out.println(obj.toString());
			String string = (String) obj;
			JSONObject jData = new JSONObject(string);
			if (jData.getString("status_code").equals("1")) {
				JSONObject jResponse = (JSONObject) jData
						.getJSONObject("response");
				Toast.makeText(getActivity(), "Company has been updated!",
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
					Constants.PROFILE_ID_BUSINESS, "current_company", editText1
							.getText().toString(), Constants.SESSION_TOKEN,
					EditDataCompanyFragment.this);
			updateProfileObject.getData();
			businessModel.setCompany(editText1.getText().toString());
		}
	}
}
