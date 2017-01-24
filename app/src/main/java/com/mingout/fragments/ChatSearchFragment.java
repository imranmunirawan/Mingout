package com.mingout.fragments;

import java.util.Locale;

import android.app.Fragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mingout.activities.R;
import com.mingout.dialog.ChatAgeFromDialog;
import com.mingout.dialog.ChatAgeFromDialog.AgeFromListner;
import com.mingout.dialog.ChatAgeToDialog;
import com.mingout.dialog.ChatAgeToDialog.AgeToListner;
import com.mingout.dialog.ChatGenderDialog;
import com.mingout.dialog.ChatGenderDialog.GenderListner;

public class ChatSearchFragment extends Fragment implements GenderListner,
		AgeFromListner, AgeToListner {
	TextView TV_cancel;
	EditText ET_gender, ET_ageFrom, ET_ageTo, ET_name;
	Fragment parentFragment;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater
				.inflate(R.layout.fragment_chat_search_layout, null);

		TV_cancel = (TextView) view.findViewById(R.id.TV_cancel);
		ET_gender = (EditText) view.findViewById(R.id.ET_gender);
		ET_ageFrom = (EditText) view.findViewById(R.id.ET_ageFrom);
		ET_ageTo = (EditText) view.findViewById(R.id.ET_ageTo);
		ET_name = (EditText) view.findViewById(R.id.ET_name);

		ET_gender.setKeyListener(null);
		ET_ageFrom.setKeyListener(null);
		ET_ageTo.setKeyListener(null);

		ET_gender.setText("Any");
		ET_ageFrom.setText("Any");
		ET_ageTo.setText("Any");

		TV_cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				((RemoveChatSearchFragmentTriger) getActivity())
						.RemoveChatSearchFragment();
				((ChatSearchListner) parentFragment).startRefreshListAgain();
			}
		});

		ET_gender.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				ChatGenderDialog dialog = new ChatGenderDialog(getActivity(),
						ChatSearchFragment.this);
				dialog.getWindow().setBackgroundDrawable(
						new ColorDrawable(Color.TRANSPARENT));
				dialog.show();
			}
		});
		ET_ageFrom.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				ChatAgeFromDialog dialog = new ChatAgeFromDialog(getActivity(),
						ChatSearchFragment.this);
				dialog.getWindow().setBackgroundDrawable(
						new ColorDrawable(Color.TRANSPARENT));
				dialog.show();
			}
		});
		ET_ageTo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				ChatAgeToDialog dialog = new ChatAgeToDialog(getActivity(),
						ChatSearchFragment.this, ET_ageFrom.getText()
								.toString());
				dialog.getWindow().setBackgroundDrawable(
						new ColorDrawable(Color.TRANSPARENT));
				dialog.show();
			}
		});

		ET_name.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
				String name = ET_name.getText().toString()
						.toLowerCase(Locale.getDefault());
				((ChatSearchListner) parentFragment).searchUserListner(
						name,
						ET_gender.getText().toString()
								.toLowerCase(Locale.getDefault()),
						ET_ageFrom.getText().toString()
								.toLowerCase(Locale.getDefault()),
						ET_ageTo.getText().toString()
								.toLowerCase(Locale.getDefault()));
			}
		});

		return view;
	}

	public interface RemoveChatSearchFragmentTriger {
		public void RemoveChatSearchFragment();
	}

	@Override
	public void setGender(String gender) {
		// TODO Auto-generated method stub
		ET_gender.setText(gender);
	}

	@Override
	public void setAgeFrom(String ageFrom) {
		// TODO Auto-generated method stub
		ET_ageFrom.setText(ageFrom);
		if (!ET_ageTo.getText().toString().equals("Any")) {
			ET_ageTo.setText(ET_ageFrom.getText().toString());
		}
	}

	@Override
	public void setAgeTo(String age) {
		// TODO Auto-generated method stub
		ET_ageTo.setText(age);
		if (ET_ageFrom.getText().toString().equals("Any")) {
			ET_ageFrom.setText(ET_ageTo.getText().toString());
		}
	}

	public void setParent(Fragment fragment) {
		// TODO Auto-generated method stub
		this.parentFragment = fragment;
	}

	public interface ChatSearchListner {
		public void searchUserListner(String name, String sex, String ageFrom,
									  String ageTo);

		public void stopRefreshListForSearch();

		public void startRefreshListAgain();
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Log.e("Methord", "Start");
		((ChatSearchListner) parentFragment).stopRefreshListForSearch();
	}
}
