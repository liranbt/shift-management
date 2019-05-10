package com.FinalProject;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.*;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.appcompat.app.AppCompatActivity;


public class LoginActivity extends AppCompatActivity {

    private EditText email, password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        email = findViewById(R.id.txtEmail);
        password  = findViewById(R.id.txtPassword);
        Button login = findViewById(R.id.btnLogin);


        login.setOnClickListener(v -> {
            Validator.validateUser(email, password, new Callback() {
                @Override
                public void onLoginCallback(boolean success, boolean isAdmin, long userId)
                {
                    if(success)
                    {
                        // Move to Calendar Activity
                        Intent intent = new Intent(LoginActivity.this, CalendarActivity.class);
                        SharedPreferences sp = getSharedPreferences("sp", 0);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putBoolean("isAdmin", isAdmin);
                        editor.putLong("userId", userId);
                        editor.apply();
                        startActivity(intent);
                    }
                    else
                    {
                        // Issue Toast message
                        Toast.makeText(getApplicationContext(),"Incorrect Email / Password", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        });
    }
}

