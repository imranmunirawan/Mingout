package com.mingout.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.mingout.activities.R;
import com.mingout.adapters.ReviewViewPagerAdapter;
import com.mingout.models.ReviewSocialDataModel;

public class ReviewSocialDetailFragment extends Fragment {
	TextView TV_myPhrase, TV_gender, TV_status, TV_age, TV_height,
			TV_lookingFor, TV_aboutMe, TV_wantToMeet, TV_name, TV_type;
	ReviewSocialDataModel socialModel;
	ImageView IV_genderLogo;
	ReviewViewPagerAdapter viewPagerAdapter;
    LinearLayout LL_phrase, LL_about, LL_want;
    boolean phraseVisibilityFlag = false, aboutVisibilityFlag = false, wantVisibilityFlag = false;
    RelativeLayout RL_phraseTop, RL_aboutTop, RL_wantTop;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_review_social_detail_layout, null);

		TV_myPhrase = (TextView) view.findViewById(R.id.TV_myPhrase);
		TV_gender = (TextView) view.findViewById(R.id.TV_gender);
		TV_status = (TextView) view.findViewById(R.id.TV_status);
		TV_age = (TextView) view.findViewById(R.id.TV_age);
		TV_height = (TextView) view.findViewById(R.id.TV_height);
		TV_lookingFor = (TextView) view.findViewById(R.id.TV_lookingFor);
		TV_aboutMe = (TextView) view.findViewById(R.id.TV_aboutMe);
		TV_wantToMeet = (TextView) view.findViewById(R.id.TV_wantToMeet);
		TV_name = (TextView) view.findViewById(R.id.TV_name);
		TV_type = (TextView) view.findViewById(R.id.textView2);

		IV_genderLogo = (ImageView) view.findViewById(R.id.IV_genderLogo);

        LL_phrase = (LinearLayout) view.findViewById(R.id.LL_phrase);
        LL_about = (LinearLayout) view.findViewById(R.id.LL_about);
		LL_want = (LinearLayout) view.findViewById(R.id.LL_want);
        RL_phraseTop = (RelativeLayout) view.findViewById(R.id.RL_phraseTop);
        RL_aboutTop = (RelativeLayout) view.findViewById(R.id.RL_aboutTop);
		RL_wantTop = (RelativeLayout) view.findViewById(R.id.RL_wantTop);
		ViewPager mViewPager = (ViewPager) view.findViewById(R.id.viewPager);

		try {
			socialModel = (ReviewSocialDataModel) getArguments().getSerializable("review data");
			int i = getArguments().getInt("another user detail", 0);
			if (String.valueOf(i).equals("0")) {
				TV_myPhrase.setText(socialModel.getMyPhrase());
				TV_gender.setText(socialModel.getGender());
				TV_status.setText(socialModel.getStatus());
				TV_age.setText(socialModel.getAge());
				TV_height.setText(socialModel.getHeight());
				TV_lookingFor.setText(socialModel.getLookingFor());
				TV_aboutMe.setText(socialModel.getAboutMe());
				TV_wantToMeet.setText(socialModel.getWantToMeet());
				TV_name.setText(socialModel.getName());

				if (socialModel.getGender().equals(getActivity().getResources().getString(R.string.male))) {
					IV_genderLogo.setImageResource(R.drawable.d_male);
				} else if (socialModel.getGender().equals(getActivity().getResources().getString(
								R.string.female))) {
					IV_genderLogo.setImageResource(R.drawable.d_female);
				}

				viewPagerAdapter = new ReviewViewPagerAdapter(getActivity(), R.layout.item_review_view_pager, socialModel.getImagesUrls());
				mViewPager.setAdapter(viewPagerAdapter);
			} else {
				TV_myPhrase.setText(socialModel.getMyPhrase());
				TV_gender.setText(socialModel.getGender());
				TV_age.setText(socialModel.getAge());
				TV_lookingFor.setText(socialModel.getLookingFor());
				TV_name.setText(socialModel.getName());

				if (socialModel.getGender().equals("male") || socialModel.getGender().equals("Male")) {
					IV_genderLogo.setImageResource(R.drawable.d_male);
				} else {
					IV_genderLogo.setImageResource(R.drawable.d_female);
				}

				if (socialModel.getType().equals("B")) {
					TV_type.setBackgroundResource(R.drawable.round_business_shape);
					TV_type.setText("Business");
				} else {
					TV_type.setBackgroundResource(R.drawable.round_social_shape);
					TV_type.setText("Social");
				}
			}
		} catch (Exception e) {}

        RL_phraseTop.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
            if(!phraseVisibilityFlag){
                phraseVisibilityFlag = true;
                LL_phrase.setVisibility(View.VISIBLE);
            }else{
                phraseVisibilityFlag = false;
                LL_phrase.setVisibility(View.GONE);
            }
			}
		});

        RL_aboutTop.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
            if(!aboutVisibilityFlag){
                aboutVisibilityFlag = true;
                LL_about.setVisibility(View.VISIBLE);
            }else{
                aboutVisibilityFlag = false;
                LL_about.setVisibility(View.GONE);
            }
			}
		});
		RL_wantTop.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				if(!wantVisibilityFlag){
					wantVisibilityFlag = true;
					LL_want.setVisibility(View.VISIBLE);
				}else{
					wantVisibilityFlag = false;
					LL_want.setVisibility(View.GONE);
				}
			}
		});
		return view;
	}
}
