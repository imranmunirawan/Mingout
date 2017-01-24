package com.mingout.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;
import com.mingout.models.ProfileModel;
import com.mingout.models.ReviewBusinessDataModel;
import com.mingout.models.ReviewSocialDataModel;

public class GetProfileData {

	Activity context;
	Fragment fragment;

	public GetProfileData(Activity context) {
		this.context = context;
		getProfileData();
	}

	public GetProfileData(Fragment fragment) {
		this.fragment = fragment;
		getFragmentProfileData();
	}

	public void getProfileData() {
		JSONObject jsonobj;
		jsonobj = new JSONObject();
		try {
			// adding some keys
			jsonobj.put("appkey", "spiderman1450@gmail.com");
			jsonobj.put("email", Constants.USER_EMAIL);
			jsonobj.put("session_token", Constants.SESSION_TOKEN);
			jsonobj.put("user_id", Constants.USER_ID);
			new ConnectionTask(context, jsonobj).execute(Constants.API_PROFILE);
		} catch (JSONException ex) {
			ex.printStackTrace();
		}
	}

	public void getFragmentProfileData() {
		JSONObject jsonobj;
		jsonobj = new JSONObject();
		try {
			// adding some keys
			jsonobj.put("appkey", "spiderman1450@gmail.com");
			jsonobj.put("email", Constants.USER_EMAIL);
			jsonobj.put("session_token", Constants.SESSION_TOKEN);
			jsonobj.put("user_id", Constants.USER_ID);
			new ConnectionTask(fragment, jsonobj)
					.execute(Constants.API_PROFILE);
		} catch (JSONException ex) {
			ex.printStackTrace();
		}
	}

	public class ConnectionTask extends AsyncTask<String, String, Object> {

		private Activity context;
		private ProgressDialog progressDialog;
		JSONObject obj;
		Fragment f;

		Boolean waitingDialog = true, fragFlag = false;

		public ConnectionTask(Activity context, JSONObject obj) {
			this.context = context;
			this.obj = obj;
		}

		public ConnectionTask(Fragment frag, JSONObject jsonobj) {
			// TODO Auto-generated constructor stub
			this.obj = jsonobj;
			this.f = frag;
			fragFlag = true;
		}

		public ConnectionTask(Activity context, JSONObject obj,
				Boolean waitingDialog) {
			this.context = context;
			this.obj = obj;
			this.waitingDialog = waitingDialog;
		}

		public ConnectionTask(Fragment frag, JSONObject jsonobj,
				Boolean waitingDialog) {
			// TODO Auto-generated constructor stub
			this.obj = jsonobj;
			this.f = frag;
			fragFlag = true;
			this.waitingDialog = waitingDialog;
		}

		@Override
		protected Object doInBackground(String... url) {

			String response = null;

			HttpClient httpClient = new DefaultHttpClient();
			HttpContext httpContext = new BasicHttpContext();
			HttpPost httpPost = new HttpPost(url[0]);

			try {
				StringEntity se = new StringEntity(obj.toString());

				httpPost.setEntity(se);
				httpPost.setHeader("Accept", "application/json");
				httpPost.setHeader("Content-type", "application/json");

				HttpResponse rrresponse = httpClient.execute(httpPost,
						httpContext);
				HttpEntity entity = rrresponse.getEntity();

				response = EntityUtils.toString(entity);

			} catch (Exception e) {
				e.printStackTrace();
			}

			return response;
		}

		@Override
		protected void onProgressUpdate(String... s) {
			super.onProgressUpdate(s);
			Toast.makeText(context, s[0], Toast.LENGTH_SHORT).show();
		}

		@Override
		protected void onPostExecute(Object object) {
			super.onPostExecute(object);
			try {

				if (fragFlag) {
					if (object != null) {
						UpdateResult(object);
					} else {

					}
				} else {
					if (object != null) {
						UpdateResult(object);
					} else {

					}
				}

			} catch (Exception e) {
				System.out.println(e.toString());
			}

		}

		@Override
		protected void onPreExecute() {
			try {
				if (fragFlag) {
					if (waitingDialog)
						progressDialog = ProgressDialog.show(f.getActivity(),
								"", "Please wait", true);
				} else {
					if (waitingDialog)
						progressDialog = ProgressDialog.show(context, "",
								"Please wait", true);
				}

			} catch (Exception e) {
				System.out.println(e.toString());
			}

		}

	}

	public void UpdateResult(Object obj) {
		// TODO Auto-generated method stub
		Constants.PROFILE_DATA = new ProfileModel();
		try {
			String string = (String) obj;
			JSONObject jData = new JSONObject(string);
			if (jData.getString("status_code").equals("1")) {
				JSONObject jResponse = (JSONObject) jData
						.getJSONObject("response");

				try {
					JSONObject jBusiness = (JSONObject) jResponse
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

					Constants.PROFILE_DATA.setBusinessModel(businessModel);

				} catch (Exception e) {
					// TODO: handle exception

				}

				try {
					JSONObject jSocial = (JSONObject) jResponse
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

					Constants.PROFILE_DATA.setSocialModel(socialModel);

				} catch (Exception e) {
					// TODO: handle exception

				}
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}