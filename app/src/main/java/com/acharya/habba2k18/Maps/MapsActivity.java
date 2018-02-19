package com.acharya.habba2k18.Maps;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.acharya.habba2k18.Events.HttpHandler;
import com.acharya.habba2k18.MainMenu.MainActivity;
import com.acharya.habba2k18.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.ui.IconGenerator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.widget.Toast.LENGTH_LONG;
import static com.acharya.habba2k18.R.id.map;


public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {


    GoogleMap mGoogleMap;
    SupportMapFragment mapFrag;
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    Marker mMarkerA;
    IconGenerator iconFactory;
    double Lat, Lang;
    public String url;
    public static String geid, glat, glang, gevent;
    private static long back_pressed;
    private static int uid;
    public int i = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        iconFactory = new IconGenerator(getApplicationContext());
        //Array spinnerlist to Store all the details of the contact
        contactList = new ArrayList<>();
        //Execute GET Request
        new GetContacts().execute();

        //URL to return all the location in the database
        url = "http://acharyahabba.in/habba18/location.php";

        getSupportActionBar().hide();

        mapFrag = (SupportMapFragment) getSupportFragmentManager().findFragmentById(map);
        mapFrag.getMapAsync(this);

    }

    @Override
    public void onPause() {
        //onPause, remove Location Updates
        super.onPause();
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        //When map is ready, set googlemap variable
        mGoogleMap = googleMap;
        //Custom styling of google map (Dark theme)
        MapStyleOptions style = MapStyleOptions.loadRawResourceStyle(this, R.raw.style_json);
        googleMap.setMapStyle(style);

        //move map camera
        LatLng latLng1 = new LatLng(13.0845, 77.4851);
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng1));
        mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(18));

        //Check permission and set my marker
        mymarker();
    }


    public void mymarker() {
        //Check permissions before enabling MY_LOCATION
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();

                mGoogleMap.setMyLocationEnabled(true);

            }
        }
    }


    //Setting GoogleApiClient Variable
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {
        //When the device is connected, request for the current location of the user
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }


    @Override
    public void onLocationChanged(final Location location) {
        //When the current location of the user is changed, it has to be Updated
        mLastLocation = location;

        //Setting Marker to my current Location
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Position");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));


        //Get latitude and Longitude of the current location of User
        Lat = location.getLatitude();
        Lang = location.getLongitude();


        //When clicked on a marker, that particular marker's uid should be passed to next activity
        //So that,that marker's details can be displayed
        mGoogleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                //If the clicked marker is not my current location
                if (!(marker.getTitle().equals("me"))) {

                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                            Uri.parse("http://maps.google.com/maps?saddr="+location.getLatitude()+","+location.getLongitude()+"&daddr="+marker.getPosition().latitude+","+marker.getPosition().longitude));
                    intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                    startActivity(intent);

                }
                //If clicked on own location, display "this is your location"
                else
                    Toast.makeText(getApplicationContext(), "This is your location", Toast.LENGTH_SHORT).show();

                return true;

            }
        });


        //move map camera
        LatLng latLng1 = new LatLng(13.0845, 77.4851);
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng1));
        mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(18));


        //stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);

        }


    }


    private String TAG = MapsActivity.class.getSimpleName();

    //ArrayList of ArrayList to get multiple Json Values (2D JSON)
    ArrayList<ArrayList<String>> contactList;

    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    //Fetching the JSON Object
    private class GetContacts extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        //GET request done in background
        @Override
        protected Void doInBackground(Void... arg0) {
            //Calling the HTTPHandler
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    String title;
                    //Create JSON Object
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    //Create Json Array
                    JSONArray contacts = jsonObj.getJSONArray("result");

                    //Fetch all the Values from the JSON OBject
                    for (i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);
                        geid = c.getString("eid");
                        glat = c.getString("lat");
                        glang = c.getString("lang");
                        gevent = c.getString("name");

                        //Get all the attribute values from the Json Object
                        if(!(geid.equals("")||glat.equals("")||glang.equals("")||gevent.equals(""))){
                            ArrayList<String> contact = new ArrayList<>();
                            contact.add(geid);
                            contact.add(glat);
                            contact.add(glang);
                            contact.add(gevent);
                            contactList.add(contact);
                        }

                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                LENGTH_LONG)
                                .show();
                    }
                });

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);



            //On UI Thread, perform Front-End tasks
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String title;
                    /*
                    * Contact list is a array list of array list
                    * Here Multiple values for every uid is sent
                    * Every values of each uid is stored in a 1 dimensional arraylist
                    * Multiple of these values are again stored in another arraylist
                    * Hence, its a ArrayList of ArrayList
                    * */

                    /*
                    * Format of this ArrayList is - j is for traversing multiple uid values
                    * Insisde each array list {"uid","lat","lang","type"}
                    * Hence I have used index 0 to fetch uid,1 to fetch Latitude, 2 to fetch Longitude,3 to fetch type i.e student or teacher
                    * */

                    for (int j = 0; j < contactList.size(); j++) {
                        mMarkerA = mGoogleMap.addMarker(new MarkerOptions()
                                .position(new LatLng(Double.parseDouble(contactList.get(j).get(1)), Double.parseDouble(contactList.get(j).get(2)))).draggable(true));
                        mMarkerA.setTitle(contactList.get(j).get(3));
                        mMarkerA.setIcon(BitmapDescriptorFactory.fromBitmap(iconFactory.makeIcon(contactList.get(j).get(3))));
                        System.out.println("prateek" + contactList.get(j));

                    }

                }

            });
        }


    }

    @Override
    public void onBackPressed() {
        //On backpress twice within 2000 milliseconds, perform logout
        if (back_pressed + 2000 > System.currentTimeMillis()) {

            Intent i8 = new Intent(MapsActivity.this, MainActivity.class);
            startActivity(i8);
            finish();
        } else {
            Toast.makeText(getApplicationContext(), "Press back again to go to Main Menu ", Toast.LENGTH_SHORT).show();
            back_pressed = System.currentTimeMillis();
        }
    }
}