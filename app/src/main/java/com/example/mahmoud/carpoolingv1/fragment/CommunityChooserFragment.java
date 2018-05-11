package com.example.mahmoud.carpoolingv1.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mahmoud.carpoolingv1.R;
import com.example.mahmoud.carpoolingv1.activity.MainActivity;
import com.example.mahmoud.carpoolingv1.data.map.MapPreference;
import com.example.mahmoud.carpoolingv1.data.map.MapPreferenceDriverImpl;
import com.example.mahmoud.carpoolingv1.data.map.MapPreferencePassengerImpl;
import com.example.mahmoud.carpoolingv1.data.preference.init.InitPreferenceImpl;
import com.example.mahmoud.carpoolingv1.mvp.presenter.CommunityChooserPresenter;
import com.example.mahmoud.carpoolingv1.mvp.view.CommunityChooserView;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class CommunityChooserFragment extends Fragment {

    private CommunityChooserPresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_community_chooser, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MapPreference driverMapPreference = new MapPreferenceDriverImpl(getActivity(), MapPreferenceDriverImpl.NAME);
        MapPreference passengerMapPreference = new MapPreferencePassengerImpl(getActivity(), MapPreferencePassengerImpl.NAME);
        presenter = new CommunityChooserPresenter(
                new CommunityChooserView(this),
                driverMapPreference,
                passengerMapPreference,
                new InitPreferenceImpl(getActivity(), InitPreferenceImpl.NAME)
        );
        presenter.init();
    }

    @OnClick(R.id.btn_icesi)
    void onIcesiClick() {
        presenter.saveCommunity(MapPreference.COMMUNITY_ICESI);
        MainActivity activity = (MainActivity) getActivity();
        if (activity == null) return;
        if (activity.getNavigationManager() == null) return;
        activity.getNavigationManager().goToMyProfileFragment();
    }

    @OnClick(R.id.btn_javeriana)
    void onJaverianaClick() {
        presenter.saveCommunity(MapPreference.COMMUNITY_JAVERIANA);
        MainActivity activity = (MainActivity) getActivity();
        if (activity == null) return;
        if (activity.getNavigationManager() == null) return;
        activity.getNavigationManager().goToMyProfileFragment();
    }


}
