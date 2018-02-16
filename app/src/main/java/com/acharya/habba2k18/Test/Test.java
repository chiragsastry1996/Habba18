package com.acharya.habba2k18.Test;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.acharya.habba2k18.Events.HttpHandler;
import com.acharya.habba2k18.MainMenu.MainActivity;
import com.acharya.habba2k18.R;
import com.google.firebase.analytics.FirebaseAnalytics;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static android.widget.Toast.LENGTH_LONG;

public class Test extends AppCompatActivity {
    private static long time;
    ProgressBar progressBar;
    public String url = "http://acharyahabba.in/habba18/json.php";
    public boolean connection = false, dbchange = false;
    public static ArrayList<ArrayList<String>> eventList;
    public static HashMap<String,String> dbChangeList;
    public static HashMap<String,HashMap<String,ArrayList<String>>> subcatList;
    public static ArrayList<ArrayList<String>> timeline;
    public static String subcat_version="0",feeds_version="0",mainc_version="0";
    private String TAG = Test.class.getSimpleName();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        final ConnectivityManager connectivityManager = ((ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE));
        connection = (connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting());


        eventList = new ArrayList<>();
        dbChangeList = new HashMap<>();
        subcatList = new HashMap<>();
        timeline = new ArrayList<>();
        time = System.currentTimeMillis();

        new GetVersion().execute();

        progressBar = (ProgressBar)findViewById(R.id.progressBar2);
    }


    //Fetching the JSON Object
    public class GetVersion extends AsyncTask<Void, Void, Void> {



        @Override
        public void onPreExecute() {
            super.onPreExecute();
        }

        //GET request done in background
        @Override
        protected Void doInBackground(Void... arg0) {

            // Making a request to url and getting response

            if(connection) {
                try {
                    HttpHandler sh = new HttpHandler();
                    String jsonStr = sh.makeServiceCall("http://acharyahabba.in/habba18/dbchange.php");
                    new ReadWriteJsonFileUtils(getApplicationContext()).createJsonFileData("EventVersion", jsonStr);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            String jsonString = null;

            jsonString = new ReadWriteJsonFileUtils(getApplicationContext()).readJsonFileData("EventVersion");


            Log.e(TAG, "Response from url: " + jsonString);

            if (jsonString != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonString);


                    JSONArray contacts = jsonObj.getJSONArray("result");
                    for(int i = 0; i<contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);
                        String tableName = c.getString("tableName");
                        String version = c.getString("version");


                        dbChangeList.put(tableName,version);
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
                                "No data available!\nPlease switch on your internet",
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

                    SharedPreferences prefs = getSharedPreferences("Version", MODE_PRIVATE);
                        subcat_version = prefs.getString("subcat", "0");
                        feeds_version = prefs.getString("feeds", "0");
                        mainc_version = prefs.getString("mainc", "0");

                    String current_subcat_version = dbChangeList.get("subcat");
                    String current_mainc_version = dbChangeList.get("mainc");
                    if((!mainc_version.equals(current_mainc_version)||(!subcat_version.equals(current_subcat_version)))){
                        dbchange = true;
                        Toast.makeText(getApplicationContext(),"DB changed",Toast.LENGTH_SHORT).show();
                    }
                    new GetEvents().execute();
                    System.out.println("ssss "+ mainc_version+subcat_version+current_mainc_version+current_subcat_version);
                    SharedPreferences.Editor editor = getSharedPreferences("Version", MODE_PRIVATE).edit();
                    editor.putString("subcat", dbChangeList.get("subcat"));
                    editor.putString("feeds", dbChangeList.get("feeds"));
                    editor.putString("mainc", dbChangeList.get("mainc"));
                    editor.apply();
                }

            });
        }


    }

    //Fetching the JSON Object
    public class GetEvents extends AsyncTask<Void, Void, Void> {



        @Override
        public void onPreExecute() {
            super.onPreExecute();
        }

        //GET request done in background
        @Override
        protected Void doInBackground(Void... arg0) {

            // Making a request to url and getting response


            if(connection == true && dbchange == true) {
                try {
                    HttpHandler sh = new HttpHandler();
                    String jsonStr = sh.makeServiceCall("http://acharyahabba.in/habba18/events.php");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),"Event fetched",Toast.LENGTH_SHORT).show();
                        }
                    });

                    new ReadWriteJsonFileUtils(getApplicationContext()).createJsonFileData("EventCache", jsonStr);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            String jsonString = null;

            jsonString = new ReadWriteJsonFileUtils(getApplicationContext()).readJsonFileData("EventCache");


            Log.e(TAG, "Response from url: " + jsonString);

            if (jsonString != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonString);


                    JSONArray contacts = jsonObj.getJSONArray("result");
                    for(int i = 0; i<contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);
                        String id = c.getString("id");
                        String name = c.getString("name");
                        String url = c.getString("url");

                        ArrayList<String> contact = new ArrayList<>();
                        contact.add(id);
                        contact.add(name);
                        contact.add(url);

                        eventList.add(contact);
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
                                "No data available!\nPlease switch on your internet",
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


                    new GetSubCat().execute();
                }

            });
        }


    }

    public class GetSubCat extends AsyncTask<Void, Void, Void> {



        @Override
        public void onPreExecute() {
            super.onPreExecute();
        }

        //GET request done in background
        @Override
        public Void doInBackground(Void... arg0) {
            //Calling the HTTPHandler

            // Making a request to url and getting response

            if(connection == true && dbchange == true) {
                try {
                    HttpHandler sh = new HttpHandler();
                    String jsonStr = sh.makeServiceCall("http://acharyahabba.in/habba18/json.php");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),"Subcat fetched",Toast.LENGTH_SHORT).show();
                        }
                    });
                    new ReadWriteJsonFileUtils(getApplicationContext()).createJsonFileData("MainCache", jsonStr);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            String jsonString = null;

            jsonString = new ReadWriteJsonFileUtils(getApplicationContext()).readJsonFileData("MainCache");

            Log.e(TAG, "Response from second url: " + jsonString);
            if (jsonString != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonString);
                    JSONArray result = jsonObj.getJSONArray("result");
                    for (int j = 0; j < result.length(); j++) {
                        JSONObject elem = result.getJSONObject(j);
                        HashMap<String,ArrayList<String>> details = new HashMap<>();
                        JSONArray event = elem.getJSONArray(eventList.get(j).get(1));
                        for (int i = 0; i < event.length(); i++) {
                            JSONObject eventdetails = event.getJSONObject(i);
                            String name = eventdetails.getString("name");
                            String url = eventdetails.getString("url");
                            String eid = eventdetails.getString("eid");
                            String description = eventdetails.getString("description");
                            String amount = eventdetails.getString("amount");
                            String category = eventdetails.getString("cid");
                            String rules = eventdetails.getString("rules");
                            String number = eventdetails.getString("numb");
                            String eventhead = eventdetails.getString("eventhead");
                            String prizemoney = eventdetails.getString("pmoney");
                            String date = eventdetails.getString("date");
                            String time = eventdetails.getString("time");
                            String venue = eventdetails.getString("venue");
                            String mainc =  eventdetails.getString("mainc");

                            ArrayList<String> contact = new ArrayList<>();
                            contact.add(eid);
                            contact.add(name);
                            contact.add(url);
                            contact.add(description);
                            contact.add(amount);
                            contact.add(category);
                            contact.add(rules);
                            contact.add(number);
                            contact.add(eventhead);
                            contact.add(prizemoney);
                            contact.add(date);
                            details.put(name,contact);

                            ArrayList<String> timelist = new ArrayList<String>();
                            timelist.add(name + " at " + venue);
                            timelist.add(date);
                            timelist.add(time);
                            timelist.add(name);
                            timelist.add(mainc);
                            timeline.add(timelist);

                        }
                        subcatList.put(eventList.get(j).get(1), details);
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
                                "No data available!\nPlease switch on your internet",
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
                    progressBar.setVisibility(View.GONE);



                    Intent intent = new Intent(Test.this, MainActivity.class);
                    startActivity(intent);
                    finish();

                }
            });
        }


    }

}
