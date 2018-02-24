package com.acharya.habbaregistration.MainMenu;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.acharya.habbaregistration.R;
import com.acharya.habbaregistration.Registration.RegisterUserClass;
import com.acharya.habbaregistration.Test.Test;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.MediaStoreSignature;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.special.ResideMenu.ResideMenu;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.io.IOException;
import java.util.HashMap;

import static com.acharya.habbaregistration.MainMenu.MainActivity.resideMenu;

public class HomeFragment extends Fragment {

    private View parentView;
    private ImageView imageView1;
    private CarouselView carouselView;
    Button left, right;
    private static final String REGISTER_URL = "http://acharyahabba.in/habba18/firebase/register.php";

    private String[] sampleImages = {"http://acharyahabba.in/habba18/images/sliders1.jpg",
            "http://acharyahabba.in/habba18/images/sliders2.jpg",
            "http://acharyahabba.in/habba18/images/sliders3.jpg",
            "http://acharyahabba.in/habba18/images/sliders4.jpg"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.home, container, false);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create channel to show notifications.
            String channelId  = getString(R.string.default_notification_channel_id);
            String channelName = getString(R.string.default_notification_channel_name);
            NotificationManager notificationManager =
                    getActivity().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(new NotificationChannel(channelId,
                    channelName, NotificationManager.IMPORTANCE_LOW));
        }

        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    99);
        }

        setUpViews();
        return parentView;

    }

    private void setUpViews() {
        MainActivity parentActivity = (MainActivity) getActivity();

        try {

            FirebaseMessaging.getInstance().subscribeToTopic("news");
            String token = FirebaseInstanceId.getInstance().getToken();

            Log.d("token", token);
            registerToken(token);

        }catch (Exception e) {
            System.out.println("Token Error");
        }

        left = (Button)parentView.findViewById(R.id.left);
        right = (Button)parentView.findViewById(R.id.right);

        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
            }
        });
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resideMenu.openMenu(ResideMenu.DIRECTION_RIGHT);
            }
        });

        carouselView = (CarouselView) parentView.findViewById(R.id.carouselView);
        carouselView.setPageCount(sampleImages.length);

        carouselView.setImageListener(new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {

                Glide.with(getContext())
                        .load(sampleImages[position])
                        .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE).placeholder(R.drawable.slider1))
                       .into(imageView);
            }
        });


        imageView1 = (ImageView)parentView.findViewById(R.id.imageView11);
        try {
            final pl.droidsonroids.gif.GifDrawable gifFromResource = new pl.droidsonroids.gif.GifDrawable( getResources(), R.drawable.landing);
            imageView1.setImageDrawable(gifFromResource);
        } catch (IOException e) {
            e.printStackTrace();
        }

        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // do nothing
            }
        });

    }

    private void registerToken(String token) {
        class RegisterUser extends AsyncTask<String, Void, String> {
            RegisterUserClass ruc = new RegisterUserClass();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                // Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();
                Log.e("error",s);
            }

            @Override
            protected String doInBackground(String... params) {

                HashMap<String, String> data = new HashMap<String, String>();
                data.put("token", params[0]);

                String result = ruc.sendPostRequest(REGISTER_URL, data);

                return result;
            }
        }

        RegisterUser ru = new RegisterUser();
        ru.execute(token);
    }

}
