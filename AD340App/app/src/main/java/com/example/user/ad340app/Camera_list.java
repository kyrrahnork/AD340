package com.example.user.ad340app;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class Camera_list extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CameraAdapter cameraAdapter;
    private ArrayList<CameraItem> camerasList;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_list);

        Toolbar myChildToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myChildToolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.camera_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        camerasList = new ArrayList<>();
        cameraAdapter = new CameraAdapter(Camera_list.this, camerasList);
        recyclerView.setAdapter(cameraAdapter);

        requestQueue = Volley.newRequestQueue(this);

        parseJSON();
    }




    private void parseJSON() {
        String URL = "https://web6.seattle.gov/Travelers/api/Map/Data?zoomId=13&type=2";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("Features");

                            for(int i = 1; i < jsonArray.length(); i+=2) {
                                JSONObject feature =  jsonArray.getJSONObject(i);

                                JSONArray f =  feature.getJSONArray("Cameras");
                                for(int j = 0; j < f.length(); j++) {
                                    JSONObject ff =  f.getJSONObject(j);

                                    String name = ff.getString("Description");
                                    String imageURL = ff.getString("ImageUrl");

                                    camerasList.add(new CameraItem("http://www.seattle.gov/trafficcams/images/" + imageURL, name));
                                }

                            }
                            cameraAdapter.notifyDataSetChanged();
                            //cameraAdapter = new CameraAdapter(LiveCamerasActivity.this, cameraItemArrayList);
                            //recyclerView.setAdapter(cameraAdapter);



                        } catch (JSONException error) { // json error
                            Toast.makeText(Camera_list.this, error.toString(), Toast.LENGTH_SHORT).show();
                            error.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) { // COULD NOT CONNECT, NEEDS TESTING WITH WIFI OFF
                Toast.makeText(Camera_list.this,
                        "Network ERROR!  Please check you internet connection:\n" + error.toString(), Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        });


        requestQueue.add(request);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cam_list_menu, menu);

        // Configure the search info and add any event listeners...

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Toast.makeText(getApplicationContext(), "Settings option clicked!", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.action_share_cams_list:
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
