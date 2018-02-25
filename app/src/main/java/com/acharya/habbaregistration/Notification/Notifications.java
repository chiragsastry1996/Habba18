package com.acharya.habbaregistration.Notification;

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

import com.acharya.habbaregistration.Error.Error;
import com.acharya.habbaregistration.Events.HttpHandler;
import com.acharya.habbaregistration.MainMenu.MainActivity;
import com.acharya.habbaregistration.R;
import com.google.firebase.analytics.FirebaseAnalytics;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Notifications extends AppCompatActivity {
    private FirebaseAnalytics mFirebaseAnalytics;
    private List<NotificationModel> NotificationList;
    private NotificationAdapter NotificationAdapter;
    private RecyclerView recyclerView;
    private static final String url ="http://acharyahabba.in/habba18/notification.php";
    public ArrayList<ArrayList<String>> ServerDatatList;
    private static final String TAG = "Notifications";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransitionEnter();
        setContentView(R.layout.activity_notifications);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        getSupportActionBar().hide();

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
                    JSONArray contacts =  jsonObj.getJSONArray("result");

                    //Fetch all the Values from the JSON OBject
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);
                        String id = c.getString("id");
                        String title = c.getString("title");
                        String message = c.getString("message");


                        //Get all the attribute values from the Json Object
                        ArrayList<String> contact = new ArrayList<>();

                        contact.add(id);
                        contact.add(title);
                        contact.add(message);

                        ServerDatatList.add(contact);
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(Notifications.this, Error.class);
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
            //On UI Thread, perform Front-End tasks
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    NotificationList = new ArrayList<>();

                    for (int j = 0; j < ServerDatatList.size(); j++)
                        NotificationList.add(new NotificationModel(ServerDatatList.get(j).get(1), ServerDatatList.get(j).get(2)));
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
