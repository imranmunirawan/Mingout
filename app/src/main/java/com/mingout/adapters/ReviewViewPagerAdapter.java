package com.mingout.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.mingout.activities.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ReviewViewPagerAdapter extends PagerAdapter {

	String imageUrl;
	List<String> list;
	int layoutId;
	private Context context;

	// constructor
	public ReviewViewPagerAdapter(Context context, int layoutId,
			List<String> list) {
		this.context = context;
		this.list = list;
		this.layoutId = layoutId;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == object;
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		ImageView imgDisplay;
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View viewLayout = inflater.inflate(layoutId, container, false);

		imgDisplay = (ImageView) viewLayout.findViewById(R.id.imageView1);
		Picasso.with(context).load(list.get(position)).fit().centerCrop()
				.into(imgDisplay, new Callback() {

					@Override
					public void onSuccess() {
						// TODO Auto-generated method stub
					}

					@Override
					public void onError() {
						// TODO Auto-generated method stub
					}
				});

		container.addView(viewLayout);



		return viewLayout;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((RelativeLayout) object);

	}

}
