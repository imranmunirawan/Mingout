package com.mingout.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mingout.activities.MatchesActivity;
import com.mingout.activities.R;
import com.mingout.activities.ReviewItemDetailActivity;
import com.mingout.util.Constants;
import com.squareup.picasso.Picasso;

public class MatchesFragment extends Fragment {

    ImageView IV_logo, IV_yes, IV_no;
    TextView TV_name;
    LinearLayout LL_chat, LL_profile, LL_yes, LL_no;
    String logoUrl, name, profileId, type;
    MatchesActivity matchesActivity;

    public MatchesFragment(String logoUrl, String name, String profileId, String type, MatchesActivity matchesActivity){
        this.logoUrl = logoUrl;
        this.name = name;
        this.profileId = profileId;
        this.type = type;
        this.matchesActivity = matchesActivity;
    }

    public String getitle(){
        return name;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_matches_layout, null);

        IV_logo = (ImageView) view.findViewById(R.id.IV_logo);
        IV_yes = (ImageView) view.findViewById(R.id.IV_yes);
        IV_no = (ImageView) view.findViewById(R.id.IV_no);
        TV_name = (TextView) view.findViewById(R.id.TV_name);

        LL_chat = (LinearLayout) view.findViewById(R.id.LL_chat);
        LL_profile = (LinearLayout) view.findViewById(R.id.LL_profile);
        LL_yes = (LinearLayout) view.findViewById(R.id.LL_yes);
        LL_no = (LinearLayout) view.findViewById(R.id.LL_no);

        if(logoUrl != ""){
            Picasso.with(getActivity()).load(logoUrl).fit().centerCrop().into(IV_logo);
        }else {
            IV_logo.setImageResource(R.mipmap.test_person);
        }

        TV_name.setText(name);

        LL_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Constants.PROVIEW_UPDATE_SCREEN = 0;
            getActivity().finish();
            }
        });

        LL_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            if (type.equals("B")) {
                Intent i = new Intent();
                i.setClass(getActivity(), ReviewItemDetailActivity.class);
                i.putExtra("Fragment Flag", Constants.BUSINESS_FRAGMENT_FLAG);
                i.putExtra("detail flag", 1);
                i.putExtra("profile id", profileId);
                getActivity().startActivity(i);
            } else {
                Intent i = new Intent();
                i.setClass(getActivity(), ReviewItemDetailActivity.class);
                i.putExtra("Fragment Flag", Constants.SOCIAL_FRAGMENT_FLAG);
                i.putExtra("detail flag", 1);
                i.putExtra("profile id", profileId);
                getActivity().startActivity(i);
            }
            }
        });

        LL_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                matchesActivity.moveMatches();
            }
        });

        LL_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                matchesActivity.moveMatches();
            }
        });

        return view;
    }
}
