package com.mingout.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mingout.fragments.ProfileGalleryBusinessFragment;
import com.mingout.fragments.ProfileGallerySocialFragment;
import com.mingout.models.GalleryModel;
import com.mingout.util.ConnectionTask;
import com.mingout.util.Constants;
import com.mingout.util.ResultJSON;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@SuppressWarnings("ALL")
public class ProfileGalleryActivity extends AppCompatActivity implements
        ResultJSON, ProfileGallerySocialFragment.RefreshDataListner, ProfileGalleryBusinessFragment.RefreshBusinessDataListner {

    Button B_social, B_business;
    ProfileGalleryBusinessFragment businessFrag;
    ProfileGallerySocialFragment socialFrag;
    TextView  TV_socailArrow, TV_businessArrow;
            //TV_titleName,

    int fragmentFlag;
    ImageView IV_business, IV_social;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_gallery_layout);

        final ViewPager pager = (ViewPager) findViewById(R.id.viewPager);
        B_social = (Button) findViewById(R.id.B_social);
        B_business = (Button) findViewById(R.id.B_business);
        //TV_titleName = (TextView) findViewById(R.id.TV_titleName);
        TV_socailArrow = (TextView) findViewById(R.id.TV_socailArrow);
        TV_businessArrow = (TextView) findViewById(R.id.TV_businessArrow);

        IV_business = (ImageView) findViewById(R.id.IV_business);
        IV_social = (ImageView) findViewById(R.id.IV_social);

        businessFrag = new ProfileGalleryBusinessFragment();
        socialFrag = new ProfileGallerySocialFragment();

        setSocialGallery();

        try {
            fragmentFlag = this.getIntent().getIntExtra("Fragment Flag", 0);
            getData();
        } catch (Exception e) {
            // TODO: handle exception
        }

        pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));

        if (fragmentFlag == Constants.SOCIAL_FRAGMENT_FLAG) {
            pager.setCurrentItem(0, true);
            setSocialGallery();

        } else if (fragmentFlag == Constants.BUSINESS_FRAGMENT_FLAG) {
            pager.setCurrentItem(1, true);
            setBusinessGallery();
        }

        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                // TODO Auto-generated method stub
                if (arg0 == 0) {
                    setSocialGallery();
                } else if (arg0 == 1) {
                    setBusinessGallery();
                }
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {}

            @Override
            public void onPageScrollStateChanged(int arg0) {}
        });

        B_social.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                pager.setCurrentItem(0, true);
            }
        });

        B_business.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                pager.setCurrentItem(1, true);
            }
        });

    }

    private void setSocialGallery(){
        IV_social.setImageResource(R.drawable.icon_social_active);
        IV_business.setImageResource(R.drawable.icon_business);
        B_social.setTextColor(Color.rgb(255, 149, 51));
        B_business.setTextColor(Color.rgb(127, 128, 128));
        //TV_titleName.setText("Social Photos");
        TV_socailArrow.setVisibility(TextView.VISIBLE);
        TV_businessArrow.setVisibility(TextView.INVISIBLE);
    }

    private void setBusinessGallery(){
        IV_social.setImageResource(R.drawable.icon_social);
        IV_business.setImageResource(R.drawable.icon_business_active);
        B_business.setTextColor(Color.rgb(255, 149, 51));
        B_social.setTextColor(Color.rgb(127, 128, 128));
     //   TV_titleName.setText("Business Photos");
        TV_socailArrow.setVisibility(TextView.INVISIBLE);
        TV_businessArrow.setVisibility(TextView.VISIBLE);
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

    @Override
    public void UpdateResult(Object obj) {
        // TODO Auto-generated method stub
        try {
            String string = (String) obj;
            JSONObject jData = new JSONObject(string);
            JSONObject jResponse = (JSONObject) jData.getJSONObject("response");

            try {
                JSONObject jBusiness = (JSONObject) jResponse
                        .getJSONObject("business_profile");

                GalleryModel businessGallery = new GalleryModel();

                JSONArray profileImages = jBusiness
                        .getJSONArray("profileImages");

                if (profileImages.get(0).toString()
                        .equals(Constants.PROFILE_IMAGE_NOT_AVAILABLE)) {
                    businessGallery.setPicUrl1(Constants.facebookProfileImage);
                } else {
                    businessGallery.setPicUrl1(profileImages.get(0).toString());
                }

                // businessGallery.setPicUrl1(profileImages.get(0).toString());
                businessGallery.setPicUrl2(profileImages.get(1).toString());
                businessGallery.setPicUrl3(profileImages.get(2).toString());
                businessGallery.setPicUrl4(profileImages.get(3).toString());
                businessGallery.setPicUrl5(profileImages.get(4).toString());
                businessGallery.setPicUrl6(profileImages.get(5).toString());

                JSONArray profileIds = jBusiness
                        .getJSONArray("profileImagesIds");

                businessGallery.setPicId1(profileIds.get(0).toString());
                businessGallery.setPicId2(profileIds.get(1).toString());
                businessGallery.setPicId3(profileIds.get(2).toString());
                businessGallery.setPicId4(profileIds.get(3).toString());
                businessGallery.setPicId5(profileIds.get(4).toString());
                businessGallery.setPicId6(profileIds.get(5).toString());

                businessFrag.setGalleryData(businessGallery);

            } catch (Exception e) {
                // TODO: handle exception

            }

            try {
                JSONObject jSocial = (JSONObject) jResponse
                        .getJSONObject("social_profile");

                GalleryModel socialGallery = new GalleryModel();

                JSONArray profileImages = jSocial.getJSONArray("profileImages");

                if (profileImages.get(0).toString()
                        .equals(Constants.PROFILE_IMAGE_NOT_AVAILABLE)) {
                    socialGallery.setPicUrl1(Constants.facebookProfileImage);
                } else {
                    socialGallery.setPicUrl1(profileImages.get(0).toString());
                }

                socialGallery.setPicUrl2(profileImages.get(1).toString());
                socialGallery.setPicUrl3(profileImages.get(2).toString());
                socialGallery.setPicUrl4(profileImages.get(3).toString());
                socialGallery.setPicUrl5(profileImages.get(4).toString());
                socialGallery.setPicUrl6(profileImages.get(5).toString());

                JSONArray profileIds = jSocial.getJSONArray("profileImagesIds");

                socialGallery.setPicId1(profileIds.get(0).toString());
                socialGallery.setPicId2(profileIds.get(1).toString());
                socialGallery.setPicId3(profileIds.get(2).toString());
                socialGallery.setPicId4(profileIds.get(3).toString());
                socialGallery.setPicId5(profileIds.get(4).toString());
                socialGallery.setPicId6(profileIds.get(5).toString());

                socialFrag.setGalleryData(socialGallery);
            } catch (Exception e) {
                // TODO: handle exception
                Toast.makeText(ProfileGalleryActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            // TODO: handle exception
            Toast.makeText(ProfileGalleryActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
        }

    }

    private void getData() throws JSONException {
        // TODO Auto-generated method stub

        JSONObject jsonobj;
        jsonobj = new JSONObject();
        try {
            // adding some keys
            jsonobj.put("appkey", "spiderman1450@gmail.com");
            jsonobj.put("email", Constants.USER_EMAIL);
            jsonobj.put("session_token", Constants.SESSION_TOKEN);
            jsonobj.put("user_id", Constants.USER_ID);
            new ConnectionTask(ProfileGalleryActivity.this, jsonobj).execute(Constants.API_PROFILE);
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void refreshData() {
        // TODO Auto-generated method stub
        try {
            getData();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void refreshBusinessData() {
        // TODO Auto-generated method stub
        try {
            getData();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
