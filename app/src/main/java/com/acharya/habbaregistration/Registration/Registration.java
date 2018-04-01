package com.acharya.habbaregistration.Registration;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.acharya.habbaregistration.Error.Error;
import com.acharya.habbaregistration.Events.HttpHandler;
import com.acharya.habbaregistration.MainMenu.MainActivity;
import com.acharya.habbaregistration.R;
import com.acharya.habbaregistration.Test.ReadWriteJsonFileUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class Registration extends AppCompatActivity implements View.OnClickListener {


    private EditText editTextName;
    private EditText editTextUsername;
    private EditText editTextPhone;
    private EditText editTextEmail;
    private CoordinatorLayout coordinatorLayout;
    Spinner s1, s2;
    TextView textView, amountTextview;
    private boolean connection = false;
    private static String url = null;
    private static String url1 = null;

    public static ArrayList<String> subspinnerlist,amountlist;
    private Button buttonRegister;

    private static final String REGISTER_URL = "http://acharyahabba.in/habba18/register.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransitionExit();
        setContentView(R.layout.activity_registration);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        getSupportActionBar().hide();

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        final ConnectivityManager connectivityManager = ((ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE));
        connection = (connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting());

        url = "http://acharyahabba.in/habba18/events.php";
        spinnerlist = new ArrayList<>();
        amountlist = new ArrayList<>();
        if(connection){
            new GetContacts().execute();
        }


        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextUsername = (EditText) findViewById(R.id.editTextUserName);
        editTextPhone = (EditText) findViewById(R.id.editPhone);
        coordinatorLayout = (CoordinatorLayout)findViewById(R.id.coordinator);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        s1 = (Spinner) findViewById(R.id.spinner);
        s2 = (Spinner) findViewById(R.id.spinner2);
        textView = (TextView) findViewById(R.id.textView);
        amountTextview = (TextView)findViewById(R.id.amount);


        buttonRegister = (Button) findViewById(R.id.buttonRegister);

        buttonRegister.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        if (v == buttonRegister) {
            if(connection) {
                registerUser();
            }
            else {
                Snackbar snackbar = Snackbar
                        .make(coordinatorLayout, "No Internet Connection\nPlease switch on internet to register", Snackbar.LENGTH_LONG);

                snackbar.show();
            }

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
        String ctg = " ";
                ctg= s1.getSelectedItem().toString();
        String sctg = " ";
                sctg= s2.getSelectedItem().toString();

        register(name, clg, number, email, sctg);
    }

    private void register(String name, String clg, String number, String email,String sctg) {
        class RegisterUser extends AsyncTask<String, Void, String> {
            RegisterUserClass ruc = new RegisterUserClass();


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
//                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
                Snackbar snackbar = Snackbar
                        .make(coordinatorLayout, s, Snackbar.LENGTH_LONG);

                snackbar.show();
            }

            @Override
            protected String doInBackground(String... params) {

                HashMap<String, String> data = new HashMap<String, String>();
                data.put("name", params[0]);
                data.put("clg", params[1]);
                data.put("num", params[2]);
                data.put("email", params[3]);
                data.put("sub", params[4]);

                String result = ruc.sendPostRequest(REGISTER_URL, data);

                return result;
            }
        }

        RegisterUser ru = new RegisterUser();
        ru.execute(name, clg, number, email, sctg);
    }

    private String TAG = Registration.class.getSimpleName();

    // URL to get contacts JSON

    public static ArrayList<String> spinnerlist;


    private class GetContacts extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... arg0) {


            try {
                HttpHandler sh = new HttpHandler();
                String jsonStr = sh.makeServiceCall(url);
                deleteFile("MainEvent");
                new ReadWriteJsonFileUtils(getApplicationContext()).createJsonFileData("MainEvent", jsonStr);
            } catch (Exception e) {
                e.printStackTrace();
            }

            String jsonString = null;

            jsonString = new ReadWriteJsonFileUtils(getApplicationContext()).readJsonFileData("MainEvent");

            // Making a request to url and getting response


            Log.e(TAG, "Response from url: " + jsonString);

            if (jsonString != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonString);

                    JSONArray contacts = jsonObj.getJSONArray("result");
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);
                        String name = c.getString("name");
                        if(!(name.equals("Dance")))
                        spinnerlist.add(name);
                    }

                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(Registration.this, Error.class);
                            intent.putExtra("error_title","No Data Available");
                            intent.putExtra("error_message","No Cache or Data availble\nPlease switch on your Internet and open the app again");
                            startActivity(intent);
                            finish();
                        }
                    });

                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");


            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog

            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(Registration.this,R.layout.spinner_item, spinnerlist);
                    spinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                    s1.setAdapter(spinnerAdapter);

                   s1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                       @Override
                       public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                           String main = s1.getSelectedItem().toString();
                           url1 = "http://acharyahabba.in/habba18/subevents.php?main=" + main;
                           System.out.println("spinner"+url1);
                           subspinnerlist = new ArrayList<>();
                           new GetCategory().execute();

                       }

                       @Override
                       public void onNothingSelected(AdapterView<?> parent) {

                       }
                   });
                    s2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                           s2.setSelection(position);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                }
            });
        }


    }




    private class GetCategory extends AsyncTask<Void, Void, Void> {
        String amount = " ";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... arg0) {



            try {
                HttpHandler sh = new HttpHandler();
                String jsonStr = sh.makeServiceCall(url1);
                deleteFile("SubEvent");
                new ReadWriteJsonFileUtils(getApplicationContext()).createJsonFileData("SubEvent", jsonStr);
            } catch (Exception e) {
                e.printStackTrace();
            }

            String jsonString = null;

            jsonString = new ReadWriteJsonFileUtils(getApplicationContext()).readJsonFileData("SubEvent");

            // Making a request to url and getting response


            Log.e(TAG, "Response from url: " + jsonString);

            if (jsonString != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonString);

                    JSONArray contacts = jsonObj.getJSONArray("result1");
                    for(int i = 0; i<contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);
                        String name = c.getString("name");
                         amount = c.getString("amount");
                         amountlist.add(amount);
                        subspinnerlist.add(name);
                    }

                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());


                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");


            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog

            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    ArrayAdapter<String> subspinnerAdapter = new ArrayAdapter<String>(getApplicationContext(),
                            R.layout.spinner_item , subspinnerlist);
                    subspinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                    s2.setAdapter(subspinnerAdapter);

                    s2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            amountTextview.setText(amountlist.get(position));
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });




                }
            });
        }


    }

    @Override
    public void onBackPressed() {
        Intent i8 = new Intent(Registration.this, MainActivity.class);
        overridePendingTransitionExit();
        startActivity(i8);
        finish();

    }
    protected void overridePendingTransitionEnter() {
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }
    protected void overridePendingTransitionExit() {
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

}