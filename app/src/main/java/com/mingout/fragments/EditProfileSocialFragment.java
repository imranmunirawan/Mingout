package com.mingout.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mingout.activities.EditBusinessSocialDataActivity;
import com.mingout.activities.R;
import com.mingout.models.ReviewSocialDataModel;
import com.mingout.util.Constants;
import com.mingout.util.Utilities;

public class EditProfileSocialFragment extends Fragment {

	RelativeLayout RR_myPhrase, RR_sex, RR_status, RR_age, RR_height,
			RR_lookingFor, RR_aboutMe, RR_wantToMeet;
	TextView TV_myPhrase, TV_gender, TV_status, TV_age, TV_height,
			TV_lookingFor, TV_wantToMeet, TV_aboutMe;
	ReviewSocialDataModel socialModel;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(
				R.layout.fragment_edit_profile_social_layout, null);

		RR_myPhrase = (RelativeLayout) view.findViewById(R.id.RR_myPhrase);
		RR_sex = (RelativeLayout) view.findViewById(R.id.RR_sex);
		RR_status = (RelativeLayout) view.findViewById(R.id.RR_status);
		RR_age = (RelativeLayout) view.findViewById(R.id.RR_age);
		RR_height = (RelativeLayout) view.findViewById(R.id.RR_height);
		RR_lookingFor = (RelativeLayout) view.findViewById(R.id.RR_lookingFor);
		RR_aboutMe = (RelativeLayout) view.findViewById(R.id.RR_aboutMe);
		RR_wantToMeet = (RelativeLayout) view.findViewById(R.id.RR_wantToMeet);

		TV_myPhrase = (TextView) view.findViewById(R.id.TV_myPhrase);
		TV_gender = (TextView) view.findViewById(R.id.TV_gender);
		TV_status = (TextView) view.findViewById(R.id.TV_status);
		TV_age = (TextView) view.findViewById(R.id.TV_age);
		TV_height = (TextView) view.findViewById(R.id.TV_height);
		TV_lookingFor = (TextView) view.findViewById(R.id.TV_lookingFor);
		TV_wantToMeet = (TextView) view.findViewById(R.id.TV_wantToMeet);
		TV_aboutMe = (TextView) view.findViewById(R.id.TV_aboutMe);

		RR_myPhrase.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent();
				i.setClass(getActivity(), EditBusinessSocialDataActivity.class);
				i.putExtra("fragment flag", Constants.SOCIAL_FRAGMENT_FLAG);
				i.putExtra("frag_selecter", Constants.FLAG_MY_PHRASE);
				i.putExtra("data", socialModel.getMyPhrase());
				startActivity(i);
			}
		});

		RR_sex.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent();
				i.setClass(getActivity(), EditBusinessSocialDataActivity.class);
				i.putExtra("fragment flag", Constants.SOCIAL_FRAGMENT_FLAG);
				i.putExtra("frag_selecter", Constants.FLAG_SEX);
				i.putExtra("data", socialModel.getGender());
				startActivity(i);
			}
		});
		RR_status.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent();
				i.setClass(getActivity(), EditBusinessSocialDataActivity.class);
				i.putExtra("fragment flag", Constants.SOCIAL_FRAGMENT_FLAG);
				i.putExtra("frag_selecter", Constants.FLAG_STATUS);
				i.putExtra("data", socialModel.getStatus());
				startActivity(i);
			}
		});
		RR_age.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent();
				i.setClass(getActivity(), EditBusinessSocialDataActivity.class);
				i.putExtra("fragment flag", Constants.SOCIAL_FRAGMENT_FLAG);
				i.putExtra("frag_selecter", Constants.FLAG_AGE);
				i.putExtra("data", socialModel.getDob());
				startActivity(i);
			}
		});
		RR_height.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent();
				i.setClass(getActivity(), EditBusinessSocialDataActivity.class);
				i.putExtra("fragment flag", Constants.SOCIAL_FRAGMENT_FLAG);
				i.putExtra("frag_selecter", Constants.FLAG_HEIGHT);
				i.putExtra("data", socialModel.getHeight());
				startActivity(i);
			}
		});
		RR_lookingFor.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent();
				i.setClass(getActivity(), EditBusinessSocialDataActivity.class);
				i.putExtra("fragment flag", Constants.SOCIAL_FRAGMENT_FLAG);
				i.putExtra("frag_selecter", Constants.FLAG_LOOKING_FOR);
				i.putExtra("data", socialModel.getLookingFor());
				startActivity(i);
			}
		});
		RR_aboutMe.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent();
				i.setClass(getActivity(), EditBusinessSocialDataActivity.class);
				i.putExtra("fragment flag", Constants.SOCIAL_FRAGMENT_FLAG);
				i.putExtra("frag_selecter", Constants.FLAG_ABOUT_ME);
				i.putExtra("data", socialModel.getAboutMe());
				startActivity(i);
			}
		});
		RR_wantToMeet.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent();
				i.setClass(getActivity(), EditBusinessSocialDataActivity.class);
				i.putExtra("fragment flag", Constants.SOCIAL_FRAGMENT_FLAG);
				i.putExtra("frag_selecter", Constants.FLAG_WANT_TO_MEET);
				i.putExtra("data", socialModel.getWantToMeet());
				startActivity(i);
			}
		});

		return view;
	}

	public void setSocialViewData(Bundle data) {
		try {
			socialModel = (ReviewSocialDataModel) data
					.getSerializable("review data");

			TV_myPhrase.setText(socialModel.getMyPhrase());
			TV_gender.setText(socialModel.getGender());
			TV_status.setText(socialModel.getStatus());

			TV_age.setText(String.valueOf(Utilities.getAgeFromDOB(socialModel
					.getDob())));
			TV_height.setText(socialModel.getHeight());
			if (socialModel.getLookingFor().contains("Not")) {
				TV_lookingFor.setText("Not Looking");
			} else {
				TV_lookingFor.setText(socialModel.getLookingFor());
			}
			TV_aboutMe.setText(socialModel.getAboutMe());
			TV_wantToMeet.setText(socialModel.getWantToMeet());

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

//	public  void refresh() {
//		TV_myPhrase.setText(socialModel.getMyPhrase());
//		TV_gender.setText(socialModel.getGender());
//		TV_status.setText(socialModel.getStatus());
//
//		TV_age.setText(String.valueOf(Utilities.getAgeFromDOB(socialModel
//				.getDob())));
//		TV_height.setText(socialModel.getHeight());
//		if (socialModel.getLookingFor().contains("Not")) {
//			TV_lookingFor.setText("Not Looking");
//		} else {
//			TV_lookingFor.setText(socialModel.getLookingFor());
//		}
//		TV_aboutMe.setText(socialModel.getAboutMe());
//		TV_wantToMeet.setText(socialModel.getWantToMeet());
//
//	}
}
