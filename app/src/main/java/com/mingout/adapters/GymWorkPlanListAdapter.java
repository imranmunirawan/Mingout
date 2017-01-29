package com.mingout.adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mingout.activities.GymWorkPlanEditActivity;
import com.mingout.activities.R;
import com.mingout.models.workoutPlanModel;

import java.util.List;

public class GymWorkPlanListAdapter extends BaseAdapter {

	Activity context;
	List<workoutPlanModel> productList;
	int LayoutId;

	public GymWorkPlanListAdapter(Activity contex, int layoutId,
			List<workoutPlanModel> vIEW_Productss_LIST) {
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
			LayoutInflater inflater = context.getLayoutInflater();
			row = inflater.inflate(LayoutId, parent, false);
		}
		final workoutPlanModel objj = productList.get(position);

		TextView TV_sets = (TextView) row.findViewById(R.id.TV_sets);
		TextView TV_reps = (TextView) row.findViewById(R.id.TV_reps);
		TextView TV_weight = (TextView) row.findViewById(R.id.TV_weight);
		TextView TV_rest = (TextView) row.findViewById(R.id.TV_rest);
		TextView TV_date = (TextView) row.findViewById(R.id.TV_date);

		ImageView IV_edit = (ImageView) row.findViewById(R.id.IV_edit);

		TV_sets.setText(objj.getSets());
		TV_reps.setText(objj.getReps());
		TV_weight.setText(objj.getWeight());
		TV_rest.setText(objj.getRest());
		TV_date.setText(objj.getDate());

		IV_edit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent();
				i.setClass(context, GymWorkPlanEditActivity.class);
				i.putExtra("data", objj);
				i.putExtra("edit flag", true);
				context.startActivity(i);
			}
		});

		return row;
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

}
