package com.mingout.adapters;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.mingout.activities.R;
import com.mingout.activities.ReviewItemDetailActivity;
import com.mingout.models.PreviewProfileModel;
import com.mingout.util.Constants;
import com.mingout.util.RoundedImageView;
import com.squareup.picasso.Picasso;

public class PreviewProfileListAdapter extends
		ArrayAdapter<PreviewProfileModel> {

	Activity context;
	List<PreviewProfileModel> productList;
	int LayoutId;

	public PreviewProfileListAdapter(Activity contex, int layoutId,
			List<PreviewProfileModel> vIEW_Productss_LIST) {
		super(contex, layoutId, vIEW_Productss_LIST);
		this.context = contex;
		this.productList = vIEW_Productss_LIST;
		this.LayoutId = layoutId;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return productList.size();
	}

	@Override
	public View getView(final int position, View row, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(LayoutId, parent, false);
		}
		PreviewProfileModel objj = productList.get(position);

		TextView TV_name = (TextView) row.findViewById(R.id.TV_name);
		TextView TV_label1 = (TextView) row.findViewById(R.id.TV_label1);
		TextView TV_label2 = (TextView) row.findViewById(R.id.TV_label2);
		TextView TV_review1 = (TextView) row.findViewById(R.id.TV_review1);
		TextView TV_review2 = (TextView) row.findViewById(R.id.TV_review2);
		TextView TV_type = (TextView) row.findViewById(R.id.TV_type);

        RoundedImageView IV_logo = (RoundedImageView) row.findViewById(R.id.IV_logo);

        LinearLayout LL_type = (LinearLayout) row.findViewById(R.id.LL_type);
        LinearLayout LL_mainBg = (LinearLayout) row.findViewById(R.id.LL_mainBg);

		RatingBar ratingBar1 = (RatingBar) row.findViewById(R.id.ratingBar1);

		TV_name.setText(objj.getFirst_name() + " " + objj.getLast_name());
		TV_label1.setText(objj.getLabel1());
		TV_label2.setText(objj.getLabel2());
		TV_review1.setText(objj.getReview1());
		TV_review2.setText(objj.getReview2());

        LL_type.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent();
				i.setClass(context, ReviewItemDetailActivity.class);
				i.putExtra("Fragment Flag", Constants.BUSINESS_FRAGMENT_FLAG);
				i.putExtra("detail flag", 1);
				i.putExtra("profile id", productList.get(position)
						.getProfileId());
				context.startActivity(i);
			}
		});

        if (objj.getType().equals("B")) {
            LL_type.setBackgroundResource(R.drawable.profile_comment_item_bottom);
            LL_mainBg.setBackgroundResource(R.drawable.profile_comment_item);
            TV_type.setText("Business");
            IV_logo.setBorderColor(Color.rgb(31, 112, 195));
        } else {
            LL_type.setBackgroundResource(R.drawable.profile_comment_item_bottom_s);
            LL_mainBg.setBackgroundResource(R.drawable.profile_comment_item_s);
            TV_type.setText("Social");
            IV_logo.setBorderColor(Color.rgb(105, 163, 53));
        }

		if (!objj.getOriginal_picture().equals("")) {
			if (objj.getOriginal_picture().contains("http")) {
				Picasso.with(context).load(objj.getOriginal_picture()).fit()
						.centerCrop().into(IV_logo);
			} else {
				Picasso.with(context)
						.load("http://mingout.cloudapp.net/mingout-beta-api/assets/profile-images/"
								+ objj.getOriginal_picture()).fit().centerCrop()
						.into(IV_logo);
			}
		} else if (!objj.getThumb_picture().equals("")) {
			Picasso.with(context).load(objj.getThumb_picture()).fit().centerCrop().into(IV_logo);
		}

		ratingBar1.setRating(Float.valueOf(objj.getRating()));

		return row;
	}
}
