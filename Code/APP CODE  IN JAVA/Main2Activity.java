package com.learn.kiit.shramseva;

import androidx.appcompat.app.AppCompatActivity;

import android.app.admin.DevicePolicyManager;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextPaint;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.shashank.sony.fancytoastlib.FancyToast;

import org.w3c.dom.Text;

public class Main2Activity extends AppCompatActivity {
    static ProgressBar webprogress;
    Button button1, button2, button3, Webview;
    ImageView shram;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        textView = findViewById(R.id.textView3);
        textView.setTranslationX(-500);
        webprogress = findViewById(R.id.progressBar3);
        webprogress.setVisibility(View.INVISIBLE);
        final Intent intent = new Intent(this, administrative_login.class);
        final Intent intent1 = new Intent(this, host_login.class);
        final Intent intent2 = new Intent(this, host_signup.class);
        button1 = (Button) findViewById(R.id.button);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        Webview = findViewById(R.id.WebView);
        shram = findViewById(R.id.imageView2);
        shram.animate().translationX(1000).setDuration(3000);
        textView.animate().translationX(70).setDuration(3000);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FancyToast.makeText(Main2Activity.this, "Redirecting To Administrative Login", FancyToast.LENGTH_SHORT, FancyToast.DEFAULT, true).show();
                startActivity(intent);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FancyToast.makeText(Main2Activity.this, "Redirecting To Host Login", FancyToast.LENGTH_SHORT, FancyToast.DEFAULT, true).show();
                startActivity(intent1);
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FancyToast.makeText(Main2Activity.this, "Redirecting To Host Sign Up", FancyToast.LENGTH_SHORT, FancyToast.DEFAULT, true).show();
                startActivity(intent2);
            }
        });
        Webview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webprogress.setVisibility(View.VISIBLE);
                startActivity(new Intent(getApplicationContext(), webview.class));
            }
        });
    }

}
