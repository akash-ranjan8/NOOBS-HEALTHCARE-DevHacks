package com.learn.kiit.shramseva;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import static com.learn.kiit.shramseva.Main2Activity.webprogress;

public class update extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        WebView webView = (WebView) findViewById(R.id.web_view1);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webprogress.setVisibility(View.INVISIBLE);
        webView.loadUrl("https://shramseva-updates.netlify.app/");
    }
}
