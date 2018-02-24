package com.acharya.habbaregistration.Feed;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.acharya.habbaregistration.R;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.OnInitializedListener;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;


public class YoutubeFragment extends Fragment {

    private static final String API_KEY = "AIzaSyDZMD76BmE86Hv5CaTWX8Co4TYJR7TlTMg";
    Button backbutton;
    TextView tName, tCaption;


    private static String VIDEO_ID,Name = "",Caption = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_youtube, container, false);

        backbutton = (Button)rootView.findViewById(R.id.back);
        tName = (TextView)rootView.findViewById(R.id.name);
        tCaption = (TextView)rootView.findViewById(R.id.caption);

        VIDEO_ID = getArguments().getString("url");
        Name = getArguments().getString("name");
        Caption = getArguments().getString("caption");

        tName.setText(Name); tCaption.setText(Caption);

        YouTubePlayerSupportFragment youTubePlayerFragment = YouTubePlayerSupportFragment.newInstance();


        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.youtube_layout, youTubePlayerFragment).commit();


        youTubePlayerFragment.initialize(API_KEY, new OnInitializedListener() {


            @Override
            public void onInitializationSuccess(Provider provider, YouTubePlayer player, boolean wasRestored) {
                if (!wasRestored) {
                    player.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
                    player.loadVideo(VIDEO_ID);
                    player.play();
                }
            }


            @Override
            public void onInitializationFailure(Provider provider, YouTubeInitializationResult error) {
                // YouTube error
                String errorMessage = error.toString();
                Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_LONG).show();
                Log.d("errorMessage:", errorMessage);
            }
        });

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
       FeedActivity.toolbar2.setVisibility(View.GONE);
    }
    @Override
    public void onStop() {
        super.onStop();
        FeedActivity.toolbar2.setVisibility(View.VISIBLE);
    }


}