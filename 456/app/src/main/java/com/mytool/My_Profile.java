package com.mytool;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.client.android.CaptureActivity;
import com.google.zxing.client.android.R;
import com.modify_identity.Modify_identity;
import com.my_profile.My_Gender;
import com.my_profile.My_Name;
import com.my_profile.My_Phone;
import com.my_profile.My_Photo;
import com.my_profile.My_QR_Code;
import com.text.text1;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.content.ContentValues.TAG;

/**
 * Created by apple on 2017/3/30.
 */

public class My_Profile extends Activity implements View.OnClickListener{
    private TextView  my_name, my_gender, my_role, my_id, my_phone;
    private RelativeLayout photo, name, gender, phone;
    private ImageView my_photo;
    private String photo_path = "";
    private OkHttpClient mOkHttpClient;
    private Bitmap bitmap_photo;
    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("My_Profile");
        actionBar.setDisplayShowHomeEnabled(false);

        setContentView(R.layout.my_profile);


        photo = (RelativeLayout)findViewById(R.id.next_photo);
        photo.setOnClickListener(this);

        name = (RelativeLayout)findViewById(R.id.next_name);
        name.setOnClickListener(this);



        gender = (RelativeLayout)findViewById(R.id.next_gender);
        gender.setOnClickListener(this);



        phone = (RelativeLayout)findViewById(R.id.next_phone);
        phone.setOnClickListener(this);

        my_id = (TextView)findViewById(R.id.my_ID);
        my_phone =(TextView)findViewById(R.id.my_phone);
//        my
        my_name = (TextView)findViewById(R.id.my_name);
        my_gender = (TextView)findViewById(R.id.my_gender);
        my_role = (TextView)findViewById(R.id.my_role);
        my_photo = (ImageView)findViewById(R.id.my_photo);


       //uploadMultiFile();
        //post_infomation();
        getinfomation();
        downpicture();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.v("1", "123123");
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_profile, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Intent intent = new Intent(Intent.ACTION_VIEW);
        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        switch (item.getItemId()) {

            case R.id.menu_scan:
                Intent intent1 = new Intent(My_Profile.this, CaptureActivity.class);
                startActivity(intent1);
                break;
            case R.id.menu_create:
                Intent intent2 = new Intent(My_Profile.this, Create_QR_Code.class);
                startActivity(intent2);
                break;
            case R.id.menu_quit:
                Intent intent3 = new Intent(My_Profile.this, Modify_identity.class);
                startActivity(intent3);
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    public  void onClick(View view){
        Intent intent;
        Bitmap bitmap;
        switch (view.getId()){

            case R.id.next_photo:
                intent = new Intent(My_Profile.this, My_Photo.class);
                bitmap = ((BitmapDrawable)my_photo.getDrawable()).getBitmap();
                intent.putExtra("photo",bitmap);
                startActivityForResult(intent, 1);
                break;
            case R.id.next_name:
                intent = new Intent(My_Profile.this, My_Name.class);
                intent.putExtra("name", my_name.getText().toString());
                startActivityForResult(intent, 2);
                break;
            case R.id.next_gender:
                intent = new Intent(My_Profile.this, My_Gender.class);
                intent.putExtra("gender", my_gender.getText().toString());
                startActivityForResult(intent, 5);
                break;
            case R.id.next_phone:
                intent = new Intent(My_Profile.this, My_Phone.class);
                intent.putExtra("phone",my_phone.getText().toString());
                startActivityForResult(intent, 6);
                break;
            default:
                break;
        }

    }
    @Override
    protected  void onActivityResult(int requestCode, int resultCode, Intent data){
        switch (requestCode){
            case 1:
                if(resultCode == RESULT_OK){
                    my_photo.setImageBitmap((Bitmap) data.getParcelableExtra("photo"));
                    if(!data.getStringExtra("path").equals(""))
                         photo_path = data.getStringExtra("path");
                    uploadMultiFile();
                }
                break;
            case 2:
                if(resultCode == RESULT_OK){
                    String result = data.getStringExtra("name");
                    my_name.setText(result);
                    post_infomation();
                }
                break;
            case 5:
                if(resultCode == RESULT_OK){
                    String result = data.getStringExtra("gender");
                    my_gender.setText(result);
                    post_infomation();
                    Log.d("gender", result);
                    Log.d("phone", my_phone.getText().toString());
                }
                break;
            case 6:
                if(resultCode == RESULT_OK){
                    String result = data.getStringExtra("phone");
                    my_phone.setText(result);
                    post_infomation();
                }
                break;
        }

    }
    private void post_infomation() {
        Log.d("post_phone",my_phone.getText().toString());
        mOkHttpClient=new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("account", my_id.getText().toString())
                .add("nickname",my_name.getText().toString())
                .add("gender",my_gender.getText().toString())
                .add("phone",my_phone.getText().toString())
                .build();
        Request request = new Request.Builder()
                .url("http://120.25.247.207:8081/update.php")
                .post(formBody)
                .build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "post_infomation() e=" + e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str = response.body().string();
                Log.i("wangshu", str);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "请求成功", Toast.LENGTH_SHORT).show();
                    }
                });
            }

        });
    }

    public static final MediaType MEDIA_TYPE_MARKDOWN
            = MediaType.parse("text/x-markdown; charset=utf-8");

    private void uploadMultiFile() {
        final String url = "http://120.25.247.207:8081/user_file.php";
        Log.d("path",photo_path);
        File file = new File(photo_path);
        RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("userfile",my_id.getText().toString()+"_photo.png", fileBody)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();


        final okhttp3.OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder();
        OkHttpClient okHttpClient  = httpBuilder
                //设置超时
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "uploadMultiFile() e=" + e);
            }


            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i(TAG, "uploadMultiFile() response=" + response.body().string());
            }
        });
    }

    Handler handler = new Handler(){
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            String str = (String)msg.obj;
            switch (msg.what) {
                case  1:
                    try {
                        JSONArray jsonArray = new JSONArray(str);
                        JSONObject info = jsonArray.getJSONObject(0);
                        my_name.setText(info.getString("nickname").toString());
                        my_id.setText(info.getString("account").toString());
                        my_gender.setText(info.getString("gender").toString());
                        my_role.setText(info.getString("identity").toString());
                        my_phone.setText(info.getString("phone").toString());
                        Log.d("name", info.getString("nickname").toString());
                    } catch (Exception e) {
                    }
                    break;
                case 2:
                    my_photo.setImageBitmap(bitmap_photo);
                    break;
                }



            }

    };
    private void getinfomation() {
        mOkHttpClient=new OkHttpClient();
        SharedPreferences pref = getSharedPreferences("userData",MODE_PRIVATE);
        String account = pref.getString("account","");
        RequestBody formBody = new FormBody.Builder()
                .add("account", account)
                .build();
        Request request_post = new Request.Builder()
                .url("http://120.25.247.207:8081/getuserinfo.php")
                .post(formBody)
                .build();
        Call call = mOkHttpClient.newCall(request_post);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "post_infomation() e=" + e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str = response.body().string();
                Log.i("wangshu", str);



                   Message message = new Message();
                   message.what = 1;
                   message.obj = str;
                handler.sendMessage(message);


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "请求成功", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
    private void downpicture() {
        mOkHttpClient = new OkHttpClient();
        String url = "http://120.25.247.207:8081/account/" + my_id.getText().toString() +"_photo.png";
        Request request = new Request.Builder().url(url).build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) {
                InputStream inputStream = response.body().byteStream();
                bitmap_photo = BitmapFactory.decodeStream(inputStream);
                Message message = new Message();
                message.what = 2;
                handler.sendMessage(message);
               /* FileOutputStream fileOutputStream = null;
                try {
                    fileOutputStream = new FileOutputStream(new File("/sdcard/wangshu.jpg"));
                    byte[] buffer = new byte[2048];
                    int len = 0;
                    while ((len = inputStream.read(buffer)) != -1) {
                        fileOutputStream.write(buffer, 0, len);
                    }
                    fileOutputStream.flush();
                } catch (IOException e) {
                    Log.i("wangshu", "IOException");
                    e.printStackTrace();
                }

                Log.d("wangshu", "文件下载成功");*/
            }
        });
    }

}
