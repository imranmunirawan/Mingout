package com.mingout.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.mingout.adapters.PreviewBillboardListAdapter;
import com.mingout.models.PreviewBillboardModel;
import com.mingout.util.ConnectionTask;
import com.mingout.util.Constants;
import com.mingout.util.ResultJSON;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class BillboardActivity extends Activity implements ResultJSON {
	ListView lv;
	PreviewBillboardListAdapter adapter;
	TextView TV_addBillBoard;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_billboard_layout);

		lv = (ListView) findViewById(R.id.listView1);
		TV_addBillBoard = (TextView) findViewById(R.id.TV_addBillBoard);

		getAddReviewFlagData(Constants.QR_CODE);

		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent i = new Intent();
				i.setClass(BillboardActivity.this, AddBillboardActivity.class);
				i.putExtra("data", Constants.List_billboard_preview.get(arg2));
				i.putExtra("detail flag", 1);
				startActivity(i);
			}
		});

		TV_addBillBoard.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent();
				i.setClass(BillboardActivity.this, AddBillboardActivity.class);
				startActivity(i);
			}
		});
	}

	private void getAddReviewFlagData(String qrCode) {
		// TODO Auto-generated method stub

		JSONObject jsonobj;
		jsonobj = new JSONObject();
		try {
			// adding some keys
			jsonobj.put("appkey", "spiderman1450@gmail.com");
			jsonobj.put("session_token", Constants.SESSION_TOKEN);
			jsonobj.put("user_id", Constants.USER_ID);
			jsonobj.put("qr_code", qrCode);
			new ConnectionTask(this, jsonobj)
					.execute(Constants.API_GET_BILLBOARD);
		} catch (JSONException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void UpdateResult(Object obj) {
		// TODO Auto-generated method stub
		Constants.List_billboard_preview = new ArrayList<PreviewBillboardModel>();
		try {
			String string = (String) obj;
			Log.e("Response :", string);
			JSONObject jData = new JSONObject(string);
			if (jData.getString("status_code").equals("1")) {
				JSONArray jResponse = jData.getJSONArray("response");
				for (int i = 0; i < jResponse.length(); i++) {
					JSONObject jSon = (JSONObject) jResponse.get(i);

					PreviewBillboardModel objj = new PreviewBillboardModel();
					objj.setDesc(jSon.getString("detail"));
					objj.setId(jSon.getString("billboard_id"));
					objj.setName(jSon.getString("subject"));
					objj.setType(jSon.getString("type"));
					objj.setImgUrl(jSon.getString("image"));
					objj.setPrice(jSon.getString("price"));
					objj.setCategory(jSon.getString("category"));

					Constants.List_billboard_preview.add(objj);
				}
				if (Constants.List_billboard_preview.size() != 0
						&& Constants.List_billboard_preview != null) {
					adapter = new PreviewBillboardListAdapter(
							BillboardActivity.this,
							R.layout.item_billboard_preview_layout,
							Constants.List_billboard_preview);
					lv.setAdapter(adapter);
				}

			}
		} catch (Exception e) {
			// TODO: handle exception
			Log.e("Exception :", e.toString());
		}
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		getAddReviewFlagData(Constants.QR_CODE);

	}
}
