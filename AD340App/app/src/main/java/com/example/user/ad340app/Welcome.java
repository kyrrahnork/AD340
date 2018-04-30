package com.example.user.ad340app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

public class Welcome extends AppCompatActivity {

    public final String TAG = "Welcome activity";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        Log.d(TAG, "onCreate done");

        Intent intent = getIntent();
        Log.d(TAG, "getIntent done");

        String welcome = intent.getStringExtra("Name");
        Log.d(TAG, "retrieved name");

        TextView greeting = findViewById(R.id.welcomeTextView);

        greeting.setText("Welcome, " + welcome);

    }
}
