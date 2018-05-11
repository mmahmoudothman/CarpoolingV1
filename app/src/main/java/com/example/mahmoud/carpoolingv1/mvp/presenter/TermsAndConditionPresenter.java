package com.example.mahmoud.carpoolingv1.mvp.presenter;

import com.example.mahmoud.carpoolingv1.data.preference.init.InitPreference;
import com.example.mahmoud.carpoolingv1.mvp.view.TermsAndConditionView;

public class TermsAndConditionPresenter {
    private TermsAndConditionView view;
    private InitPreference initPreference;

    public TermsAndConditionPresenter(InitPreference initPreference, TermsAndConditionView view) {
        this.initPreference = initPreference;
        this.view = view;
    }

    public void init() {
        view.init();
    }

    public void setTermsAndConditionAccepted() {
        initPreference.putWasTermsAndCondictionsAccepted(true);
    }
}
