package com.acharya.habba2k18.Notification;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.acharya.habba2k18.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;


public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MyViewHolder> {
    private Context mContext;
    List<NotificationModel> NotificationList;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, message;
        public ImageView imageView;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.event_name);
            message = (TextView) view.findViewById(R.id.message_notification);
            imageView = (ImageView) view.findViewById(R.id.image_notification);
        }

    }

    public NotificationAdapter(Context mContext, List<NotificationModel> NotificationList) {
        this.mContext = mContext;
        this.NotificationList = NotificationList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notification_list_item, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final NotificationModel notificationModel = NotificationList.get(position);
        holder.name.setText(notificationModel.getName());
        holder.message.setText(notificationModel.getCaption());
        Glide.with(mContext)
                .load(notificationModel.getImage()).apply(new RequestOptions().override(100, 100)
                .placeholder(R.drawable.loader))
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return NotificationList.size();
    }

}



