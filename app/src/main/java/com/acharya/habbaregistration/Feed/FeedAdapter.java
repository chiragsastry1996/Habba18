package com.acharya.habbaregistration.Feed;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.acharya.habbaregistration.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.youtube.player.YouTubePlayerFragment;

import java.util.List;


public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.MyViewHolder>{



    private String KEY = "AIzaSyCSugGCL8B3H9jWohKzUlm1mI1-fiJHjTM";
    private static final String TAG = FeedAdapter.class.getSimpleName();

    private Context mContext;
    List<Feed> feedList;



    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView,ageTextView;
        RelativeLayout relativeLayout;
        public ImageView imageView;
        protected ImageView playButton;
        protected RelativeLayout relativeLayoutOverYouTubeThumbnailView;
        private YouTubePlayerFragment playerFragment;

        public MyViewHolder(View view) {
            super(view);
            nameTextView = (TextView) view.findViewById(R.id.name);
            ageTextView = (TextView) view.findViewById(R.id.txtStatusMsg);
            relativeLayout = (RelativeLayout)view.findViewById(R.id.relativeLayout_over_youtube_thumbnail);
            relativeLayout.setVisibility(View.GONE);
            imageView = (ImageView) view.findViewById(R.id.feedImage1);
            playButton=(ImageView)itemView.findViewById(R.id.btnYoutube_player);
            playButton.setVisibility(View.GONE);


        }
    }

    public FeedAdapter(Context mContext, List<Feed> albumList) {
        this.mContext = mContext;
        this.feedList = albumList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.feed_list_item, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Feed feed = feedList.get(position);
        holder.nameTextView.setText(feed.getName());
        holder.ageTextView.setText(feed.getCaption());
        if(feed.getImage().contains("http://acharyahabba.in/habba18/admin/feeds")){
            holder.relativeLayout.setVisibility(View.GONE);
            holder.playButton.setVisibility(View.GONE);
            Glide.with(mContext).load(feed.getImage()).apply(new RequestOptions().override(100, 100).placeholder(R.drawable.loader)).into(holder.imageView);
        }
        else {
            Glide.with(mContext).load("https://img.youtube.com/vi/"+feed.getImage()+"/0.jpg").into(holder.imageView);
            System.out.println("youtube "+ "https://img.youtube.com/vi/"+feed.getImage()+"/0.jpg");
            holder.relativeLayout.setVisibility(View.VISIBLE);
            holder.playButton.setVisibility(View.VISIBLE);
            holder.playButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Bundle args = new Bundle();
                    args.putString("url", feed.getImage());
                    args.putString("name", feed.getName());
                    args.putString("caption", feed.getCaption());
                    YoutubeFragment youtube = new YoutubeFragment();
                    youtube.setArguments(args);
                    ((AppCompatActivity) mContext).getSupportFragmentManager()
                            .beginTransaction()
                            .add(R.id.frameContainer, youtube).addToBackStack(null).commit();
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return feedList.size();
    }

}
