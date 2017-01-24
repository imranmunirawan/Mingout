package com.mingout.adapters;

import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.mingout.models.PreviewBillboardModel;

public class WorkOutPlanListAdapter extends
		ArrayAdapter<PreviewBillboardModel> {

	Activity context;
	List<PreviewBillboardModel> productList;
	int LayoutId;

	public WorkOutPlanListAdapter(Activity contex, int layoutId,
			List<PreviewBillboardModel> vIEW_Productss_LIST) {
		super(contex, layoutId, vIEW_Productss_LIST);
		this.context = contex;
		this.productList = vIEW_Productss_LIST;
		this.LayoutId = layoutId;

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 20;
	}

	@Override
	public View getView(final int position, View row, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(LayoutId, parent, false);
		}

		return row;
	}

}
