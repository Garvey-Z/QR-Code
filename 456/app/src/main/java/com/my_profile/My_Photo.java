package com.my_profile;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;

import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import com.google.zxing.client.android.R;


/**
 * Created by apple on 2017/3/30.
 */

public class My_Photo extends Activity {
    private Bitmap bitmap;
    private TextView rt;
    private static final int PHOTO_REQUEST_CAREMA = 1;// 拍照
    private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
    private static final int PHOTO_REQUEST_CUT = 3;// 结果

    private ImageView iv_image;
    private String path ="";

    /* 头像名称 */
    private static final String PHOTO_FILE_NAME = "temp_photo.jpg";
    private File tempFile;
    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("My_Photo");
        actionBar.setDisplayShowHomeEnabled(false);
       setContentView(R.layout.my_photo);

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       // setSuppportActionBar(toolbar);
        iv_image = (ImageView)findViewById(R.id.save_photo);
        Intent intent = getIntent();
        bitmap = intent.getParcelableExtra("photo");
        iv_image.setImageBitmap(bitmap);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.v("1", "123123");
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.picture_open, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Intent intent = new Intent(Intent.ACTION_VIEW);
        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        switch (item.getItemId()) {

            case R.id.open_camera:
                camera();
                break;
            case R.id.open_gallery:
               // intent.setClassName(this, HelpActivity.class.getName());
               // startActivity(intent);
                gallery();
                break;
            case android.R.id.home:
                Intent intent1 = new Intent();
                bitmap = ((BitmapDrawable)iv_image.getDrawable()).getBitmap();
                intent1.putExtra("photo",bitmap);
                intent1.putExtra("path", path);
                setResult(RESULT_OK, intent1);
                finish();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
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
     * 从相机获取
     */
    public void camera() {
        // 激活相机
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        // 判断存储卡是否可以用，可用进行存储
        if (hasSdcard()) {
            tempFile = new File(Environment.getExternalStorageDirectory(),
                    PHOTO_FILE_NAME);
            // 从文件中创建uri
            Uri uri = Uri.fromFile(tempFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        }
        // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CAREMA
        startActivityForResult(intent, PHOTO_REQUEST_CAREMA);
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

    /*
     * 判断sdcard是否被挂载
     */
    private boolean hasSdcard() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
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

        } else if (requestCode == PHOTO_REQUEST_CAREMA) {
            // 从相机返回的数据
            if (hasSdcard()) {


                crop(Uri.fromFile(tempFile));

                Log.i("path2", Uri.fromFile(tempFile).getPath().toString());
            } else {
                Toast.makeText(getApplicationContext(), "未找到存储卡，无法存储照片！",Toast.LENGTH_SHORT).show();
                Log.i("w","未找到存储卡，无法存储照片！");
            }

        }else if (requestCode == PHOTO_REQUEST_CUT) {
            // 从剪切图片返回的数据
            if (data != null) {
                Bitmap bitmap = data.getParcelableExtra("data");
                FileOutputStream fileOutputStream = null;
                try {
                    // 获取 SD 卡根目录
                    String saveDir = Environment.getExternalStorageDirectory() + "/meitian_photos";
                    // 新建目录
                    File dir = new File(saveDir);
                    if (! dir.exists()) dir.mkdir();
                    // 生成文件名
                    SimpleDateFormat t = new SimpleDateFormat("yyyyMMddssSSS");
                    String filename = "MT" + (t.format(new Date())) + ".jpg";
                    // 新建文件
                    File file = new File(saveDir, filename);
                    // 打开文件输出流
                    fileOutputStream = new FileOutputStream(file);
                    // 生成图片文件
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                    // 相片的完整路径
                    this.path = file.getPath();

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                this.iv_image.setImageBitmap(bitmap);

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
