package com.acharya.habba2k18.Scroll;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.acharya.habba2k18.Gallery.Image;
import com.acharya.habba2k18.R;
import com.acharya.habba2k18.Registration.RegisterUserClass;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.HashMap;


public class EventRegistration extends AppCompatActivity implements View.OnClickListener {

    private String email, name,status,event,amount,image;
    private EditText editTextName;
    private EditText editTextUsername;
    private EditText editTextPhone;
    private EditText editTextEmail;
    private ImageView imageView;


    TextView textView, amountTextview,eventTextview;
    private static String url = null;
    private static String url1 = null;

    private Button buttonRegister;

    private static final String REGISTER_URL = "http://theprince.96.lt//android/register.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_registration);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        Intent mIntent = getIntent();
        Bundle bundle = mIntent.getExtras();
        if (bundle != null) {
            event = bundle.getString("event");
            amount = bundle.getString("amount");
        }



        imageView = (ImageView)findViewById(R.id.image_card);
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextUsername = (EditText) findViewById(R.id.editTextUserName);
        editTextPhone = (EditText) findViewById(R.id.editPhone);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);

        textView = (TextView) findViewById(R.id.textView);
        eventTextview = (TextView)findViewById(R.id.event);
        amountTextview = (TextView)findViewById(R.id.amount);

        editTextName.setText(name, TextView.BufferType.EDITABLE);
        editTextEmail.setText(email, TextView.BufferType.EDITABLE);
        eventTextview.setText(event);
        amountTextview.setText(amount);


        buttonRegister = (Button) findViewById(R.id.buttonRegister);

        buttonRegister.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        if (v == buttonRegister) {
            registerUser();
        }
    }

    private void registerUser() {

        String name = editTextName.getText().toString().trim().toLowerCase();
        if (editTextName.getText().toString().trim().equals("")) {
            editTextName.setError("First name is required!");

            editTextName.setHint("Please enter your name");
        }
        String clg = editTextUsername.getText().toString().trim().toLowerCase();

        if (editTextUsername.getText().toString().trim().equals("")) {
            editTextUsername.setError("College name is required!");

            // editTextUsername.setHint("Please enter your College's name");
        }
        String number = editTextPhone.getText().toString().trim().toLowerCase();

        if (editTextPhone.getText().toString().trim().equals("")) {
            editTextPhone.setError("Phone number is required!");

            // editTextPhone.setHint("Please enter your phone number");
        }

        String email = editTextEmail.getText().toString().trim().toLowerCase();

        if (editTextEmail.getText().toString().trim().equals("")) {
            editTextEmail.setError("Email address is required!");

            editTextEmail.setHint("Please enter your email address");
        }

        String sctg = event;

        register(name, clg, number, email, sctg);
    }

    private void register(String name, String clg, String number, String email, String sctg) {
        class RegisterUser extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            RegisterUserClass ruc = new RegisterUserClass();


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(String... params) {

                HashMap<String, String> data = new HashMap<String, String>();
                data.put("name", params[0]);
                data.put("clg", params[1]);
                data.put("number", params[2]);
                data.put("email", params[3]);
                data.put("sctg", params[5]);

                String result = ruc.sendPostRequest(REGISTER_URL, data);

                return result;
            }
        }

        RegisterUser ru = new RegisterUser();
        ru.execute(name, clg, number, email,sctg);
    }

    public void onBackPressed(){
       finish();
    }

}

