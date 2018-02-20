package com.acharya.habba2k18.Devs;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.acharya.habba2k18.MainMenu.MainActivity;
import com.acharya.habba2k18.R;

public class Dev extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dev);

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
        finish();

    }
}
