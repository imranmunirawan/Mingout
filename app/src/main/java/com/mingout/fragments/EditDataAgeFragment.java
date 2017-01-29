package com.mingout.fragments;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Toast;

import com.mingout.activities.EditBusinessSocialDataActivity.OnSaveButtonPressListner;
import com.mingout.activities.R;
import com.mingout.util.Constants;
import com.mingout.util.ResultJSON;
import com.mingout.util.UpdateProfileData;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EditDataAgeFragment extends Fragment implements
		OnSaveButtonPressListner, ResultJSON {
	String dataString;
	DatePicker datePicker1;
	int day, month, year;
	UpdateProfileData updateProfileObject;
	int fragmentFlag;
	Date date1;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_edit_age_layout, null);

		datePicker1 = (DatePicker) view.findViewById(R.id.datePicker1);

		try {
			dataString = getArguments().getString("data");
			//if(fragmentFlag == Constants.SOCIAL_FRAGMENT_FLAG){
			//	dataString = Constants.S_AGE_STR;
			//}
			//dataString = Constants.B_AGE_STR;
			fragmentFlag = getArguments().getInt("frag flag");
		} catch (Exception e) {
			// TODO: handle exception
		}
		@SuppressLint("SimpleDateFormat")
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		try {
			date1 = sdf.parse(dataString);
			String dateTime = dataString;
			String[] dayTimer = dateTime.split("\\-");
			year = Integer.valueOf(dayTimer[0]);
			month = Integer.valueOf(dayTimer[1]);
			day = Integer.valueOf(dayTimer[2]);

			datePicker1.updateDate(year, month - 1, day);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
				Toast.makeText(getActivity(), "Age has been updated!",
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
		switch (fragmentFlag) {
		case Constants.BUSINESS_FRAGMENT_FLAG:
			updateProfileObject = new UpdateProfileData(Constants.USER_ID,
					Constants.PROFILE_ID_BUSINESS, "dob",
					String.valueOf(datePicker1.getYear()) + "-"
							+ String.valueOf(datePicker1.getMonth() + 1) + "-"
							+ String.valueOf(datePicker1.getDayOfMonth()),
					Constants.SESSION_TOKEN, EditDataAgeFragment.this);
					updateProfileObject.getData();
			break;

		case Constants.SOCIAL_FRAGMENT_FLAG:
			updateProfileObject = new UpdateProfileData(Constants.USER_ID,
					Constants.PROFILE_ID_SOCIAL, "dob",
					String.valueOf(datePicker1.getYear()) + "-"
							+ String.valueOf(datePicker1.getMonth() + 1) + "-"
							+ String.valueOf(datePicker1.getDayOfMonth()),
					Constants.SESSION_TOKEN, EditDataAgeFragment.this);
					updateProfileObject.getData();
			break;
		}

	}
}
