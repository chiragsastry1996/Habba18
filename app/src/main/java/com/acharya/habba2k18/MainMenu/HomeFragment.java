package com.acharya.habba2k18.MainMenu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.acharya.habba2k18.R;
import com.special.ResideMenu.ResideMenu;

public class HomeFragment extends Fragment {

    private View parentView;
    private ResideMenu resideMenu;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.home, container, false);
        setUpViews();
        return parentView;

    }

    private void setUpViews() {
        MainActivity parentActivity = (MainActivity) getActivity();
        resideMenu = parentActivity.getResideMenu();

        parentView.findViewById(R.id.btn_open_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            //    Toast.makeText(getContext(),"Click hua",Toast.LENGTH_LONG).show();
                resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);

            }
        });

        // add gesture operation's ignored views
        //FrameLayout ignored_view = (FrameLayout) parentView.findViewById(R.id.ignored_view);
       // resideMenu.addIgnoredView(ignored_view);
    }

}
