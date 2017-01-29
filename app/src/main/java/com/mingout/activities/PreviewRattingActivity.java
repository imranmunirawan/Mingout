package com.mingout.activities;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mingout.fragments.RattingCommentListFragment;
import com.mingout.util.ConnectionTask;
import com.mingout.util.Constants;
import com.mingout.util.ResultJSON;

import org.json.JSONException;
import org.json.JSONObject;

public class PreviewRattingActivity extends Activity implements ResultJSON {

	int addRattingFlag;
	float rattingValue;
	String reviewId;
	Button B_addComment;
	EditText ET_comment1, ET_comment2, ET_comment3;
	TextView TV_comment2, TV_comment1, TV_title, TV_save, TV_review1,
			TV_review2;
	RattingCommentListFragment frag;
	RatingBar ratingBar1;
	Fragment PreviewListFragment;
	boolean reviewListFlag = false, newRattingFlag = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		setContentView(R.layout.activity_preview_ratting_layout);

		B_addComment = (Button) findViewById(R.id.B_addComment);

		ET_comment1 = (EditText) findViewById(R.id.ET_comment1);
		ET_comment2 = (EditText) findViewById(R.id.ET_comment2);
		ET_comment3 = (EditText) findViewById(R.id.ET_comment3);

		TV_comment1 = (TextView) findViewById(R.id.TV_comment1);
		TV_comment2 = (TextView) findViewById(R.id.TV_comment2);
		TV_title = (TextView) findViewById(R.id.TV_title);
		TV_save = (TextView) findViewById(R.id.TV_save);
		TV_review1 = (TextView) findViewById(R.id.TV_review1);
		TV_review2 = (TextView) findViewById(R.id.TV_review2);

		ratingBar1 = (RatingBar) findViewById(R.id.ratingBar1);

		try {
			reviewId = getIntent().getStringExtra("review id");
			rattingValue = getIntent().getFloatExtra("review ratting", 0);
			reviewListFlag = getIntent().getBooleanExtra("review list flag",
					false);

			PreviewListFragment = (Fragment) getIntent().getSerializableExtra(
					"fragment object");

			// addRattingFlag = getIntent().getIntExtra("add ratting flag", 0);
		} catch (Exception e) {
			// TODO: handle exception
		}

		if (reviewListFlag) {
			try {
				ratingBar1.setRating(rattingValue);
				TV_save.setVisibility(TextView.INVISIBLE);

				TV_review1.setText(getIntent().getStringExtra("review label1"));
				TV_review2.setText(getIntent().getStringExtra("review label2"));

				TV_comment1.setText(getIntent().getStringExtra(
						"review comment1"));
				TV_comment2.setText(getIntent().getStringExtra(
						"review comment2"));

				TV_review1.setTextColor(Color.rgb(0, 0, 0));
				TV_review2.setTextColor(Color.rgb(0, 0, 0));

				Bundle b = new Bundle();
				b.putString("review id", reviewId);

				FragmentManager fm = getFragmentManager();
				FragmentTransaction ft = fm.beginTransaction();

				frag = new RattingCommentListFragment();
				frag.setArguments(b);
				ft.add(R.id.frag_container_rattingCommentList, frag);

				ft.commit();

				ET_comment1.setVisibility(EditText.INVISIBLE);
				ET_comment2.setVisibility(EditText.INVISIBLE);
				TV_comment1.setVisibility(TextView.VISIBLE);
				TV_comment2.setVisibility(TextView.VISIBLE);
				ET_comment3.setVisibility(EditText.VISIBLE);
				ratingBar1.setEnabled(false);
				TV_title.setText(getResources()
						.getString(R.string.comment_here));
			} catch (Exception e) {
				// TODO: handle exception
			}
		} else {
			B_addComment.setVisibility(Button.INVISIBLE);
		}

		B_addComment.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (!TextUtils.isEmpty(ET_comment3.getText())) {
					try {
						setCommentData(ET_comment3.getText().toString());
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		TV_save.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (!TextUtils.isEmpty(ET_comment1.getText())
						&& !TextUtils.isEmpty(ET_comment2.getText())) {
					setNewReviewData();
				}
			}
		});
	}

	protected void setNewReviewData() {
		// TODO Auto-generated method stub

		JSONObject jsonobj;
		jsonobj = new JSONObject();
		try {
			// adding some keys
			newRattingFlag = true;
			jsonobj.put("appkey", "spiderman1450@gmail.com");
			jsonobj.put("session_token", Constants.SESSION_TOKEN);
			jsonobj.put("user_id", Constants.USER_ID);
			jsonobj.put("review1", ET_comment1.getText().toString());
			jsonobj.put("review2", ET_comment2.getText().toString());
			jsonobj.put("rating", String.valueOf(ratingBar1.getRating()));
			jsonobj.put("profile_id", Constants.QR_LOGIN_PROFILE_ID);
			jsonobj.put("qr_code", Constants.QR_CODE);
			new ConnectionTask(this, jsonobj)
					.execute(Constants.API_SAVE_REVIEW);
		} catch (JSONException ex) {
			ex.printStackTrace();
		}

	}

	private void setCommentData(String comment) throws JSONException {
		// TODO Auto-generated method stub

		JSONObject jsonobj;
		jsonobj = new JSONObject();
		try {
			// adding some keys

			jsonobj.put("appkey", "spiderman1450@gmail.com");
			jsonobj.put("session_token", Constants.SESSION_TOKEN);
			jsonobj.put("user_id", Constants.USER_ID);
			jsonobj.put("review_id", reviewId);
			jsonobj.put("profile_id", Constants.PROFILE_ID_BUSINESS);
			jsonobj.put("comment", comment);
			new ConnectionTask(this, jsonobj)
					.execute(Constants.API_SAVE_REVIEW_COMMENTS);
		} catch (JSONException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void UpdateResult(Object obj) {
		// TODO Auto-generated method stub
		try {
			String string = (String) obj;
			Log.e("Response :", string);
			JSONObject jData = new JSONObject(string);
			if (!newRattingFlag) {
				if (jData.getString("status_code").equals("1")) {
					Toast.makeText(PreviewRattingActivity.this,
							"Posted successfully!", Toast.LENGTH_LONG).show();
					frag.updateData();
					ET_comment3.setText("");

				} else {
					Toast.makeText(PreviewRattingActivity.this,
							jData.getString("status_message"),
							Toast.LENGTH_LONG).show();
				}
			} else {
				newRattingFlag = false;
				if (jData.getString("status_code").equals("1")) {
					Toast.makeText(PreviewRattingActivity.this,
							"Ratting Posted successfully!", Toast.LENGTH_LONG)
							.show();
					finish();

				} else {
					Toast.makeText(PreviewRattingActivity.this,
							jData.getString("status_message"),
							Toast.LENGTH_LONG).show();
				}
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public interface UpdateReviewListListner {
		void updateReviewList();
	}

}
