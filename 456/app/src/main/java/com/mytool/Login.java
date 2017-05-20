package com.mytool;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.client.android.R;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Login extends Activity{
    //登录按钮
    private Button btLogin;
    //账户
    private DeletableEditText userEditText;
    //密码
    private DeletableEditText pwdEditText;
    //验证码
    private Identifying_code idcode;


    //验证码的图片文本
    private ImageView ivNum;
    //验证码输入文本
    private EditText etCheck;

    //存储整个验证码的数字
    private String numStr = "";
    OkHttpClient client = new OkHttpClient();
    //远程端地址
    private String url = "http://120.25.247.207:8081/getpwd.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        setupViews();

    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            switch (msg.what) {
                case 1: {
                    String val = data.getString("password");
                    if (TextUtils.equals(pwdEditText.getText().toString(), val)) {
                        Toast.makeText(getApplicationContext(), "密码正确", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            }
        }
    };


    private void setupViews() {
        btLogin = (Button) findViewById(R.id.login_btn);
        btLogin.setOnClickListener(new OnClickListenerImpl());
        userEditText = (DeletableEditText) findViewById(R.id.login_account);
        pwdEditText = (DeletableEditText) findViewById(R.id.login_password);

        ivNum = (ImageView) findViewById(R.id.login_ivNumA);

        ivNum.setOnClickListener(new OnClickListenerImpl());

        etCheck = (EditText) findViewById(R.id.login_etCheck);

        setNum();
    }
    private void setNum() {
        idcode = Identifying_code.getInstance();
        Bitmap bitmap = idcode.createBitmap();
        ivNum.setImageBitmap(bitmap);
        numStr = idcode.getCode();

    }

    String post(String url, String password) throws IOException {
        RequestBody builder = new FormBody.Builder().add("account", password).build();
        Request request = new Request.Builder()
                .url(url)
                .post(builder)
                .build();
        Response response = client.newCall(request).execute();

        if (response.isSuccessful())
        {
            return response.body().string();
        } else {
            throw new IOException("Unexpected code " + response);
        }

    }

    private class OnClickListenerImpl implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            //当点击的为登录按钮时
            if(v==btLogin){
                //判断账户字符是否为空，
                if (TextUtils.isEmpty(userEditText.getText().toString())){
                    //为空时抖动提示
                    userEditText.setShakeAnimation();
                    Toast.makeText(Login.this,"账户或密码不能为空",Toast.LENGTH_SHORT).show();
                }
                //判断密码字符是否为空
                if (TextUtils.isEmpty(pwdEditText.getText().toString())){
                    //为空时抖动提示
                    pwdEditText.setShakeAnimation();
                    Toast.makeText(Login.this,"账户或密码不能为空",Toast.LENGTH_SHORT).show();
                }

                //验证输入的验证码是否正确
                if(etCheck.getText().toString()!=null&&etCheck.getText().toString().trim().length()>0)
                {
                    Toast.makeText(getApplicationContext(), "验证码正确", Toast.LENGTH_SHORT).show();
                    if (numStr.equalsIgnoreCase(etCheck.getText().toString().trim())){
                        new Thread() {
                            @Override
                            public void run() {
                                try
                                {
                                    String password = post(url, userEditText.getText().toString());
                                    Message message = new Message();
                                    message.what = 1;
                                    message.obj = password;
                                    handler.sendMessage(message);
                                } catch (Exception ex)
                                {
                                    ex.printStackTrace();
                                }
                            }
                        }.start();

                    }else{
                        Toast.makeText(getApplicationContext(), "验证码错误",Toast.LENGTH_SHORT).show();
                        setNum();
                    }
                }
            }else {
                setNum();
            }


        }
    }
}
