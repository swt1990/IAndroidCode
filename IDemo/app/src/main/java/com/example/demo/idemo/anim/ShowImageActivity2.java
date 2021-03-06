package com.example.demo.idemo.anim;

import android.animation.AnimatorSet;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.demo.idemo.R;

public class ShowImageActivity2 extends AppCompatActivity implements View.OnClickListener {
    private ImageView iv;
    private LinearLayout llContainer;
    private float left, right, top, bottom;
    private int outWidth;
    private int outHeight;
    private int screenWidth;
    private float screenHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_image2);
        llContainer = (LinearLayout) findViewById(R.id.ll_show2);
        iv = (ImageView) findViewById(R.id.iv_show2);

        iv.setOnClickListener(this);
        Intent intent = getIntent();
        width1 = intent.getFloatExtra("width", 0);
        height1 = intent.getFloatExtra("height", 0);
        left = intent.getFloatExtra("left", 0);
        top = intent.getFloatExtra("top", 0);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        screenWidth = displayMetrics.widthPixels;
        screenHeight = displayMetrics.heightPixels - getStatusHeight();
        BitmapFactory.Options options = new BitmapFactory.Options();
        //设置为true,表示解析Bitmap对象，该对象不占内存
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), R.drawable.act_guide_three, options);
        outWidth = options.outWidth;
        outHeight = options.outHeight;
        float screenScale = screenHeight / screenWidth;
        int bitmapScale = outHeight / outWidth;
        if (bitmapScale > screenScale) {
            outHeight = (int) screenHeight;
            outWidth = outHeight / bitmapScale;
        } else {
            outWidth = (int) screenWidth;
            outHeight = outWidth * bitmapScale;
        }
        RelativeLayout.LayoutParams rParams = (RelativeLayout.LayoutParams) llContainer.getLayoutParams();
//        rParams.width = outWidth;
//        rParams.height = outHeight;
//        llContainer.setLayoutParams(rParams);
        //设置为false,解析Bitmap对象加入到内存中
        options.inJustDecodeBounds = false;
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) iv.getLayoutParams();
        params.width = (int) width1;
        params.height = (int) height1;
        params.leftMargin = (int) left;
        params.topMargin = (int) top;
        iv.setLayoutParams(params);
        llContainer.requestLayout();
        iv.invalidate();
        handler.postDelayed(null, 200);
    }

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            showAnim();

            return false;
        }
    });

    @Override
    public void onClick(View view) {
        showAnim1();
    }

    float width1, height1;

    private void showAnim() {
        float width = iv.getWidth();
        int height = iv.getHeight();
        float x = iv.getX();
        float y = iv.getY();
//        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
//        float widthPixels = displayMetrics.widthPixels;
//        float heightPixels = displayMetrics.heightPixels - getStatusHeight();
//        float widthPixels = llContainer.getWidth();
//        float heightPixels = llContainer.getHeight();
        float widthPixels = outWidth;
        float heightPixels = outHeight;
        float sX = width * x / (widthPixels - width);
        float sY = height * y / (heightPixels - height);
        ScaleAnimation animation = new ScaleAnimation(1f, widthPixels / width, 1f, heightPixels / height, sX, sY);
//        animation.setDuration(3000);
//        animation.setFillAfter(true);
//        iv.startAnimation(animation);








        float transY = screenHeight / 2 - iv.getY() - iv.getHeight() / 2;
        TranslateAnimation translateAnimation = new TranslateAnimation(0, 0, 0f, transY);
        AnimationSet animationSet = new AnimationSet(true);
        animationSet.setDuration(500);
        animationSet.addAnimation(translateAnimation);
        animationSet.addAnimation(animation);
        animationSet.setFillAfter(true);
        iv.startAnimation(animationSet);
    }

    private void showAnim1() {
        float width = iv.getWidth();
        float height = iv.getHeight();
        float x = iv.getX();
        float y = iv.getY();
//        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
//        float widthPixels = displayMetrics.widthPixels;
//        float heightPixels = displayMetrics.heightPixels - getStatusHeight();
//        float widthPixels = llContainer.getWidth();
//        float heightPixels = llContainer.getHeight();
        float widthPixels = outWidth;
        float heightPixels = outHeight;
        float sX = width * x / (widthPixels - width);
        float sY = height * y / (heightPixels - height);
        ScaleAnimation animation = new ScaleAnimation(widthPixels / width, 1f, heightPixels / height, 1f, sX, sY);
        animation.setDuration(3000);
        animation.setFillAfter(true);
        iv.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                finish();
                overridePendingTransition(0, 0);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    public float getStatusHeight() {
        float statusBarHeight2 = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusBarHeight2 = getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusBarHeight2;
    }
}
