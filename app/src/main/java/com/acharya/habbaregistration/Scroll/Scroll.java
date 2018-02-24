package com.acharya.habbaregistration.Scroll;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneNumberUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.acharya.habbaregistration.R;
import com.acharya.habbaregistration.Test.Test;
import com.acharya.habbaregistration.Timeline.TimeLineActivity;
import com.bumptech.glide.Glide;
import com.google.firebase.analytics.FirebaseAnalytics;

public class Scroll extends AppCompatActivity {
    public String eventname,category;
    ImageView whatsapp_button,caller_button,navigate_button;
    public int val;
    Button button;
    TextView tv1,tv2,tv3,tv4,tv5,tv6,tv7,tv8;
    ImageView imageView;
    private static String name;
    private static String about;
    private static String rules;
    private static String amount;
    private static String image;
    private static String number;
    private static String ename;
    private static String prizemoney;
    private static String venue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll);
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        button = (Button)findViewById(R.id.button3);
        whatsapp_button = findViewById(R.id.whatsapp_button);
        caller_button =  findViewById(R.id.callerbut);
        navigate_button =  findViewById(R.id.button_navigate);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Intent activityChangeIntent = new Intent(Scroll.this, EventRegistration.class);
                activityChangeIntent.putExtra("event", name);
                activityChangeIntent.putExtra("amount", amount);
                startActivity(activityChangeIntent);
            }
        });

        whatsapp_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String smsNumber = "91" + number;                                                              //without '+'
                try {
                    Intent sendIntent = new Intent("android.intent.action.MAIN");
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.setType("text/plain");
                    sendIntent.putExtra(Intent.EXTRA_TEXT, "Hello "+ename+",\n");                       //Message body in Whatsapp
                    sendIntent.putExtra("jid", PhoneNumberUtils.stripSeparators(smsNumber)
                            + "@s.whatsapp.net");                                                           //phone number without "+" prefix
                    sendIntent.setPackage("com.whatsapp");
                    startActivity(sendIntent);
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Error/n" + e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        caller_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:+91" + number));
                startActivity(intent);
            }
        });

        navigate_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String urlAddress = "http://maps.google.com/maps?q="+ Test.subcatList.get(category).get(eventname).get(12)  +"," + Test.subcatList.get(category).get(eventname).get(13) +"("+ name + ")&iwloc=A&hl=es";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlAddress));
                intent.setPackage("com.google.android.apps.maps");
                if (intent.resolveActivity(getApplication().getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });



        Intent mIntent = getIntent();
        Bundle bundle = mIntent.getExtras();
        if (bundle != null) {
            eventname = bundle.getString("event");
            category = bundle.getString("category");
            System.out.println("num"+val);
        }
        //Toast.makeText(getBaseContext(),""+val,Toast.LENGTH_LONG).show();

        tv1=(TextView)findViewById(R.id.name);
        tv2=(TextView)findViewById(R.id.about);
        tv3=(TextView)findViewById(R.id.rules);
        tv4=(TextView)findViewById(R.id.amount);
        tv5=(TextView)findViewById(R.id.number);
        tv6=(TextView)findViewById(R.id.ename);
        tv7=(TextView)findViewById(R.id.pmoney);
        tv8=(TextView)findViewById(R.id.venue);
        imageView = (ImageView)findViewById(R.id.imageView);

        name = Test.subcatList.get(category).get(eventname).get(1);
        about = Test.subcatList.get(category).get(eventname).get(3);
        rules = Test.subcatList.get(category).get(eventname).get(6);
        number = Test.subcatList.get(category).get(eventname).get(7);
        ename = Test.subcatList.get(category).get(eventname).get(8);
        image = Test.subcatList.get(category).get(eventname).get(2);
        amount = Test.subcatList.get(category).get(eventname).get(4);
        prizemoney = Test.subcatList.get(category).get(eventname).get(9);
        venue = Test.subcatList.get(category).get(eventname).get(11);

        tv1.setText(name);
        tv2.setText(about);
        tv3.setText(rules);
        tv4.setText(amount);
        tv5.setText(number);
        tv6.setText(ename);
        tv7.setText(prizemoney);
        tv8.setText(venue);
        Glide.with(getBaseContext()).load(image).into(imageView);

    }
    @Override
    public void onBackPressed()
    {
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
        super.onBackPressed();
        Intent i8 = new Intent(Scroll.this, TimeLineActivity.class);
        startActivity(i8);
        finish();
    }
    }
