package com.mingout.fragments;

import org.jivesoftware.smack.packet.Message;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mingout.activities.R;
import com.mingout.util.Constants;

public class ChatFragment extends Fragment {

	ImageView IV_searchChat;
	ChatUsersListFragment chatFrag;
	TextView TV_title;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_chat_layout, null);

		IV_searchChat = (ImageView) view.findViewById(R.id.IV_searchChat);
		TV_title = (TextView) view.findViewById(R.id.TV_title);

		FragmentManager fm = getFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();

		chatFrag = new ChatUsersListFragment();
		ft.add(R.id.frag_container_chatList, chatFrag);
		ft.commit();

		TV_title.setText(Constants.COMPANY_NAME);

		Constants.PreviewSearchChat.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				FragmentManager fm = getFragmentManager();
				FragmentTransaction ft = fm.beginTransaction();

				ChatSearchFragment frag = new ChatSearchFragment();
				frag.setParent(chatFrag);
				ft.add(R.id.frag_container_chatSearch, frag).addToBackStack(frag.getTag());
				ft.commit();
			}
		});

		return view;
	}

	public void newMessageListner(Message message) {
		// TODO Auto-generated method stub

		if (chatFrag != null)
			chatFrag.newMessageListner(message);
		// Log.e("New Message", message.getFrom() + " " + message.getBody());
	}

}
