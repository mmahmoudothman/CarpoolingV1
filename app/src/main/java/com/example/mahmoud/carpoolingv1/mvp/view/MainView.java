package com.example.mahmoud.carpoolingv1.mvp.view;

import android.view.View;

import com.example.mahmoud.carpoolingv1.R;
import com.example.mahmoud.carpoolingv1.activity.MainActivity;
import com.example.mahmoud.carpoolingv1.mvp.base.ActivityView;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainView extends ActivityView<MainActivity> {

    @BindView(R.id.bottom_navigation)
    BottomNavigationViewEx bottomMenu;

    public MainView(MainActivity activity) {
        super(activity);
        ButterKnife.bind(this, activity);
        bottomMenu = (BottomNavigationViewEx) activity.findViewById(R.id.bottom_navigation);
    }

    public void init() {

    }

    public void hideMenu() {
        bottomMenu.setVisibility(View.GONE);
    }

    public void showMenu() {
        bottomMenu.setVisibility(View.VISIBLE);
    }
}
