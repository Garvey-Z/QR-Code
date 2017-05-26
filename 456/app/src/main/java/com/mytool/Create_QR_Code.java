package com.mytool;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.android.Contents;
import com.google.zxing.client.android.Intents;
import com.google.zxing.client.android.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
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
 * Created by apple on 2017/4/9.
 */

public class Create_QR_Code extends Activity {
    private EditText name, size, price, number, cost, from;
    private String Name, Size, Price, Number, Cost, From;
    private TextView rt, create, choose_pic;
    private ImageView picture;
    private File tempFile;
    private String path ="";
    private OkHttpClient mOkHttpClient;
    private static final int PHOTO_REQUEST_CAREMA = 1;// 拍照
    private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
    private static final int PHOTO_REQUEST_CUT = 3;// 结果
    private String codenumber="";
    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);
        ActionBar actionBar = getActionBar();
        actionBar.hide();
        setContentView(R.layout.create_qr_code);

        name = (EditText)findViewById(R.id.thing_name);
        size = (EditText)findViewById(R.id.thing_size);
        price = (EditText)findViewById(R.id.thing_price);
        number = (EditText)findViewById(R.id.thing_number);
        cost = (EditText)findViewById(R.id.thing_cost);
        from = (EditText)findViewById(R.id.thing_from);
        picture = (ImageView)findViewById(R.id.thing_picture);

        choose_pic = (TextView)findViewById(R.id.choose_pic);
        rt = (TextView)findViewById(R.id.rt_create_qrcode);
        create = (TextView)findViewById(R.id.create_qrcode);
        rt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        choose_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gallery();
            }
        });
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Name = name.getText().toString();
                Size = size.getText().toString();
                Price = price.getText().toString();
                Number = number.getText().toString();
                Cost = cost.getText().toString();
                From = from.getText().toString();
                //launchSearch("http://120.25.247.207:8081/getinfo.php?");

                if(Name.equals("") | Size.equals("") | Price.equals("")|Number.equals("")|Cost.equals("")|From.equals("")){
                    Toast.makeText(getApplicationContext(),"信息不能为空!",Toast.LENGTH_SHORT).show();
                }
                else{
                    codenumber = new Random().nextInt(1000)+"";
                    uploadMultiFile();
                    launchSearch("http://120.25.247.207:8081/getinfo.php?number=" + codenumber);
                }
            }
        });



    }
    private void uploadMultiFile() {
        final String url = "http://120.25.247.207:8081/receive_info.php";
        File file = new File(path);
        RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("userfile",Name +".png", fileBody)
                .addFormDataPart("name", Name)
                .addFormDataPart("size", Size)
                .addFormDataPart("price", Price)
                .addFormDataPart("prime_cost", Cost)
                .addFormDataPart("number", Number)
                .addFormDataPart("pop", From)
                .addFormDataPart("codenumber",codenumber)
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

    private void launchSearch(String text) {
        Intent intent = new Intent(Intents.Encode.ACTION);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        intent.putExtra(Intents.Encode.TYPE, Contents.Type.TEXT);
        intent.putExtra(Intents.Encode.DATA, text);
        intent.putExtra(Intents.Encode.FORMAT, BarcodeFormat.QR_CODE.toString());
        intent.putExtra("codenumber", codenumber);
        startActivity(intent);
    }

    /*
     * 从相册获取
     */
    public void gallery() {
        // 激活系统图库，选择一张图片
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_GALLERY
        startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
    }

    /*
     * 剪切图片
     */
    private void crop(Uri uri) {
        // 裁剪图片意图
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // 裁剪框的比例，1：1
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // 裁剪后输出图片的尺寸大小
        intent.putExtra("outputX", 250);
        intent.putExtra("outputY", 250);

        intent.putExtra("outputFormat", "JPEG");// 图片格式
        intent.putExtra("noFaceDetection", true);// 取消人脸识别
        intent.putExtra("return-data", true);
        // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CUT
        startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PHOTO_REQUEST_GALLERY) {
            // 从相册返回的数据
            if (data != null) {
                // 得到图片的全路径
                Uri uri = data.getData();
                Cursor cursor = getContentResolver().query(uri, null, null, null,null);
                if (cursor != null && cursor.moveToFirst()) {
                    path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
                }
                Log.i("path1", path);
                crop(uri);
            }

        } else if (requestCode == PHOTO_REQUEST_CUT) {
            // 从剪切图片返回的数据
            if (data != null) {
                Bitmap bitmap = data.getParcelableExtra("data");

                this.picture.setImageBitmap(bitmap);
            }
            try {
                // 将临时文件删除
                tempFile.delete();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
