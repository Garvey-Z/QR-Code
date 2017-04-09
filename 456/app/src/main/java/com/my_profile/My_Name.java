package com.my_profile;

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
 * Created by apple on 2017/3/30.
 */

public class My_Name extends Activity {
    TextView rt, save;
    EditText name;
    String name_String;
    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.my_name);
        Intent intent = getIntent();


        rt = (TextView)findViewById(R.id.rt_save_name);
        rt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        save = (TextView)findViewById(R.id.save_name);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent();
                intent1.putExtra("name", name.getText().toString());
                setResult(RESULT_OK, intent1);
                finish();
            }
        });

        name = (EditText)findViewById(R.id.new_name);
        name_String = intent.getStringExtra("name").toString();
        name.setText(name_String);
        name.setSelection(name_String.length());
        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!name_String.equals(name.getText().toString()))
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
