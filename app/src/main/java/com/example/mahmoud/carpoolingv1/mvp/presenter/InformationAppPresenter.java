package com.example.mahmoud.carpoolingv1.mvp.presenter;


import com.example.mahmoud.carpoolingv1.mvp.view.InformationAppView;

public class InformationAppPresenter {
    private InformationAppView view;

    public InformationAppPresenter( InformationAppView view) {
        this.view = view;
    }

    public void init() {
        view.init();
    }
}
