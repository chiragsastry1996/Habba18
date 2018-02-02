package com.acharya.habba2k18.Scroll;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.acharya.habba2k18.R;
import com.acharya.habba2k18.Test.Test;
import com.bumptech.glide.Glide;

public class Scroll extends AppCompatActivity {
    public String eventname,category;
    public int val;
    Button button;
    TextView tv1,tv2,tv3,tv4,tv5,tv6,tv7;
    ImageView imageView;
    private static String name;
    private static String about;
    private static String rules;
    private static String amount;
    private static String image;
    private static String number;
    private static String ename;
    private static String prizemoney;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll);
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
        button = (Button)findViewById(R.id.button3);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Intent activityChangeIntent = new Intent(Scroll.this, EventRegistration.class);
                activityChangeIntent.putExtra("event", name);
                activityChangeIntent.putExtra("amount", amount);
                startActivity(activityChangeIntent);
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
        imageView = (ImageView)findViewById(R.id.imageView);

        name = Test.subcatList.get(category).get(eventname).get(1);
        about = Test.subcatList.get(category).get(eventname).get(3);
        rules = Test.subcatList.get(category).get(eventname).get(6);
        number = Test.subcatList.get(category).get(eventname).get(7);
        ename = Test.subcatList.get(category).get(eventname).get(8);
        image = Test.subcatList.get(category).get(eventname).get(2);
        amount = Test.subcatList.get(category).get(eventname).get(4);
        prizemoney = Test.subcatList.get(category).get(eventname).get(9);

        tv1.setText(name);
        tv2.setText(about);
        tv3.setText(rules);
        tv4.setText(amount);
        tv5.setText(number);
        tv6.setText(ename);
        tv7.setText(prizemoney);
        Glide.with(getBaseContext()).load(image).into(imageView);

    }

    public void caller(View view){
        try {
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:"+number));
            this.startActivity(callIntent);
        } catch (ActivityNotFoundException activityException) {
            Toast.makeText(getApplicationContext(), "Call has failed", Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public void onBackPressed()
    {
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
        super.onBackPressed();
        finish();
    }
    }
