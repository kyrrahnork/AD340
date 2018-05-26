package com.example.user.ad340app;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.AbstractQueue;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private FusedLocationProviderClient mFusedLocationClient;
    private LocationCallback mLocationCallback;

    private LocationRequest mLocationRequest;
    private Location mLastLocation;
    private GoogleMap mMap;

    private final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 9;
    protected boolean mAddressRequested;
    protected String mAddressOutput;

    protected TextView mLatitudeText;
    protected TextView mLongitudeText;
    protected TextView mLocationText;
    private RequestQueue requestQueue;
    private ImageLoader imageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

//        Toolbar myChildToolbar = findViewById(R.id.my_map_toolbar);
//        setSupportActionBar(myChildToolbar);
//
//        // Get a support ActionBar corresponding to this toolbar
//        ActionBar ab = getSupportActionBar();
//
//        // Enable the Up button
//        ab.setDisplayHomeAsUpEnabled(true);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        requestQueue = Volley.newRequestQueue(this);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        createLocationCallback();
        getLocation();
        parseJSON();
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

    @Override
    public void onConnected(Bundle connectionHint) {}

    @Override
    public void onConnectionSuspended(int i) {

    }



    public void updateUI() {
        if (mLastLocation == null) {
            // get location updates

            startLocationUpdates();
        } else {





            LatLng myLocation = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
            mMap.setMinZoomPreference(10); // zoom to city level
            mMap.addMarker(new MarkerOptions().position(myLocation)
                    .title("My current location"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(myLocation));
        }
    }

    protected void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mFusedLocationClient.requestLocationUpdates(mLocationRequest,
                    mLocationCallback,
                    null /* Looper */);        }
    }

    private void createLocationCallback() {
        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {

                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    mLastLocation = location;
                    updateUI();
                }
            };
        };
    }

    public void getLocation() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                ==PackageManager.PERMISSION_GRANTED){

            mFusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {


                            // Got last known location. In some rare situations this can be null.
                            if (location != null) {
                                // Logic to handle location object
                                mLastLocation = location;
                                updateUI();
                            }
                        }
                    });
        } else {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.


                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);

            }
        }

    }

    private void parseJSON() {
       final String URL = "https://web6.seattle.gov/Travelers/api/Map/Data?zoomId=17&type=2";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("Features");

                            for(int i = 1; i < jsonArray.length(); i+=2) {
                                JSONObject feature =  jsonArray.getJSONObject(i);

                                // get coordinates of camera
                                JSONArray coordinates = feature.getJSONArray("PointCoordinate");
                                double latitude = coordinates.getDouble(0);
                                double longitude = coordinates.getDouble(1);

                                JSONArray cameras = feature.getJSONArray("Cameras");
                                JSONObject camera = cameras.getJSONObject(0);
                                String type = camera.getString("Type");
                                String imageURL = camera.getString("ImageUrl");
                                String desc = camera.getString("Description");
                                if (type.equals("sdot")) {
                                    imageURL = "http://www.seattle.gov/trafficcams/images/"
                                            + imageURL;
                                } else {
                                    imageURL = "http://images.wsdot.wa.gov/nw/" + imageURL;
                                }

                                CameraItem cam = new CameraItem(imageURL, desc);

                                // Create info window
                                CustomInfoWindow customWindow =
                                        new CustomInfoWindow(MapsActivity.this);
                                mMap.setInfoWindowAdapter(customWindow);

                                //add map marker
                                if (type.equals("sdot")) {
                                    Marker marker = mMap.addMarker(new MarkerOptions()
                                            .position(new LatLng(latitude, longitude))
                                            .title(desc));
                                    marker.setTag(cam);
                                    marker.showInfoWindow();
                                } else {
                                    Marker marker = mMap.addMarker(new MarkerOptions()
                                            .position(new LatLng(latitude, longitude))
                                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                                            .title(desc));
                                    marker.setTag(cam);
                                    marker.showInfoWindow();
                                }

                            }
                            } catch (JSONException error) { // json error
                            Toast.makeText(MapsActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                            error.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) { // COULD NOT CONNECT, NEEDS TESTING WITH WIFI OFF
                Toast.makeText(MapsActivity.this,
                        "Network ERROR!  Please check you internet connection:\n" + error.toString(), Toast.LENGTH_LONG).show();
                error.printStackTrace();
            }
        });


        requestQueue.add(request);

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, "Connection Failed!", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        updateUI();
    }


    private class CustomInfoWindow implements GoogleMap.InfoWindowAdapter {

        private Context context;

        public CustomInfoWindow(Context context) {
            this.context = context;
        }

        @Override
        public View getInfoWindow(Marker marker) {
            return null;
        }

        @Override
        public View getInfoContents(Marker marker) {
            // Check if the marker is a camera
            if (marker.getTag() != null) {
                View view = ((Activity) context).getLayoutInflater().inflate(R.layout.activity_info__window,
                        null);
                TextView cameraName = view.findViewById(R.id.info_window_desc);
                ImageView cameraImg = view.findViewById(R.id.info_window_image);

                cameraName.setText(marker.getTitle());
                CameraItem camera = (CameraItem) marker.getTag();
                String imageURL = camera.getImageURL();


                Picasso.with(view.getContext()).load(imageURL).error(R.mipmap.ic_launcher)
                        .resize(640, 480).into(cameraImg,
                                new MarkerCallback(marker));

                return view;
            } else {
                return null;
            }
        }

        private class MarkerCallback implements Callback {
            Marker marker = null;

            MarkerCallback(Marker marker) {
                this.marker = marker;
            }

            public void onError() {

            }

            @Override
            public void onSuccess() {
                if (marker == null) {
                    return;
                }

                if (!marker.isInfoWindowShown()) {
                    return;
                }

                marker.hideInfoWindow();
                marker.showInfoWindow();
            }

            @Override
            public void onError(Exception e) {

            }
        }
    }
}

