package com.mingout.fragments;

import com.mingout.activities.EditBusinessSocialDataActivity;
import com.mingout.activities.R;
import com.mingout.adapters.ReviewViewPagerAdapter;
import com.mingout.models.ReviewBusinessDataModel;
import com.mingout.util.Constants;
import com.squareup.picasso.Picasso;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ReviewBusinessDetailFragment extends Fragment {
	RelativeLayout RL_phraseTop, RL_companyTop, RL_biographyTop;
	LinearLayout LL_phrase, LL_company, LL_biography;
	TextView TV_myPhrase, TV_gender, TV_age, TV_jobTitle, TV_company, TV_biography, TV_name;
	ImageView IV_genderLogo;
	boolean phraseVisibilityFlag = false, companyVisibilityFlag = false, bioVisibilityFlag;

	ReviewBusinessDataModel businessModel;
	ReviewViewPagerAdapter viewPagerAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_review_business_detail_layout, null);

		RL_phraseTop = (RelativeLayout) view.findViewById(R.id.RL_phraseTop);
        RL_companyTop = (RelativeLayout) view.findViewById(R.id.RL_companyTop);
        RL_biographyTop = (RelativeLayout) view.findViewById(R.id.RL_biographyTop);

		LL_phrase = (LinearLayout) view.findViewById(R.id.LL_phrase);
        LL_company = (LinearLayout) view.findViewById(R.id.LL_company);
        LL_biography = (LinearLayout) view.findViewById(R.id.LL_biography);

		TV_myPhrase = (TextView) view.findViewById(R.id.TV_myPhrase);
		TV_gender = (TextView) view.findViewById(R.id.TV_gender);
		TV_age = (TextView) view.findViewById(R.id.TV_age);
		TV_jobTitle = (TextView) view.findViewById(R.id.TV_jobTitle);
		TV_company = (TextView) view.findViewById(R.id.TV_company);
		TV_biography = (TextView) view.findViewById(R.id.TV_biography);
		TV_name = (TextView) view.findViewById(R.id.TV_name);

		IV_genderLogo = (ImageView) view.findViewById(R.id.IV_genderLogo);
		ViewPager mViewPager = (ViewPager) view.findViewById(R.id.viewPager);

		try {
			businessModel = (ReviewBusinessDataModel) getArguments()
					.getSerializable("review data");

			TV_myPhrase.setText(businessModel.getMyPhrase());
			TV_gender.setText(businessModel.getGender());
			TV_age.setText(businessModel.getAge());
			TV_jobTitle.setText(businessModel.getJobTitle());
			TV_company.setText(businessModel.getCompany());
			TV_biography.setText(businessModel.getBiography());
			TV_name.setText(businessModel.getName());

			if (businessModel.getGender().equals("male")
					|| businessModel.getGender().equals("Male")) {
				IV_genderLogo.setImageResource(R.drawable.d_male);
			} else {
				IV_genderLogo.setImageResource(R.drawable.d_female);
			}

			viewPagerAdapter = new ReviewViewPagerAdapter(getActivity(),
					R.layout.item_review_view_pager,
					businessModel.getImagesUrl());
			mViewPager.setAdapter(viewPagerAdapter);
		} catch (Exception e) {
			// TODO: handle exception
		}

		RL_phraseTop.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
			if(!phraseVisibilityFlag){
				phraseVisibilityFlag = true;
				LL_phrase.setVisibility(View.VISIBLE);
			}else{
				phraseVisibilityFlag = false;
				LL_phrase.setVisibility(View.GONE);
			}
			}
		});

        RL_companyTop.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!companyVisibilityFlag){
                    companyVisibilityFlag = true;
                    LL_company.setVisibility(View.VISIBLE);
                }else{
                    companyVisibilityFlag = false;
                    LL_company.setVisibility(View.GONE);
                }
            }
        });

        RL_biographyTop.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!bioVisibilityFlag){
                    bioVisibilityFlag = true;
                    LL_biography.setVisibility(View.VISIBLE);
                }else{
                    bioVisibilityFlag = false;
                    LL_biography.setVisibility(View.GONE);
                }
            }
        });

		return view;
	}

}
