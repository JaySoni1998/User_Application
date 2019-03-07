package com.example.jayso.wheelersslotbooking;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.app.Dialog;
import android.bluetooth.BluetoothClass;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.GpsStatus;
import android.location.Location;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.jayso.wheelersslotbooking.models.PlaceInfo;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener {

    private static final String KEY_PLACE_NO = "Place_No";
    private static final String KEY_PLACE_NAME = "Parking_Place_Name";
    private static final String KEY_ADDESS = "Address";
    private static final String KEY_AREA_CODE = "Area_Code";
    private static final String KEY_LETITUTE = "Latitude";
    private static final String KEY_LONGITUDE = "Longitude";

    private long backPressedTime;

    //map
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public void onMapReady(GoogleMap googleMap) {
        //Toast.makeText(this, "Map is ready", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onMapReady: map is ready");
        mMap = googleMap;

        if (mLocationPermissionsGranted) {
            getDeviceLocation();

            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                return;
            }
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setAllGesturesEnabled(true);

//            getLocation();
            init();
        }

        //When Map Loads Successfully
        mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                //Your code where exception occurs goes here...

                getLocation();
                /*List<LatLng> locations = new ArrayList<>();
                locations.add(new LatLng(23.039327, 72.531411));
                locations.add(new LatLng(23.046695, 72.530994));
                locations.add(new LatLng(23.043966, 72.631519));
                locations.add(new LatLng(23.118513, 72.624612));

                for (LatLng latLng : locations) {
                    mMap.addMarker(new MarkerOptions().position(latLng).title("Alpha One Mall" +
                            "").icon(BitmapDescriptorFactory.fromResource(R.drawable.map_marker)));
                }
                //LatLngBound will cover all your marker on Google Maps
                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                builder.include(locations.get(0)); //Taking Point A (First LatLng)
                builder.include(locations.get(locations.size() - 1)); //Taking Point B (Second LatLng)
                LatLngBounds bounds = builder.build();
                CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 200);
                mMap.moveCamera(cu);*/
//                mMap.animateCamera(CameraUpdateFactory.zoomTo(14), 2000, null);
            }
        });

    }


    /*===============    get Place location     ==========================*/


    private void getLocation() {


        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                Constants.URL_LOCATION,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            if (!obj.getBoolean("error")) {
//                                JSONObject locData = obj.getJSONObject("location");

                                final JSONArray jsonArray = obj.getJSONArray("location");

//                                            List<LatLng> locations = new ArrayList<>();
                                try {
                                    LatLngBounds.Builder builder = new LatLngBounds.Builder();

                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject locData = jsonArray.getJSONObject(i);



                                        LatLng latLng = new LatLng(Double.parseDouble(locData.getString("Latitude").toString()),
                                                Double.parseDouble(locData.getString("Longitude").toString()));



                                        mMap.addMarker(new MarkerOptions().position(latLng).
                                                title("" + locData.getString("Parking_Place_Name"))
                                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.map_marker)));

                                        builder.include(latLng);
                                    }

                                    try {

                                        View view = findViewById(R.id.bottom_sheet_show_loc);
                                        final TextView place_address = view.findViewById(R.id.place_address);
                                        final TextView main_location = view.findViewById(R.id.main_location);

                                        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                                            @Override
                                            public boolean onMarkerClick(Marker marker) {

                                                for (int x = 0; x < jsonArray.length(); x++) {
                                                    try {
                                                        JSONObject locData1 = jsonArray.getJSONObject(x);
                                                        if (locData1.getString("Parking_Place_Name").equals(marker.getTitle())) {
                                                            main_location.setText(locData1.getString("Parking_Place_Name"));
                                                            place_address.setText(locData1.getString("Address"));

                                                            mPlace = new PlaceInfo();
//                                                            mPlace.setLatlng(latLng);
                                                            mPlace.setAddress(locData1.getString("Address"));
                                                            mPlace.setName(locData1.getString("Parking_Place_Name"));
                                                            mPlace.setPin(locData1.getString("Area_Code"));
                                                        }
                                                    } catch (Exception e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                                return false;
                                            }
                                        });


                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }


                                    LatLngBounds bounds = builder.build();
                                    CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 12);
                                    mMap.moveCamera(cu);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            } else {
                                Toast.makeText(
                                        getApplicationContext(),
                                        obj.getString("message"),
                                        Toast.LENGTH_LONG
                                ).show();


                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("Error", response);
                            Toast.makeText(MapActivity.this, "json error", Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MapActivity.this, "error", Toast.LENGTH_SHORT).show();
                    }
                }
        ) /*{
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Email_ID", email);
                params.put("Password", pass);
                return params;
            }
        }*/;
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }


    private void getDeviceLocation() {
        Log.d(TAG, "getDeviceLocation: getting the device current location");

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);


        try {


            if (mLocationPermissionsGranted) {
                final com.google.android.gms.tasks.Task<Location> location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull com.google.android.gms.tasks.Task<Location> task) {
                        if (task.isSuccessful()) {


                            Log.d(TAG, "onComplete: found location!");
                            Location currentLocation = (Location) task.getResult();
                            String latitude = String.valueOf(currentLocation.getLatitude());
                            String longitude = String.valueOf(currentLocation.getLongitude());
                            //Toast.makeText(MapActivity.this, latitude + " - " + longitude, Toast.LENGTH_SHORT).show();
                            moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()), DEFAULT_ZOOM, "My Location");

                        } else {
                            Log.d(TAG, "onComplete: ");
                        }
                    }
                });
            }
        } catch (SecurityException e) {
            Log.e(TAG, "getDeviceLocation: SecurityException" + e.getMessage());
        }
    }

    private void moveCamera(LatLng latLng, float zoom, PlaceInfo placeInfo) {
        Log.d(TAG, "moveCamera: moving the camera to: lat: " + latLng.latitude + ", lng: " + latLng.longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));

        mMap.clear();

        //mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter(MapActivity.this));

        if (placeInfo != null) {
            try {
                String snippet = "Address: " + placeInfo.getAddress() + "\n" +
                        "Phone Number: " + placeInfo.getPhoneNumber() + "\n" +
                        "Website: " + placeInfo.getWebsiteUri() + "\n" +
                        "Price Rating: " + placeInfo.getRating() + "\n";

                MarkerOptions options = new MarkerOptions()
                        .position(latLng)
                        .title(placeInfo.getName()).icon(BitmapDescriptorFactory.fromResource(R.drawable.map_marker));
//                        .snippet(snippet);
                mMarker = mMap.addMarker(options);

            } catch (NullPointerException e) {
                Log.e(TAG, "moveCamera: NullPointerException: " + e.getMessage());
            }
        } else {
            mMap.addMarker(new MarkerOptions().position(latLng));
        }

        hideSoftKeyboard();
    }

    private void moveCamera(LatLng latLng, float zoom, String title) {
        Log.e(TAG, "moveCamera: moving the camera to: lat: " + latLng.latitude + ", lng: " + latLng.latitude);
        mMap.clear();

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));

        //Live Location Marker
       /* if (!title.equals("MyLocation")) {
            MarkerOptions markerOptions = new MarkerOptions().position(latLng).title(title);
            mMap.addMarker(markerOptions);
        }*/
        hideSoftKeyboard();
    }

    private void initMap() {
        //Toast.makeText(this, "init map", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "initMap: initializing map");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(MapActivity.this);
    }

    String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION};

    private static final String TAG = "MapActivity";
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 15f;
    private static final LatLngBounds LAT_LNG_BOUNDS = new LatLngBounds(
            new LatLng(-40, -168), new LatLng(71, 136));


    //widgets
    private AutoCompleteTextView mSearchText;


    //vars
    private Boolean mLocationPermissionsGranted = false;
    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private PlaceAutocompleteAdapter mPlaceAutocompleteAdapter;
    private GoogleApiClient mGoogleApiClient = null;
    private PlaceInfo mPlace;
    private Marker mMarker;

    private static final String TAG1 = "MapActivity";
    private static final int ERROR_DIALOG_REQUEST = 9001;

    //*map

    ArrayList<HashMap<String, String>> location = new ArrayList<HashMap<String, String>>();
    HashMap<String, String> map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
//        mSearchText = findViewById(R.id.search_location);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Button btnbooking = findViewById(R.id.btn_booking);
        btnbooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MapActivity.this, BookingActivity.class);
                startActivity(it);

            }
        });

        Button btnMoreInfo = findViewById(R.id.btn_more_info);
        btnMoreInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MapActivity.this, MoreInfoActivity.class);

                it.putExtra("NAME",mPlace.getName());
                it.putExtra("ADDRESS",mPlace.getAddress());
                it.putExtra("PIN",mPlace.getPin());

                startActivity(it);

            }
        });


//        if (isServicesOK()) init();

        getLocationPermission(); //map


//        mSearchText = (SearchView) findViewById(R.id.search_location);

        init();


    }

    private void getLocationPermission() {
        Log.d(TAG, "getLocationPermission: getting location permissions");
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(), COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionsGranted = true;
                initMap();
            } else {
                ActivityCompat.requestPermissions(this,
                        permissions,
                        LOCATION_PERMISSION_REQUEST_CODE);
            }
        } else {
            ActivityCompat.requestPermissions(this,
                    permissions,
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: called.");
        mLocationPermissionsGranted = false;

        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            mLocationPermissionsGranted = false;
                            Log.d(TAG, "onRequestPermissionsResult: permission failed");
                            return;
                        }
                    }
                    Log.d(TAG, "onRequestPermissionsResult: permission granted");
                    mLocationPermissionsGranted = true;
                    //initialize our map
                    initMap();
                }
            }
        }
    }

    //map
    private void init() {

        Log.d(TAG, "init: initializing");

        if (mGoogleApiClient == null) {

            mGoogleApiClient = new GoogleApiClient
                    .Builder(this)
                    .addApi(Places.GEO_DATA_API)
                    .addApi(Places.PLACE_DETECTION_API)
                    .enableAutoManage(this, this)
                    .build();

//            mSearchText.setOnItemClickListener(mAutocompleteClickListener);

        } else {
            //Toast.makeText(this, "Search your parking place", Toast.LENGTH_LONG).show();
        }

        hideSoftKeyboard();

//        mPlaceAutocompleteAdapter = new PlaceAutocompleteAdapter(this, mGoogleApiClient, LAT_LNG_BOUNDS, null);

//        mSearchText.setAdapter(mPlaceAutocompleteAdapter);

        /*mSearchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || event.getAction() == KeyEvent.ACTION_DOWN
                        || event.getAction() == KeyEvent.KEYCODE_ENTER) {
                    geoLocation();
                }
                return false;
            }
        });
        hideSoftKeyboard();*/
    }

    private void geoLocation() {
        Log.d(TAG, "geoLocation: geolocation");

        String searchString = mSearchText.getText().toString();
        Geocoder geocoder = new Geocoder(MapActivity.this);
        List<Address> list = new ArrayList<>();
        try {
            list = geocoder.getFromLocationName(searchString, 10);

        } catch (IOException e) {
            Log.e(TAG, "geoLocate: found a location: " + e.getMessage());
        }
        if (list.size() > 0) {
            Address address = list.get(0);

            Log.d(TAG, "geoLocation: found a location:" + address.toString());

            moveCamera(new LatLng(address.getLatitude(), address.getLongitude()), DEFAULT_ZOOM, address.getAddressLine(0));
        }
    }
    public boolean isServicesOK() {
        Log.d(TAG, "isServicesOK: cheking google services versoin ");
        int avalable = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MapActivity.this);

        if (avalable == ConnectionResult.SUCCESS) {
            Log.d(TAG, "isServicesOK: Google play services is working ");
            return true;
        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(avalable)) {
            Log.d(TAG, "isServicesOK: an error occured but we can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(MapActivity.this, avalable, ERROR_DIALOG_REQUEST);
            dialog.show();
        } else {
            Toast.makeText(this, "You can't make map requests", Toast.LENGTH_SHORT).show();
        }
        return false;
    }
    //*map

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }

        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        MapActivity.super.onBackPressed();
                    }
                })
                .setNegativeButton("No", null)
                .show();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.map, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            Intent it = new Intent(MapActivity.this, ProfileActivity.class);
            startActivity(it);
        } else if (id == R.id.nav_booking) {
            Intent it = new Intent(MapActivity.this, BookingDetailsActivity.class);
            startActivity(it);
        } else if (id == R.id.nav_invoice) {
            Intent it = new Intent(MapActivity.this, InvoiceActivity.class);
            startActivity(it);
        } else if (id == R.id.nav_package) {
            Intent it = new Intent(MapActivity.this, PackageActivity.class);
            startActivity(it);
        } else if (id == R.id.nav_history) {
            Intent it = new Intent(MapActivity.this, TimelineActivity.class);
            startActivity(it);
        } else if (id == R.id.nav_help) {
            Intent it = new Intent(MapActivity.this, SliderActivity.class);
            startActivity(it);
        } else if (id == R.id.nav_feedback) {
            Intent it = new Intent(MapActivity.this, FeedbackActivity.class);
            startActivity(it);
        } else if (id == R.id.nav_aboutus) {
            Intent it = new Intent(MapActivity.this, AboutUsActivity.class);
            startActivity(it);
        } else if (id == R.id.nav_signout) {
            if (SharedPrefManager.getInstance(MapActivity.this).logout()) {
                Toast.makeText(this, "LogOut", Toast.LENGTH_SHORT).show();
                AppPref.setValue("IS_LOGIN", "false", MapActivity.this);
                startActivity(new Intent(MapActivity.this, LoginSignupActivity.class));
                //return;
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }

    public void hideSoftKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) MapActivity.this
                .getSystemService(INPUT_METHOD_SERVICE);
        Activity act = (Activity) MapActivity.this;
        if (act.getCurrentFocus() != null)
            inputMethodManager.hideSoftInputFromWindow(act.getCurrentFocus().getWindowToken(), 0);
    }


    //    --------------------------- google places API autocomplete suggestions -----------------

    private AdapterView.OnItemClickListener mAutocompleteClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            hideSoftKeyboard();

            final AutocompletePrediction item = mPlaceAutocompleteAdapter.getItem(i);
            final String placeId = item.getPlaceId();

            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                    .getPlaceById(mGoogleApiClient, placeId);
            placeResult.setResultCallback(mUpdatePlaceDetailsCallback);
        }
    };

    private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(@NonNull PlaceBuffer places) {
            if (!places.getStatus().isSuccess()) {
                Log.d(TAG, "onResult: Place query did not complete successfully: " + places.getStatus().toString());
                places.release();
                return;
            }
            final Place place = places.get(0);

            try {

                View view = findViewById(R.id.bottom_sheet_show_loc);
                TextView place_address = view.findViewById(R.id.place_address);
                TextView main_location = view.findViewById(R.id.main_location);

                main_location.setText(place.getName());
                place_address.setText(place.getAddress());

            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                mPlace = new PlaceInfo();
                mPlace.setName(place.getName().toString());
                Log.d(TAG, "onResult: name: " + place.getName());
                mPlace.setAddress(place.getAddress().toString());
                Log.d(TAG, "onResult: address: " + place.getAddress());
                mPlace.setAttributions(place.getAttributions().toString());
                Log.d(TAG, "onResult: attributions: " + place.getAttributions());
                mPlace.setId(place.getId());
                Log.d(TAG, "onResult: id:" + place.getId());
                mPlace.setLatlng(place.getLatLng());
                Log.d(TAG, "onResult: latlng: " + place.getLatLng());
                mPlace.setRating(place.getRating());
                Log.d(TAG, "onResult: rating: " + place.getRating());
                mPlace.setPhoneNumber(place.getPhoneNumber().toString());
                Log.d(TAG, "onResult: phone number: " + place.getPhoneNumber());
                mPlace.setWebsiteUri(place.getWebsiteUri());
                Log.d(TAG, "onResult: website uri: " + place.getWebsiteUri());

                Log.d(TAG, "onResult: place: " + mPlace.toString());
            } catch (NullPointerException e) {
                Log.e(TAG, "onResult: NullPointerException: " + e.getMessage());
            }

            moveCamera(new LatLng(place.getViewport().getCenter().latitude,
                    place.getViewport().getCenter().longitude), DEFAULT_ZOOM, mPlace);

            places.release();
        }
    };

}