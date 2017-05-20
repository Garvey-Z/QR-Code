package com.text;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.google.zxing.client.android.CaptureActivity;
import com.google.zxing.client.android.R;
import com.mytool.Create_QR_Code;
import com.mytool.Login;
import com.mytool.My_Profile;

/**
 * Created by apple on 2017/3/30.
 */

public class text1 extends Activity {
    private Button btn1, btn2, btn3, btn4;
    protected void  onCreate(Bundle bundle){
        super.onCreate(bundle); requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main_activity);

        btn1 = (Button)findViewById(R.id.bt1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(text1.this, Login.class);
                startActivity(intent);
            }
        });
        btn2 = (Button)findViewById(R.id.bt2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(text1.this, CaptureActivity.class);
                startActivity(intent);
            }
        });
        btn3 = (Button)findViewById(R.id.bt3);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(text1.this, My_Profile.class);
                startActivity(intent);
            }
        });
        btn4 = (Button)findViewById(R.id.bt4);
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(text1.this, Create_QR_Code.class);
                startActivity(intent);
            }
        });

    }
}
