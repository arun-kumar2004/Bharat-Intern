package com.example.todoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.todoapp.databinding.ActivitySignUpPageBinding;

public class SignUpPage extends AppCompatActivity {

    ActivitySignUpPageBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Inflate the layout using ViewBinding
        binding = ActivitySignUpPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Set up the toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Create an instance of the database helper
        MyHelper helper = new MyHelper(this);

        // Handle the sign-up button click
        binding.signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Get the input values
                String uname = binding.editusername.getText().toString();
                String em = binding.editemail.getText().toString();
                String pass = binding.editpassword.getText().toString();

                // Check if any field is empty
                if (uname.equals("") || em.equals("") || pass.equals("")) {
                    Toast.makeText(SignUpPage.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                } else {
                    // Check if the username already exists
                    boolean i = helper.checkusername(uname);
                    if (i == false) {
                        // Insert the user record into the database
                        boolean insert = helper.insertdata(uname, em, pass);
                        if (insert == true) {
                            Toast.makeText(SignUpPage.this, "Record Saved", Toast.LENGTH_SHORT).show();
                            // Start the main activity
                            Intent intent = new Intent(SignUpPage.this, MainPage.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(SignUpPage.this, "Failed to saved record", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(SignUpPage.this, "User Already Exist ", Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });

    }
}