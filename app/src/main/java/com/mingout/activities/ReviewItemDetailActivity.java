package com.mingout.activities;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.mingout.fragments.ReviewBusinessDetailFragment;
import com.mingout.fragments.ReviewSocialDetailFragment;
import com.mingout.models.ReviewBusinessDataModel;
import com.mingout.models.ReviewSocialDataModel;
import com.mingout.util.ConnectionTask;
import com.mingout.util.Constants;
import com.mingout.util.ResultJSON;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ReviewItemDetailActivity extends Activity implements ResultJSON {

	int fragmentFlag, newProfileDetailFlag;
	String selectedApi, profileId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_review_item_detail_layout);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			Window window = getWindow();
			window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			window.setStatusBarColor(Color.rgb(255, 136, 72));
		}

		try {
			fragmentFlag = getIntent().getIntExtra("Fragment Flag", 0);
			newProfileDetailFlag = getIntent().getIntExtra("detail flag", 0);
			profileId = getIntent().getStringExtra("profile id");

		} catch (Exception e) {
			// TODO: handle exception
		}

		try {
			if (newProfileDetailFlag == 0) {
				selectedApi = Constants.API_PROFILE;
				getData(Constants.USER_ID);
			} else {
				selectedApi = Constants.API_USER_PROFILE;
				getData(Constants.USER_ID);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	private void getData(String userId) throws JSONException {
		// TODO Auto-generated method stub

		JSONObject jsonobj;
		jsonobj = new JSONObject();
		try {
			// adding some keys
			jsonobj.put("appkey", "spiderman1450@gmail.com");
			jsonobj.put("session_token", Constants.SESSION_TOKEN);
			jsonobj.put("user_id", userId);
			if (newProfileDetailFlag != 0) {
				jsonobj.put("profile_id", profileId);
			}
			new ConnectionTask(this, jsonobj).execute(selectedApi);
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

			JSONObject jResponse = jData.getJSONObject("response");

			if (newProfileDetailFlag == 0) {
				try {
					JSONObject jBusiness = jResponse
							.getJSONObject("business_profile");

					ReviewBusinessDataModel businessModel = new ReviewBusinessDataModel();
					businessModel.setName(jBusiness.getString("full_name"));
					businessModel.setMyPhrase(jBusiness.getString("my_phrase"));
					businessModel.setGender(jBusiness.getString("gender"));
					businessModel.setAge(jBusiness.getString("age"));
					businessModel.setJobTitle(jBusiness.getString("job_title"));
					businessModel.setCompany(jBusiness
							.getString("current_company"));
					businessModel.setBiography(jBusiness.getString("bio"));
					ArrayList<String> imagesUrls = new ArrayList<String>();
					for (int i = 0; i < jBusiness.getJSONArray("profileImages")
							.length(); i++) {
						if (!jBusiness.getJSONArray("profileImages").get(i)
								.toString()
								.equals(Constants.PROFILE_IMAGE_NOT_AVAILABLE)
								&& !jBusiness.getJSONArray("profileImages")
										.get(i).toString().equals("")) {
							imagesUrls.add(jBusiness
									.getJSONArray("profileImages").get(i)
									.toString());
						}
					}
					businessModel.setImagesUrl(imagesUrls);
					b.putSerializable("review data",
							businessModel);

				} catch (Exception e) {
					// TODO: handle exception
					Log.i("Exception :", e.toString());
				}

				try {
					JSONObject jSocial = jResponse
							.getJSONObject("social_profile");

					ReviewSocialDataModel socialModel = new ReviewSocialDataModel();
					socialModel.setName(jSocial.getString("full_name"));
					socialModel.setMyPhrase(jSocial.getString("my_phrase"));
					socialModel.setGender(jSocial.getString("gender"));
					socialModel.setStatus(jSocial.getString("martial_status"));
					socialModel.setAge(jSocial.getString("age"));
					socialModel.setHeight(jSocial.getString("height"));
					socialModel.setLookingFor(jSocial.getString("looking_for"));
					socialModel.setAboutMe(jSocial.getString("about_me"));
					socialModel
							.setWantToMeet(jSocial.getString("want_to_meet"));
					socialModel.setImageUrl(jSocial
							.getJSONArray("profileImages").get(0).toString());
					ArrayList<String> imagesUrls = new ArrayList<String>();
					for (int i = 0; i < jSocial.getJSONArray("profileImages")
							.length(); i++) {
						if (!jSocial.getJSONArray("profileImages").get(i)
								.toString()
								.equals(Constants.PROFILE_IMAGE_NOT_AVAILABLE)
								&& !jSocial.getJSONArray("profileImages")
										.get(i).toString().equals("")) {
							imagesUrls.add(jSocial
									.getJSONArray("profileImages").get(i)
									.toString());
						}
					}
					socialModel.setImagesUrls(imagesUrls);
					s.putSerializable("review data", socialModel);

				} catch (Exception e) {
					// TODO: handle exception

				}

				FragmentManager fm = getFragmentManager();
				FragmentTransaction ft = fm.beginTransaction();
				switch (fragmentFlag) {
				case Constants.BUSINESS_FRAGMENT_FLAG:
					ReviewBusinessDetailFragment frag = new ReviewBusinessDetailFragment();
					frag.setArguments(b);
					ft.add(R.id.frag_container_review_detail, frag);
					break;
				case Constants.SOCIAL_FRAGMENT_FLAG:
					ReviewSocialDetailFragment fragg = new ReviewSocialDetailFragment();
					fragg.setArguments(s);
					ft.add(R.id.frag_container_review_detail, fragg);
					break;

				}
				ft.commit();
			} else {
				if (jResponse.getString("type").equals("B")) {

					ReviewBusinessDataModel businessModel = new ReviewBusinessDataModel();
					businessModel.setName(jResponse.getString("full_name"));
					businessModel.setMyPhrase(jResponse.getString("my_phrase"));
					businessModel.setGender(jResponse.getString("gender"));
					businessModel.setAge(jResponse.getString("age"));
					businessModel.setJobTitle(jResponse.getString("job_title"));
					businessModel.setCompany(jResponse
							.getString("current_company"));
					businessModel
							.setBiography(jResponse.getString("biography"));
					// businessModel.setImageUrl(jBusiness
					// .getJSONArray("profileImages").get(0).toString());

					// businessModel.setImageUrl(jResponse
					// .getJSONArray("profileImages").get(0).toString());
					ArrayList<String> imagesUrls = new ArrayList<String>();
					for (int i = 0; i < jResponse.getJSONArray("profileImages")
							.length(); i++) {
						if (!jResponse.getJSONArray("profileImages").get(i)
								.toString()
								.equals(Constants.PROFILE_IMAGE_NOT_AVAILABLE)
								&& !jResponse.getJSONArray("profileImages")
										.get(i).toString().equals("")) {
							imagesUrls.add(jResponse
									.getJSONArray("profileImages").get(i)
									.toString());
						}
					}
					businessModel.setImagesUrl(imagesUrls);

					b.putSerializable("review data",
							businessModel);
				} else {

					ReviewSocialDataModel socialModel = new ReviewSocialDataModel();
					socialModel.setName(jResponse.getString("full_name"));
					socialModel.setMyPhrase(jResponse.getString("my_phrase"));
					socialModel.setGender(jResponse.getString("gender"));
					socialModel
							.setStatus(jResponse.getString("marital_status"));
					socialModel.setAge(jResponse.getString("age"));
					socialModel.setHeight(jResponse.getString("height"));
					socialModel.setLookingFor(jResponse
							.getString("looking_for"));
					socialModel.setAboutMe(jResponse.getString("about_me"));
					socialModel
							.setWantToMeet(jResponse.getString("want_to_me"));
					socialModel.setImageUrl(jResponse
							.getJSONArray("profileImages").get(0).toString());

					socialModel.setImageUrl(jResponse
							.getJSONArray("profileImages").get(0).toString());

					ArrayList<String> imagesUrls = new ArrayList<String>();
					for (int i = 0; i < jResponse.getJSONArray("profileImages")
							.length(); i++) {
						if (!jResponse.getJSONArray("profileImages").get(i)
								.toString()
								.equals(Constants.PROFILE_IMAGE_NOT_AVAILABLE)
								&& !jResponse.getJSONArray("profileImages")
										.get(i).toString().equals("")) {
							imagesUrls.add(jResponse
									.getJSONArray("profileImages").get(i)
									.toString());
						}
					}
					socialModel.setImagesUrls(imagesUrls);

					s.putSerializable("review data", socialModel);
				}
			}

			FragmentManager fm = getFragmentManager();
			FragmentTransaction ft = fm.beginTransaction();

			if (jResponse.getString("type").equals("B")) {
				ReviewBusinessDetailFragment frag = new ReviewBusinessDetailFragment();
				frag.setArguments(b);
				ft.add(R.id.frag_container_review_detail, frag);
			} else {
				ReviewSocialDetailFragment fragg = new ReviewSocialDetailFragment();
				fragg.setArguments(s);
				ft.add(R.id.frag_container_review_detail, fragg);
			}
			ft.commit();

		} catch (Exception e) {
			// TODO: handle exception
			Log.e("Exception :", e.toString());
		}

	}
}
