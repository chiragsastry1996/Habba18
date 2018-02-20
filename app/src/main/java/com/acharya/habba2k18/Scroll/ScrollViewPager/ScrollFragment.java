package com.acharya.habba2k18.Scroll.ScrollViewPager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.telephony.PhoneNumberUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.acharya.habba2k18.R;
import com.acharya.habba2k18.Scroll.EventRegistration;


public class ScrollFragment extends Fragment{
    int position;
    View view;
    Button button;
    ImageView whatsapp_button,caller_button,navigate_button;
    TextView tv1,tv2,tv3,tv4,tv5,tv6,tv7,tv8;
    ImageView imageView;
    private static String name;
    private static String about;
    private static String rules;
    private static String amount;
    private static String image;
    private static String number;
    private static String ename;
    private static String prizemoney;
    private static String venue;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.scroll_fragment_layout, container, false);

        tv1=(TextView)view.findViewById(R.id.name);
        tv2=(TextView)view.findViewById(R.id.about);
        tv3=(TextView)view.findViewById(R.id.rules);
        tv4=(TextView)view.findViewById(R.id.amount);
        tv5=(TextView)view.findViewById(R.id.number);
        tv6=(TextView)view.findViewById(R.id.ename);
        tv7=(TextView)view.findViewById(R.id.pmoney);
        tv8=(TextView)view.findViewById(R.id.venue);

        button = view.findViewById(R.id.button_register);
        whatsapp_button = view.findViewById(R.id.whatsapp_button);
        caller_button =  view.findViewById(R.id.callerbut);
        navigate_button =  view.findViewById(R.id.button_navigate);
        setUpFragmentData();
        return view;
    }

    public void setUpFragmentData() {
        name = ScrollViewPager.scrollEvents.get(position).get(1);
        about = ScrollViewPager.scrollEvents.get(position).get(3);
        rules = ScrollViewPager.scrollEvents.get(position).get(6);
        number = ScrollViewPager.scrollEvents.get(position).get(7);
        ename = ScrollViewPager.scrollEvents.get(position).get(8);
        image = ScrollViewPager.scrollEvents.get(position).get(2);
        amount = ScrollViewPager.scrollEvents.get(position).get(4);
        prizemoney = ScrollViewPager.scrollEvents.get(position).get(9);
        venue = ScrollViewPager.scrollEvents.get(position).get(11);

        tv1.setText(name);
        tv2.setText(about);
        tv3.setText(rules);
        tv4.setText(amount);
        tv5.setText(number);
        tv6.setText(ename);
        tv7.setText(prizemoney);
        tv8.setText(venue);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), EventRegistration.class);
                intent.putExtra("event",name);
                intent.putExtra("amount",amount);
                startActivity(intent);
            }
        });

        whatsapp_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String smsNumber = "91" + number;                                                              //without '+'
                try {
                    Intent sendIntent = new Intent("android.intent.action.MAIN");
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.setType("text/plain");
                    sendIntent.putExtra(Intent.EXTRA_TEXT, "Hello "+ename+",\n");                       //Message body in Whatsapp
                    sendIntent.putExtra("jid", PhoneNumberUtils.stripSeparators(smsNumber)
                            + "@s.whatsapp.net");                                                           //phone number without "+" prefix
                    sendIntent.setPackage("com.whatsapp");
                    startActivity(sendIntent);
                } catch (Exception e) {
                    Toast.makeText(getContext(), "Error/n" + e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        caller_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + number));
                startActivity(intent);
            }
        });

        navigate_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String urlAddress = "http://maps.google.com/maps?q="+ ScrollViewPager.scrollEvents.get(position).get(12)  +"," + ScrollViewPager.scrollEvents.get(position).get(13) +"("+ name + ")&iwloc=A&hl=es";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlAddress));
                intent.setPackage("com.google.android.apps.maps");
                if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });
    }

    public Fragment setFragmentPosition(int position) {
        this.position = position;
        return this;
    }



}