package com.acharya.habba2k18.Timeline;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.acharya.habba2k18.R;
import com.acharya.habba2k18.Scroll.Scroll;
import com.acharya.habba2k18.Timeline.model.OrderStatus;
import com.acharya.habba2k18.Timeline.model.Orientation;
import com.acharya.habba2k18.Timeline.model.TimeLineModel;
import com.acharya.habba2k18.Timeline.utils.DateTimeUtils;
import com.acharya.habba2k18.Timeline.utils.VectorDrawableUtils;
import com.github.vipulasri.timelineview.TimelineView;

import java.util.List;

public class TimeLineAdapter extends RecyclerView.Adapter<TimeLineViewHolder> {

    private List<TimeLineModel> mFeedList;
    private Context mContext;
    private Orientation mOrientation;
    private boolean mWithLinePadding;
    private LayoutInflater mLayoutInflater;


    public TimeLineAdapter(List<TimeLineModel> feedList, Orientation orientation, boolean withLinePadding) {
        mFeedList = feedList;
        mOrientation = orientation;
        mWithLinePadding = withLinePadding;
    }


    @Override
    public int getItemViewType(int position) {
        return TimelineView.getTimeLineViewType(position, getItemCount());
    }


    @Override
    public TimeLineViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        mLayoutInflater = LayoutInflater.from(mContext);
        View view;

        if (mOrientation == Orientation.HORIZONTAL) {
            view = mLayoutInflater.inflate(mWithLinePadding ? R.layout.item_timeline : R.layout.item_timeline, parent, false);
        } else {
            view = mLayoutInflater.inflate(mWithLinePadding ? R.layout.item_timeline : R.layout.item_timeline, parent, false);
        }

        return new TimeLineViewHolder(view, viewType);
    }


    @Override
    public void onBindViewHolder(TimeLineViewHolder holder, final int position) {

        TimeLineModel timeLineModel = mFeedList.get(position);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mFeedList.get(position).getName().equals("0")) {
                    Toast.makeText(mContext,"No events on this date!!",Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent1 = new Intent(mContext, Scroll.class);
                    intent1.putExtra("event", mFeedList.get(position).getName());
                    intent1.putExtra("category", mFeedList.get(position).getcName());
                    mContext.startActivity(intent1);
                }

            }
        });



        if (timeLineModel.getStatus() == OrderStatus.INACTIVE) {
            holder.mTimelineView.setMarker(VectorDrawableUtils.getDrawable(mContext, R.drawable.ic_marker_inactive, android.R.color.darker_gray));
        } else if (timeLineModel.getStatus() == OrderStatus.ACTIVE) {
            holder.mTimelineView.setMarker(VectorDrawableUtils.getDrawable(mContext, R.drawable.ic_marker_active, R.color.colorPrimary));
        } else {
            holder.mTimelineView.setMarker(ContextCompat.getDrawable(mContext, R.drawable.ic_marker_active), ContextCompat.getColor(mContext, R.color.colorPrimary));
        }

        if (!timeLineModel.getDate().isEmpty()) {
            holder.mDate.setVisibility(View.VISIBLE);
            holder.mDate.setText(DateTimeUtils.parseDateTime(timeLineModel.getDate(), "yyyy-MM-dd HH:mm", "hh:mm a, dd-MMM-yyyy"));
        } else
            holder.mDate.setVisibility(View.GONE);

        holder.mMessage.setText(timeLineModel.getMessage());
    }


    @Override
    public int getItemCount() {
        return (mFeedList != null ? mFeedList.size() : 0);
    }

}
