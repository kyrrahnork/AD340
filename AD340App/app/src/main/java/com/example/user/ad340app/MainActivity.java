package com.example.user.ad340app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button submitButton = findViewById(R.id.submitButton);
    }

    public void send(View v){
        Intent intent = new Intent(this, Welcome.class);
        EditText visitorNameEditText = (EditText) findViewById(R.id.visitorNameEditText);

        String message = visitorNameEditText.getText().toString();
        intent.putExtra("Name", message);
                startActivity(intent);
    }
}
