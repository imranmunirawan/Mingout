package com.mingout.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mingout.activities.EditBusinessSocialDataActivity;
import com.mingout.activities.R;
import com.mingout.models.ReviewBusinessDataModel;
import com.mingout.util.Constants;
import com.mingout.util.Utilities;

public class EditProfileBusinessFragment extends Fragment {

	LinearLayout RR_myPhrase, RR_sex, RR_age, RR_jobTitle, RR_company,
			RR_biography;
	TextView TV_myPhrase, TV_gender, TV_age, TV_jobTitle, TV_company,
			TV_biography;
	ReviewBusinessDataModel businessModel;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(
				R.layout.fragment_edit_profile_business_layout, null);

		RR_myPhrase = (LinearLayout) view.findViewById(R.id.RR_myPhrase);
		RR_sex = (LinearLayout) view.findViewById(R.id.RR_sex);
		RR_jobTitle = (LinearLayout) view.findViewById(R.id.RR_jobTitle);
		RR_age = (LinearLayout) view.findViewById(R.id.RR_age);
		RR_company = (LinearLayout) view.findViewById(R.id.RR_company);
		RR_biography = (LinearLayout) view.findViewById(R.id.RR_biography);

		TV_myPhrase = (TextView) view.findViewById(R.id.TV_myPhrase);
		TV_gender = (TextView) view.findViewById(R.id.TV_gender);
		TV_age = (TextView) view.findViewById(R.id.TV_age);
		TV_jobTitle = (TextView) view.findViewById(R.id.TV_jobTitle);
		TV_company = (TextView) view.findViewById(R.id.TV_company);
		TV_biography = (TextView) view.findViewById(R.id.TV_biography);

		RR_myPhrase.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent();
				i.setClass(getActivity(), EditBusinessSocialDataActivity.class);
				i.putExtra("fragment flag", Constants.BUSINESS_FRAGMENT_FLAG);
				i.putExtra("frag_selecter", Constants.FLAG_MY_PHRASE);
				i.putExtra("data", businessModel.getMyPhrase());
				startActivity(i);
			}
		});

		RR_sex.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent();
				i.setClass(getActivity(), EditBusinessSocialDataActivity.class);
				i.putExtra("fragment flag", Constants.BUSINESS_FRAGMENT_FLAG);
				i.putExtra("frag_selecter", Constants.FLAG_SEX);
				i.putExtra("data", businessModel.getGender());
				startActivity(i);
			}
		});

		RR_age.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent();
				i.setClass(getActivity(), EditBusinessSocialDataActivity.class);
				i.putExtra("fragment flag", Constants.BUSINESS_FRAGMENT_FLAG);
				i.putExtra("frag_selecter", Constants.FLAG_AGE);
				i.putExtra("data", businessModel.getDob());
				startActivity(i);
			}
		});

		RR_jobTitle.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent();
				i.setClass(getActivity(), EditBusinessSocialDataActivity.class);
				i.putExtra("fragment flag", Constants.BUSINESS_FRAGMENT_FLAG);
				i.putExtra("frag_selecter", Constants.FLAG_JOB_TITLE);
				i.putExtra("data", businessModel.getJobTitle());
				startActivity(i);
			}
		});

		RR_company.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent();
				i.setClass(getActivity(), EditBusinessSocialDataActivity.class);
				i.putExtra("fragment flag", Constants.BUSINESS_FRAGMENT_FLAG);
				i.putExtra("frag_selecter", Constants.FLAG_COMPANY);
				i.putExtra("data", businessModel.getCompany());
				startActivity(i);
			}
		});

		RR_biography.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent();
				i.setClass(getActivity(), EditBusinessSocialDataActivity.class);
				i.putExtra("fragment flag", Constants.BUSINESS_FRAGMENT_FLAG);
				i.putExtra("frag_selecter", Constants.FLAG_BIOGRAPHY);
				i.putExtra("data", businessModel.getBiography());
				startActivity(i);
			}
		});

		return view;
	}

	public void setBusinessViewData(Bundle b) {
		// TODO Auto-generated method stub
		try {
			businessModel = (ReviewBusinessDataModel) b
					.getSerializable("review data");

			TV_myPhrase.setText(businessModel.getMyPhrase());
			TV_gender.setText(businessModel.getGender());
			TV_age.setText(String.valueOf(Utilities.getAgeFromDOB(businessModel
					.getDob())));
			TV_jobTitle.setText(businessModel.getJobTitle());
			TV_company.setText(businessModel.getCompany());
			TV_biography.setText(businessModel.getBiography());
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

//	public  void refresh(Bundle b) {
//		businessModel = (ReviewBusinessDataModel) b
//				.getSerializable("review data");
//		TV_myPhrase.setText(businessModel.getMyPhrase());
//		TV_gender.setText(businessModel.getGender());
//		TV_age.setText(String.valueOf(Utilities.getAgeFromDOB(businessModel
//				.getDob())));
//		TV_jobTitle.setText(businessModel.getJobTitle());
//		TV_company.setText(businessModel.getCompany());
//		TV_biography.setText(businessModel.getBiography());
//
//	}
}
