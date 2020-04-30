package com.learn.kiit.shramseva;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.media.ImageReader;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class administrative_login extends AppCompatActivity {
Button labourRegistration,updates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrative_login);
        labourRegistration = findViewById(R.id.labourreg);
        updates = findViewById(R.id.update);
        labourRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(administrative_login.this,labourreg.class));
            }
        });
        updates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(administrative_login.this,update.class));
            }
        });


    }
}
