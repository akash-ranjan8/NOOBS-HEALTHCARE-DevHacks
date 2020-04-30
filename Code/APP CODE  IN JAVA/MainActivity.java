package com.learn.kiit.shramseva;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
ImageView front;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        front = (ImageView)findViewById(R.id.imageView);
        front.setTranslationX(30);
        front.setTranslationY(-120);
       final Intent intent  = new Intent(this,Main2Activity.class);

        new CountDownTimer(4000,4000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Animation anim = new AnimationUtils().loadAnimation(getApplicationContext(),R.anim.fade_in);
                front.startAnimation(anim);
            }
            @Override
            public void onFinish() {
                front.setAlpha(0);
                startActivity(intent);
                finish();
            }
        }.start();


    }

}
