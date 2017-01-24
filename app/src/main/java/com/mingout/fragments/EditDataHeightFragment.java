package com.mingout.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.NumberPicker;
import android.widget.NumberPicker.OnValueChangeListener;
import android.widget.Toast;

import com.mingout.activities.EditBusinessSocialDataActivity.OnSaveButtonPressListner;
import com.mingout.activities.R;
import com.mingout.util.Constants;
import com.mingout.util.ResultJSON;
import com.mingout.util.UpdateProfileData;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class EditDataHeightFragment extends Fragment implements
		OnSaveButtonPressListner, ResultJSON {

	//Spinner spinner1;
	NumberPicker feetPicker;
	List<String> heightType;
	UpdateProfileData updateProfileObject;
	ArrayAdapter<String> spinnerAdapter;
	String[] feet = { "4'3\"", "4'4\"", "4'5\"", "4'6\"", "4'7\"", "4'8\"",
			"4'9\"", "4'10\"", "4'11\"", "5'0\"", "5'1\"", "5'2\"", "5'3\"",
			"5'4\"", "5'5\"", "5'6\"", "5'7\"", "5'8\"", "5'9\"", "5'10\"",
			"5'11\"", "6'0\"", "6'1\"", "6'2\"", "6'3\"", "6'4\"", "6'5\"",
			"6'6\"", "6'7\"", "6'8\"", "6'9\"", "6'10\"", "6'11\"", "7'0\"",
			"7'1\"", "7'2\"" };

	int selectedValue, lockFrag = 0;;
	String dataString = null;
//	boolean feetFlag = false;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater
				.inflate(R.layout.fragment_edit_height_layout, null);

	//	spinner1 = (Spinner) view.findViewById(R.id.spinner1);
		feetPicker = (NumberPicker) view.findViewById(R.id.numberPicker1);

		try {
			lockFrag = getArguments().getInt("frag lock");
			dataString = getArguments().getString("data");

			dataString = Constants.S_HEIGHT;
		} catch (Exception e) {
			// TODO: handle exception
		}

		heightType = new ArrayList<String>();
	//	spinnerAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, heightType);

	//	heightType.add("cm");
		heightType.add("feet");

	//	spinner1.setAdapter(spinnerAdapter);


		if (dataString != null) {
			try {
				//if (dataString.contains("\'")) {
					// Feet
				//	spinner1.setSelection(1);
					for (int i = 0; i < feet.length; i++) {
						if (dataString.equals(feet[i])) {
							feetPicker.setValue(i);
						}

					}

				//} else {
				//	// CM
				//	spinner1.setSelection(0);
				//	for (int i = 0; i <= cm.length; i++) {
//
				//	}
				//}

			} catch (Exception e) {
				// TODO: handle exception

			}
		}

	//	spinner1.setOnItemSelectedListener(new OnItemSelectedListener() {
//
	//		@Override
	//		public void onItemSelected(AdapterView<?> arg0, View arg1,
	//				int arg2, long arg3) {
	//			// TODO Auto-generated method stub
				//if (arg2 == 0) {
				//	feetPicker.setDisplayedValues(cm);
				//	feetPicker.setMinValue(0);
				//	feetPicker.setMaxValue(90);
//
				//	feetFlag = false;
				//} else {
					feetPicker.setMinValue(0);
					feetPicker.setMaxValue(30);
					feetPicker.setDisplayedValues(feet);



					//feetFlag = true;
				//}
		//	}

		//	@Override
	//		public void onNothingSelected(AdapterView<?> arg0) {
	//			// TODO Auto-generated method stub
//
	//		}
	//	});

		feetPicker.setOnValueChangedListener(new OnValueChangeListener() {

			@Override
			public void onValueChange(NumberPicker arg0, int arg1, int arg2) {
				// TODO Auto-generated method stub
				selectedValue = feetPicker.getValue();
			}
		});

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
				Toast.makeText(getActivity(), "Height has been updated!",
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
	//	if (feetFlag) {
			updateProfileObject = new UpdateProfileData(Constants.USER_ID,
					Constants.PROFILE_ID_SOCIAL, "height", feet[selectedValue],
					Constants.SESSION_TOKEN, EditDataHeightFragment.this);
					Constants.S_HEIGHT = feet[selectedValue];

			updateProfileObject.getData();
	//	} else {
	//		updateProfileObject = new UpdateProfileData(Constants.USER_ID,
	//				Constants.PROFILE_ID_SOCIAL, "height",
	//				cm[selectedValue].toString(), Constants.SESSION_TOKEN,
	//				EditDataHeightFragment.this);
	//		updateProfileObject.getData();
	//	}
	}
}
