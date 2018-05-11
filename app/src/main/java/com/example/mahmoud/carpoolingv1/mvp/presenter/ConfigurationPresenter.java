package com.example.mahmoud.carpoolingv1.mvp.presenter;


import com.example.mahmoud.carpoolingv1.data.map.MapPreference;
import com.example.mahmoud.carpoolingv1.data.map.MapPreferenceDriverImpl;
import com.example.mahmoud.carpoolingv1.data.map.MapPreferencePassengerImpl;
import com.example.mahmoud.carpoolingv1.data.preference.init.InitPreference;
import com.example.mahmoud.carpoolingv1.mvp.base.BaseFragmentPresenter;
import com.example.mahmoud.carpoolingv1.mvp.view.ConfigurationView;
import com.example.mahmoud.carpoolingv1.service.UserService;
import com.facebook.AccessToken;
import com.google.firebase.auth.FirebaseAuth;

public class ConfigurationPresenter extends BaseFragmentPresenter {
    private ConfigurationView view;
    private FirebaseAuth firebaseAuth;
    private InitPreference initPreference;

    public ConfigurationPresenter(ConfigurationView view, UserService userService, InitPreference initPreference) {
        super(view, userService);
        this.view = view;
        this.initPreference = initPreference;
    }

    public void init() {
        firebaseAuth = FirebaseAuth.getInstance();
        view.init();
    }

    public void onCloseSession() {
        MapPreference driverMapPreference = new MapPreferenceDriverImpl(view.getActivity(), MapPreferenceDriverImpl.NAME);
        resetAllMapPreferences(driverMapPreference);
        MapPreference passengerMapPreference = new MapPreferencePassengerImpl(view.getActivity(), MapPreferencePassengerImpl.NAME);
        resetAllMapPreferences(passengerMapPreference);
        resetInitPreferences();
        firebaseAuth.signOut();
        AccessToken.setCurrentAccessToken(null);
        view.finishActivity();
    }

    private void resetInitPreferences() {
        initPreference.putWasTermsAndCondictionsAccepted(false);
        initPreference.putAlreadyRegistered(false);
        initPreference.putCommunityChoosed(false);
    }

    public void goToReportIssueWebPage() {
        view.goToReportIssueWebPage();
    }

    public void goToReportUserWebPage() {
        view.goToReportUserWebPage();
    }


    public void goToActivateUser() {
        view.goToActiveUserWebPage();
    }
}
