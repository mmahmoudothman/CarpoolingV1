package com.example.mahmoud.carpoolingv1.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mahmoud.carpoolingv1.R;
import com.example.mahmoud.carpoolingv1.fragment.base.BaseMapPreferenceFragment;
import com.example.mahmoud.carpoolingv1.mvp.presenter.MyBookingPassengerFragmentPresenter;
import com.example.mahmoud.carpoolingv1.mvp.view.MyBookingPassengerView;
import com.example.mahmoud.carpoolingv1.service.MyBookingPassengerService;
import com.example.mahmoud.carpoolingv1.service.UserService;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyBookingPassengerFragment extends BaseMapPreferenceFragment {

    private MyBookingPassengerFragmentPresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_booking_passenger, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = new MyBookingPassengerFragmentPresenter(
                new MyBookingPassengerView(this),
                new MyBookingPassengerService(),
                new UserService(),
                mapPreference);
        presenter.init();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (presenter != null) {
            presenter.getDriversRequestInfo();
        }
    }

    @OnClick(R.id.btn_cancel_booking)
    void onCancelBooking() {
        if (presenter != null) {
            presenter.onCancelBookingClick();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.unsuscribeFirebaseListener();
        }
    }
}
