package com.acharya.habba2k18.CardView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.acharya.habba2k18.R;
import com.acharya.habba2k18.Scroll.Scroll;
import com.acharya.habba2k18.Scroll.ScrollViewPager.ScrollViewPager;
import com.bumptech.glide.Glide;

import java.util.List;

import static com.acharya.habba2k18.CardView.CardView.appBarLayout;


public class AlbumsAdapter extends RecyclerView.Adapter<AlbumsAdapter.MyViewHolder> {

    private Context mContext;
    private List<Album> albumList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, titlealpha;
        public ImageView thumbnail, overflow;

        public MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
            titlealpha = view.findViewById(R.id.titlealpha);
            thumbnail = view.findViewById(R.id.thumbnail);
        }
    }


    public AlbumsAdapter(Context mContext, List<Album> albumList) {
        this.mContext = mContext;
        this.albumList = albumList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.album_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Album album = albumList.get(position);
        holder.title.setText(album.getName());
        holder.titlealpha.setText(album.getName());
        // loading album cover using Glide library
        Glide.with(mContext).load(album.getThumbnail()).into(holder.thumbnail);

        holder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                appBarLayout.setExpanded(true);
                Bundle args = new Bundle();
                args.putInt("position", position);
                args.putString("category", CardView.name);
                Fragment detail = new ScrollViewPager();
                detail.setArguments(args);
                ((AppCompatActivity) mContext).getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.frameContainer, detail).addToBackStack(null).commit();


//                        Intent i = new Intent(view.getContext(),ScrollViewPager.class);
//                        i.putExtra("position",position);
//                        i.putExtra("category",CardView.name);
//                        Toast.makeText(view.getContext(),album.getName(),Toast.LENGTH_SHORT).show();
//                        view.getContext().startActivity(i);
            }

        });

    }


    @Override
    public int getItemCount() {
        return albumList.size();
    }
}