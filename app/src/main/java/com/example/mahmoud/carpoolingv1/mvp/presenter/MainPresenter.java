package com.example.mahmoud.carpoolingv1.mvp.presenter;

import com.example.mahmoud.carpoolingv1.activity.MainActivity;
import com.example.mahmoud.carpoolingv1.mvp.view.MainView;

public class MainPresenter {

    private MainView view;

    public MainPresenter(MainView view) {
        this.view = view;
    }

    public void init() {
        MainActivity activity = view.getActivity();
        if (activity == null) {
            return;
        }
        if (activity.getFragmentManager() == null) {
            return;
        }
        view.init();
    }
}
