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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class EditDataStatusFragment extends Fragment implements
		OnSaveButtonPressListner, ResultJSON {

	String dataString;
	UpdateProfileData updateProfileObject;
    String finalValue;
    RadioButton RB_single, RB_inRelation, RB_married, RB_divorced, RB_widow;

    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_edit_status_layout, null);

        RB_single = (RadioButton) view.findViewById(R.id.RB_single);
        RB_inRelation = (RadioButton) view.findViewById(R.id.RB_inRelation);
        RB_married = (RadioButton) view.findViewById(R.id.RB_married);
        RB_divorced = (RadioButton) view.findViewById(R.id.RB_divorced);
        RB_widow = (RadioButton) view.findViewById(R.id.RB_widow);

        try {

			dataString = getArguments().getString("data");
            //dataString = Constants.S_STATUS;
            finalValue = dataString;

            if (dataString.equals("Single")) {
                setSingle();
            } else if (dataString.equals("In Relationship")) {
                setRelation();
            } else if (dataString.equals("Married")) {
                setMarried();
            } else if (dataString.equals("Divorced")) {
                setDivorced();
            } else if (dataString.equals("Widow/er")) {
                setWidow();
            }
        } catch (Exception e) {}




		return view;
	}

    private String getStatus(){
        String value = null;
        if (RB_single.isChecked()) {
            value = "Single";
            Constants.S_STATUS = value;
        } else  if (RB_inRelation.isChecked()) {
            value = "In Relationship";
            Constants.S_STATUS = value;
        } else  if (RB_married.isChecked()) {
            value = "Married";
            Constants.S_STATUS = value;
        } else  if (RB_divorced.isChecked()) {
            value = "Divorced";
            Constants.S_STATUS = value;
        } else  if (RB_widow.isChecked()) {
            value = "Widow/er";
            Constants.S_STATUS = value;
        }

        if (Constants.S_STATUS.equals("Single")) {
            setSingle();
        } else if (Constants.S_STATUS.equals("In Relationship")) {
            setRelation();
        } else if (Constants.S_STATUS.equals("Married")) {
            setMarried();
        } else if (Constants.S_STATUS.equals("Divorced")) {
            setDivorced();
        } else if (Constants.S_STATUS.equals("Widow/er")) {
            setWidow();
        }
        return value;
    }

    private void setSingle(){
        RB_single.setChecked(true);
        RB_inRelation.setChecked(false);
        RB_married.setChecked(false);
        RB_divorced.setChecked(false);
        RB_widow.setChecked(false);
    }

    private void setRelation(){
        RB_single.setChecked(false);
        RB_inRelation.setChecked(true);
        RB_married.setChecked(false);
        RB_divorced.setChecked(false);
        RB_widow.setChecked(false);
    }

    private void setMarried(){
        RB_single.setChecked(false);
        RB_inRelation.setChecked(false);
        RB_married.setChecked(true);
        RB_divorced.setChecked(false);
        RB_widow.setChecked(false);
    }

    private void setWidow(){
        RB_single.setChecked(false);
        RB_inRelation.setChecked(false);
        RB_married.setChecked(false);
        RB_divorced.setChecked(false);
        RB_widow.setChecked(true);
    }

	@Override
	public void UpdateResult(Object obj) {
		try {
			System.out.println(obj.toString());
			String string = (String) obj;
			JSONObject jData = new JSONObject(string);
			if (jData.getString("status_code").equals("1")) {
				JSONObject jResponse = (JSONObject) jData.getJSONObject("response");
				Toast.makeText(getActivity(), "Status has been updated!", Toast.LENGTH_SHORT).show();
				getActivity().finish();
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

    private void setDivorced(){
        RB_single.setChecked(false);
        RB_inRelation.setChecked(false);
        RB_married.setChecked(false);
        RB_divorced.setChecked(true);
        RB_widow.setChecked(false);
    }

	@Override
	public void saveButtonPressed() {
		if (getStatus() != null) {
            updateProfileObject = new UpdateProfileData(Constants.USER_ID, Constants.PROFILE_ID_SOCIAL, "marital_status", getStatus(), Constants.SESSION_TOKEN, EditDataStatusFragment.this);
            updateProfileObject.getData();
		}

	}
}
