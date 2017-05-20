package com.my_profile;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

import com.google.zxing.client.android.R;

/**
 * Created by apple on 2017/3/30.
 */

public class My_QR_Code extends Activity {
    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);
        ActionBar actionBar = getActionBar();
        actionBar.hide();
        setContentView(R.layout.my_qr_code);
    }
}
