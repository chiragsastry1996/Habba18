package com.acharya.habba2k18.AboutUs;

import android.content.Intent;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.acharya.habba2k18.MainMenu.MainActivity;
import com.acharya.habba2k18.R;

public class AboutUs extends AppCompatActivity {

    ViewPager mViewPager;
    TextView title;
    AboutUsAdapter aboutUsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        getSupportActionBar().hide();

        title = (TextView)findViewById(R.id.title);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }


        mViewPager = (ViewPager)findViewById(R.id.view_pager_aboutus);
        aboutUsAdapter = new AboutUsAdapter(getSupportFragmentManager());

                mViewPager.setPageTransformer(false, new ViewPager.PageTransformer() {
                    @Override public void transformPage(View page, float position) {
                        page.setScaleX(1f - Math.abs(position * 0.3f));
                        page.setScaleY(1f - Math.abs(position * 0.1f));
                        page.setAlpha(1.0f - Math.abs(position * 0.5f));
            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if(position == 0) title.setText("About Habba");
                if(position == 1) title.setText("About Acharya");
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

    }

    @Override
    public void onBackPressed() {
        Intent i8 = new Intent(AboutUs.this, MainActivity.class);
        startActivity(i8);
        finish();
    }

}
