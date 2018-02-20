package com.acharya.habba2k18.Notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
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
import android.widget.Toast;

import com.acharya.habba2k18.Events.HttpHandler;
import com.acharya.habba2k18.MainMenu.MainActivity;
import com.acharya.habba2k18.R;
import com.google.firebase.analytics.FirebaseAnalytics;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.LENGTH_LONG;

public class Notifications extends AppCompatActivity {
    private FirebaseAnalytics mFirebaseAnalytics;
    private List<NotificationModel> NotificationList;
    private NotificationAdapter NotificationAdapter;
    private RecyclerView recyclerView;
    private static final String url ="http://acharyahabba.in/habba18/feeds.php";
    public ArrayList<ArrayList<String>> ServerDatatList;
    private static final String TAG = "Notifications";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        getSupportActionBar().hide();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create channel to show notifications.
            String channelId  = getString(R.string.default_notification_channel_id);
            String channelName = getString(R.string.default_notification_channel_name);
            NotificationManager notificationManager =
                    getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(new NotificationChannel(channelId,
                    channelName, NotificationManager.IMPORTANCE_LOW));
        }

        // If a notification message is tapped, any data accompanying the notification
        // message is available in the intent extras. In this sample the launcher
        // intent is fired when the notification is tapped, so any accompanying data would
        // be handled here. If you want a different intent fired, set the click_action
        // field of the notification message to the desired intent. The launcher intent
        // is used when no click_action is specified.
        //
        // Handle possible data accompanying notification message.
        // [START handle_data_extras]
        if (getIntent().getExtras() != null) {
            for (String key : getIntent().getExtras().keySet()) {
                Object value = getIntent().getExtras().get(key);
                Log.d(TAG, "Key: " + key + " Value: " + value);
            }
        }



        recyclerView = (RecyclerView) findViewById(R.id.notification_list_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);

        ServerDatatList = new ArrayList<>();
        ServerDatatList.clear();
        recyclerView.invalidate();
        new GetNotification().execute();

    }

    //Fetching the JSON Object
    final private class GetNotification extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        //GET request done in background
        @Override
        protected Void doInBackground(Void... arg0) {
            //Calling the HTTPHandler
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {

                    //Create JSON Object
                    JSONObject jsonObj = new JSONObject(jsonStr);

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

                        ServerDatatList.add(contact);
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
                                "Couldn't get json from server. Check LogCat for possible errors!",
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
                    NotificationList = new ArrayList<>();

                    for (int j = 0; j < ServerDatatList.size(); j++)
                        NotificationList.add(new NotificationModel(ServerDatatList.get(j).get(0), ServerDatatList.get(j).get(1), ServerDatatList.get(j).get(2)));
                    NotificationAdapter = new NotificationAdapter(Notifications.this, NotificationList);
                    recyclerView = (RecyclerView) findViewById(R.id.notification_list_view);
                    recyclerView.setAdapter(NotificationAdapter);
                }

            });
        }

    }

    @Override
    public void onBackPressed() {
        Intent i8 = new Intent(Notifications.this, MainActivity.class);
        startActivity(i8);
        finish();

    }

}
