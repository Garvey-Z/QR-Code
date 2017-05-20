package com.mytool;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.android.Contents;
import com.google.zxing.client.android.Intents;
import com.google.zxing.client.android.R;

/**
 * Created by apple on 2017/4/9.
 */

public class Create_QR_Code extends Activity {
    private EditText text;
    private Button bt;
    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);
        ActionBar actionBar = getActionBar();
        actionBar.hide();
        setContentView(R.layout.create_qr_code);

        text = (EditText)findViewById(R.id.text_qr_code);
        bt = (Button)findViewById(R.id.bt_create);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchSearch(text.getText().toString());
            }
        });


    }
    private void launchSearch(String text) {
        Intent intent = new Intent(Intents.Encode.ACTION);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        intent.putExtra(Intents.Encode.TYPE, Contents.Type.TEXT);
        intent.putExtra(Intents.Encode.DATA, text);
        intent.putExtra(Intents.Encode.FORMAT, BarcodeFormat.QR_CODE.toString());
        startActivity(intent);
    }
}
