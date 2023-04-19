package com.example.trainingapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.trainingapp.DataBase.MatchDatabaseHelper;
import com.example.trainingapp.R;

public class ActivityLogin extends AppCompatActivity {

    private EditText editTextUsername;
    private EditText editTextPassword;
    private Button buttonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);




        // Get references to the UI elements
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);


        // Set up the click listener for the login button
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform login authentication
                String username = editTextUsername.getText().toString();
                String password = editTextPassword.getText().toString();
                if (username.equals("admin") && password.equals("password")) {
                    // Successful login
                        Intent intent = new Intent(ActivityLogin.this, ActivityHome.class);

                         startActivity(intent);
                    System.out.println("ok");
                } else {
                    // Invalid login
                    Toast.makeText(ActivityLogin.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}
