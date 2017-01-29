package com.mingout.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mingout.activities.R;
import com.mingout.models.ReviewBusinessDataModel;
import com.mingout.models.ReviewSocialDataModel;
import com.mingout.util.ConnectionTaskSupportFragment;
import com.mingout.util.Constants;
import com.mingout.util.ResultJSON;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

@SuppressWarnings("ALL")
public class EditProfileActivity extends Fragment implements ResultJSON {
	Button B_social, B_business;
	EditProfileBusinessFragment businessFrag;
	EditProfileSocialFragment socialFrag;
	LinearLayout LL_social, LL_business;
	TextView TV_socailArrow, TV_businessArrow;
	int fragmentFlag;
	ImageView IV_social, IV_business;
	ViewPager pager;
	TextView TV_titleName;

	ReviewBusinessDataModel businessModel;
	ReviewSocialDataModel socialModel;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.activity_edit_profile_layout, null);

		pager = (ViewPager) view.findViewById(R.id.viewPager);
		LL_social = (LinearLayout) view.findViewById(R.id.LL_social);
		LL_business = (LinearLayout) view.findViewById(R.id.LL_business);
		B_social = (Button) view.findViewById(R.id.B_social);
		B_business = (Button) view.findViewById(R.id.B_business);
		TV_titleName  = (TextView) view.findViewById(R.id.TV_title);
		TV_socailArrow = (TextView) view.findViewById(R.id.TV_socailArrow);
		TV_businessArrow = (TextView) view.findViewById(R.id.TV_businessArrow);

		IV_social = (ImageView) view.findViewById(R.id.IV_social);
		IV_business = (ImageView) view.findViewById(R.id.IV_business);

		businessFrag = new EditProfileBusinessFragment();
		socialFrag = new EditProfileSocialFragment();

        setSocialProfile();

		try {
			Bundle bundle = this.getArguments();
			fragmentFlag = bundle.getInt("Fragment Flag", 0);
			//fragmentFlag = getActivity().getIntent().getIntExtra("Fragment Flag", 0);
			getData(true);
		} catch (Exception e) {
			// TODO: handle exception
		}
		pager.setAdapter(new MyPagerAdapter(getChildFragmentManager()));

		if (fragmentFlag == Constants.SOCIAL_FRAGMENT_FLAG) {
			pager.setCurrentItem(0, true);
            setSocialProfile();
		} else if (fragmentFlag == Constants.BUSINESS_FRAGMENT_FLAG) {
			pager.setCurrentItem(1, true);
            setBusinessProfile();
		}

		pager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				if (arg0 == 0) {
                    setSocialProfile();
				} else if (arg0 == 1) {
                    setBusinessProfile();
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {}

			@Override
			public void onPageScrollStateChanged(int arg0) {}
		});

		LL_social.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				pager.setCurrentItem(0, true);
			}
		});
		B_social.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				pager.setCurrentItem(0, true);
			}
		});

		LL_business.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				pager.setCurrentItem(1, true);
			}
		});
		B_business.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				pager.setCurrentItem(1, true);
			}
		});
		return view;
	}

	private void setSocialProfile(){
		IV_social.setImageResource(R.drawable.icon_social_active);
		IV_business.setImageResource(R.drawable.icon_business);
		B_social.setTextColor(Color.rgb(255, 149, 51));
		B_business.setTextColor(Color.rgb(127, 128, 128));
		TV_titleName.setText(getResources().getString(R.string.social));
		TV_socailArrow.setVisibility(TextView.VISIBLE);
		TV_businessArrow.setVisibility(TextView.INVISIBLE);
	}

	private void setBusinessProfile(){
		IV_social.setImageResource(R.drawable.icon_social);
		IV_business.setImageResource(R.drawable.icon_business_active);
		B_business.setTextColor(Color.rgb(255, 149, 51));
		B_social.setTextColor(Color.rgb(127, 128, 128));
		TV_titleName.setText(getResources().getString(R.string.business));
		TV_socailArrow.setVisibility(TextView.INVISIBLE);
		TV_businessArrow.setVisibility(TextView.VISIBLE);
	}

	private void getData(boolean waitingFlag) throws JSONException {
		// TODO Auto-generated method stub

		JSONObject jsonobj;
		jsonobj = new JSONObject();
		try {
			// adding some keys
			jsonobj.put("appkey", "spiderman1450@gmail.com");
			jsonobj.put("email", Constants.USER_EMAIL);
			jsonobj.put("session_token", Constants.SESSION_TOKEN);
			jsonobj.put("user_id", Constants.USER_ID);
			new ConnectionTaskSupportFragment(EditProfileActivity.this, jsonobj).execute(Constants.API_PROFILE);
		} catch (JSONException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void UpdateResult(Object obj) {
		// TODO Auto-generated method stub
		Bundle b = new Bundle(), s = new Bundle();
		try {
			String string = (String) obj;
			JSONObject jData = new JSONObject(string);
			JSONObject jResponse = (JSONObject) jData.getJSONObject("response");

			try {
				JSONObject jBusiness = (JSONObject) jResponse
						.getJSONObject("business_profile");
				Constants.PROFILE_ID_BUSINESS = jBusiness
						.getString("profile_id");

				businessModel = new ReviewBusinessDataModel();
				businessModel.setName(jBusiness.getString("full_name"));
				businessModel.setMyPhrase(jBusiness.getString("my_phrase"));
				businessModel.setGender(jBusiness.getString("gender"));
				businessModel.setAge(jBusiness.getString("age"));
				businessModel.setJobTitle(jBusiness.getString("job_title"));
				businessModel.setCompany(jBusiness.getString("current_company"));
				businessModel.setBiography(jBusiness.getString("bio"));
				businessModel.setDob(jBusiness.getString("dob"));


				b.putSerializable("review data", (Serializable) businessModel);
				businessFrag.setBusinessViewData(b);
			} catch (Exception e) {
				// TODO: handle exception

			}

			try {
				JSONObject jSocial = (JSONObject) jResponse
						.getJSONObject("social_profile");
				Constants.PROFILE_ID_SOCIAL = jSocial.getString("profile_id");

				socialModel = new ReviewSocialDataModel();
				socialModel.setName(jSocial.getString("full_name"));
				socialModel.setMyPhrase(jSocial.getString("my_phrase"));
				socialModel.setGender(jSocial.getString("gender"));
				socialModel.setStatus(jSocial.getString("martial_status"));
				socialModel.setAge(jSocial.getString("age"));
				socialModel.setHeight(jSocial.getString("height"));
				socialModel.setLookingFor(jSocial.getString("looking_for"));
				socialModel.setAboutMe(jSocial.getString("about_me"));
				socialModel.setWantToMeet(jSocial.getString("want_to_meet"));
				socialModel.setDob(jSocial.getString("dob"));

				s.putSerializable("review data", (Serializable) socialModel);
				socialFrag.setSocialViewData(s);

			} catch (Exception e) {
				// TODO: handle exception

			}

		} catch (Exception e) {
			// TODO: handle exception
			Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
		}

	}

	private class MyPagerAdapter extends FragmentPagerAdapter {

		public MyPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int pos) {
			switch (pos) {

				case 0:

					return socialFrag;

				case 1:

					return businessFrag;

				default:
					return socialFrag;
			}
		}

		@Override
		public int getCount() {
			return 2;
		}
	}

//	@Override
//	protected void onRestart() {
//		// TODO Auto-generated method stub
//		super.onRestart();
//		try {
//			getData(false);
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//
//	}

}
