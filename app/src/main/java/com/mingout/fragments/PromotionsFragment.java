package com.mingout.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mingout.activities.R;
import com.mingout.util.ConnectionTask;
import com.mingout.util.Constants;
import com.mingout.util.ResultJSON;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class PromotionsFragment extends Fragment implements ResultJSON {
	WebView WV_text;
	String upperLink, lowerLink;
	ImageView IV_lowerBanner = null, IV_upperBanner = null;
	TextView TV_RemainingTime;
	LinearLayout LL_timer;
	boolean controlLoadingFlag = true, timmerOneStartFlag = true;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_promotions_layout, null);

		WV_text = (WebView) view.findViewById(R.id.WV_text);
		IV_lowerBanner = (ImageView) view.findViewById(R.id.IV_lowerBanner);
		IV_upperBanner = (ImageView) view.findViewById(R.id.IV_upperBanner);
		LL_timer = (LinearLayout) view.findViewById(R.id.LL_timer);
		TV_RemainingTime = (TextView) view.findViewById(R.id.TV_RemainingTime);

		getData(Constants.QR_CODE, true);
		WV_text.getSettings();
		WV_text.setBackgroundColor(Color.TRANSPARENT);

		if (!Constants.QR_LOGIN_TIMER) {
			LL_timer.setVisibility(LinearLayout.GONE);
		}

		if (Constants.PROMOTIONS_UPPER_BANNER != null) {
			Picasso.with(getActivity())
					.load(Constants.PROMOTIONS_BANNER_URL
							+ Constants.PROMOTIONS_UPPER_BANNER).fit()
					.centerCrop().into(IV_upperBanner);
		}

		if (Constants.PROMOTIONS_LOWER_BANNER != null) {
			Picasso.with(getActivity())
					.load(Constants.PROMOTIONS_BANNER_URL
							+ Constants.PROMOTIONS_LOWER_BANNER).fit()
					.centerCrop().into(IV_lowerBanner);
		}

		IV_upperBanner.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (upperLink != null) {
					Intent browserIntent = new Intent(
							"android.intent.action.VIEW", Uri.parse(upperLink));
					startActivity(browserIntent);
				}
			}
		});
		IV_lowerBanner.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (lowerLink != null) {
					Intent browserIntent = new Intent(
							"android.intent.action.VIEW", Uri.parse(lowerLink));
					startActivity(browserIntent);
				}
			}
		});

        if(Constants.cachePromotionsText != ""){
            try {
                byte[] decoded = Base64.decode(Constants.cachePromotionsText, Base64.DEFAULT);
                String decodedString = new String(decoded, "UTF-8");

                final String mimeType = "text/html";
                final String encoding = "UTF-8";
                String html = decodedString.toString();
                WV_text.loadDataWithBaseURL("", html, mimeType, encoding, "");
            }catch (Exception e){}
        }

		return view;
	}

	private void startCounter() {
		// TODO Auto-generated method stub
		if (Constants.QR_LOGIN_TIMER) {
			new CountDownTimer(10000, 1000) {

				public void onTick(long millisUntilFinished) {
					TV_RemainingTime.setText(millisUntilFinished / 1000 + "");
				}

				public void onFinish() {
					TV_RemainingTime.setText("");
					LL_timer.setVisibility(LinearLayout.GONE);
					Constants.QR_LOGIN = true;
					Constants.QR_LOGIN_TIMER = false;
					timmerOneStartFlag = true;
				}
			}.start();
		} else {
			LL_timer.setVisibility(LinearLayout.GONE);
		}
	}

	private void getData(String qrCode, boolean flag) {
		// TODO Auto-generated method stub

		if (controlLoadingFlag) {
			JSONObject jsonobj;
			jsonobj = new JSONObject();
			try {
				// adding some keys
				jsonobj.put("appkey", "spiderman1450@gmail.com");
				jsonobj.put("session_token", Constants.SESSION_TOKEN);
				jsonobj.put("user_id", Constants.USER_ID);
				jsonobj.put("qr_code", qrCode);
				new ConnectionTask(this, jsonobj, flag)
						.execute(Constants.API_PROMOTIONS);
			} catch (JSONException ex) {
				ex.printStackTrace();
			}
		}
	}

	@Override
	public void UpdateResult(Object obj) {
		// TODO Auto-generated method stub
		try {
			String string = (String) obj;
			JSONObject jData = new JSONObject(string);

			if (jData.getString("status_code").equals("1")) {
				JSONObject jResponse = jData.getJSONObject("response");
				String text = jResponse.getString("text");
				upperLink = jResponse.getString("upper_link");
				lowerLink = jResponse.getString("bottom_link");
				if (Constants.PROMOTIONS_UPPER_BANNER == null) {
					if (!jResponse.getString("upper_banner").equals("")) {
						Constants.PROMOTIONS_UPPER_BANNER = jResponse
								.getString("upper_banner");
						Picasso.with(getActivity())
								.load(Constants.PROMOTIONS_BANNER_URL
										+ Constants.PROMOTIONS_UPPER_BANNER)
								.fit().centerCrop().into(IV_upperBanner);

					}
				} else {
					if (!jResponse.getString("upper_banner").equals("")) {
						if (!Constants.PROMOTIONS_UPPER_BANNER.equals(jResponse
								.getString("upper_banner"))) {
							Constants.QR_LOGIN_TIMER = true;
							LL_timer.setVisibility(LinearLayout.VISIBLE);
							timmerOneStartFlag = true;

							Constants.PROMOTIONS_UPPER_BANNER = jResponse
									.getString("upper_banner");
							Picasso.with(getActivity())
									.load(Constants.PROMOTIONS_BANNER_URL
											+ Constants.PROMOTIONS_UPPER_BANNER)
									.fit().centerCrop().into(IV_upperBanner);
						}
					}
				}
				if (Constants.PROMOTIONS_LOWER_BANNER == null) {
					if (!jResponse.getString("bottom_banner").equals("")) {
						Constants.PROMOTIONS_LOWER_BANNER = jResponse
								.getString("bottom_banner");

						Picasso.with(getActivity())
								.load(Constants.PROMOTIONS_BANNER_URL
										+ Constants.PROMOTIONS_LOWER_BANNER)
								.fit().centerCrop().into(IV_lowerBanner);

					}
				} else {
					if (!jResponse.getString("bottom_banner").equals("")) {
						if (!Constants.PROMOTIONS_LOWER_BANNER.equals(jResponse
								.getString("bottom_banner"))) {
							Constants.QR_LOGIN_TIMER = true;
							LL_timer.setVisibility(LinearLayout.VISIBLE);
							timmerOneStartFlag = true;

							Constants.PROMOTIONS_LOWER_BANNER = jResponse
									.getString("bottom_banner");
							Picasso.with(getActivity())
									.load(Constants.PROMOTIONS_BANNER_URL
											+ Constants.PROMOTIONS_LOWER_BANNER)
									.fit().centerCrop().into(IV_lowerBanner);
						}
					}
				}

                if(!Constants.cachePromotionsText.equals(text.toString())){
                    Constants.cachePromotionsText = text.toString();
                    byte[] decoded = Base64.decode(text.toString(), Base64.DEFAULT);
                    String decodedString = new String(decoded, "UTF-8");

                    final String mimeType = "text/html";
                    final String encoding = "UTF-8";
                    String html = decodedString.toString();
                    WV_text.loadDataWithBaseURL("", html, mimeType, encoding, "");
                    WV_text.setWebViewClient(new WebViewClient() {
                        @Override
                        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                            super.onReceivedError(view, errorCode, description, failingUrl);
                            if (timmerOneStartFlag) {
                                startCounter();
                                timmerOneStartFlag = false;
                            }
                        }

                        @Override
                        public void onPageFinished(WebView view, String url) {super.onPageFinished(view, url);
                            if (timmerOneStartFlag) {
                                startCounter();
                                timmerOneStartFlag = false;
                            }
                        }
                    });
                }
				getData(Constants.QR_CODE, false);

			} else {
				Toast.makeText(getActivity(),
						jData.getString("status_message"), Toast.LENGTH_LONG)
						.show();
			}
		} catch (Exception e) {
			// TODO: handle exception
			Log.e("Exception :", e.toString());
		}
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		controlLoadingFlag = false;
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		controlLoadingFlag = true;
		getData(Constants.QR_CODE, false);
	}

}
