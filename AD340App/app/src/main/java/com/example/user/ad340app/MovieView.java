package com.example.user.ad340app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
//import com.squareup.picasso.Picasso;
//import android.widget.ImageView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class MovieView extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_view);

        Toolbar myChildToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myChildToolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);

        Intent intent2 = getIntent();

        String title = intent2.getStringExtra("Title");
        String year = intent2.getStringExtra("Year");
        String director = intent2.getStringExtra("Director");
        //String image = intent.getStringExtra("Image");
        String description = intent2.getStringExtra("Description");

        TextView titleView2 = findViewById(R.id.titleView2);
        TextView yearView2 = findViewById(R.id.yearView2);
        TextView descriptionView = findViewById(R.id.descriptionView);
        TextView directorView = findViewById(R.id.directorView);
        //ImageView image = findViewById(R.id.image);

        titleView2.setText("Title: " + title);
        yearView2.setText("Year: " + year);
        directorView.setText("Director: " + director);
        descriptionView.setText("Description: " + description);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.movie_view_menu, menu);

        // Configure the search info and add any event listeners...

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI...
                return true;

            case R.id.action_like_this_movie:
                // User chose the "Like" action, mark the current item
                // as a favorite...
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

}
