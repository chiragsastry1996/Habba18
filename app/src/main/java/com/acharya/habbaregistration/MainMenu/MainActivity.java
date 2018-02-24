package com.acharya.habbaregistration.MainMenu;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.acharya.habbaregistration.AboutUs.AboutUs;
import com.acharya.habbaregistration.Devs.Dev;
import com.acharya.habbaregistration.Events.Event;
import com.acharya.habbaregistration.Feed.FeedActivity;
import com.acharya.habbaregistration.Gallery.Gallery_activity;
import com.acharya.habbaregistration.Maps.MapsActivity;
import com.acharya.habbaregistration.Notification.Notifications;
import com.acharya.habbaregistration.R;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;


import com.acharya.habbaregistration.Registration.Registration;
import com.acharya.habbaregistration.Timeline.TimeLineActivity;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public static ResideMenu resideMenu;
    private ResideMenuItem itemDeveloper,itemRegister,itemEvents,itemMaps,itemTimeline,itemNotification, itemAboutUs,itemGallery,itemFeed;
    private static long back_pressed;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }


        getSupportActionBar().hide();
        overridePendingTransition(R.anim.slide_in_left_fast,R.anim.slide_out_right_fast);
        setUpMenu();
        if( savedInstanceState == null )
            changeFragment(new HomeFragment());
        }

    private void setUpMenu() {


        resideMenu = new ResideMenu(this);
        resideMenu.setBackground(R.drawable.background_gradient);
        resideMenu.attachToActivity(this);
        // resideMenu.setMenuListener(menuListener);
        resideMenu.setScaleValue(0.6f);

        // create menu items;
        //sets the menu pictures
        itemNotification = new ResideMenuItem(this, R.drawable.notifications, "Notification");
        itemMaps = new ResideMenuItem(this,R.drawable.maps, "Maps");
        itemEvents = new ResideMenuItem(this,R.drawable.events,"Events");
        itemTimeline = new ResideMenuItem(this,R.drawable.timeline,"Timeline");
        itemRegister = new ResideMenuItem(this,R.drawable.registration,"Register");
        itemDeveloper = new ResideMenuItem(this,R.drawable.devs,"Devs");
        itemFeed = new ResideMenuItem(this,R.drawable.feed,"Feed");
        itemAboutUs = new ResideMenuItem(this,R.drawable.about_us,"About Us");
        itemGallery = new ResideMenuItem(this,R.drawable.gallery,"Gallery");


        itemNotification.setOnClickListener(this);
        itemMaps.setOnClickListener(this);
        itemEvents.setOnClickListener(this);
        itemTimeline.setOnClickListener(this);
        itemRegister.setOnClickListener(this);
        itemDeveloper.setOnClickListener(this);
        itemFeed.setOnClickListener(this);
        itemAboutUs.setOnClickListener(this);
        itemGallery.setOnClickListener(this);

        //******LEFT MENU********
        resideMenu.addMenuItem(itemFeed, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemEvents, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemRegister,ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemMaps,ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemTimeline,ResideMenu.DIRECTION_LEFT);


        //*******RIGHT MENU*******

        resideMenu.addMenuItem(itemGallery,ResideMenu.DIRECTION_RIGHT);
        resideMenu.addMenuItem(itemNotification, ResideMenu.DIRECTION_RIGHT);
        resideMenu.addMenuItem(itemAboutUs, ResideMenu.DIRECTION_RIGHT);
        resideMenu.addMenuItem(itemDeveloper,ResideMenu.DIRECTION_RIGHT);


        // You can disable a direction by setting ->
       // resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_RIGHT);

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        return resideMenu.dispatchTouchEvent(ev);
    }

    @Override
    public void onClick(View view) {

        if (view ==itemFeed){
            resideMenu.closeMenu();

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent1 = new Intent(MainActivity.this, FeedActivity.class);
                    startActivity(intent1);
                }
            }, 200);

        }

        if (view ==itemRegister){

            resideMenu.closeMenu();

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
            Intent intent1 = new Intent(MainActivity.this, Registration.class);
            intent1.putExtra("event", 0);
            startActivity(intent1);
                }
            }, 200);

        }

        else if (view ==itemEvents){
            final int currentposition = 0;
            resideMenu.closeMenu();
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent1 = new Intent(MainActivity.this, Event.class);
                    intent1.putExtra("currentposition", currentposition );
                    startActivity(intent1);
                    finish();
                }
            }, 200);

        }

        else if (view == itemTimeline){
            resideMenu.closeMenu();
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent1 = new Intent(MainActivity.this, TimeLineActivity.class);
                    startActivity(intent1);
                }
            }, 200);

        }

        else if (view == itemGallery){
            resideMenu.closeMenu();
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent1 = new Intent(MainActivity.this, Gallery_activity.class);
                    startActivity(intent1);
                }
            }, 200);

        }

        else if (view ==itemMaps) {


                resideMenu.closeMenu();

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);



                            if (ContextCompat.checkSelfPermission(MainActivity.this,
                                    Manifest.permission.ACCESS_FINE_LOCATION)
                                    == PackageManager.PERMISSION_GRANTED) {

                                if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                                    buildAlertMessageNoGps();

                                } else if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

                                    Intent intent1 = new Intent(MainActivity.this, MapsActivity.class);
                                    startActivity(intent1);
                                }
                            }
                            else {
                                ActivityCompat.requestPermissions(MainActivity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        99);
                            }


//                        Toast.makeText(getApplicationContext(),"under development",Toast.LENGTH_LONG).show();


                    }
                }, 200);



        }

        else if (view == itemDeveloper)
        {
            resideMenu.closeMenu();

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent1 = new Intent(MainActivity.this, Dev.class);
                    startActivity(intent1);
                }
            }, 200);
        }

        else if (view == itemNotification){

            resideMenu.closeMenu();

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent1 = new Intent(MainActivity.this, Notifications.class);
                    startActivity(intent1);
                }
            }, 200);
        }

        else if (view == itemAboutUs){

            resideMenu.closeMenu();

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent1 = new Intent(MainActivity.this, AboutUs.class);
                    startActivity(intent1);
                }
            }, 200);
        }

        resideMenu.closeMenu();
    }
    private void changeFragment(Fragment targetFragment){
        resideMenu.clearIgnoredViewList();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_fragment, targetFragment, "fragment")
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)                  //animation of the fragment transition
                .commit();
    }
    public ResideMenu getResideMenu(){
        return resideMenu;
    }
    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                //Onclick should go to Location settings to Switch ON GPS
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                //If chose not to switch on GPS, exit the app
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
//                        finish();
//                        System.exit(0);
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }
    @Override
    public void onBackPressed()
    {
        if(back_pressed +2000 >System.currentTimeMillis()) {
            finish();
        }
        else
        {
            Toast.makeText(getApplicationContext(),"Press back again to Close app ",Toast.LENGTH_SHORT).show();
            back_pressed = System.currentTimeMillis();
        }
    }
}

