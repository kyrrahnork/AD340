package com.example.user.ad340app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;


public class MovieView extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



    }

}
