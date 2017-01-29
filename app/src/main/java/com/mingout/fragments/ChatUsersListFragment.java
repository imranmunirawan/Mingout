package com.mingout.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mingout.activities.ChatUserActivity;
import com.mingout.activities.R;
import com.mingout.adapters.ChatUsersListAdapter;
import com.mingout.fragments.ChatSearchFragment.ChatSearchListner;
import com.mingout.models.ChatRoomUserListModel;
import com.mingout.util.ConnectionTask;
import com.mingout.util.Constants;
import com.mingout.util.GPSHelper;
import com.mingout.util.ResultJSON;
import com.mingout.util.Utilities;

import org.jivesoftware.smack.packet.Message;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ChatUsersListFragment extends Fragment implements ResultJSON, ChatSearchListner {
	ListView lv;
	String Lat, ChatingWithUser = "";
	TextView TV_noUserAvailable;
	String Long;
	boolean resumeReloadListFlag = false, stopRefreshListForSearchFlag = false;
	ChatUserActivity chatUserActivity;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_chat_users_list_layout, null);

		lv = (ListView) view.findViewById(R.id.listView1);
		TV_noUserAvailable = (TextView) view.findViewById(R.id.TV_noUserAvailable);

		GPSHelper gps = new GPSHelper(getActivity());
		gps.getMyLocation();
		if (Constants.chatUserListAdapter != null) {
			lv.setAdapter(Constants.chatUserListAdapter);
		}
		getData(Constants.USER_LAT, Constants.USER_LONG, Constants.QR_CODE, true);

		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				// TODO Auto-generated method stub
				Constants.List_chat_users.get(arg2).setUnreadMessageCounter("");
				Constants.chatUserListAdapter.notifyDataSetChanged();
				ChatingWithUser = Constants.List_chat_users.get(arg2).getUserName();

				chatUserActivity = new ChatUserActivity();

				Intent i = new Intent();
				i.setClass(getActivity(), chatUserActivity.getClass());
				i.putExtra("to", Constants.List_chat_users.get(arg2).getProfileId());
				i.putExtra("name", Constants.List_chat_users.get(arg2).getName());
				i.putExtra("email", Constants.List_chat_users.get(arg2).getEmail());
				i.putExtra("data", Constants.List_chat_users.get(arg2));
				startActivity(i);

			}
		});

		return view;
	}

	private void getData(String Lat, String Long, String qrCode, boolean flag) {
		// TODO Auto-generated method stub
		if (!stopRefreshListForSearchFlag) {
			JSONObject jsonobj;
			jsonobj = new JSONObject();
			try {
				// adding some keys
				jsonobj.put("appkey", "spiderman1450@gmail.com");
				jsonobj.put("session_token", Constants.SESSION_TOKEN);
				jsonobj.put("user_id", Constants.USER_ID);
				jsonobj.put("profile_id", Constants.QR_LOGIN_PROFILE_ID);
				jsonobj.put("qr_code", qrCode);
				jsonobj.put("lat", Lat);
				jsonobj.put("lon", Long);
				jsonobj.put("limit", "100");
				new ConnectionTask(this, jsonobj, flag).execute(Constants.API_ROOM_USERS);
			} catch (JSONException ex) {
				ex.printStackTrace();
			}
		}
	}

	@Override
	public void UpdateResult(Object obj) {
		// TODO Auto-generated method stub
		if (!stopRefreshListForSearchFlag) {
			ArrayList<ChatRoomUserListModel> Cache_Users_list = new ArrayList<ChatRoomUserListModel>();
			try {
				String string = (String) obj;
				JSONObject jData = new JSONObject(string);
				if (jData.getString("status_code").equals("1")) {
					JSONObject jResponse = jData.getJSONObject("response");
					JSONArray userArray = jResponse.getJSONArray("users");

					for (int i = 0; i < userArray.length(); i++) {
                        if(!userArray.get(i).equals(null)){
                            JSONObject jsonObj = (JSONObject) userArray.get(i);

                            ChatRoomUserListModel modelObj = new ChatRoomUserListModel();

                            modelObj.setName(jsonObj.getString("first_name"));
                            modelObj.setAge(jsonObj.getString("age"));
                            modelObj.setEmail(jsonObj.getString("email"));
                            modelObj.setGender(jsonObj.getString("gender").substring(0,1).toUpperCase() + jsonObj.getString("gender").substring(1));
                            modelObj.setGender(jsonObj.getString("gender"));
                            modelObj.setMyPhrase(jsonObj.getString("my_phrase"));
                            modelObj.setProfileId(jsonObj.getString("profileid"));
                            modelObj.setUserId(jsonObj.getString("uid"));
                            modelObj.setType(jsonObj.getString("type"));
                            modelObj.setOriginal_picture(jsonObj.getString("original_picture"));
                            modelObj.setThumb_picture(jsonObj.getString("thumb_picture"));
                            modelObj.setLookingFor(jsonObj.getString("looking_for"));
                            modelObj.setUserName(Utilities.getXMPPuid(jsonObj.getString("email")) + "@chatserver.mingout.com");
                            modelObj.setUnreadMessageCounter("");

                            Cache_Users_list.add(modelObj);
                        }
					}

					if (Cache_Users_list.size() == 0) {
						TV_noUserAvailable.setVisibility(TextView.VISIBLE);
						lv.setVisibility(View.INVISIBLE);

					} else if (Constants.List_chat_users == null) {
						lv.setVisibility(View.VISIBLE);
						Constants.List_chat_users = Cache_Users_list;

						Constants.chatUserListAdapter = new ChatUsersListAdapter(getActivity(),
								R.layout.item_chat_users_list_layout, Constants.List_chat_users,
								ChatUsersListFragment.this);
						lv.setAdapter(Constants.chatUserListAdapter);

					} else if (Constants.List_chat_users.size() != Cache_Users_list.size()) {
						lv.setVisibility(View.VISIBLE);
						Constants.List_chat_users = Cache_Users_list;
						Constants.chatUserListAdapter = new ChatUsersListAdapter(getActivity(),
								R.layout.item_chat_users_list_layout, Constants.List_chat_users,
								ChatUsersListFragment.this);
						lv.setAdapter(Constants.chatUserListAdapter);
						Constants.chatUserListAdapter.notifyDataSetChanged();
					}

					if (resumeReloadListFlag && !stopRefreshListForSearchFlag) {
						getData(Constants.USER_LAT, Constants.USER_LONG, Constants.QR_CODE, false);
					}
				} else {
					Toast.makeText(getActivity(), jData.getString("status_message"), Toast.LENGTH_LONG).show();
				}
			} catch (Exception e) {
				Log.e("ChatUsersList", e.toString());
			}
		}
	}

	public void newMessageListner(Message message) {
		// TODO Auto-generated method stub
		try {
			for (int i = 0; i < Constants.List_chat_users.size(); i++) {
				String userName = Constants.List_chat_users.get(i).getUserName();
				String[] email1 = message.getFrom().split("\\/");
				Log.e("asdasdasdasd", userName + " : " + email1[0]);
				if (userName.equals(email1[0]) && !ChatingWithUser.equals(userName)) {

					Constants.List_chat_users.get(i).setNewMessages("1");
					Constants.chatUserListAdapter.notifyDataSetChanged();
					if (Constants.List_chat_users.get(i).getUnreadMessageCounter() == "") {
						Constants.List_chat_users.get(i).setUnreadMessageCounter("1");

						Constants.chatUserListAdapter.notifyDataSetChanged();
						break;

					} else {
						int count = Integer.valueOf(Constants.List_chat_users.get(i).getUnreadMessageCounter());
						count++;
						Constants.List_chat_users.get(i).setUnreadMessageCounter(String.valueOf(count));

						Constants.chatUserListAdapter.notifyDataSetChanged();
						break;

					}
				}
			}

			if (chatUserActivity != null)
				chatUserActivity.newMessageListner(message);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (!resumeReloadListFlag && !stopRefreshListForSearchFlag)
			getData(Constants.USER_LAT, Constants.USER_LONG, Constants.QR_CODE, false);
		resumeReloadListFlag = true;

		Log.e("Methord", "onResume");
		ChatingWithUser = "";
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		resumeReloadListFlag = false;
		Log.e("Methord", "onStop");
	}

	@Override
	public void searchUserListner(String name, String sex, String ageFrom, String ageTo) {
		// TODO Auto-generated method stub
		Constants.chatUserListAdapter.filter(name, sex, ageFrom, ageTo);
	}

	@Override
	public void stopRefreshListForSearch() {
		// TODO Auto-generated method stub
		stopRefreshListForSearchFlag = true;
	}

	@Override
	public void startRefreshListAgain() {
		// TODO Auto-generated method stub
		stopRefreshListForSearchFlag = false;
		getData(Constants.USER_LAT, Constants.USER_LONG, Constants.QR_CODE, false);
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Log.e("Methord", "onStart");
	}
}
