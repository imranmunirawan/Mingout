package com.mingout.adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mingout.activities.GymWorkoutPlanDevicesEditActivity;
import com.mingout.activities.R;
import com.mingout.activities.WorkoutPlanDescriptionActivity;
import com.mingout.models.WorkOutDevicesModel;

import java.util.List;

public class PreviewWorkoutPlanListAdapter extends
		ArrayAdapter<WorkOutDevicesModel> {

	Activity context;
	List<WorkOutDevicesModel> productList;
	int LayoutId;

	public PreviewWorkoutPlanListAdapter(Activity contex, int layoutId,
			List<WorkOutDevicesModel> vIEW_Productss_LIST) {
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
			LayoutInflater inflater = context.getLayoutInflater();
			row = inflater.inflate(LayoutId, parent, false);
		}
		final WorkOutDevicesModel objj = productList.get(position);

		TextView TV_body = (TextView) row.findViewById(R.id.TV_body);
		TextView TV_device = (TextView) row.findViewById(R.id.TV_device);
		TextView TV_sets = (TextView) row.findViewById(R.id.TV_sets);

		ImageView IV_edit = (ImageView) row.findViewById(R.id.IV_edit);
		ImageView TV_desc = (ImageView) row.findViewById(R.id.TV_desc);

		TV_body.setText(objj.getBody_part());
		TV_device.setText(objj.getDevice_number());
		TV_sets.setText(objj.getDevice_seat());

		if (!objj.getDescription().equals(" ")) {
			TV_desc.setVisibility(View.VISIBLE);
		}

		IV_edit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent();
				i.setClass(context, GymWorkoutPlanDevicesEditActivity.class);
				i.putExtra("editflag", 1);
				i.putExtra("id", objj.getGym_device_id());
				i.putExtra("body part", objj.getBody_part());
				i.putExtra("device", objj.getDevice_number());
				i.putExtra("sets", objj.getDevice_seat());
				i.putExtra("desc", objj.getDescription());
				i.putExtra("edit flag", true);
				context.startActivity(i);
			}
		});
		TV_desc.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent();
				i.setClass(context, WorkoutPlanDescriptionActivity.class);
				i.putExtra("desc", objj.getDescription());
				context.startActivity(i);
			}
		});
		return row;
	}

}
