package com.mingout.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mingout.activities.MainActivity;
import com.mingout.activities.R;
import com.mingout.dialog.ConfirmationDialog;
import com.mingout.models.FacebookDataModel;
import com.mingout.models.ReviewBusinessDataModel;
import com.mingout.models.ReviewSocialDataModel;
import com.mingout.util.ConnectionTask;
import com.mingout.util.Constants;
import com.mingout.util.ResultJSON;
import com.mingout.util.Utilities;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;

@SuppressWarnings("ALL")
public class HomeFragment extends Fragment implements FragmentManager.OnBackStackChangedListener, ResultJSON, ConfirmationDialog.ConfirmationDialogListner {
    DashboardBusinessFragment businessFrag;
    DashboardSocialFragment socialFrag;
    TextView TV_title, TV_home, TV_updateProfile, TV_updatePhotos, TV_contactUs, TV_help, TV_nameDetail;
    ImageView IV_menu, IV_logout, IV_settings, IV_logo;
    ImageView IV_home, IV_updateProfile, IV_updatePhotos, IV_contactUs,	IV_help;
    FacebookDataModel facebookDataObj;
    SharedPreferences mPrefs;
    FragmentManager fm;
    FragmentTransaction ft;
    NavigationView navigationView;
    DrawerLayout mDrawerLayout;
    boolean logoutFlag = false;
    Bundle b;
    Fragment fragment;
    ActionBarDrawerToggle mDrawerToggle;
    View drawerView,drawerContent, mainView;
    Toolbar toolBar;
    private Handler mHandler = new Handler();
    private boolean mShowingBack = false;
    private boolean socialFlipFlag = false;
    private boolean businessFlipFlag = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view = inflater.inflate(
                R.layout.activity_dash_board_layout, null);
        facebookDataObj  =  new FacebookDataModel();

        TV_title = (TextView) view.findViewById(R.id.TV_title);
        TV_home = (TextView) view.findViewById(R.id.TV_home);
        TV_updateProfile = (TextView) view.findViewById(R.id.TV_updateProfile);
        TV_updatePhotos = (TextView) view.findViewById(R.id.TV_updatePhotos);
        TV_contactUs = (TextView) view.findViewById(R.id.TV_contactUs);
        TV_help = (TextView) view.findViewById(R.id.TV_help);
        TV_nameDetail = (TextView) view.findViewById(R.id.TV_nameDetail);


        IV_home = (ImageView) view.findViewById(R.id.IV_profile);
        IV_updateProfile = (ImageView) view.findViewById(R.id.IV_updateProfile);
        IV_updatePhotos = (ImageView) view.findViewById(R.id.IV_updatePhotos);
        IV_contactUs = (ImageView) view.findViewById(R.id.IV_contactUs);
        IV_help = (ImageView) view.findViewById(R.id.IV_help);


        IV_menu = (ImageView) view.findViewById(R.id.IV_menu);
        IV_logout = (ImageView) view.findViewById(R.id.IV_logout);
        IV_settings = (ImageView) view.findViewById(R.id.IV_settings);
        IV_logo = (ImageView) view.findViewById(R.id.IV_logo);

        businessFrag = new DashboardBusinessFragment();
        socialFrag = new DashboardSocialFragment();
        try {
            facebookDataObj = (FacebookDataModel) getActivity().getIntent().getSerializableExtra("facebook data");
            Bundle b = new Bundle();
            b.putSerializable("facebook data", facebookDataObj);
            socialFrag.setArguments(b);
            businessFrag.setArguments(b);
        } catch (Exception e) {}


       if (savedInstanceState == null) {
           getChildFragmentManager().beginTransaction().add(R.id.viewPager, socialFrag).commit();
       } else {
            mShowingBack = (getChildFragmentManager().getBackStackEntryCount() > 0);
        }

        try {
            getData(true);
        } catch (JSONException e1) {e1.printStackTrace();}

        getChildFragmentManager().addOnBackStackChangedListener(this);
        mPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

        return view;
    }


    public void showSocialFragment(){
        if (socialFlipFlag) {
            flipCard();
            businessFlipFlag = true;
            socialFlipFlag = false;
            TV_title.setText(R.string.social_profile);
        }
    }

    public void showBusinessFragment(){
        if (businessFlipFlag) {
            flipCard();
            socialFlipFlag = true;
            businessFlipFlag = false;
            TV_title.setText(R.string.business_profile);
        }
    }

 //   @Override
 //   protected void onActivityResult(int requestCode, int resultCode, Intent data) {
 //       super.onActivityResult(requestCode, resultCode, data);
 //       if(resultCode == RESULT_OK){
 //           socialFrag.receiveScannedData(data);
 //           businessFrag.receiveScannedData(data);
 //       }
 //   }

    @Override
    public void onBackStackChanged() {
        // TODO Auto-generated method stub
        mShowingBack = (getChildFragmentManager().getBackStackEntryCount() > 0);
        getActivity().invalidateOptionsMenu();
    }

    private void flipCard() {
        if (mShowingBack) {
            getChildFragmentManager().popBackStack();
            return;
        }
        mShowingBack = true;

        getChildFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.rotate_left,
                        R.anim.rotate_right, 0, 0)
             .setCustomAnimations(R.animator.card_flip_right_in,
                     R.animator.card_flip_right_out,
                     R.animator.card_flip_left_in,
                     R.animator.card_flip_left_out)
                .replace(R.id.viewPager, businessFrag).addToBackStack(null)
                .commit();

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                getActivity().invalidateOptionsMenu();
            }
        });
    }

    private void getData(boolean progressBarFlag) throws JSONException {
        JSONObject jsonobj;
        jsonobj = new JSONObject();
        try {
            jsonobj.put("appkey", "spiderman1450@gmail.com");
            jsonobj.put("email", Constants.USER_EMAIL);
            jsonobj.put("session_token", Constants.SESSION_TOKEN);
            jsonobj.put("user_id", Constants.USER_ID);
            new ConnectionTask(getActivity(), jsonobj, progressBarFlag)
                    .execute(Constants.API_PROFILE);
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
    }

    /************************************** Async Thread Result ******************************************/

    @Override
    public void UpdateResult(Object obj) {
        b = new Bundle();
        try {
            String string = (String) obj;
            JSONObject jData = new JSONObject(string);
            Log.e("Response :", string);
            if (jData.getString("status_code").equals("1")) {
                JSONObject jResponse = (JSONObject) jData.getJSONObject("response");
                if (logoutFlag != true) {
                    try {
                        JSONObject jBusiness = (JSONObject) jResponse.getJSONObject("business_profile");

                        Constants.PROFILE_ID_BUSINESS = jBusiness.getString("profile_id");

                        ReviewBusinessDataModel businessModel = new ReviewBusinessDataModel();
                        businessModel.setName(jBusiness.getString("full_name"));
                        businessModel.setMyPhrase(jBusiness.getString("my_phrase"));
                        businessModel.setGender(jBusiness.getString("gender"));
                        businessModel.setAge(jBusiness.getString("age"));
                        businessModel.setJobTitle(jBusiness.getString("job_title"));
                        businessModel.setCompany(jBusiness.getString("current_company"));
                        businessModel.setBiography(jBusiness.getString("bio"));
                        businessModel.setDob(jBusiness.getString("dob"));

                        JSONArray array = jBusiness.getJSONArray("profileImages");
                        businessModel.setImageUrl(array.getString(0));

                        if (array.getString(0).equals(Constants.PROFILE_IMAGE_NOT_AVAILABLE)) {
                            Constants.PROFILE_BUSINESS_image = facebookDataObj.getImageUrl();

                            DownloadBitmap load = new DownloadBitmap();
                            load.execute(facebookDataObj.getImageUrl());

                            uploadImage(Constants.PROFILE_ID_BUSINESS, Utilities.imageToBase64(((BitmapDrawable) load.get()).getBitmap()));
                        } else {
                            Constants.PROFILE_BUSINESS_image = array.getString(0);
                        }
                        businessFrag.setAge(Utilities.getAgeFromDOB(jBusiness.getString("dob")));
                        Constants.BUSINESS_AGE = String.valueOf(Utilities.getAgeFromDOB(jBusiness.getString("dob")));
                        Constants.BUSINESS_DOB = jBusiness.getString("dob");
                        b.putSerializable("business review data", (Serializable) businessModel);
                    } catch (Exception e) {
                        Log.e("Business Exception :", e.toString());
                    }

                    try {
                        JSONObject jSocial = (JSONObject) jResponse.getJSONObject("social_profile");

                        Constants.PROFILE_ID_SOCIAL = jSocial.getString("profile_id");

                        ReviewSocialDataModel socialModel = new ReviewSocialDataModel();
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

                        JSONArray array = jSocial.getJSONArray("profileImages");
                        socialModel.setImageUrl(array.getString(0));
                        if (array.getString(0).equals(Constants.PROFILE_IMAGE_NOT_AVAILABLE)) {

                            Constants.PROFILE_SOCIAL_image = facebookDataObj.getImageUrl();
                            DownloadBitmap load = new DownloadBitmap();
                            load.execute(facebookDataObj.getImageUrl());
                            uploadImage(Constants.PROFILE_ID_SOCIAL, Utilities.imageToBase64(((BitmapDrawable) load.get()).getBitmap()));
                        } else {
                            Constants.PROFILE_SOCIAL_image = array.getString(0);
                        }
                        Picasso.with(getActivity()).load(Constants.PROFILE_SOCIAL_image).fit().centerCrop().into(IV_logo);
                        socialFrag.updateImage();
                        socialFrag.setAge(Utilities.getAgeFromDOB(jSocial.getString("dob")));
                        Constants.SOCIAL_AGE = String.valueOf(Utilities.getAgeFromDOB(jSocial.getString("dob")));
                        Constants.SOCIAL_DOB = jSocial.getString("dob");

                        TV_nameDetail.setText(Constants.USER_NAME + " / " + Constants.SOCIAL_AGE);

                        b.putSerializable("socialreview data", (Serializable) socialModel);
                    } catch (Exception e) {
                        Log.e("Social Exception :", e.toString());
                    }
                } else {
                    logoutFlag = false;
                    mPrefs.edit().clear().commit();
                    getActivity().finish();

                    Intent i = new Intent();
                    i.setClass(getActivity(), MainActivity.class);
                    startActivity(i);
                }
            } else {
                Toast.makeText(getActivity(), jData.getString("status_message"), Toast.LENGTH_LONG).show();
            }

        } catch (Exception e) {
            Log.e("Main Exception :", e.toString());
        }
    }

    @Override
    public void confirmationDialogYesButtonPressed() {
        JSONObject jsonobj;
        jsonobj = new JSONObject();
        try {
            logoutFlag = true;
            jsonobj.put("appkey", "spiderman1450@gmail.com");
            jsonobj.put("session_token", Constants.SESSION_TOKEN);
            jsonobj.put("user_id", Constants.USER_ID);
            new ConnectionTask(getActivity(), jsonobj).execute(Constants.API_LOGOUT);
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
    }

    public void callConfirmationDialog() {
        ConfirmationDialog dialog = new ConfirmationDialog(getActivity(), "Are you sure you want to logout?");
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

  //  @Override
  //  protected void onRestart() {
  //      super.onRestart();
  //      try {
  //          getData(false);
  //      } catch (JSONException e) {
  //          e.printStackTrace();
  //      }
  //  }

    public void uploadImage(String profileId, String base64Image) {
        JSONObject jsonobj;
        jsonobj = new JSONObject();
        try {
            jsonobj.put("appkey", "spiderman1450@gmail.com");
            jsonobj.put("profile_id", profileId);
            jsonobj.put("session_token", Constants.SESSION_TOKEN);
            jsonobj.put("user_id", Constants.USER_ID);
            jsonobj.put("pic_id", "");
            jsonobj.put("place", "1");
            jsonobj.put("pic", base64Image);
            new ConnectionTask(getActivity(), jsonobj).execute(Constants.API_PICTURE_UPLOAD);
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
    }

    public class DownloadBitmap extends AsyncTask<String, Void, Drawable> {
        Drawable d;

        protected void onPreExecute() {}

        protected Drawable doInBackground(String... urls) {
            try {
                InputStream is = (InputStream) new URL(urls[0]).getContent();
                d = Drawable.createFromStream(is, "src name");
            } catch (Exception e1) {e1.printStackTrace();}
            return d;
        }

        @Override
        protected void onPostExecute(Drawable result) {
            super.onPostExecute(result);
        }

    }

}
