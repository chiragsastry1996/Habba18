package com.acharya.habbaregistration.Devs;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.acharya.habbaregistration.MainMenu.MainActivity;
import com.acharya.habbaregistration.R;

public class Dev extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransitionEnter();
        setContentView(R.layout.activity_dev);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        getSupportActionBar().hide();
    }

    public void call(View view){
        try {
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            String number = view.getTag().toString();
            callIntent.setData(Uri.parse("tel:"+number));

            startActivity(callIntent);
        } catch (ActivityNotFoundException activityException) {
            Toast.makeText(getApplicationContext(), "Call has failed", Toast.LENGTH_LONG).show();
        }

    }

    public void mail(View view){
        try {

            String email = view.getTag().toString();
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
            emailIntent.setData(Uri.parse("mailto:"+email));
            startActivity(Intent.createChooser(emailIntent, "Send feedback"));
        } catch (ActivityNotFoundException activityException) {
            Toast.makeText(getApplicationContext(), "Email switch has failed", Toast.LENGTH_LONG).show();
        }

    }


    @Override
    public void onBackPressed() {
        Intent i8 = new Intent(Dev.this, MainActivity.class);
        startActivity(i8);
        overridePendingTransitionExit();
        finish();

    }
    protected void overridePendingTransitionEnter() {
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }
    protected void overridePendingTransitionExit() {
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }
}
