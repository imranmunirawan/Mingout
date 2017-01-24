package com.mingout.activities;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mingout.util.Constants;

public class LoyaltyProgramActivity extends Activity {

    TextView TV_save, TV_cancel;
    EditText ET_name, ET_id, ET_email, ET_phone, ET_dob, ET_street, ET_city, ET_zip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loyalty_program);

        TV_save = (TextView) findViewById(R.id.TV_save);
        TV_cancel = (TextView) findViewById(R.id.TV_cancel);

        ET_name = (EditText) findViewById(R.id.ET_name);
        ET_id = (EditText) findViewById(R.id.ET_id);
        ET_email = (EditText) findViewById(R.id.ET_email);
        ET_phone = (EditText) findViewById(R.id.ET_phone);
        ET_dob = (EditText) findViewById(R.id.ET_dob);
        ET_street = (EditText) findViewById(R.id.ET_street);
        ET_city = (EditText) findViewById(R.id.ET_city);
        ET_zip = (EditText) findViewById(R.id.ET_zip);

        ET_name.setText(Constants.USER_NAME);
        ET_email.setText(Constants.USER_EMAIL);

        switch (Constants.CURRENT_FRAGMENT_FLAG){
            case Constants.SOCIAL_FRAGMENT_FLAG:
                ET_dob.setText(Constants.SOCIAL_AGE);
                break;

            case Constants.BUSINESS_FRAGMENT_FLAG:
                ET_dob.setText(Constants.BUSINESS_AGE);
                break;
        }

        TV_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        TV_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(ET_name.getText())){
                    ET_name.setError("Mandatory Field!");
                    return;
                }

                if(TextUtils.isEmpty(ET_id.getText())){
                    ET_id.setError("Mandatory Field!");
                    return;
                }

                if(TextUtils.isEmpty(ET_email.getText())){
                    ET_email.setError("Mandatory Field!");
                    return;
                }

                if(TextUtils.isEmpty(ET_phone.getText())){
                    ET_phone.setError("Mandatory Field!");
                    return;
                }

                if(TextUtils.isEmpty(ET_dob.getText())){
                    ET_dob.setError("Mandatory Field!");
                    return;
                }

                if(TextUtils.isEmpty(ET_street.getText())){
                    ET_street.setError("Mandatory Field!");
                    return;
                }

                if(TextUtils.isEmpty(ET_city.getText())){
                    ET_city.setError("Mandatory Field!");
                    return;
                }

                if(TextUtils.isEmpty(ET_zip.getText())){
                    ET_zip.setError("Mandatory Field!");
                    return;
                }

                Toast.makeText(LoyaltyProgramActivity.this, "Thank you for applying Loyalty Program!", Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }
}
