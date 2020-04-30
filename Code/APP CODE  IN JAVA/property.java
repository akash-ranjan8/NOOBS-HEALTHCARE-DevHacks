package com.learn.kiit.shramseva;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shashank.sony.fancytoastlib.FancyToast;

import static com.learn.kiit.shramseva.host_login.id;
import static com.learn.kiit.shramseva.host_login.mAuth;
import static com.learn.kiit.shramseva.host_login.progressBar;

public class property extends AppCompatActivity {
    static EditText PropertyName, OwnerName, Owned, Type, Locationp, Price;
    Button save, navigation;
    propertyInfo propertyinfo;
    int c = 0;
    DatabaseReference reference;
    String intented;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property);
        PropertyName = findViewById(R.id.editText);
        OwnerName = findViewById(R.id.name);
        Owned = findViewById(R.id.editText1);
        Type = findViewById(R.id.editText2);
        Locationp = findViewById(R.id.editText3);
        save = findViewById(R.id.button6);
        Price = findViewById(R.id.price);
        c++;
        navigation = findViewById(R.id.navigation);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String propertyName = PropertyName.getText().toString();
                String ownerName = OwnerName.getText().toString();
                String owned = Owned.getText().toString();
                String type = Type.getText().toString();
                String location = Locationp.getText().toString();
                String price = Price.getText().toString();
                propertyinfo = new propertyInfo(propertyName, ownerName, owned, type, location, price);
                reference = FirebaseDatabase.getInstance().getReference("Property");
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        reference.child(mAuth.getUid()).setValue(propertyinfo).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    FancyToast.makeText(property.this, "Saved Successfully", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, true).show();
                                } else {
                                    FancyToast.makeText(property.this, "Failed To Save", FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                                }
                            }

                        });
                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                startActivity(new Intent(getApplicationContext(), host_signin_screen.class));
            }
        });

        navigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(property.this,MapsActivity.class);
                startActivityForResult(intent,1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1){
            if(resultCode==RESULT_OK){
                String result = data.getStringExtra("result");
                Locationp.setText(result);

            }
        }
    }
}

