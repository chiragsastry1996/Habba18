package com.acharya.habbaregistration.Feed;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.acharya.habbaregistration.Error.Error;
import com.acharya.habbaregistration.Events.HttpHandler;
import com.acharya.habbaregistration.MainMenu.MainActivity;
import com.acharya.habbaregistration.R;
import com.acharya.habbaregistration.Test.ReadWriteJsonFileUtils;
import com.google.firebase.analytics.FirebaseAnalytics;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FeedActivity extends AppCompatActivity {
    private FirebaseAnalytics mFirebaseAnalytics;
    private List<Feed> feedList;
    private FeedAdapter feedAdapter;
    private RecyclerView recyclerView;
    public boolean connection = false, dbchange = true;
    public static android.support.v7.widget.Toolbar toolbar2;
    private static final String url ="http://acharyahabba.in/habba18/feeds.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransitionEnter();
        setContentView(R.layout.activity_feed);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        final ConnectivityManager connectivityManager = ((ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE));
        connection = (connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting());

        toolbar2 = (android.support.v7.widget.Toolbar)findViewById(R.id.toolbar2);
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
                    deleteFile("FeedCache");
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

                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(FeedActivity.this, Error.class);
                        intent.putExtra("error_title","No Data Available");
                        intent.putExtra("error_message","No Cache or Data availble\nPlease switch on your Internet and open the app again");
                        startActivity(intent);
                        finish();
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

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            Intent i8 = new Intent(FeedActivity.this, MainActivity.class);
            overridePendingTransitionExit();
            startActivity(i8);
            finish();
        } else {
            getSupportFragmentManager().popBackStack();
        }
    }
    protected void overridePendingTransitionEnter() {
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }
    protected void overridePendingTransitionExit() {
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

}