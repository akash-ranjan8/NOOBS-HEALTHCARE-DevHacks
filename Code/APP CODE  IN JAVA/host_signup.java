package com.learn.kiit.shramseva;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.shashank.sony.fancytoastlib.FancyToast;

public class host_signup extends AppCompatActivity {
EditText Phone,Password,Name,Email;
static String name;
FirebaseAuth auth;
ProgressBar progressBar;
Button signUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_signup);
        Name = findViewById(R.id.name);
        Password=findViewById(R.id.password);
        Email =findViewById(R.id.email);
        Phone = findViewById(R.id.phone);
        signUp=findViewById(R.id.signup);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        auth = FirebaseAuth.getInstance();
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = Name.getText().toString();
                String email = Email.getText().toString().trim();
                String password = Password.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    FancyToast.makeText(getApplicationContext(), "Enter email address!", FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    FancyToast.makeText(getApplicationContext(), "Enter Password!", FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();
                    return;
                }

                if (password.length() < 6) {
                    FancyToast.makeText(getApplicationContext(), "Password Too Short , Minimum 6 Characters!", FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                //create user
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(host_signup.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                FancyToast.makeText(getApplicationContext(), "Successfully Signed Up!", FancyToast.LENGTH_LONG,FancyToast.SUCCESS,true).show();
                                progressBar.setVisibility(View.GONE);
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    FancyToast.makeText(getApplicationContext(), "Error Signing Up", FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();
                                } else {
                                    startActivity(new Intent(host_signup.this, host_signin_screen.class));
                                    finish();
                                }
                            }
                        });

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }
}

