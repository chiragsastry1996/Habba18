package com.acharya.habba2k18.Feed;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.acharya.habba2k18.Events.HttpHandler;
import com.acharya.habba2k18.R;
import com.acharya.habba2k18.Test.ReadWriteJsonFileUtils;
import com.google.firebase.analytics.FirebaseAnalytics;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.LENGTH_LONG;

public class FeedActivity extends AppCompatActivity {
    private FirebaseAnalytics mFirebaseAnalytics;
    private List<Feed> feedList;
    private FeedAdapter feedAdapter;
    private RecyclerView recyclerView;
    public boolean connection = false, dbchange = true;
    private static final String url ="http://acharyahabba.in/habba18/feeds.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        getSupportActionBar().hide();

        final ConnectivityManager connectivityManager = ((ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE));
        connection = (connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting());

        recyclerView = (RecyclerView) findViewById(R.id.names_list_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);


        contactList = new ArrayList<>();
        contactList.clear();
        recyclerView.invalidate();
        new GetContacts().execute();
    }

    private String TAG = FeedActivity.class.getSimpleName();

    //ArrayList of ArrayList to get multiple Json Values (2D JSON)
    ArrayList<ArrayList<String>> contactList;

    protected void onResume() {
        super.onResume();
    }


    //Fetching the JSON Object
    final private class GetContacts extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        //GET request done in background
        @Override
        protected Void doInBackground(Void... arg0) {
            //Calling the HTTPHandler

            // Making a request to url and getting response

            if(connection == true && dbchange == true) {
                try {
                    HttpHandler sh = new HttpHandler();
                    String jsonStr = sh.makeServiceCall(url);
                    new ReadWriteJsonFileUtils(getApplicationContext()).createJsonFileData("FeedCache", jsonStr);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            String jsonString = new ReadWriteJsonFileUtils(getApplicationContext()).readJsonFileData("FeedCache");

            Log.e(TAG, "Response from url: " + jsonString);

            if (jsonString != null) {
                try {

                    //Create JSON Object
                    JSONObject jsonObj = new JSONObject(jsonString);

                    //Create Json Array
                    JSONArray contacts =  jsonObj.getJSONArray("feed");

                    //Fetch all the Values from the JSON OBject
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);
                        String name = c.getString("heading");
                        String caption = c.getString("caption");
                        String image = c.getString("resources");


                        //Get all the attribute values from the Json Object
                        ArrayList<String> contact = new ArrayList<>();

                        contact.add(name);
                        contact.add(caption);
                        contact.add(image);

                        contactList.add(contact);
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
                    feedList = new ArrayList<>();

                    for (int j = 0; j < contactList.size(); j++)
                        feedList.add(new Feed(contactList.get(j).get(0), contactList.get(j).get(1), contactList.get(j).get(2)));
                    feedAdapter = new FeedAdapter(FeedActivity.this, feedList);
                    recyclerView = (RecyclerView) findViewById(R.id.names_list_view);
                    recyclerView.setAdapter(feedAdapter);
                }

            });
        }


    }

}