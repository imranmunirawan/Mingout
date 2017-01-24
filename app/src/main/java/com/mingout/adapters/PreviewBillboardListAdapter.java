package com.mingout.adapters;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mingout.activities.R;
import com.mingout.activities.ReviewItemDetailActivity;
import com.mingout.models.PreviewBillboardModel;
import com.mingout.util.Constants;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class PreviewBillboardListAdapter extends
		ArrayAdapter<PreviewBillboardModel> {

	Activity context;
	List<PreviewBillboardModel> productList;
	int LayoutId;

	public PreviewBillboardListAdapter(Activity contex, int layoutId,
			List<PreviewBillboardModel> vIEW_Productss_LIST) {
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
		final PreviewBillboardModel obj = productList.get(position);

		TextView TV_name = (TextView) row.findViewById(R.id.TV_name);
		TextView TV_type = (TextView) row.findViewById(R.id.TV_type);
		TextView TV_desc = (TextView) row.findViewById(R.id.TV_desc);
		ImageView IV_logo = (ImageView) row.findViewById(R.id.IV_logo);

		LinearLayout LL_type = (LinearLayout) row.findViewById(R.id.LL_type);

		TV_name.setText(obj.getName());
		TV_desc.setText(obj.getDesc());

		LL_type.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (obj.getType().equals("B")) {
					Intent i = new Intent();
					i.setClass(context, ReviewItemDetailActivity.class);
					i.putExtra("Fragment Flag", Constants.BUSINESS_FRAGMENT_FLAG);
					context.startActivity(i);
				} else {
					Intent i = new Intent();
					i.setClass(context, ReviewItemDetailActivity.class);
					i.putExtra("Fragment Flag", Constants.SOCIAL_FRAGMENT_FLAG);
					context.startActivity(i);
				}
			}
		});

		if (obj.getType().equals("B")) {
			LL_type.setBackgroundResource(R.drawable.round_business_shape);
			TV_type.setText("Business");
		} else {
			LL_type.setBackgroundResource(R.drawable.round_social_shape);
			TV_type.setText("Social");
		}

		if (!obj.getImgUrl().equals("")) {

			Picasso.with(context)
					.load(Constants.URL_GET_BILLBOARD_IMAGE + obj.getImgUrl())
					.fit().centerCrop().into(IV_logo, new Callback() {

						@Override
						public void onSuccess() {
							// TODO Auto-generated method stub

						}

						@Override
						public void onError() {
							// TODO Auto-generated method stub

						}
					});

		} else {

		}

		return row;
	}

}
