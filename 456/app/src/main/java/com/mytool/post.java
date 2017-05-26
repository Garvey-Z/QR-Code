package com.mytool;

import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.content.ContentValues.TAG;

/**
 * Created by Think on 2017/5/26.
 */

public class post {
    private OkHttpClient mOkHttpClient;

    public void post_infomation(String account, String identity) {
    mOkHttpClient = new OkHttpClient();
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

        }

    });
}
}
