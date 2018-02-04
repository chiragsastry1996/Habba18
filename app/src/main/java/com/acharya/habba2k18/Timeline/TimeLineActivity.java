package com.acharya.habba2k18.Timeline;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;


import com.acharya.habba2k18.Events.HttpHandler;
import com.acharya.habba2k18.R;
import com.acharya.habba2k18.Test.Test;
import com.acharya.habba2k18.Timeline.model.OrderStatus;
import com.acharya.habba2k18.Timeline.model.Orientation;
import com.acharya.habba2k18.Timeline.model.TimeLineModel;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import static android.widget.Toast.LENGTH_LONG;


public class TimeLineActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private TimeLineAdapter mTimeLineAdapter;
    private List<TimeLineModel> mDataList = new ArrayList<>();
    public ArrayList<Timestamp> timestampsList;
    public MaterialCalendarView calendarView;
    HashSet<CalendarDay> calendarDays;
    private Orientation mOrientation;
    private boolean mWithLinePadding;
    public int size, oldDate = 1, oldMonth = 1, oldYear = 1971;
    public String url, url1 = "http://acharyahabba.in/habba18/datespan.php", tmstmp;
    ArrayList<ArrayList<String>> timelineList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        timestampsList = new ArrayList<>();
        timelineList = new ArrayList();


        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        calendarView = (MaterialCalendarView) findViewById(R.id.calendar);


     //   setTitle(mOrientation == Orientation.HORIZONTAL ? getResources().getString(R.string.horizontal_timeline) : getResources().getString(R.string.vertical_timeline));

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(getLinearLayoutManager());
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setNestedScrollingEnabled(false);

        calendarDays = new HashSet<CalendarDay>();
        datespan();

        calendarView.setDateSelected(CalendarDay.today(), true);
//        calendarView.addDecorator(new EventDecorator(Color.RED, calendarDays));

//        calendarView.state().edit()
//                .setFirstDayOfWeek(Calendar.WEDNESDAY)
//                .setMinimumDate(CalendarDay.from(2018, 1, 18))
//                .setMaximumDate(CalendarDay.from(2018, 2, 18))
//                .commit();

        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                mDataList.clear();
                timelineList.clear();
                mRecyclerView.invalidate();


                int curDate = date.getDay();
                int months = date.getMonth() + 1;
                String mon = "", dat = "";
                if (months < 10) mon = "0" + String.valueOf(months);
                else mon = String.valueOf(months);
                if (curDate < 10) dat = "0" + String.valueOf(curDate);
                else dat = String.valueOf(curDate);

                String searchDate = date.getYear() + "-" + mon + "-" + dat;

                toolbar.setTitle(searchDate);


                for (int i = 0; i < Test.timeline.size(); i++) {
                    if (searchDate.equals(Test.timeline.get(i).get(1))) {
                        ArrayList<String> indexList = new ArrayList<String>();
                        indexList.add(Test.timeline.get(i).get(0));
                        String time = Test.timeline.get(i).get(2);

                        SimpleDateFormat date12Format = new SimpleDateFormat("hh:mm a");

                        SimpleDateFormat date24Format = new SimpleDateFormat("HH:mm");

                        try {
                            tmstmp = Test.timeline.get(i).get(1) + " " + date24Format.format(date12Format.parse(time));
                            indexList.add(tmstmp);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        indexList.add(Test.timeline.get(i).get(3));
                        indexList.add(Test.timeline.get(i).get(4));
                        timelineList.add(indexList);
                    }

                }
                initView();
            }
        });

        calendarView.setDateSelected(CalendarDay.today(), true);

        int curDate = CalendarDay.today().getDay();
        int months = CalendarDay.today().getMonth() + 1;
        String mon = "", dat = "";
        if (months < 10) mon = "0" + String.valueOf(months);
        else mon = String.valueOf(months);
        if (curDate < 10) dat = "0" + String.valueOf(curDate);
        else dat = String.valueOf(curDate);

        String search = CalendarDay.today().getYear() + "-" + mon + "-" + dat;

        toolbar.setTitle(search);
        for (int i = 0; i < Test.timeline.size(); i++) {
            if (search.equals(Test.timeline.get(i).get(1))) {
                ArrayList<String> indexList = new ArrayList<String>();
                indexList.add(Test.timeline.get(i).get(0));
                String time = Test.timeline.get(i).get(2);

                SimpleDateFormat date12Format = new SimpleDateFormat("hh:mm a");

                SimpleDateFormat date24Format = new SimpleDateFormat("HH:mm");

                try {

                    indexList.add(Test.timeline.get(i).get(1) + " " + date24Format.format(date12Format.parse(time)));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                indexList.add(Test.timeline.get(i).get(3));
                indexList.add(Test.timeline.get(i).get(4));
                timelineList.add(indexList);
            }

        } initView();

    }

    public void datespan() {

        for (int i = 0; i < Test.timeline.size(); i++) {

            SimpleDateFormat date12Format = new SimpleDateFormat("hh:mm a");

            SimpleDateFormat date24Format = new SimpleDateFormat("HH:mm");

            String time = null;
            try {
                time = Test.timeline.get(i).get(1) + " " + date24Format.format(date12Format.parse(Test.timeline.get(i).get(2)));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat(
                    "yyyy-MM-dd hh:mm");

            Date parsedTimeStamp = null;
            try {
                parsedTimeStamp = dateFormat.parse(time);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            try {
                Timestamp timestamp = new Timestamp(parsedTimeStamp.getTime());
                timestampsList.add(timestamp);
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "TimeStap parsing error", LENGTH_LONG).show();
            }

        }

        for (int j = 0; j < timestampsList.size(); j++) {

            calendarDays.add(CalendarDay.from(timestampsList.get(j).getYear() - 100 + 2000, timestampsList.get(j).getMonth(), timestampsList.get(j).getDate()));

        }
        calendarView.addDecorator(new EventDecorator(Color.RED, calendarDays));

    }

    private LinearLayoutManager getLinearLayoutManager() {
        if (mOrientation == Orientation.HORIZONTAL) {
            return new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        } else {
            return new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        }
    }

    private void initView() {
        setDataListItems();
        mTimeLineAdapter = new TimeLineAdapter(mDataList, mOrientation, mWithLinePadding);
        mRecyclerView.setAdapter(mTimeLineAdapter);
    }

    private void setDataListItems() {

        if (timelineList.size() < 1) {
            mDataList.add(new TimeLineModel("No events at this date", "0000-00-00 00:00", OrderStatus.COMPLETED, "0", "0"));
        } else if (timelineList.size() > 0) {
            for (int j = 0; j < timelineList.size(); j++) {
                mDataList.add(new TimeLineModel(timelineList.get(j).get(0), timelineList.get(j).get(1), OrderStatus.ACTIVE, timelineList.get(j).get(2), timelineList.get(j).get(3)));
                System.out.println("ttttt " + timelineList.get(j).get(0) + timelineList.get(j).get(1));
            }

        }
    }
}
