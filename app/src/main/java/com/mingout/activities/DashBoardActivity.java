package com.mingout.activities;

import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
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
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.mingout.dialog.ConfirmationDialog;
import com.mingout.dialog.ConfirmationDialog.ConfirmationDialogListner;
import com.mingout.dialog.OptionsDialog;
import com.mingout.fragments.DashboardBusinessFragment;
import com.mingout.fragments.DashboardSocialFragment;
import com.mingout.fragments.EditProfileActivity;
import com.mingout.fragments.HomeFragment;
import com.mingout.fragments.MenuContactUsActivity;
import com.mingout.fragments.MenuFragment;
import com.mingout.fragments.MenuHelpActivity;
import com.mingout.fragments.ProfileGalleryFragment;
import com.mingout.models.FacebookDataModel;
import com.mingout.models.ReviewBusinessDataModel;
import com.mingout.models.ReviewSocialDataModel;
import com.mingout.util.ConnectionTask;
import com.mingout.util.Constants;
import com.mingout.util.ResultJSON;
import com.mingout.util.Utilities;
import com.squareup.picasso.Picasso;

import static com.mingout.activities.R.id.drawerLayout;

public class DashBoardActivity extends SlidingFragmentActivity implements FragmentManager.OnBackStackChangedListener, ResultJSON, ConfirmationDialogListner {
	DashboardBusinessFragment businessFrag;
	DashboardSocialFragment socialFrag;
	TextView TV_title, TV_home, TV_updateProfile, TV_updatePhotos, TV_contactUs, TV_help, TV_nameDetail;
	ImageView IV_menu, IV_logout, IV_settings, IV_logo;
	ImageView IV_home, IV_updateProfile, IV_updatePhotos, IV_contactUs, IV_help;
	FacebookDataModel facebookDataObj;
	SharedPreferences mPrefs;
	FragmentManager fm;
	FragmentTransaction ft;
	//	NavigationView navigationView;
	DrawerLayout mDrawerLayout;
	private Handler mHandler = new Handler();
	private boolean mShowingBack = false;
	private boolean socialFlipFlag = false;
	private boolean businessFlipFlag = true;
	boolean logoutFlag = false;
	Bundle b;
	SlidingMenu sm;
	Fragment fragment;
	ActionBarDrawerToggle mDrawerToggle;
	View drawerView, drawerContent, mainView;
	Toolbar toolBar;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dash_board_layout);

		//drawerContent = findViewById(R.id.nav_view_dashboard);
		//mainView = findViewById(R.id.main_view_dashboard);

		//toolBar = (Toolbar) findViewById(R.id.toolbar);
		//setSupportActionBar(toolBar);
//
		//toolBar.setNavigationIcon(R.drawable.dashboard_menu_d);
		//toolBar.setNavigationOnClickListener(new View.OnClickListener() {
		//	@Override
		//	public void onClick(View v) {
		//
		//	}
		//});

		sm = getSlidingMenu();
		//sm.setShadowWidthRes(R.dimen.shadow_width);
		sm.setShadowDrawable(R.drawable.shadow);
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		sm.setFadeDegree(0.35f);
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		TV_title = (TextView) findViewById(R.id.TV_title);
		TV_home = (TextView) findViewById(R.id.TV_home);
		TV_updateProfile = (TextView) findViewById(R.id.TV_updateProfile);
		TV_updatePhotos = (TextView) findViewById(R.id.TV_updatePhotos);
		TV_contactUs = (TextView) findViewById(R.id.TV_contactUs);
		TV_help = (TextView) findViewById(R.id.TV_help);
		TV_nameDetail = (TextView) findViewById(R.id.TV_nameDetail);


		IV_home = (ImageView) findViewById(R.id.IV_profile);
		IV_updateProfile = (ImageView) findViewById(R.id.IV_updateProfile);
		IV_updatePhotos = (ImageView) findViewById(R.id.IV_updatePhotos);
		IV_contactUs = (ImageView) findViewById(R.id.IV_contactUs);
		IV_help = (ImageView) findViewById(R.id.IV_help);


		IV_menu = (ImageView) findViewById(R.id.IV_menu);
		IV_logout = (ImageView) findViewById(R.id.IV_logout);
		IV_settings = (ImageView) findViewById(R.id.IV_settings);
		IV_logo = (ImageView) findViewById(R.id.IV_logo);

		//navigationView = (NavigationView) findViewById(R.id.NV_container);
	//	mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
		IV_menu.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				mainView.setTranslationX(-1000f);
			}
		});

	  //  mDrawerLayout.addDrawerListener(mDrawerToggle);

		// This should be called before `syncState()` on your toggle
		//	mDrawerLayout.closeDrawer(navigationView);
		//	drawerContent.setX(navigationView.getWidth());

		businessFrag = new DashboardBusinessFragment();
		socialFrag = new DashboardSocialFragment();

		try
		{
			getData(true);
		} catch (JSONException e1)
		{
			e1.printStackTrace();
		}

		if (savedInstanceState == null)
		{
			getSupportFragmentManager().beginTransaction().add(R.id.viewPager, socialFrag).commit();
		}
		else
		{
			mShowingBack = (getFragmentManager().getBackStackEntryCount() > 0);
		}

		getSupportFragmentManager().addOnBackStackChangedListener(this);
		mPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		fm = getSupportFragmentManager();
		ft = fm.beginTransaction();

		try {
			facebookDataObj = (FacebookDataModel) getIntent().getSerializableExtra("facebook data");
			Bundle b = new Bundle();
			b.putSerializable("facebook data", facebookDataObj);
			socialFrag.setArguments(b);
			businessFrag.setArguments(b);
		} catch (Exception e) {
		}

		IV_menu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
					mainView.setTranslationX(-1000f);

					mDrawerLayout.closeDrawers();
				} else {
					mDrawerLayout.openDrawer(Gravity.LEFT);
				}
			}
		});

/*		TV_home.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				IV_home.setImageResource(R.drawable.dashboard_home_active);
				IV_updateProfile.setImageResource(R.drawable.update_profile_inactive);
				IV_updatePhotos.setImageResource(R.drawable.update_photos_inactive);
				IV_contactUs.setImageResource(R.drawable.callus_inactive);
				IV_help.setImageResource(R.drawable.help_inactive);

				mDrawerLayout.closeDrawers();
			}
		});


		TV_updateProfile.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				IV_home.setImageResource(R.drawable.dashboard_icon_home);
				IV_updateProfile.setImageResource(R.drawable.update_profile_inactive);
				IV_updatePhotos.setImageResource(R.drawable.update_photos_inactive);
				IV_contactUs.setImageResource(R.drawable.callus_inactive);
				IV_help.setImageResource(R.drawable.help_inactive);

				EditProfileActivity editProfileActivity = new EditProfileActivity();
				Bundle bundle = new Bundle();
				bundle.putInt("Fragment Flag", Constants.CURRENT_FRAGMENT_FLAG);
				editProfileActivity.setArguments(bundle);
				ft.replace(R.id.viewPager, editProfileActivity);
				ft.commit();
				mDrawerLayout.closeDrawers();
			}
		});

		TV_updatePhotos.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				IV_home.setImageResource(R.drawable.dashboard_icon_home);
				IV_updateProfile.setImageResource(R.drawable.dashboard_icon_update_profile);
				IV_updatePhotos.setImageResource(R.drawable.dashboard_update_photos_active);
				IV_contactUs.setImageResource(R.drawable.callus_inactive);
				IV_help.setImageResource(R.drawable.help_inactive);
				mDrawerLayout.closeDrawers();
			}
		});

		TV_contactUs.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				IV_home.setImageResource(R.drawable.dashboard_icon_home);
				IV_updateProfile.setImageResource(R.drawable.dashboard_icon_update_profile);
				IV_updatePhotos.setImageResource(R.drawable.update_photos_inactive);
				IV_contactUs.setImageResource(R.drawable.dashboard_callus_active);
				IV_help.setImageResource(R.drawable.help_inactive);

				MenuContactUsActivity menuContactUsActivity = new MenuContactUsActivity();
				ft.replace(R.id.viewPager, menuContactUsActivity);
				ft.commit();
				// Intent i = new Intent();
				// i.setClass(DashBoardActivity.this, MenuContactUsActivity.class);
				// startActivity(i);
				mDrawerLayout.closeDrawers();
			}
		});

		TV_help.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				IV_home.setImageResource(R.drawable.dashboard_icon_home);
				IV_updateProfile.setImageResource(R.drawable.dashboard_icon_update_profile);
				IV_updatePhotos.setImageResource(R.drawable.update_photos_inactive);
				IV_contactUs.setImageResource(R.drawable.callus_inactive);
				IV_help.setImageResource(R.drawable.dashboard_help_active);

				MenuHelpActivity menuHelpActivity = new MenuHelpActivity();
				ft.replace(R.id.viewPager, menuHelpActivity);
				ft.commit();
				// Intent iii = new Intent();
				// iii.setClass(DashBoardActivity.this, MenuHelpActivity.class);
				// startActivity(iii);
				mDrawerLayout.closeDrawers();
			}
		});

		IV_logout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				callConfirmationDialog();
				mDrawerLayout.closeDrawers();
			}
		});

		IV_settings.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent ii = new Intent();
				ii.setClass(DashBoardActivity.this, MenuSettingsActivity.class);
				startActivity(ii);
				mDrawerLayout.closeDrawers();
			}
		});   */

		setBehindView();
	}


	public void showSocialFragment() {
		if (socialFlipFlag) {
			flipCard();
			businessFlipFlag = true;
			socialFlipFlag = false;
			TV_title.setText(R.string.social_profile);
		}
	}


	public void showBusinessFragment() {
		if (businessFlipFlag) {
			flipCard();
			socialFlipFlag = true;
			businessFlipFlag = false;
			TV_title.setText(R.string.business_profile);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			socialFrag.receiveScannedData(data);
			businessFrag.receiveScannedData(data);
		}
	}

	@Override
	public void onBackStackChanged() {
		// TODO Auto-generated method stub
		mShowingBack = (getFragmentManager().getBackStackEntryCount() > 0);
		invalidateOptionsMenu();
	}

	private void flipCard() {
		if (mShowingBack) {
			getFragmentManager().popBackStack();
			return;
		}
		mShowingBack = true;

		getFragmentManager()
				.beginTransaction()
				.setCustomAnimations(R.animator.card_flip_right_in,
						R.animator.card_flip_right_out,
						R.animator.card_flip_left_in,
						R.animator.card_flip_left_out)
				//.replace(R.id.viewPager, businessFrag).addToBackStack(null)
				.commit();

		mHandler.post(new Runnable() {
			@Override
			public void run() {
				invalidateOptionsMenu();
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
			new ConnectionTask(this, jsonobj, progressBarFlag)
					.execute(Constants.API_PROFILE);
		} catch (JSONException ex) {
			ex.printStackTrace();
		}
	}

	/**************************************
	 * Async Thread Result
	 ******************************************/

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
						Picasso.with(DashBoardActivity.this).load(Constants.PROFILE_SOCIAL_image).fit().centerCrop().into(IV_logo);
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
					finish();

					Intent i = new Intent();
					i.setClass(DashBoardActivity.this, MainActivity.class);
					startActivity(i);
				}
			} else {
				Toast.makeText(DashBoardActivity.this, jData.getString("status_message"), Toast.LENGTH_LONG).show();
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
			new ConnectionTask(this, jsonobj).execute(Constants.API_LOGOUT);
		} catch (JSONException ex) {
			ex.printStackTrace();
		}
	}

	public void callConfirmationDialog() {
		ConfirmationDialog dialog = new ConfirmationDialog(this, "Are you sure you want to logout?");
		dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		dialog.show();
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		try {
			getData(false);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public class DownloadBitmap extends AsyncTask<String, Void, Drawable> {
		Drawable d;

		protected void onPreExecute() {
		}

		protected Drawable doInBackground(String... urls) {
			try {
				InputStream is = (InputStream) new URL(urls[0]).getContent();
				d = Drawable.createFromStream(is, "src name");
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			return d;
		}

		@Override
		protected void onPostExecute(Drawable result) {
			super.onPostExecute(result);
		}

	}

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
			new ConnectionTask(DashBoardActivity.this, jsonobj).execute(Constants.API_PICTURE_UPLOAD);
		} catch (JSONException ex) {
			ex.printStackTrace();
		}
	}

//-----------------------------------------------------------------------------------------------------------------------


	private void setBehindView() {
		setBehindContentView(R.layout.menu_slide);
		transactionFragments(MenuFragment.newInstance(), R.id.menu_slide);

	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
			//	showSocialFragment();
			//	showBusinessFragment();
			//	onRestart();
			//	recreate();
				// HomeFragment homeFragment = new HomeFragment();
				// Bundle bundle1 = new Bundle();
				// bundle1.putInt("Fragment Flag", Constants.CURRENT_FRAGMENT_FLAG);
				// homeFragment.setArguments(bundle1);
				// transactionFragments(homeFragment,
				// 		R.id.content);

				return true;

		}
		return super.onOptionsItemSelected(item);
	}
	public void transactionFragments(Fragment fragment, int viewResource) {
		android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
		android.support.v4.app.FragmentTransaction ft = fm.beginTransaction();
		ft.replace(viewResource, fragment);
		//ft.detach(fragment);
		//ft.attach(fragment);
		ft.addToBackStack(null);
		ft.commit();
		toggle();
	}

	@Override
	protected void onResume() {
		super.onResume();
		setBehindView();
	}
}
