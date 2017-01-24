package com.mingout.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mingout.util.Constants;
import com.squareup.picasso.Picasso;

public class AddOnMenuActivity extends Activity {

    ImageView IV_NavLogo, IV_titleBarPromos, IV_titleBarChat, IV_close;
    TextView TV_gym, TV_loyalty, TV_match, TV_album,TV_NavName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_on_menu);

        IV_NavLogo = (ImageView) findViewById(R.id.IV_NavLogo);
        IV_titleBarPromos = (ImageView) findViewById(R.id.IV_titleBarPromos);
        IV_titleBarChat = (ImageView) findViewById(R.id.IV_titleBarChat);
        IV_close = (ImageView) findViewById(R.id.IV_close);

        TV_gym = (TextView) findViewById(R.id.TV_gym);
        TV_loyalty = (TextView) findViewById(R.id.TV_loyalty);
        TV_match = (TextView) findViewById(R.id.TV_match);
        TV_album = (TextView) findViewById(R.id.TV_album);
        TV_NavName = (TextView) findViewById(R.id.TV_NavName);

        switch (Constants.CURRENT_FRAGMENT_FLAG){
            case Constants.SOCIAL_FRAGMENT_FLAG:
                TV_NavName.setText(Constants.USER_NAME + " / " + Constants.SOCIAL_AGE);
                Picasso.with(AddOnMenuActivity.this).load(Constants.PROFILE_SOCIAL_image).fit().centerCrop().into(IV_NavLogo);
                break;

            case Constants.BUSINESS_FRAGMENT_FLAG:
                TV_NavName.setText(Constants.USER_NAME + " / " + Constants.BUSINESS_AGE);
                Picasso.with(AddOnMenuActivity.this).load(Constants.PROFILE_BUSINESS_image).fit().centerCrop().into(IV_NavLogo);
                break;
        }

        TV_gym.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent ii = new Intent();
                ii.setClass(AddOnMenuActivity.this, GymWorkPlanDevicesActivity.class);
                startActivity(ii);
            }
        });

        TV_loyalty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent ii = new Intent();
                ii.setClass(AddOnMenuActivity.this, LoyaltyProgramActivity.class);
                startActivity(ii);
            }
        });

        TV_match.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent ii = new Intent();
                ii.setClass(AddOnMenuActivity.this, MatchesActivity.class);
                startActivity(ii);
            }
        });

        IV_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
