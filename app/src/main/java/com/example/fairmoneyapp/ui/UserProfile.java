package com.example.fairmoneyapp.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.fairmoneyapp.R;

public class UserProfile extends AppCompatActivity {
    private String firstname, lastname, email, picture;
    private TextView tvFname, tvLname, tvEmail;
    private ImageView imageView;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        getSupportActionBar().hide();

        tvFname = findViewById(R.id.tv_firstname);
        tvLname = findViewById(R.id.tv_lastname);
        tvEmail = findViewById(R.id.tv_email);
        imageView = findViewById(R.id.userPic);

        intent = getIntent();
        firstname = intent.getStringExtra("fName");
        lastname = intent.getStringExtra("lName");
        email = intent.getStringExtra("email");
        picture = intent.getStringExtra("picture");

        tvEmail.setText(email);
        tvLname.setText(lastname);
        tvFname.setText(firstname);

        Glide.with(getApplicationContext())
                .load(picture)
                .asBitmap()
                .into(imageView);
    }
}