package com.modify_identity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.client.android.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.content.ContentValues.TAG;

/**
 * Created by apple on 2017/5/25.
 */

public class Modify_identity extends Activity {
    private List<User_infomation> info_list = new ArrayList<>();
    private OkHttpClient mOkHttpClient;
    private TextView rt, save;
    private ListView listView;
    private int list_size = 0;
    private User_Adapter user_adapter;
    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);
        ActionBar actionBar = getActionBar();
        actionBar.hide();
        setContentView(R.layout.modify_role);
        getinfomation();

        rt = (TextView)findViewById(R.id.rt_save_identity);
        save = (TextView)findViewById(R.id.save_identity);
        rt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {

                listView.setAdapter(user_adapter);
                user_adapter.notifyDataSetChanged();
                for (int i = 0; i < list_size; i++) {
                    User_infomation ui = (User_infomation) listView.getItemAtPosition(i);
                    //post_infomation(ui.getAccount(), ui.getIdentity());
                    Log.d(ui.getAccount(),ui.getIdentity());
                }
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
                        for(int i = 0 ;i < jsonArray.length(); ++ i) {
                            JSONObject info = jsonArray.getJSONObject(i);
                            User_infomation user_infomation = new User_infomation(info.getString("account").toString(),
                                    info.getString("nickname").toString(), info.getString("identity").toString());
                            info_list.add(user_infomation);

                        }
                        list_size = info_list.size();
                        user_adapter = new User_Adapter(Modify_identity.this,R.layout.list_item, info_list);
                        listView = (ListView)findViewById(R.id.list_modify_role);
                        listView.setAdapter(user_adapter);


                    } catch (Exception e) {
                    }
                    break;

            }



        }

    };

    private void getinfomation() {
        mOkHttpClient=new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()

                .build();
        Request request_post = new Request.Builder()
                .url("http://120.25.247.207:8081/getalluser.php")
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
                message.obj = str;
                message.what = 1;
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
    private void post_infomation(String account, String identity) {
        mOkHttpClient=new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("account",account)
                .add("identity",identity)
                .build();
        Request request = new Request.Builder()
                .url("http://120.25.247.207:8081/setuseriden.php")
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
}
