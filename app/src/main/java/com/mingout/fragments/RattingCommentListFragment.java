package com.mingout.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.mingout.activities.R;
import com.mingout.adapters.PreviewRattingCommentListAdapter;
import com.mingout.models.PreviewRattingCommentModel;
import com.mingout.util.ConnectionTask;
import com.mingout.util.Constants;
import com.mingout.util.ResultJSON;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RattingCommentListFragment extends Fragment implements ResultJSON {

	ListView lv;
	PreviewRattingCommentListAdapter adatper;
	ImageView IV_logo;
	String reviewId;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(
				R.layout.fragment_ratting_comment_list_layout, null);
		lv = (ListView) view.findViewById(R.id.listView1);

		try {
			reviewId = getArguments().getString("review id");

		} catch (Exception e) {
			// TODO: handle exception
		}

		getData(reviewId);
		return view;
	}

	private void getData(String reviewId) {
		// TODO Auto-generated method stub

		JSONObject jsonobj;
		jsonobj = new JSONObject();
		try {
			// adding some keys
			jsonobj.put("appkey", "spiderman1450@gmail.com");
			jsonobj.put("session_token", Constants.SESSION_TOKEN);
			jsonobj.put("user_id", Constants.USER_ID);
			jsonobj.put("review_id", reviewId);
			new ConnectionTask(this, jsonobj)
					.execute(Constants.API_SHOW_REVIEW_COMMENTS);
		} catch (JSONException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void UpdateResult(Object obj) {
		// TODO Auto-generated method stub
		Constants.List_ratting_comment = new ArrayList<PreviewRattingCommentModel>();
		try {
			String string = (String) obj;
			JSONObject jData = new JSONObject(string);
			if (jData.getString("status_code").equals("1")) {
				JSONArray jResponse = jData.getJSONArray("response");
				for (int i = 0; i < jResponse.length(); i++) {
					JSONObject jSon = (JSONObject) jResponse.get(i);

					PreviewRattingCommentModel objj = new PreviewRattingCommentModel();
					objj.setName(jSon.getString("f_name") + " "
							+ jSon.getString("last_name"));
					objj.setImageUrl(jSon.getString("picture"));
					objj.setComment(jSon.getString("comments"));
					objj.setId(jSon.getString("pid"));

					Constants.List_ratting_comment.add(objj);
				}
				if (Constants.List_ratting_comment.size() != 0
						&& Constants.List_ratting_comment != null) {

					adatper = new PreviewRattingCommentListAdapter(
							getActivity(),
							R.layout.item_ratting_comment_layout,
							Constants.List_ratting_comment);
					lv.setAdapter(adatper);
				}

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

	public void updateData() {
		// TODO Auto-generated method stub
		getData(reviewId);
	}

}
