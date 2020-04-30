package com.learn.kiit.shramseva;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.shashank.sony.fancytoastlib.FancyToast;


public class host_login extends AppCompatActivity {
SignInButton googlebutton;
static FirebaseAuth mAuth;
GoogleApiClient mGoogleApiClient;
private final static int RC_SIGN_IN = 2;
FirebaseAuth.AuthStateListener mAuthListener;
static String name1;
static Button Login;
static FirebaseAuth auth;
static ProgressBar progressBar;
static EditText Email,Password;
static String id;

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_login);
        Email = findViewById(R.id.email1);
        Password = findViewById(R.id.password1);
        progressBar = findViewById(R.id.progressBar2);
        progressBar.setVisibility(View.INVISIBLE);
        googlebutton = (SignInButton)findViewById(R.id.googleBtn);
        Login= findViewById(R.id.button4);
        mAuth = FirebaseAuth.getInstance();

        googlebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                signIn();
            }
        });
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()!=null) {
                    FancyToast.makeText(host_login.this, "Signed In Successfully", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();
                    startActivity(new Intent(host_login.this, host_signin_screen.class));
                }
            }
        };

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build();
        mGoogleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
            @Override
            public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                FancyToast.makeText(host_login.this,"SomeThing Went Wrong",FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();
            }
        }).addApi(Auth.GOOGLE_SIGN_IN_API,gso).build();

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = Email.getText().toString();
                final String password = Password.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    FancyToast.makeText(host_login.this,"Enter Email Please",FancyToast.LENGTH_SHORT,FancyToast.ERROR,true).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    FancyToast.makeText(host_login.this,"Enter Password Please",FancyToast.LENGTH_SHORT,FancyToast.ERROR,true).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                //authenticate user
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(host_login.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                progressBar.setVisibility(View.GONE);
                                if (!task.isSuccessful()) {
                                    // there was an error
                                    if (password.length() < 6) {
                                        id= auth.getUid();
                                        Password.setError("Password Not Valid or Incorrect");
                                    } else {
                                        FancyToast.makeText(host_login.this,"Authentication Failed",FancyToast.LENGTH_SHORT,FancyToast.ERROR,true).show();
                                    }
                                } else {
                                    Intent intent = new Intent(host_login.this, host_signin_screen.class);
                                    FancyToast.makeText(host_login.this,"Signed In Successfully",FancyToast.LENGTH_SHORT,FancyToast.SUCCESS,true).show();
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });
            }
        });
    }

private void signIn(){
    Intent signInIntent=Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
    startActivityForResult(signInIntent,RC_SIGN_IN);
}

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                FancyToast.makeText(host_login.this,"Successful",FancyToast.LENGTH_LONG,FancyToast.SUCCESS,true).show();// Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                FancyToast.makeText(host_login.this,"Access Denied Due To API",FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();
                // ...
            }
        }
    }

    private void firebaseAuthWithGoogle(final GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FancyToast.makeText(host_login.this,"Credential Successful",FancyToast.LENGTH_LONG,FancyToast.SUCCESS,true).show(); // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            name1 = account.getDisplayName().toString();
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "signInWithCredential:failure", task.getException());
                            FancyToast.makeText(host_login.this,"Access Denied Due To Credential",FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();
                           // updateUI(null);
                        }

                        // ...
                    }
                });
    }
}
