package com.mingout.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mingout.adapters.ChatUsersListAdapter;
import com.mingout.adapters.MatchesSubAdapter;
import com.mingout.fragments.ChatUsersListFragment;
import com.mingout.fragments.MatchesFragment;
import com.mingout.models.ChatRoomUserListModel;
import com.mingout.models.MatchesSubModel;
import com.mingout.util.ConnectionTask;
import com.mingout.util.Constants;
import com.mingout.util.ResultJSON;
import com.mingout.util.Utilities;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MatchesActivity extends FragmentActivity implements ResultJSON {
    ImageView IV_settings;
    private List<MatchesSubModel> subMatchesList = new ArrayList<>();
    private RecyclerView recyclerView;
    private MatchesSubAdapter mAdapter;
    public static ViewPager viewPager;
    ImageView IMG_matchRight, IMG_matchLeft;
    MyAdapter matchesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matches);

        IV_settings = (ImageView) findViewById(R.id.IV_settings);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        IMG_matchRight = (ImageView) findViewById(R.id.IMG_matchRight);
        IMG_matchLeft = (ImageView) findViewById(R.id.IMG_matchLeft);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        getData(Constants.USER_LAT, Constants.USER_LONG, Constants.QR_CODE, true);

        IV_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.setClass(MatchesActivity.this, MatchesSettingsActivity.class);
                startActivity(i);
            }
        });

        IMG_matchRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setMainMatchPosition(getItem(+1));
            }
        });

        IMG_matchLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setMainMatchPosition(getItem(-1));
            }
        });
    }

    public void moveMatches(){
        setMainMatchPosition(getItem(+1));
    }

    public void setMainMatchPosition(int position){
        viewPager.setCurrentItem(position, true);
    }

    private void getData(String Lat, String Long, String qrCode, boolean flag) {
        // TODO Auto-generated method stub
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

    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }

    class MyAdapter extends FragmentPagerAdapter {
        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public MatchesFragment getItem(int position) {
            if (Constants.fragments != null) {
                return Constants.fragments.get(position);
            } else {
                return null;
            }
        }

        @Override
        public int getCount() {
            return Constants.fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (Constants.fragments != null) {
                return Constants.fragments.get(position).getitle();
            } else {
                return null;
            }
        }
    }

    @Override
    public void UpdateResult(Object obj) {
        ArrayList<ChatRoomUserListModel> Cache_Users_list = new ArrayList<ChatRoomUserListModel>();
        Constants.fragments = new ArrayList<>();
        try {
            String string = (String) obj;
            JSONObject jData = new JSONObject(string);
            if (jData.getString("status_code").equals("1")) {
                JSONObject jResponse = jData.getJSONObject("response");
                JSONArray userArray = jResponse.getJSONArray("users");

                for (int i = 0; i < userArray.length(); i++) {
                    JSONObject jsonObj = (JSONObject) userArray.get(i);

                    ChatRoomUserListModel modelObj = new ChatRoomUserListModel();

                    modelObj.setName(jsonObj.getString("first_name"));
                    modelObj.setAge(jsonObj.getString("age"));
                    modelObj.setProfileId(jsonObj.getString("profileid"));
                    modelObj.setUserId(jsonObj.getString("uid"));
                    modelObj.setType(jsonObj.getString("type"));
                    modelObj.setOriginal_picture(jsonObj.getString("original_picture"));
                    modelObj.setThumb_picture(jsonObj.getString("thumb_picture"));

                    Cache_Users_list.add(modelObj);
                }

                if (Cache_Users_list != null && Cache_Users_list.size() != 0) {
                    Constants.List_matches = Cache_Users_list;

                    mAdapter = new MatchesSubAdapter(Constants.List_matches, this);
                    recyclerView.setAdapter(mAdapter);
                    updateMatchesFragment(Constants.List_matches);
                }
            } else {
                Toast.makeText(this, jData.getString("status_message"), Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public void updateMatchesFragment(List<ChatRoomUserListModel> userList){
        for (int i = 0; i < userList.size(); i++){
            ChatRoomUserListModel obj = userList.get(i);
            String imageUrl = "";
            if (!obj.getOriginal_picture().equals("")) {
                if (obj.getOriginal_picture().contains("http")) {
                    imageUrl = obj.getOriginal_picture();
                } else {
                   imageUrl = "http://mingout.cloudapp.net/mingout-beta-api/assets/profile-images/"
                            + obj.getOriginal_picture();
                }
            } else if (!obj.getThumb_picture().equals("")) {
                imageUrl = obj.getThumb_picture();
            }

            Constants.fragments.add(new MatchesFragment(imageUrl, obj.getName() + " | " + obj.getAge(), obj.getProfileId(), obj.getType(), MatchesActivity.this));
        }

        matchesAdapter = new MyAdapter(getSupportFragmentManager());
        viewPager.setAdapter(matchesAdapter);
    }

}
