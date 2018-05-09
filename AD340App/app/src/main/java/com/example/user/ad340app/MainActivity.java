package com.example.user.ad340app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private SharedPreferences sharedPrefs;
    private String visitorName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_drawer);

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, myToolbar, R.string.drawerOpen, R.string.drawerClose);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        sharedPrefs = this.getPreferences(Context.MODE_PRIVATE);
        visitorName = sharedPrefs.getString("visitorName", "");

        EditText visitorNameEditText = findViewById(R.id.visitorNameEditText);
        visitorNameEditText.setText(visitorName);

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

        submitButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText visitorNameEditText = findViewById(R.id.visitorNameEditText);
                TextView textView2 = findViewById(R.id.textView2);


                String name = visitorNameEditText.getText().toString();


                if(inputIsValid(name)){

                        SharedPreferences.Editor editor = sharedPrefs.edit();
                        editor.putString("visitorName", name);
                        editor.apply();

                    Intent intent = new Intent(MainActivity.this, Welcome.class);
                    intent.putExtra("Name", name);
                    startActivity(intent);
                }//end of if string is not empty
                else{
                    textView2.setText("Error, please enter a valid name");
                }//string is empty

            }//end of onClick
        });

    }

        @Override
        public void onBackPressed(){
            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            if (drawer.isDrawerOpen(GravityCompat.START)){
                drawer.closeDrawer(GravityCompat.START);
            } else {
                super.onBackPressed();
            }
        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        // Configure the search info and add any event listeners...

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Toast.makeText(getApplicationContext(), "Settings option clicked!", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.action_like:
                // User chose the "Like" action, mark the current item
                // as a favorite...
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_drawer_about) {

            Intent intent = new Intent(this, About.class);
            startActivity(intent);
        } else if (id == R.id.nav_drawer_movie_list) {

            Intent intent = new Intent(this, MovieList.class);
            startActivity(intent);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public boolean inputIsValid(String str){
        if (str.length() == 0){
            return false;
        }
        return true;
    }


}
