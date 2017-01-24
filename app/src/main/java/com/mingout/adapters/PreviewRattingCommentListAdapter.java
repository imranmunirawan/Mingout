package com.mingout.adapters;

import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mingout.activities.R;
import com.mingout.models.PreviewRattingCommentModel;
import com.mingout.util.Constants;
import com.squareup.picasso.Picasso;

public class PreviewRattingCommentListAdapter extends
		ArrayAdapter<PreviewRattingCommentModel> {

	Activity context;
	List<PreviewRattingCommentModel> productList;
	int LayoutId;

	public PreviewRattingCommentListAdapter(Activity contex, int layoutId,
			List<PreviewRattingCommentModel> vIEW_Productss_LIST) {
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
		PreviewRattingCommentModel obj = productList.get(position);

		ImageView IV_logo = (ImageView) row.findViewById(R.id.IV_logo);
		TextView TV_name = (TextView) row.findViewById(R.id.TV_name);
		TextView TV_comment = (TextView) row.findViewById(R.id.TV_comment);

		TV_name.setText(obj.getName());
		TV_comment.setText(obj.getComment());
		if (!obj.getImageUrl().equals(""))
			Picasso.with(context).load(obj.getImageUrl()).into(IV_logo);
		return row;
	}

}
