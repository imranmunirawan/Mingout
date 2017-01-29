package com.mingout.adapters;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mingout.activities.R;
import com.mingout.activities.ReviewItemDetailActivity;
import com.mingout.models.ChatRoomUserListModel;
import com.mingout.util.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ChatUsersListAdapter extends BaseAdapter {

	Activity context;
	List<ChatRoomUserListModel> productList;
	int LayoutId;
	Fragment f;
	private ArrayList<ChatRoomUserListModel> arraylist;

	public ChatUsersListAdapter(Activity contex, int layoutId,
			List<ChatRoomUserListModel> vIEW_Productss_LIST, Fragment f) {
		this.context = contex;
		this.productList = vIEW_Productss_LIST;
		this.LayoutId = layoutId;
		this.f = f;
		this.arraylist = new ArrayList<ChatRoomUserListModel>();
		this.arraylist.addAll(productList);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return productList.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return productList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(final int position, View row, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (row == null) {
			LayoutInflater inflater = context.getLayoutInflater();
			row = inflater.inflate(LayoutId, parent, false);
		}
		ChatRoomUserListModel obj = productList.get(position);

		ImageView IV_logo = (ImageView) row.findViewById(R.id.IV_logo);
		TextView TV_name = (TextView) row.findViewById(R.id.TV_name);
		TextView TV_type = (TextView) row.findViewById(R.id.TV_type);
		TextView TV_gender = (TextView) row.findViewById(R.id.TV_gender);
		TextView TV_age = (TextView) row.findViewById(R.id.TV_age);
		TextView TV_status = (TextView) row.findViewById(R.id.TV_status);
		TextView TV_newMessageCounter = (TextView) row
				.findViewById(R.id.TV_newMessageCounter);

		TV_name.setText(obj.getName());
		TV_type.setText(obj.getType());
		if (obj.getType().equals("B")) {
			TV_type.setBackgroundResource(R.drawable.round_business_shape);
			TV_type.setText("Business");

			TV_type.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Intent i = new Intent();
					i.setClass(context, ReviewItemDetailActivity.class);
					i.putExtra("Fragment Flag", Constants.BUSINESS_FRAGMENT_FLAG);
					i.putExtra("detail flag", 1);
					i.putExtra("profile id", productList.get(position).getProfileId());
					context.startActivity(i);
				}
			});
		} else {
			TV_type.setBackgroundResource(R.drawable.round_social_shape);
			TV_type.setText("Social");

			TV_type.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Intent i = new Intent();
					i.setClass(context, ReviewItemDetailActivity.class);
					i.putExtra("Fragment Flag", Constants.SOCIAL_FRAGMENT_FLAG);
					i.putExtra("detail flag", 1);
					i.putExtra("profile id", productList.get(position).getProfileId());
					context.startActivity(i);
				}
			});
		}
		TV_gender.setText(obj.getGender());
		TV_age.setText(obj.getAge());
		TV_status.setText(obj.getMyPhrase());

		if (!obj.getOriginal_picture().equals("")) {
			if (obj.getOriginal_picture().contains("http")) {
				Picasso.with(context).load(obj.getOriginal_picture()).fit()
						.centerCrop().into(IV_logo);
			} else {
				Picasso.with(context)
						.load("http://mingout.cloudapp.net/mingout-beta-api/assets/profile-images/"
								+ obj.getOriginal_picture()).fit().centerCrop()
						.into(IV_logo);
			}
		} else if (!obj.getThumb_picture().equals("")) {
			Picasso.with(context).load(obj.getThumb_picture()).fit().centerCrop().into(IV_logo);
		}
		if (!obj.getUnreadMessageCounter().equals("")) {
			TV_newMessageCounter.setVisibility(View.VISIBLE);
			TV_newMessageCounter.setText(obj.getUnreadMessageCounter());
		} else if (obj.getUnreadMessageCounter().equals("")) {
			TV_newMessageCounter.setVisibility(View.INVISIBLE);
		}

		return row;
	}

	// Filter Class
	public void filter(String nameChar, String sex, String ageFrom, String ageTo) {
		nameChar = nameChar.toLowerCase(Locale.getDefault());
		productList.clear();
		if (nameChar.length() == 0) {
			productList.addAll(arraylist);
		} else {
			for (ChatRoomUserListModel wp : arraylist) {
				if (wp.getName().toLowerCase(Locale.getDefault()) // Gender
						.contains(nameChar)
						&& sex.toLowerCase(Locale.getDefault()).equals("any")
						&& ageFrom.toLowerCase(Locale.getDefault()).equals(
								"any")
						&& ageTo.toLowerCase(Locale.getDefault()).equals("any")) {
					productList.add(wp);
				} else if (wp.getName().toLowerCase(Locale.getDefault()) // Gender
																			// Denfined
						.contains(nameChar)
						&& wp.getGender().toLowerCase(Locale.getDefault())
								.equals(sex)
						&& ageFrom.toLowerCase(Locale.getDefault()).equals(
								"any")
						&& ageTo.toLowerCase(Locale.getDefault()).equals("any")) {
					productList.add(wp);
				} else if (wp.getName().toLowerCase(Locale.getDefault()) // Gender
																			// Denfined
																			// ,
																			// Age
																			// range
																			// FROM
																			// Defined
						.contains(nameChar)
						&& wp.getGender().toLowerCase(Locale.getDefault())
								.equals(sex)
						&& Integer.valueOf(wp.getAge()) >= Integer
								.valueOf(ageFrom)
						&& ageTo.toLowerCase(Locale.getDefault()).equals("any")) {
					productList.add(wp);
				} else if (wp.getName().toLowerCase(Locale.getDefault()) // Gender
																			// Denfined
																			// ,
																			// Age
																			// range
																			// FULL
																			// Defined
						.contains(nameChar)
						&& wp.getGender().toLowerCase(Locale.getDefault())
								.equals(sex)
						&& Integer.valueOf(wp.getAge()) >= Integer
								.valueOf(ageFrom)
						&& Integer.valueOf(wp.getAge()) <= Integer
								.valueOf(ageTo)) {
					productList.add(wp);
				} else if (wp.getName().toLowerCase(Locale.getDefault()) // Any
																			// Gender
																			// ,
																			// Age
																			// range
																			// From
																			// Defined
						.contains(nameChar)
						&& sex.toLowerCase(Locale.getDefault()).equals("any")
						&& Integer.valueOf(wp.getAge()) >= Integer
								.valueOf(ageFrom)
						&& ageTo.toLowerCase(Locale.getDefault()).equals("any")) {
					productList.add(wp);
				} else if (wp.getName().toLowerCase(Locale.getDefault()) // Any
																			// Gender
																			// ,
																			// Age
																			// range
																			// To
																			// Defined
						.contains(nameChar)
						&& sex.toLowerCase(Locale.getDefault()).equals("any")
						&& ageFrom.toLowerCase(Locale.getDefault()).equals(
								"any")
						&& Integer.valueOf(wp.getAge()) <= Integer
								.valueOf(ageTo)) {
					productList.add(wp);
				} else if (wp.getName().toLowerCase(Locale.getDefault()) // Any
																			// Gender
																			// ,
																			// Age
																			// range
																			// Full
																			// defined
						.contains(nameChar)
						&& sex.toLowerCase(Locale.getDefault()).equals("any")
						&& Integer.valueOf(wp.getAge()) >= Integer
								.valueOf(ageFrom)
						&& Integer.valueOf(wp.getAge()) <= Integer
								.valueOf(ageTo)) {
					productList.add(wp);
				}
			}
		}
		notifyDataSetChanged();
	}
}
