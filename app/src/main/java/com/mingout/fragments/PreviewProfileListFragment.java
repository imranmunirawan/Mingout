package com.mingout.fragments;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mingout.activities.PreviewRattingActivity;
import com.mingout.activities.R;
import com.mingout.adapters.PreviewProfileListAdapter;
import com.mingout.dialog.MyAlertDialog;
import com.mingout.models.PreviewProfileModel;
import com.mingout.util.ConnectionTask;
import com.mingout.util.Constants;
import com.mingout.util.ResultJSON;

public class PreviewProfileListFragment extends Fragment implements ResultJSON {
	ListView lv;
	PreviewProfileListAdapter adapter;
	boolean addReviewFlag = false;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(
				R.layout.fragment_preview_profile_list_layout, null);

		lv = (ListView) view.findViewById(R.id.listView1);

        if (Constants.List_profile_preview != null) {
            adapter = new PreviewProfileListAdapter(getActivity(), R.layout.item_profile_preview_layout, Constants.List_profile_preview);
            lv.setAdapter(adapter);
		}else{
            getData(Constants.QR_CODE, true);
        }

		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub

				Intent i = new Intent();
				i.setClass(getActivity(), PreviewRattingActivity.class);
				i.putExtra("review id", Constants.List_profile_preview
						.get(arg2).getId());
				i.putExtra("review ratting", Float
						.valueOf(Constants.List_profile_preview.get(arg2)
								.getRating()));
				i.putExtra("review label1",
						Constants.List_profile_preview.get(arg2).getLabel1());
				i.putExtra("review label2",
						Constants.List_profile_preview.get(arg2).getLabel2());
				i.putExtra("review comment1", Constants.List_profile_preview
						.get(arg2).getReview1());
				i.putExtra("review comment2", Constants.List_profile_preview
						.get(arg2).getReview2());
				i.putExtra("review list flag", true);
				startActivity(i);
			}
		});

		Constants.PreviewAddProfileComment.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				getAddReviewFlagData(Constants.QR_CODE);
			}
		});
		return view;
	}

	private void getData(String qrCode, boolean flag) {
		// TODO Auto-generated method stub

		JSONObject jsonobj;
		jsonobj = new JSONObject();
		try {
			// adding some keys
			jsonobj.put("appkey", "spiderman1450@gmail.com");
			jsonobj.put("session_token", Constants.SESSION_TOKEN);
			jsonobj.put("user_id", Constants.USER_ID);
			jsonobj.put("qr_code", qrCode);
			new ConnectionTask(this, jsonobj, flag)
					.execute(Constants.API_SHOW_REVIEW_LIST);
		} catch (JSONException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void UpdateResult(Object obj) {
		// TODO Auto-generated method stub
		try {
			String string = (String) obj;
			JSONObject jData = new JSONObject(string);
			if (!addReviewFlag) {
				Constants.List_profile_preview = new ArrayList<PreviewProfileModel>();
				if (jData.getString("status_code").equals("1")) {
					JSONArray jResponse = jData.getJSONArray("response");
					for (int i = 0; i < jResponse.length(); i++) {
						JSONObject jSon = (JSONObject) jResponse.get(i);

						PreviewProfileModel objj = new PreviewProfileModel();
						objj.setFirst_name(jSon.getString("first_name"));
						objj.setLast_name(jSon.getString("last_name"));
						objj.setUser_id(jSon.getString("user_id"));
						objj.setType(jSon.getString("type"));
						objj.setReview1(jSon.getString("review1"));
						objj.setReview2(jSon.getString("review2"));
						objj.setLabel1(jSon.getString("label1"));
						objj.setLabel2(jSon.getString("label2"));
						objj.setProfileId(jSon.getString("pid"));
						objj.setId(jSon.getString("id"));
						objj.setRating(jSon.getString("rating"));
						objj.setOriginal_picture(jSon.getString("original_picture"));
						objj.setThumb_picture(jSon.getString("thumb_picture"));

						Constants.List_profile_preview.add(objj);
					}
					if (Constants.List_profile_preview.size() != 0 && Constants.List_profile_preview != null) {
						adapter = new PreviewProfileListAdapter(getActivity(), R.layout.item_profile_preview_layout, Constants.List_profile_preview);
						lv.setAdapter(adapter);
					}
				} else {
					Toast.makeText(getActivity(),
							jData.getString("status_message"),
							Toast.LENGTH_LONG).show();
				}
			} else {
				if (jData.getString("status_code").equals("1")) {
					Intent i = new Intent();
					i.setClass(getActivity(), PreviewRattingActivity.class);
					startActivity(i);
				} else {
					MyAlertDialog dialog = new MyAlertDialog(getActivity(),
							"One review can be add in one day!");
					dialog.getWindow().setBackgroundDrawable(
							new ColorDrawable(Color.TRANSPARENT));
					dialog.show();
				}
				addReviewFlag = false;
			}
		} catch (Exception e) {
			// TODO: handle exception
			Log.e("Exception :", e.toString());
		}
	}

	private void getAddReviewFlagData(String qrCode) {
		// TODO Auto-generated method stub

		JSONObject jsonobj;
		jsonobj = new JSONObject();
		try {
			// adding some keys
			addReviewFlag = true;
			jsonobj.put("appkey", "spiderman1450@gmail.com");
			jsonobj.put("session_token", Constants.SESSION_TOKEN);
			jsonobj.put("user_id", Constants.USER_ID);
			jsonobj.put("qr_code", qrCode);
			new ConnectionTask(this, jsonobj)
					.execute(Constants.API_ADD_REVIEW_FLAG);
		} catch (JSONException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.e("Methord:", "onResume");
		getData(Constants.QR_CODE, false);
	}

}
