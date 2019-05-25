
package com.FinalProject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.FirebaseDatabase;

public class CreateAccountActivity extends AppCompatActivity {

    TextView name, email, password;
    public ContentValues values;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        Button createAcountbtn = findViewById(R.id.createAcountButton);

        name = (TextView) findViewById(R.id.newAccountName);
        email = (TextView) findViewById(R.id.newAccountEmail);
        password = (TextView) findViewById(R.id.newAccountPassword);
        FirebaseDatabase db = FirebaseDatabase.getInstance();

//        createAcountbtn.setOnClickListener(v -> {
//            Validator.validateNewUser(email, new Callback() {
//                @Override
//                public void onLoginCallback(boolean success, boolean isAdmin, long userId) {
//                    if (success) {
//                    }
//                }
//
//
//            })
//
//        })
    }
}