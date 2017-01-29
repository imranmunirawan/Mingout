package com.mingout.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mingout.activities.DashBoardActivity;
import com.mingout.activities.MainActivity;
import com.mingout.activities.MenuSettingsActivity;
import com.mingout.activities.R;
import com.mingout.dialog.ConfirmationDialog;
import com.mingout.models.FacebookDataModel;
import com.mingout.models.ReviewBusinessDataModel;
import com.mingout.models.ReviewSocialDataModel;
import com.mingout.util.ConnectionTask;
import com.mingout.util.ConnectionTaskSupportFragment;
import com.mingout.util.Constants;
import com.mingout.util.ResultJSON;
import com.mingout.util.Utilities;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;

public class MenuFragment extends Fragment implements FragmentManager.OnBackStackChangedListener, ResultJSON, ConfirmationDialog.ConfirmationDialogListner {
	public static TextView TV_title, TV_home, TV_updateProfile, TV_updatePhotos, TV_contactUs, TV_help, TV_nameDetail;
	public static ImageView IV_home, IV_updateProfile, IV_updatePhotos, IV_contactUs, IV_help;
	DashboardBusinessFragment businessFrag;
	DashboardSocialFragment socialFrag;
	String s_name, s_age, b_name, b_age;
	View rootView;
	SharedPreferences mPrefs;
	ImageView IV_menu, IV_logout, IV_settings, IV_logo;

	//  private ListView listView;
	//  private ArrayList<SlidingMenuItem> listMenuItems;
	//  private final static String TAG = "MenuFragment";
	FacebookDataModel fbModel;

	int fragmentFlag, newProfileDetailFlag;
	String selectedApi, profileId;
	boolean logoutFlag = false;
	private ReviewSocialDataModel facebookDataObj;
	private Bundle b;

	public static Fragment newInstance() {
		return new MenuFragment();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

		rootView = inflater.inflate(R.layout.fragment_menu, container, false);
		fbModel = new FacebookDataModel();
		//   listView = (ListView) rootView.findViewById(R.id.list);
		TV_title 				= (TextView) rootView.findViewById(R.id.TV_title);
		TV_home 				= (TextView) rootView.findViewById(R.id.TV_home);
		TV_updateProfile 		= (TextView) rootView.findViewById(R.id.TV_updateProfile);
		TV_updatePhotos 		= (TextView) rootView.findViewById(R.id.TV_updatePhotos);
		TV_contactUs 			= (TextView) rootView.findViewById(R.id.TV_contactUs);
		TV_help 				= (TextView) rootView.findViewById(R.id.TV_help);
		TV_nameDetail 				= (TextView) rootView.findViewById(R.id.TV_nameDetail);

		IV_home = (ImageView) rootView.findViewById(R.id.IV_profile);
		IV_updateProfile = (ImageView) rootView.findViewById(R.id.IV_updateProfile);
		IV_updatePhotos = (ImageView) rootView.findViewById(R.id.IV_updatePhotos);
		IV_contactUs = (ImageView) rootView.findViewById(R.id.IV_contactUs);
		IV_help = (ImageView) rootView.findViewById(R.id.IV_help);


		IV_menu = (ImageView) rootView.findViewById(R.id.IV_menu);
		IV_logout = (ImageView) rootView.findViewById(R.id.IV_logout);
		IV_settings = (ImageView) rootView.findViewById(R.id.IV_settings);
		IV_logo = (ImageView) rootView.findViewById(R.id.IV_logo);
//		mPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());



	//	if(Constants.FRAGMENT_FLAG == Constants.SOCIAL_FRAGMENT_FLAG) {
	//		Picasso.with(getActivity()).load(Constants.PROFILE_SOCIAL_image).fit().centerCrop().into(IV_logo);
	//		TV_nameDetail.setText(Constants.S_Name + " / " + Constants.S_AGE_STR);
	//	}else if(Constants.FRAGMENT_FLAG == Constants.BUSINESS_FRAGMENT_FLAG){
	//		Picasso.with(getActivity()).load(Constants.PROFILE_BUSINESS_image).fit().centerCrop().into(IV_logo);
	//		TV_nameDetail.setText(Constants.B_Name + " / " + Constants.B_AGE_STR);
	//	}
		try {
			getData(true);
		} catch (JSONException e1) {
			e1.printStackTrace();
		}


		TV_home.setOnClickListener(new View.OnClickListener() {
									   @Override
									   public void onClick(View view) {
										   IV_home.setImageResource(R.drawable.dashboard_home_active);
										   IV_updateProfile.setImageResource(R.drawable.update_profile_inactive);
										   IV_updatePhotos.setImageResource(R.drawable.update_photos_inactive);
										   IV_contactUs.setImageResource(R.drawable.callus_inactive);
										   IV_help.setImageResource(R.drawable.help_inactive);

										   TV_home.setTypeface(null, Typeface.BOLD);
										   TV_updateProfile.setTypeface(null, Typeface.NORMAL);
										   TV_updatePhotos.setTypeface(null, Typeface.NORMAL);
										   TV_contactUs.setTypeface(null, Typeface.NORMAL);
										   TV_help.setTypeface(null, Typeface.NORMAL);

										   //	try {
										   //		getData(false);
										   //	} catch (JSONException e1) {
										   //		e1.printStackTrace();
										   //	}

										 //  Intent i = new Intent(getActivity(), DashBoardActivity.class);
										 //  i.putExtra("facebook data", fbModel);
										 //  i.putExtra("Fragment Flag", Constants.FRAGMENT_FLAG);
										 //  startActivity(i);
										 //  getActivity().finish();

										   HomeFragment homeFragment = new HomeFragment();
										   Bundle bundle1 = new Bundle();
										   bundle1.putInt("Fragment Flag", Constants.CURRENT_FRAGMENT_FLAG);
										   homeFragment.setArguments(bundle1);
										   ((DashBoardActivity) getActivity()).transactionFragments(homeFragment,
										           R.id.content);
									   }
								   }
		);


		TV_updateProfile.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				IV_home.setImageResource(R.drawable.dashboard_icon_home);
				IV_updateProfile.setImageResource(R.drawable.dashboard_update_profile_active);
				IV_updatePhotos.setImageResource(R.drawable.update_photos_inactive);
				IV_contactUs.setImageResource(R.drawable.callus_inactive);
				IV_help.setImageResource(R.drawable.help_inactive);

				TV_home.setTypeface(null, Typeface.NORMAL);
				TV_updateProfile.setTypeface(null, Typeface.BOLD);
				TV_updatePhotos.setTypeface(null, Typeface.NORMAL);
				TV_contactUs.setTypeface(null, Typeface.NORMAL);
				TV_help.setTypeface(null, Typeface.NORMAL);
				EditProfileActivity editProfileActivity = new EditProfileActivity();
				Bundle bundle = new Bundle();
				bundle.putInt("Fragment Flag", Constants.FRAGMENT_FLAG);
				editProfileActivity.setArguments(bundle);

				((DashBoardActivity) getActivity()).transactionFragments(editProfileActivity,
						R.id.content);
			}
		});

		TV_updatePhotos.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				IV_home.setImageResource(R.drawable.dashboard_icon_home);
				IV_updateProfile.setImageResource(R.drawable.update_profile_inactive);
				IV_updatePhotos.setImageResource(R.drawable.dashboard_update_photos_active);
				IV_contactUs.setImageResource(R.drawable.callus_inactive);
				IV_help.setImageResource(R.drawable.help_inactive);

				TV_home.setTypeface(null, Typeface.NORMAL);
				TV_updateProfile.setTypeface(null, Typeface.NORMAL);
				TV_updatePhotos.setTypeface(null, Typeface.BOLD);
				TV_contactUs.setTypeface(null, Typeface.NORMAL);
				TV_help.setTypeface(null, Typeface.NORMAL);

				ProfileGalleryFragment profileGallery = new ProfileGalleryFragment();
				Bundle bundle = new Bundle();
				bundle.putInt("Fragment Flag", Constants.CURRENT_FRAGMENT_FLAG);
				profileGallery.setArguments(bundle);

				((DashBoardActivity) getActivity()).transactionFragments(profileGallery,
						R.id.content);
			}
		});

		TV_contactUs.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				IV_home.setImageResource(R.drawable.dashboard_icon_home);
				IV_updateProfile.setImageResource(R.drawable.update_profile_inactive);
				IV_updatePhotos.setImageResource(R.drawable.update_photos_inactive);
				IV_contactUs.setImageResource(R.drawable.dashboard_callus_active);
				IV_help.setImageResource(R.drawable.help_inactive);

				TV_home.setTypeface(null, Typeface.NORMAL);
				TV_updateProfile.setTypeface(null, Typeface.NORMAL);
				TV_updatePhotos.setTypeface(null, Typeface.NORMAL);
				TV_contactUs.setTypeface(null, Typeface.BOLD);
				TV_help.setTypeface(null, Typeface.NORMAL);

				MenuContactUsActivity menuContactUsActivity = new MenuContactUsActivity();

				((DashBoardActivity) getActivity()).transactionFragments(menuContactUsActivity,
						R.id.content);
			}
		});

		TV_help.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				IV_home.setImageResource(R.drawable.dashboard_icon_home);
				IV_updateProfile.setImageResource(R.drawable.update_profile_inactive);
				IV_updatePhotos.setImageResource(R.drawable.update_photos_inactive);
				IV_contactUs.setImageResource(R.drawable.callus_inactive);
				IV_help.setImageResource(R.drawable.dashboard_help_active);

				TV_home.setTypeface(null, Typeface.NORMAL);
				TV_updateProfile.setTypeface(null, Typeface.NORMAL);
				TV_updatePhotos.setTypeface(null, Typeface.NORMAL);
				TV_contactUs.setTypeface(null, Typeface.NORMAL);
				TV_help.setTypeface(null, Typeface.BOLD);
				MenuHelpActivity menuHelpActivity = new MenuHelpActivity();

				((DashBoardActivity) getActivity()).transactionFragments(menuHelpActivity,
						R.id.content);

				// Intent iii = new Intent();
				// iii.setClass(getActivity(), MenuHelpActivity.class);
				// startActivity(iii);
			}
		});

		IV_logout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				callConfirmationDialog();
			}
		});

		IV_settings.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent ii = new Intent();
				ii.setClass(getActivity(), MenuSettingsActivity.class);
				startActivity(ii);
			}
		});
		try {
			getData(true);
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		return rootView;
	}


	private void getData(boolean progressBarFlag) throws JSONException {
		JSONObject jsonobj;
		jsonobj = new JSONObject();
		try {
			jsonobj.put("appkey", "spiderman1450@gmail.com");
			jsonobj.put("email", Constants.USER_EMAIL);
			jsonobj.put("session_token", Constants.SESSION_TOKEN);
			jsonobj.put("user_id", Constants.USER_ID);
			new ConnectionTaskSupportFragment(MenuFragment.this, jsonobj).execute(Constants.API_PROFILE);
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
				JSONObject jResponse = jData.getJSONObject("response");
				if (logoutFlag != true) {
					try {
						JSONObject jBusiness = jResponse.getJSONObject("business_profile");

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

						Constants.B_Name = jBusiness.getString("full_name");
						Constants.B_AGE_STR = jBusiness.getString("age");
						JSONArray array = jBusiness.getJSONArray("profileImages");
						businessModel.setImageUrl(array.getString(0));

						b_name = jBusiness.getString("f_name");
						b_age = jBusiness.getString("age");
						if (array.getString(0).equals(Constants.PROFILE_IMAGE_NOT_AVAILABLE)) {
							Constants.PROFILE_BUSINESS_image = facebookDataObj.getImageUrl();

							DownloadBitmap load = new DownloadBitmap();
							load.execute(facebookDataObj.getImageUrl());

							uploadImage(Constants.PROFILE_ID_BUSINESS, Utilities.imageToBase64(((BitmapDrawable) load.get()).getBitmap()));
						} else {
							Constants.PROFILE_BUSINESS_image = array.getString(0);
						}
						//businessFrag.setAge(Utilities.getAgeFromDOB(jBusiness.getString("dob")));
						//Constants.BUSINESS_AGE = String.valueOf(Utilities.getAgeFromDOB(jBusiness.getString("dob")));
						//Constants.BUSINESS_DOB = jBusiness.getString("dob");
						//b.putSerializable("business review data", (Serializable) businessModel);
						Picasso.with(getActivity()).load(Constants.PROFILE_BUSINESS_image).fit().centerCrop().into(IV_logo);
					} catch (Exception e) {
						Log.e("Business Exception :", e.toString());
					}

					try {
						JSONObject jSocial = jResponse.getJSONObject("social_profile");

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
						Constants.S_Name = jSocial.getString("full_name");
						Constants.S_AGE_STR = jSocial.getString("age");
						JSONArray array = jSocial.getJSONArray("profileImages");
						socialModel.setImageUrl(array.getString(0));

						s_name = jSocial.getString("f_name");
						s_age = jSocial.getString("age");
						if (array.getString(0).equals(Constants.PROFILE_IMAGE_NOT_AVAILABLE)) {

							Constants.PROFILE_SOCIAL_image = facebookDataObj.getImageUrl();
							DownloadBitmap load = new DownloadBitmap();
							load.execute(facebookDataObj.getImageUrl());
							uploadImage(Constants.PROFILE_ID_SOCIAL, Utilities.imageToBase64(((BitmapDrawable) load.get()).getBitmap()));
						} else {
							Constants.PROFILE_SOCIAL_image = array.getString(0);
						}

						Picasso.with(getActivity()).load(Constants.PROFILE_SOCIAL_image).fit().centerCrop().into(IV_logo);
						//socialFrag.updateImage();
						//socialFrag.setAge(Utilities.getAgeFromDOB(jSocial.getString("dob")));
						//Constants.SOCIAL_AGE = String.valueOf(Utilities.getAgeFromDOB(jSocial.getString("dob")));
						//Constants.SOCIAL_DOB = jSocial.getString("dob");

						b.putSerializable("socialreview data", socialModel);
					} catch (Exception e) {
						Log.e("Social Exception :", e.toString());
					}

					if(Constants.FRAGMENT_FLAG == Constants.SOCIAL_FRAGMENT_FLAG) {
						Picasso.with(getActivity()).load(Constants.PROFILE_SOCIAL_image).fit().centerCrop().into(IV_logo);
						TV_nameDetail.setText(s_name + " / " + s_age);
					}else if(Constants.FRAGMENT_FLAG == Constants.BUSINESS_FRAGMENT_FLAG) {
						Picasso.with(getActivity()).load(Constants.PROFILE_BUSINESS_image).fit().centerCrop().into(IV_logo);
						TV_nameDetail.setText(b_name + " / " + b_age);
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

	@Override
	public void onBackStackChanged() {

	}

	public void callConfirmationDialog() {
		ConfirmationDialog dialog = new ConfirmationDialog(getActivity(), "Are you sure you want to logout?");
		dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		dialog.show();
	}



//	@Override
//	protected void onRestart() {
//		super.onRestart();
//		try {
//			getData(false);
//		} catch (JSONException e) {
//			e.printStackTrace();
//		}
//	}

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


