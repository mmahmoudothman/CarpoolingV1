package com.example.mahmoud.carpoolingv1.mvp.view;


import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mahmoud.carpoolingv1.R;
import com.example.mahmoud.carpoolingv1.customviews.dialog.views.RoundedImageView;
import com.example.mahmoud.carpoolingv1.fragment.MyBookingPassengerFragment;
import com.example.mahmoud.carpoolingv1.mvp.base.FragmentView;
import com.example.mahmoud.carpoolingv1.mvp.model.DriverInfoRequest;
import com.example.mahmoud.carpoolingv1.utils.StringUtils;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyBookingPassengerView extends FragmentView<MyBookingPassengerFragment, Integer> {

    @BindView(R.id.txt_driver_name)
    TextView textDriverName;
    @BindView(R.id.txt_driver_phone)
    TextView textDriverPhone;
    @BindView(R.id.txt_driver_departure)
    TextView textDriverDeparture;
    @BindView(R.id.txt_driver_arrival)
    TextView textDriverArrival;
    @BindView(R.id.txt_driver_date)
    TextView textDriverDate;
    @BindView(R.id.txt_driver_hour)
    TextView textDriverHour;
    @BindView(R.id.image_photo)
    RoundedImageView imagePhoto;
    @BindView(R.id.btn_cancel_booking)
    Button btnCancelButton;

    public MyBookingPassengerView(MyBookingPassengerFragment fragment) {
        super(fragment);
        if (fragment.getView() != null)
            ButterKnife.bind(this, fragment.getView());
    }

    public void setDateAndHourSelected(String date, String hour) {
        textDriverDate.setText(StringUtils.formatDateWithTodayLogic(date));
        textDriverHour.setText(StringUtils.formatHour(hour));
        imagePhoto.setImageResource(R.drawable.searching_driver_image);

    }

    public void setDriverInfo(@NonNull DriverInfoRequest driverInfo, @NonNull String date, @NonNull String hour) {
        if (TextUtils.isEmpty(driverInfo.getDriverName())) {
            textDriverName.setText(StringUtils.EMPTY_STRING);
        } else {
            textDriverName.setText(driverInfo.getDriverName());
        }
        if (TextUtils.isEmpty(driverInfo.getDriverPhone())) {
            textDriverPhone.setText(StringUtils.EMPTY_STRING);
        } else {
            textDriverPhone.setText(driverInfo.getDriverPhone());
        }
        if (TextUtils.isEmpty(driverInfo.address)) {
            textDriverDeparture.setText(StringUtils.EMPTY_STRING);
        } else {
            textDriverDeparture.setText(driverInfo.address);
        }
        if (!TextUtils.isEmpty(driverInfo.getDriverPhotoUri())) {
            Picasso.with(getActivity()).load(driverInfo.getDriverPhotoUri()).into(imagePhoto);
        }
        textDriverDate.setText(StringUtils.formatDateWithTodayLogic(date));
        textDriverHour.setText(StringUtils.formatHour(hour));
        btnCancelButton.setText(R.string.btn_cancel_driver);
    }

    public void setArrivalText(String arrival) {
        if (!TextUtils.isEmpty(arrival)) {
            textDriverArrival.setText("Distination: " + arrival);
        }
    }

    public void setDepartureText(String departure) {
        if (!TextUtils.isEmpty(departure)) {
            textDriverDeparture.setText("Origen: " + departure);
        }
    }

    public void cleanFragmentView() {
        if (getActivity() == null) return;
        textDriverName.setText(StringUtils.EMPTY_STRING);
        textDriverPhone.setText(getActivity().getResources().getText(R.string.my_booking_passenger_not_selected_values));
        textDriverDeparture.setText(StringUtils.EMPTY_STRING);
        textDriverDate.setText(StringUtils.EMPTY_STRING);
        textDriverHour.setText(StringUtils.EMPTY_STRING);
        textDriverArrival.setText(StringUtils.EMPTY_STRING);
        makeButtonAndImageInvisibles();
    }

    public void makeButtonAndImageVisibles() {
        btnCancelButton.setVisibility(View.VISIBLE);
        imagePhoto.setVisibility(View.VISIBLE);
    }

    private void makeButtonAndImageInvisibles() {
        btnCancelButton.setVisibility(View.INVISIBLE);
        imagePhoto.setVisibility(View.INVISIBLE);
    }

    public void setInitialSearchingDriverInfo() {
        setTextDriverNameBySearching();
        if (getActivity() != null) {
            textDriverPhone.setText(StringUtils.EMPTY_STRING);
            textDriverDeparture.setText(getActivity().getResources().getText(R.string.my_booking_passenger_description));
            textDriverDate.setText(StringUtils.EMPTY_STRING);
            textDriverHour.setText(StringUtils.EMPTY_STRING);
            btnCancelButton.setText(R.string.my_booking_passenger_button_cancel_booking);
            imagePhoto.setImageResource(R.drawable.searching_driver_image);
            makeButtonAndImageVisibles();
        }
    }

    public void setTextDriverNameBySearching() {
        if (getActivity() != null) {
            textDriverName.setText(getActivity().getResources().getText(R.string.my_booking_passenger_searching));
        }
    }

    public void setTextDriverPhonebySearchingMessage() {
        if (getActivity() != null) {
            textDriverPhone.setText(getActivity().getResources().getText(R.string.my_booking_passenger_description));
        }
    }
}