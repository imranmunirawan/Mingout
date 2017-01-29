package com.mingout.activities;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.mingout.dialog.ChatAgeFromDialog;
import com.mingout.dialog.ChatAgeToDialog;
import com.mingout.dialog.ChatGenderDialog;
import com.mingout.util.Constants;

public class MatchesSettingsActivity extends Activity implements ChatGenderDialog.GenderListner {

    TextView TV_cancel, TV_save, TV_gender, TV_fromAge, TV_toAge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matches_settings);

        TV_cancel = (TextView) findViewById(R.id.TV_cancel);
        TV_save = (TextView) findViewById(R.id.TV_save);
        TV_fromAge = (TextView) findViewById(R.id.TV_fromAge);
        TV_toAge = (TextView) findViewById(R.id.TV_toAge);
        TV_gender = (TextView) findViewById(R.id.TV_gender);

        if (Constants.MATCHES_SETTINGS_GENDER != null){
            TV_gender.setText(Constants.MATCHES_SETTINGS_GENDER);
        }

        if(Constants.MATCHES_SETTINGS_FROM_AGE != null){
            TV_fromAge.setText(Constants.MATCHES_SETTINGS_FROM_AGE);
        }

        if(Constants.MATCHES_SETTINGS_TO_AGE != null){
            TV_toAge.setText(Constants.MATCHES_SETTINGS_TO_AGE);
        }

        TV_fromAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChatAgeFromDialog dialog = new ChatAgeFromDialog(MatchesSettingsActivity.this, MatchesSettingsActivity.this, TV_fromAge);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        TV_toAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChatAgeToDialog dialog = new ChatAgeToDialog(MatchesSettingsActivity.this, MatchesSettingsActivity.this, String.valueOf(TV_fromAge.getText()), TV_toAge);
                dialog.getWindow().setBackgroundDrawable(
                        new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        TV_gender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChatGenderDialog dialog = new ChatGenderDialog(MatchesSettingsActivity.this, MatchesSettingsActivity.this);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        TV_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        TV_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void setGender(String gender) {
        TV_gender.setText(gender);
    }
}
