package com.example.mahmoud.carpoolingv1.mvp.presenter;

import com.example.mahmoud.carpoolingv1.data.map.MapPreference;
import com.example.mahmoud.carpoolingv1.data.preference.init.InitPreference;
import com.example.mahmoud.carpoolingv1.mvp.view.CommunityChooserView;

public class CommunityChooserPresenter {

    private CommunityChooserView view;
    private MapPreference passengerMapPreference;
    private MapPreference driverMapPreference;
    private InitPreference initPreference;

    public CommunityChooserPresenter(CommunityChooserView view,
                                     MapPreference passengerMapPreference,
                                     MapPreference driverMapPreference,
                                     InitPreference initPreference) {
        this.view = view;
        this.passengerMapPreference = passengerMapPreference;
        this.driverMapPreference = driverMapPreference;
        this.initPreference = initPreference;
    }

    public void saveCommunity(String community) {
        driverMapPreference.putCommunity(community);
        passengerMapPreference.putCommunity(community);
        initPreference.putCommunityChoosed(true);
    }

    public void init() {
        view.hideMenu();
    }
}
