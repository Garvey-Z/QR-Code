package com.my_profile;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.client.android.R;

/**
 * Created by apple on 2017/3/30.
 */

public class My_Photo extends Activity {
    private ImageView photo;
    private Bitmap bitmap;
    private TextView rt;
    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.my_photo);
        photo = (ImageView)findViewById(R.id.save_photo);
        Intent intent = getIntent();
        bitmap = intent.getParcelableExtra("photo");
        photo.setImageBitmap(bitmap);

        rt = (TextView)findViewById(R.id.rt_save_photo);
        rt.setText("< My Profile");
        rt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
