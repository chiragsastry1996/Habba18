package com.acharya.habbaregistration.AboutUs;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.acharya.habbaregistration.MainMenu.MainActivity;
import com.acharya.habbaregistration.R;

public class AboutUs extends AppCompatActivity {

    ViewPager mViewPager;
    TextView title;
    AboutUsAdapter aboutUsAdapter;
    String url = "https://www.youtube.com/user/acharya7317";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransitionEnter();
        setContentView(R.layout.activity_about_us);

        getSupportActionBar().hide();

        title = (TextView) findViewById(R.id.title);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }


        mViewPager = (ViewPager) findViewById(R.id.view_pager_aboutus);
        aboutUsAdapter = new AboutUsAdapter(getSupportFragmentManager());

        mViewPager.setPageTransformer(false, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View page, float position) {
                page.setScaleX(1f - Math.abs(position * 0.3f));
                page.setScaleY(1f - Math.abs(position * 0.1f));
                page.setAlpha(1.0f - Math.abs(position * 0.5f));
            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position == 0) title.setText("About Habba");
                if (position == 1) title.setText("About Acharya");
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mViewPager.setOffscreenPageLimit(2);

        mViewPager.setAdapter(aboutUsAdapter);

        final ImageView facebook = (ImageView) findViewById(R.id.facebook);
        final ImageView instagram = (ImageView) findViewById(R.id.instagram);
        final ImageView snapchat = (ImageView) findViewById(R.id.snapchat);
        final ImageView twitter = findViewById(R.id.twitter);
        final ImageView youtube = findViewById(R.id.youtube);
        final ImageView email = findViewById(R.id.email);
        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://facewebmodal/f?href=https://www.facebook.com/acharya.ac.in"));
                    intent.setPackage("com.facebook.katana");
                    startActivity(intent);
                } catch (Exception e) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/acharya.ac.in/")));
                }
            }
        });
        instagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/habba2018/"));
                    intent.setPackage("com.instagram.android");
                    startActivity(intent);
                } catch (Exception e) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/habba2018/")));
                }
            }
        });
        snapchat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("snapchat://add/acharya_habba"));
                    startActivity(intent);
                } catch (Exception e) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.app.goo.gl/?link=https://play.google.com/store/apps/details?id%3Dcom.snapchat.android%26ddl%3D1%26pcampaignid%3Dweb_ddl_1")));
                }
            }
        });
        twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?screen_name=" + "acharyahabba"));
                    startActivity(intent);
                } catch (Exception e) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/acharyahabba?lang=en")));
                }
            }
        });
        youtube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                try {
                    intent = new Intent(Intent.ACTION_VIEW);
                    intent.setPackage("com.google.android.youtube");
                    intent.setData(Uri.parse(url));
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    startActivity(intent);
                }
            }
        });
        email.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View view) {
                                         Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                                                 "mailto", "habba@acharya.ac.in", null));
                                         startActivity(Intent.createChooser(emailIntent, "Send email..."));
                                     }
                                 }
        );
    }

    @Override
    public void onBackPressed() {
        Intent i8 = new Intent(AboutUs.this, MainActivity.class);
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
