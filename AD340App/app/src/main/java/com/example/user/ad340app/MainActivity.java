package com.example.user.ad340app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.view.View.OnClickListener;
import android.widget.Toast;
import android.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Toolbar myToolbar = findViewById(R.id.my_toolbar);
        //setSupportActionBar(myToolbar);

        Button submitButton = findViewById(R.id.submitButton);
        Button button1 = findViewById(R.id.mainButton1);
        Button button2 = findViewById(R.id.mainButton2);
        Button button3 = findViewById(R.id.mainButton3);
        Button button4 = findViewById(R.id.mainButton4);

        button1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToMovieList = new Intent(MainActivity.this, MovieList.class);
                startActivity(goToMovieList);
            }
        });

        button2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Button 2 clicked!", Toast.LENGTH_SHORT).show();
            }
        });

        button3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Button 3 clicked!", Toast.LENGTH_SHORT).show();
            }
        });

        button4.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Button 4 clicked!", Toast.LENGTH_SHORT).show();
            }
        });


    }



    public void send(View v){
        Intent intent = new Intent(this, Welcome.class);
        EditText visitorNameEditText = (EditText) findViewById(R.id.visitorNameEditText);

        String message = visitorNameEditText.getText().toString();
        intent.putExtra("Name", message);
                startActivity(intent);
    }

}
