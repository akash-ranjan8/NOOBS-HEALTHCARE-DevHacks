package com.learn.kiit.shramseva;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shashank.sony.fancytoastlib.FancyToast;

import static com.learn.kiit.shramseva.host_signup.name;
import static com.learn.kiit.shramseva.host_login.name1;

public class host_signin_screen extends AppCompatActivity {
    Button logout;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
    TextView Intro, yourProperty1;
    Button Addproperty;
    ProgressBar progressProperty;


    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_signin_screen);
        logout = (Button) findViewById(R.id.signOut);
        Intro = findViewById(R.id.intro);

        Addproperty = findViewById(R.id.button5);
        yourProperty1 = findViewById(R.id.textView6);
        yourProperty1.setText("");
        progressProperty = findViewById(R.id.propertyloader);
        progressProperty.setVisibility(View.INVISIBLE);
        if (name1 != null) {
            Intro.setText("Hello " + name1 + "!");
        }
        if (name != null) {
            Intro.setText("Hello " + name + "!");
        } else {
            Intro.setText("Please Do Contribute To Help Your Country");
        }

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null) {
                    startActivity(new Intent(host_signin_screen.this, Main2Activity.class));
                }
            }
        };
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                mAuth.signOut();
            }
        });
        Addproperty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (yourProperty1.getText() == ""){
                    startActivity(new Intent(getApplicationContext(), property.class));
                } else {
                    FancyToast.makeText(getApplicationContext(), "Limited To One Property Per User,Cannot Add More Properties", FancyToast.LENGTH_LONG, FancyToast.INFO, true).show();

                }
            }
        });
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Property");
        progressProperty.setVisibility(View.VISIBLE);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(mAuth.getUid()).exists()) {
                    String ownern = "Owner Name :" + dataSnapshot.child(mAuth.getUid()).child("ownerName").getValue().toString();
                    String propertyn = "Property Name :" + dataSnapshot.child(mAuth.getUid()).child("propertyName").getValue().toString();
                    String owned = "Owned By :" + dataSnapshot.child(mAuth.getUid()).child("owned").getValue().toString();
                    String location = "Location :" + dataSnapshot.child(mAuth.getUid()).child("location").getValue().toString();
                    String type = "Type Of Property :" + dataSnapshot.child(mAuth.getUid()).child("type").getValue().toString();
                    String price = "Price :" + dataSnapshot.child(mAuth.getUid()).child("price").getValue().toString();
                    String result = ownern + "\n" + propertyn + "\n" + owned + "\n" + location + "\n" + type + "\n" + price;
                    progressProperty.setVisibility(View.INVISIBLE);
                    yourProperty1.setText(result);
                } else {
                    yourProperty1.setText("");
                    progressProperty.setVisibility(View.INVISIBLE);
                    FancyToast.makeText(getApplicationContext(), "No Property Added Yet !!", FancyToast.LENGTH_LONG, FancyToast.WARNING, true).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                FancyToast.makeText(getApplicationContext(), "SomeThing Went Wrong While Fetching Your Properties", FancyToast.LENGTH_LONG, FancyToast.DEFAULT, true).show();
            }
        });


    }
}
