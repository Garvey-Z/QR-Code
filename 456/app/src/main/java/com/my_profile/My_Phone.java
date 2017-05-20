package com.my_profile;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.zxing.client.android.R;

/**
 * Created by apple on 2017/5/20.
 */

public class My_Phone extends Activity{
    TextView rt, save;
    EditText phone;
    String phone_String;
    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);
        ActionBar actionBar = getActionBar();
        actionBar.hide();
        setContentView(R.layout.my_phone);
        Intent intent = getIntent();


        rt = (TextView)findViewById(R.id.rt_save_phone);
        rt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        save = (TextView)findViewById(R.id.save_phone);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent();
                intent1.putExtra("phone", phone.getText().toString());
                setResult(RESULT_OK, intent1);
                finish();
            }
        });

        phone = (EditText)findViewById(R.id.new_phone);
        phone_String = intent.getStringExtra("phone").toString();
        phone.setText(phone_String);
        phone.setSelection(phone_String.length());
        phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!phone_String.equals(phone.getText().toString()))
                    save.setTextColor(Color.rgb(0, 255, 00));
                else
                    save.setTextColor(Color.rgb(0, 50, 00));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}
