package com.mingout.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mingout.activities.R;
import com.mingout.models.ChatModel;

import java.util.List;

public class ChatAdapter extends BaseAdapter {

	Activity context;
	List<ChatModel> productList;
	int LayoutId;
	String secondUserUID;

	public ChatAdapter(Activity contex, int layoutId,
			List<ChatModel> vIEW_Productss_LIST, String secondUserUID) {
		// super(contex, layoutId, vIEW_Productss_LIST);
		this.context = contex;
		this.productList = vIEW_Productss_LIST;
		this.LayoutId = layoutId;
		this.secondUserUID = secondUserUID;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return productList.size();
	}

	@Override
	public View getView(final int position, View row, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder = null;
		ChatModel obj = productList.get(position);

		// if (row == null) {
		// LayoutInflater inflater = ((Activity) context).getLayoutInflater();
		// row = inflater.inflate(LayoutId, parent, false);
		// viewHolder = new ViewHolder();
		// viewHolder.TV_senderMessage = (TextView) row
		// .findViewById(R.id.TV_senderMessage);
		// viewHolder.TV_receiverMessage = (TextView) row
		// .findViewById(R.id.TV_receiverMessage);
		// viewHolder.TV_senderMessageTime = (TextView) row
		// .findViewById(R.id.TV_senderMessageTime);
		// viewHolder.TV_receiverMessageTime = (TextView) row
		// .findViewById(R.id.TV_receiverMessageTime);
		//
		// row.setTag(viewHolder);
		// } else {
		// viewHolder = (ViewHolder) row.getTag();
		// }
		LayoutInflater inflater = context.getLayoutInflater();
		row = inflater.inflate(LayoutId, parent, false);
		viewHolder = new ViewHolder();
		viewHolder.TV_senderMessage = (TextView) row
				.findViewById(R.id.TV_senderMessage);
		viewHolder.TV_receiverMessage = (TextView) row
				.findViewById(R.id.TV_receiverMessage);
		viewHolder.TV_senderMessageTime = (TextView) row
				.findViewById(R.id.TV_senderMessageTime);
		viewHolder.TV_receiverMessageTime = (TextView) row
				.findViewById(R.id.TV_receiverMessageTime);

		String[] email1 = obj.getChatUserName().split("\\/");

		if (secondUserUID.equals(email1[0])) {
			if (obj.getMessageFrom() != null) {
				viewHolder.TV_receiverMessage.setVisibility(TextView.VISIBLE);
				viewHolder.TV_receiverMessageTime
						.setVisibility(TextView.VISIBLE);
				viewHolder.TV_senderMessage.setVisibility(TextView.INVISIBLE);
				viewHolder.TV_senderMessageTime
						.setVisibility(TextView.INVISIBLE);
				viewHolder.TV_receiverMessage.setText(obj.getMessageFrom());
				viewHolder.TV_receiverMessageTime.setText(obj
						.getMessageFromTime());
			}
			if (obj.getMessageTo() != null) {
				viewHolder.TV_receiverMessage.setVisibility(TextView.INVISIBLE);
				viewHolder.TV_receiverMessageTime
						.setVisibility(TextView.INVISIBLE);
				viewHolder.TV_senderMessage.setVisibility(TextView.VISIBLE);
				viewHolder.TV_senderMessageTime.setVisibility(TextView.VISIBLE);
				viewHolder.TV_senderMessage.setText(obj.getMessageTo());
				viewHolder.TV_senderMessageTime.setText(obj.getMessageToTime());
			}
		}

		return row;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return productList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	public class ViewHolder {
		TextView TV_senderMessage;
		TextView TV_receiverMessage;
		TextView TV_senderMessageTime;
		TextView TV_receiverMessageTime;
	}
}
