package com.mingout.activities;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.mingout.fragments.EditDataAboutMeFragment;
import com.mingout.fragments.EditDataAgeFragment;
import com.mingout.fragments.EditDataBiographyFragment;
import com.mingout.fragments.EditDataCompanyFragment;
import com.mingout.fragments.EditDataHeightFragment;
import com.mingout.fragments.EditDataJobTitleFragment;
import com.mingout.fragments.EditDataLookingForFragment;
import com.mingout.fragments.EditDataMyPhraseFragment;
import com.mingout.fragments.EditDataSexFragment;
import com.mingout.fragments.EditDataStatusFragment;
import com.mingout.fragments.EditDataWantToMeetFragment;
import com.mingout.fragments.EditProfileActivity;
import com.mingout.fragments.EditProfileBusinessFragment;
import com.mingout.fragments.EditProfileSocialFragment;
import com.mingout.util.Constants;

public class EditBusinessSocialDataActivity extends Activity {

	int fragmentFlag, dataFragmentFlag, lockFrag;
	String dataString = "";
	TextView TV_title, TV_cancel, TV_save;
	Fragment frag;
	EditProfileBusinessFragment editProfileBusinessFragment;
	EditProfileSocialFragment editProfileSocialFragment;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_business_client_layout);

		TV_title = (TextView) findViewById(R.id.TV_title);
		TV_cancel = (TextView) findViewById(R.id.TV_cancel);
		TV_save = (TextView) findViewById(R.id.TV_save);

		try {
			fragmentFlag = getIntent().getIntExtra("fragment flag", 0);
			dataFragmentFlag = getIntent().getIntExtra("frag_selecter", 0);
			lockFrag = getIntent().getIntExtra("frag lock", 0);
			dataString = getIntent().getStringExtra("data");
			try {
			} catch (Exception e) {
				// TODO: handle exception
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		if (String.valueOf(lockFrag).equals(
				String.valueOf(Constants.FLAG_LOCK_FRAGMENT))) {
			TV_cancel.setVisibility(TextView.INVISIBLE);
			TV_save.setVisibility(TextView.INVISIBLE);
		}

		FragmentManager fm = getFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		Bundle b = new Bundle();
		b.putInt("frag lock", lockFrag);
		b.putInt("frag flag", fragmentFlag);
		b.putString("data", dataString);

		switch (fragmentFlag) {
		case Constants.BUSINESS_FRAGMENT_FLAG:
			switch (dataFragmentFlag) {
			case Constants.FLAG_MY_PHRASE:
				EditDataMyPhraseFragment frag_business_phrase = new EditDataMyPhraseFragment();
				frag = frag_business_phrase;
				frag_business_phrase.setArguments(b);
				ft.add(R.id.frag_container_edit_business_client_data,
						frag_business_phrase);
				TV_title.setText(getResources().getString(R.string.my_phrase));
				break;
			case Constants.FLAG_SEX:
				EditDataSexFragment frag_sex = new EditDataSexFragment();
				frag = frag_sex;
				frag_sex.setArguments(b);
				ft.add(R.id.frag_container_edit_business_client_data, frag_sex);
				TV_title.setText(getResources().getString(R.string.gender));
				break;
			case Constants.FLAG_AGE:
				EditDataAgeFragment frag_age = new EditDataAgeFragment();
				frag = frag_age;
				frag_age.setArguments(b);
				ft.add(R.id.frag_container_edit_business_client_data, frag_age);
				TV_title.setText(getResources().getString(R.string.age));
				break;

			case Constants.FLAG_JOB_TITLE:
				EditDataJobTitleFragment frag_job_title = new EditDataJobTitleFragment();
				frag = frag_job_title;
				frag_job_title.setArguments(b);
				ft.add(R.id.frag_container_edit_business_client_data,
						frag_job_title);
				TV_title.setText(getResources().getString(R.string.job_title));
				break;
			case Constants.FLAG_COMPANY:
				EditDataCompanyFragment frag_company = new EditDataCompanyFragment();
				frag = frag_company;
				//frag_company.setArguments(b);
				frag_company.setArguments(b);
				ft.add(R.id.frag_container_edit_business_client_data,frag_company);
				TV_title.setText(getResources().getString(R.string.company));
				break;
			case Constants.FLAG_BIOGRAPHY:
				EditDataBiographyFragment frag_bio = new EditDataBiographyFragment();
				frag = frag_bio;
				//frag_bio.setArguments(b);
				frag_bio.setArguments(b);
				ft.add(R.id.frag_container_edit_business_client_data, frag_bio);
				TV_title.setText(getResources().getString(R.string.biography));
				break;
			}
			ft.commit();
			break;
		case Constants.SOCIAL_FRAGMENT_FLAG:
			switch (dataFragmentFlag) {
			case Constants.FLAG_MY_PHRASE:
				EditDataMyPhraseFragment frag_phrase = new EditDataMyPhraseFragment();
				frag = frag_phrase;
				frag_phrase.setArguments(b);
				ft.add(R.id.frag_container_edit_business_client_data,
						frag_phrase);
				TV_title.setText(getResources().getString(R.string.my_phrase));
				break;
			case Constants.FLAG_SEX:
				EditDataSexFragment frag_sex = new EditDataSexFragment();
				frag = frag_sex;
				frag_sex.setArguments(b);
				ft.add(R.id.frag_container_edit_business_client_data, frag_sex);
				TV_title.setText(getResources().getString(R.string.gender));
				break;
			case Constants.FLAG_STATUS:
				EditDataStatusFragment frag_status = new EditDataStatusFragment();
				frag = frag_status;
				frag_status.setArguments(b);
				ft.add(R.id.frag_container_edit_business_client_data,
						frag_status);
				TV_title.setText(getResources().getString(R.string.status));
				break;
			case Constants.FLAG_AGE:
				EditDataAgeFragment frag_age = new EditDataAgeFragment();
				frag = frag_age;
				frag_age.setArguments(b);
				ft.add(R.id.frag_container_edit_business_client_data, frag_age);
				TV_title.setText(getResources().getString(R.string.age));
				break;
			case Constants.FLAG_HEIGHT:
				EditDataHeightFragment frag_height = new EditDataHeightFragment();
				frag = frag_height;
				frag_height.setArguments(b);
				ft.add(R.id.frag_container_edit_business_client_data,
						frag_height);
				TV_title.setText(getResources().getString(R.string.height));
				break;
			case Constants.FLAG_LOOKING_FOR:
				EditDataLookingForFragment frag_looking_for = new EditDataLookingForFragment();
				frag = frag_looking_for;
				frag_looking_for.setArguments(b);
				ft.add(R.id.frag_container_edit_business_client_data,
						frag_looking_for);
				TV_title.setText(getResources().getString(R.string.looking_for));
				break;
			case Constants.FLAG_ABOUT_ME:
				EditDataAboutMeFragment frag_aboutMe = new EditDataAboutMeFragment();
				frag = frag_aboutMe;
				frag_aboutMe.setArguments(b);
				frag_aboutMe.setArguments(b);
				ft.add(R.id.frag_container_edit_business_client_data,
						frag_aboutMe);
				TV_title.setText(getResources().getString(R.string.about_me));
				break;
			case Constants.FLAG_WANT_TO_MEET:
				EditDataWantToMeetFragment frag_wantToMeet = new EditDataWantToMeetFragment();
				frag = frag_wantToMeet;
				frag_wantToMeet.setArguments(b);
				ft.add(R.id.frag_container_edit_business_client_data,
						frag_wantToMeet);
				TV_title.setText(getResources().getString(R.string.want_to_meet));
				break;
			}
			ft.commit();
			break;

		}

		TV_save.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				((OnSaveButtonPressListner) frag).saveButtonPressed();

				finish();

			}
		});
		TV_cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

	public interface OnSaveButtonPressListner {
		public void saveButtonPressed();

	}
}
