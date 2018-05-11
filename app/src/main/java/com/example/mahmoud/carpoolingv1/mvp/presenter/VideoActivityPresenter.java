package com.example.mahmoud.carpoolingv1.mvp.presenter;


import com.example.mahmoud.carpoolingv1.data.preference.init.InitPreference;
import com.example.mahmoud.carpoolingv1.mvp.view.VideoActivityView;

public class VideoActivityPresenter {

    private InitPreference initPreference;
    private VideoActivityView view;

    public VideoActivityPresenter(InitPreference initPreference, VideoActivityView view) {
        this.initPreference = initPreference;
        this.view = view;
    }

    public void init() {
        if (initPreference.wasVideoShown()) {
            view.goToAuthActivity();
        }
        view.init();
    }

    public void onPause() {
        view.pauseVideo();
    }

    public void onResume() {
        view.startVideo();
    }

    public void goToAuthActivity() {
        view.goToAuthActivity();
        initPreference.putWasVideoShown(true);
    }
}
