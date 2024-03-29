package com.example.todoapp;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.example.todoapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding1;
    SharedPreferences sp;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize the view binding
        binding1 = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding1.getRoot());

        // Set the toolbar as the activity's action bar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Create an instance of the database helper
        MyHelper helper = new MyHelper(this);

        // Set click listener for the signup button to navigate to the signup page
        binding1.signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SignUpPage.class));
            }
        });

        // Initialize shared preferences for storing login information
        sp = getSharedPreferences("Data", MODE_PRIVATE);
        editor = sp.edit();
        boolean login = sp.getBoolean("ISLOGGEDIN", false);

        // Set click listener for the login button
        binding1.loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String uname = binding1.editusername.getText().toString();
                String pass = binding1.editpassword.getText().toString();

                if (uname.equals("") || pass.equals("")) {
                    // Check if the username or password is empty
                    Toast.makeText(MainActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                } else {
                    // Check if the entered username and password are valid
                    boolean checkuserpass = helper.checkusernameandpassword(uname, pass);
                    if (checkuserpass == true) {
                        // If the credentials are valid, store the login information and navigate to the main page
                        editor.putString("username", uname);
                            editor.putString("password", pass);
                            editor.putBoolean("ISLOGGEDIN", true);
                            editor.apply();
                            Toast.makeText(MainActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(MainActivity.this, MainPage.class));
                            finish();
                    } else {
                        // If the credentials are invalid, show an error message
                        Toast.makeText(MainActivity.this, "Invalid Details", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}


