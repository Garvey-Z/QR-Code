package com.text;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.zxing.client.android.CaptureActivity;
import com.google.zxing.client.android.R;
import com.mytool.Login;

/**
 * Created by apple on 2017/3/30.
 */

public class text1 extends Activity {
    private Button btn1;
    protected void  onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.main_activity);
        btn1 = (Button)findViewById(R.id.bt1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(text1.this, Login.class);
                startActivity(intent);
            }
        });
    }
}
