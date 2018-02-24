package com.acharya.habbaregistration.Maps;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.acharya.habbaregistration.Error.Error;
import com.acharya.habbaregistration.Events.HttpHandler;
import com.acharya.habbaregistration.MainMenu.MainActivity;
import com.acharya.habbaregistration.R;
import com.acharya.habbaregistration.Test.ReadWriteJsonFileUtils;
import com.acharya.habbaregistration.Test.Test;
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
import java.util.HashMap;

import static android.widget.Toast.LENGTH_LONG;
import static com.acharya.habbaregistration.R.id.map;


public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {


    ViewPager mViewPager;
    MapsAdapter mapsAdapter;
    GoogleMap mGoogleMap;
    SupportMapFragment mapFrag;
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    public static int pos = 0;
    public static boolean connection = false,first_time = true;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    Marker mMarkerA;
    IconGenerator iconFactory;
    double Lat, Lang;
    public String url;
    public static String geid, glat, glang, gevent;
    private static long back_pressed;
    public static HashMap<String,ArrayList<ArrayList<String>>> mapList;
    private static int uid;
    public int i = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        final ConnectivityManager connectivityManager = ((ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE));
        connection = (connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        iconFactory = new IconGenerator(getApplicationContext());
        //Array spinnerlist to Store all the details of the contact
        mapList= new HashMap<>();
        //URL to return all the location in the database
        url = "http://acharyahabba.in/habba18/location.php";
        new GetContacts().execute();


        mViewPager = (ViewPager) findViewById(R.id.maps_view_pager);
        mapsAdapter = new MapsAdapter(getSupportFragmentManager());
        mViewPager.setPageMargin((int) (getResources().getDisplayMetrics().widthPixels * -0.28));

        mViewPager.setOffscreenPageLimit(3);

        mViewPager.setPageTransformer(false, new ViewPager.PageTransformer() {
            @Override public void transformPage(View page, float position) {
                page.setScaleX(0.9f - Math.abs(position * 0.3f));
                page.setScaleY(0.9f - Math.abs(position * 0.3f));
                page.setAlpha(1.0f - Math.abs(position * 0.5f));
            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


//                String name = Test.eventList.get(position).get(1);
//                for (int j = 0; j < mapList.get(name).size(); j++) {
//                    mMarkerA = mGoogleMap.addMarker(new MarkerOptions()
//                            .position(new LatLng(Double.parseDouble(mapList.get(name).get(j).get(1)), Double.parseDouble(mapList.get(name).get(j).get(2)))).draggable(true));
//                    mMarkerA.setTitle(mapList.get(name).get(j).get(0));
//                    mMarkerA.setIcon(BitmapDescriptorFactory.fromBitmap(iconFactory.makeIcon(mapList.get(name).get(j).get(0))));
//                    System.out.println("prateek" + mapList.get(name).get(j).get(0));
//
//                }
            }

            @Override
            public void onPageSelected(int position) {

//                mapList.clear();
                if(position > 0)
                    mGoogleMap.clear();
                pos = position;
                String name = Test.eventList.get(pos).get(1);
                if (mapList.get(name).size() != 0) {
                    for (int j = 0; j < mapList.get(name).size(); j++) {



                        mMarkerA = mGoogleMap.addMarker(new MarkerOptions()
                                .position(new LatLng(Double.parseDouble(mapList.get(name).get(j).get(1)), Double.parseDouble(mapList.get(name).get(j).get(2)))).draggable(true));
                        mMarkerA.setTitle(mapList.get(name).get(j).get(0));
                        mMarkerA.setIcon(BitmapDescriptorFactory.fromBitmap(iconFactory.makeIcon(mapList.get(name).get(j).get(0))));
                        System.out.println("Arjun " + mapList.get(name));
                    }

                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mViewPager.setAdapter(mapsAdapter);


     //   getSupportActionBar().setTitle("Map Location Activity");

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
        mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(17));
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
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }

        //Hide Action Bar
       // getSupportActionBar().hide();

        //Setting Marker to my current Location
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Position");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
        mCurrLocationMarker = mGoogleMap.addMarker(markerOptions);
        mCurrLocationMarker.setTitle("me");


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





        //stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);

        }


    }


    private String TAG = MapsActivity.class.getSimpleName();

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
            if(connection == true ) {
                try {
                    HttpHandler sh = new HttpHandler();
                    String jsonStr = sh.makeServiceCall(url);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),"Maps fetched",Toast.LENGTH_SHORT).show();
                        }
                    });

                    new ReadWriteJsonFileUtils(getApplicationContext()).createJsonFileData("MapsCache", jsonStr);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            String jsonString = null;

            jsonString = new ReadWriteJsonFileUtils(getApplicationContext()).readJsonFileData("MapsCache");


            Log.e(TAG, "Response from url: " + jsonString);

            if (jsonString != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonString);
                    JSONArray result = jsonObj.getJSONArray("result");
                    for (int j = 0; j < result.length(); j++) {
                        JSONObject elem = result.getJSONObject(j);
                        ArrayList<ArrayList<String>> details = new ArrayList<>();
                        JSONArray event = elem.getJSONArray(Test.eventList.get(j).get(1));
                        for (int i = 0; i < event.length(); i++) {
                            JSONObject eventdetails = event.getJSONObject(i);
                            String name = eventdetails.getString("name");
                            String lat =  eventdetails.getString("lat");
                            String lang =  eventdetails.getString("lang");

                            if(!(name.length() == 0 || lat.length() == 0 || lang.length() == 0)) {
                                ArrayList<String> contact = new ArrayList<>();
                                contact.add(name);
                                contact.add(lat);
                                contact.add(lang);
                                details.add(contact);
                            }



                        }
                        mapList.put(Test.eventList.get(j).get(1), details);
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
                catch (Exception e) {
                    Intent intent = new Intent(MapsActivity.this, Error.class);
                    intent.putExtra("error_title","No Maps Data Available");
                    intent.putExtra("error_message","No Cache or Data availble\nPlease switch on your Internet and open the app again");
                    startActivity(intent);
                    finish();

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

                    try {
                        if(first_time){

                            String name = Test.eventList.get(0).get(1);
                            if (mapList.get(name).size() != 0) {
                                for (int j = 0; j < mapList.get(name).size(); j++) {
                                    mMarkerA = mGoogleMap.addMarker(new MarkerOptions()
                                            .position(new LatLng(Double.parseDouble(mapList.get(name).get(j).get(1)), Double.parseDouble(mapList.get(name).get(j).get(2)))).draggable(true));
                                    mMarkerA.setTitle(mapList.get(name).get(j).get(0));
                                    mMarkerA.setIcon(BitmapDescriptorFactory.fromBitmap(iconFactory.makeIcon(mapList.get(name).get(j).get(0))));
                                }

                            }

                            first_time = false;

                        }
                    }catch (Exception e) {
                        Intent intent = new Intent(MapsActivity.this, Error.class);
                        intent.putExtra("error_title","No Maps Data Available");
                        intent.putExtra("error_message","No Cache or Data availble\nPlease switch on your Internet and open the app again");
                        startActivity(intent);
                        finish();
                    }


                }

            });
        }


    }

    @Override
    public void onBackPressed() {
        //On backpress twice within 2000 milliseconds, perform logout
        if (back_pressed + 2000 > System.currentTimeMillis()) {

            first_time = true;
            Intent i8 = new Intent(MapsActivity.this, MainActivity.class);
            startActivity(i8);
            finish();
        } else {
            Toast.makeText(getApplicationContext(), "Press back again to go to Main Menu ", Toast.LENGTH_SHORT).show();
            back_pressed = System.currentTimeMillis();
        }
    }
}