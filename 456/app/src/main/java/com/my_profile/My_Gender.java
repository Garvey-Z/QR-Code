package com.my_profile;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.zxing.client.android.R;

/**
 * Created by apple on 2017/3/30.
 */

public class My_Gender extends Activity implements View.OnClickListener{
    private ImageView img_male, img_female;
    private RelativeLayout male, female;
    private TextView rt;
    private String gender;
    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);
        ActionBar actionBar = getActionBar();
        actionBar.hide();
        setContentView(R.layout.my_gender);
        Intent intent = getIntent();
         gender = intent.getStringExtra("gender").toString();

        male = (RelativeLayout)findViewById(R.id.save_gender_male);
        female = (RelativeLayout)findViewById(R.id.save_gender_female);
        img_male = (ImageView)findViewById(R.id.save_gender_male_yes);
        img_female = (ImageView)findViewById(R.id.save_gender_female_yes);
        rt = (TextView)findViewById(R.id.rt_save_gender);
        rt.setText("< My Profile");

        if(gender.equals("Male")){
            img_male.setVisibility(View.VISIBLE);
            img_female.setVisibility(View.INVISIBLE);
        }else {
            img_male.setVisibility(View.INVISIBLE);
            img_female.setVisibility(View.VISIBLE);
        }
        rt.setOnClickListener(this);
        male.setOnClickListener(this);
        female.setOnClickListener(this);


    }
    @Override
    public void onClick(View view){
        Intent intent = new Intent();
        switch (view.getId()){
            case R.id.rt_save_gender:
                finish();
                break;
            case R.id.save_gender_male:

                intent.putExtra("gender", "Male");
                setResult(RESULT_OK, intent);
                finish();
                break;
            case R.id.save_gender_female:
                intent.putExtra("gender", "Female");
                setResult(RESULT_OK, intent);
                finish();
                break;
            default:
                break;

        }

    }
}
