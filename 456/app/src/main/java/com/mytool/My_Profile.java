package com.mytool;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.zxing.client.android.R;
import com.my_profile.My_Gender;
import com.my_profile.My_Name;
import com.my_profile.My_Photo;
import com.my_profile.My_QR_Code;
import com.my_profile.My_Role;

/**
 * Created by apple on 2017/3/30.
 */

public class My_Profile extends Activity implements View.OnClickListener{
    private TextView rt, my_name, my_gender, my_role;
    private RelativeLayout photo, name, qr_code, gender, role;
    private ImageView my_photo, my_qr_code;
    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.my_profile);
        rt = (TextView)findViewById(R.id.rt_my_profile);
        rt.setText("<");
        rt.setOnClickListener(this);

        photo = (RelativeLayout)findViewById(R.id.next_photo);
        photo.setOnClickListener(this);

        name = (RelativeLayout)findViewById(R.id.next_name);
        name.setOnClickListener(this);

        qr_code = (RelativeLayout)findViewById(R.id.next_QR_Code);
        qr_code.setOnClickListener(this);

        gender = (RelativeLayout)findViewById(R.id.next_gender);
        gender.setOnClickListener(this);

        role = (RelativeLayout)findViewById(R.id.next_role);
        role.setOnClickListener(this);

//        my
        my_name = (TextView)findViewById(R.id.my_name);
        my_gender = (TextView)findViewById(R.id.my_gender);
        my_role = (TextView)findViewById(R.id.my_role);
        my_photo = (ImageView)findViewById(R.id.my_photo);
        my_qr_code = (ImageView)findViewById(R.id.my_qr_code);


    }

    @Override
    public  void onClick(View view){
        Intent intent;
        Bitmap bitmap;
        switch (view.getId()){
            case R.id.rt_my_profile:
                finish();
                break;
            case R.id.next_photo:
                intent = new Intent(My_Profile.this, My_Photo.class);
                bitmap = ((BitmapDrawable)my_photo.getDrawable()).getBitmap();
                intent.putExtra("photo",bitmap);
                startActivity(intent);
                break;
            case R.id.next_name:
                intent = new Intent(My_Profile.this, My_Name.class);
                intent.putExtra("name", my_name.getText().toString());
                startActivityForResult(intent, 2);
                break;
            case R.id.next_QR_Code:
                intent = new Intent(My_Profile.this, My_QR_Code.class);
                startActivity(intent);
                break;
            case R.id.next_gender:
                intent = new Intent(My_Profile.this, My_Gender.class);
                intent.putExtra("gender", my_gender.getText().toString());
                startActivityForResult(intent, 5);
                break;
            case R.id.next_role:
                intent = new Intent(My_Profile.this, My_Role.class);
                startActivity(intent);
                break;
            default:
                break;
        }

    }
    @Override
    protected  void onActivityResult(int requestCode, int resultCode, Intent data){
        switch (requestCode){
            case 2:
                if(resultCode == RESULT_OK){
                    String result = data.getStringExtra("name");
                    my_name.setText(result);
                }
                break;
            case 5:
                if(resultCode == RESULT_OK){
                    String result = data.getStringExtra("gender");
                    my_gender.setText(result);
                }
        }
    }
}
