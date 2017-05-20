package com.mytool;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.client.android.R;

import java.util.Random;

public class Login extends Activity{
    //登录按钮
    private Button btLogin;
    //账户
    private DeletableEditText userEditText;
    //密码
    private DeletableEditText psdEditText;


    //验证码的数字文本
    private TextView tvHideA,tvHideB,tvHideC,tvHideD;

    //验证码的图片文本
    private ImageView ivNumA,ivNumB,ivNumC,ivNumD;
    //验证码输入文本
    private EditText etCheck;
    //验证码的检测显示文本
    private TextView tvCheck;

    //存储每个验证码的数字
    private String numStrTmp = "";
    //存储整个验证码的数字
    private String numStr = "";

    //存储验证码的数组
    private int[] numArray = new int[4];
    //存储颜色的数组
    private int[] colorArray = new int[6];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getActionBar();
        actionBar.hide();
        setContentView(R.layout.login);
        setupViews();

    }

    private void setupViews() {
        btLogin = (Button) findViewById(R.id.bt_login);
        btLogin.setOnClickListener(new OnClickListenerImpl());
        userEditText = (DeletableEditText) findViewById(R.id.tv_user);
        psdEditText = (DeletableEditText) findViewById(R.id.tv_psd);

        tvHideA = (TextView) findViewById(R.id.tvHideA);
        tvHideB = (TextView) findViewById(R.id.tvHideB);
        tvHideC = (TextView) findViewById(R.id.tvHideC);
        tvHideD = (TextView) findViewById(R.id.tvHideD);

        ivNumA = (ImageView) findViewById(R.id.ivNumA);
        ivNumB = (ImageView) findViewById(R.id.ivNumB);
        ivNumC = (ImageView) findViewById(R.id.ivNumC);
        ivNumD = (ImageView) findViewById(R.id.ivNumD);

        ivNumA.setOnClickListener(new OnClickListenerImpl());
        ivNumB.setOnClickListener(new OnClickListenerImpl());
        ivNumC.setOnClickListener(new OnClickListenerImpl());
        ivNumD.setOnClickListener(new OnClickListenerImpl());

        tvCheck = (TextView) findViewById(R.id.tvCheck);
        etCheck = (EditText) findViewById(R.id.etCheck);

        setNum();
    }
    private void setNum() {
        initNum();
        tvHideA.setText("" + numArray[0]);
        tvHideA.setTextColor(randomColor());
        tvHideB.setText("" + numArray[1]);
        tvHideB.setTextColor(randomColor());
        tvHideC.setText("" + numArray[2]);
        tvHideC.setTextColor(randomColor());
        tvHideD.setText("" + numArray[3]);
        tvHideD.setTextColor(randomColor());


        Matrix matrixA = new Matrix();
        //重设矩阵
        matrixA.reset();
        matrixA.setRotate(randomAngle());
        Bitmap bmNumA = Bitmap.createBitmap(getBitmapFromView(tvHideA,20,50),0,0,20,50,matrixA,true);
        ivNumA.setImageBitmap(bmNumA);

        Matrix matrixB = new Matrix();
        //重设矩阵
        matrixB.reset();
        matrixB.setRotate(randomAngle());
       Bitmap bmNumB = Bitmap.createBitmap(getBitmapFromView(tvHideB,20,50),0,0,20,50,matrixB,true);
        ivNumB.setImageBitmap(bmNumB);

        Matrix matrixC = new Matrix();
        //重设矩阵
        matrixC.reset();
        matrixC.setRotate(randomAngle());
        Bitmap bmNumC = Bitmap.createBitmap(getBitmapFromView(tvHideC,20,50),0,0,20,50,matrixC,true);
        ivNumC.setImageBitmap(bmNumC);

        Matrix matrixD = new Matrix();
        //重设矩阵
        matrixD.reset();
        matrixD.setRotate(randomAngle());
        Bitmap bmNumD = Bitmap.createBitmap(getBitmapFromView(tvHideD,20,50),0,0,20,50,matrixD,true);
        ivNumD.setImageBitmap(bmNumD);

    }

    private Bitmap getBitmapFromView(View v, int width, int height ) {
        int widSpec = View.MeasureSpec.makeMeasureSpec(width,View.MeasureSpec.EXACTLY);
        int heiSpec = View.MeasureSpec.makeMeasureSpec(height,View.MeasureSpec.EXACTLY);
        //重新绘制图片大小
        v.measure(widSpec, heiSpec);
        v.layout(0, 0, width, height);
        Bitmap bitmap = Bitmap.createBitmap(width,height, Bitmap.Config.ARGB_8888);

        //画出图片
        Canvas canvas = new Canvas(bitmap);
        v.draw(canvas);

        return bitmap;

    }


    //设置随机倾斜的角度
    private int randomAngle() {
        return 20*(new Random().nextInt(5)-new Random().nextInt(3));
    }



    //随机生成颜色
    private int randomColor() {
        colorArray[0] = 0xFF000000; //BLACK
        colorArray[1] = 0xFFFF00FF; // MAGENTA
        colorArray[2] = 0xFFFF0000; // RED
        colorArray[3] = 0xFF00FF00; // GREEN
        colorArray[4] = 0xFF0000FF; // BLUE
        colorArray[5] = 0xFF00FFFF; // CYAN
        int randomColoId = new Random().nextInt(5);
        return colorArray[randomColoId];




    }
    //初始化验证码
    private void initNum() {
        numStr="";
        numStrTmp="";
        for (int i = 0; i < numArray.length; i++) {
            //随机生成10以内数字
            int numIntTmp = new Random().nextInt(10);
            //保存各个验证码
            numStrTmp = String.valueOf(numIntTmp);
            //保存整个验证码
            numStr = numStr+numStrTmp;
            numArray[i] = numIntTmp;
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
                if (TextUtils.isEmpty(psdEditText.getText().toString())){
                    //为空时抖动提示
                    psdEditText.setShakeAnimation();
                    Toast.makeText(Login.this,"账户或密码不能为空",Toast.LENGTH_SHORT).show();
                }

                //验证输入的验证码是否正确
                if(etCheck.getText().toString()!=null&&etCheck.getText().toString().trim().length()>0){
                    tvCheck.setVisibility(View.VISIBLE);
                    if (numStr.equals(etCheck.getText().toString())){
                        tvCheck.setTextColor(Color.GREEN);
                        tvCheck.setText("验证码正确！");
                    }else{
                        tvCheck.setTextColor(Color.RED);
                        tvCheck.setText("验证码错误！");
                        etCheck.setText("");
                        setNum();
                    }
                }
                //如果OnClick不是登录按钮时只剩下验证码图片有监听事件。等同于点击验证码图片。改变验证码。
            }else {
                setNum();
                tvCheck.setVisibility(View.GONE);
            }


        }
    }
}
