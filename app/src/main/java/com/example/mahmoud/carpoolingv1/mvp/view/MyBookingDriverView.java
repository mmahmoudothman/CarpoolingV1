package com.example.mahmoud.carpoolingv1.mvp.view;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;

import com.example.mahmoud.carpoolingv1.R;
import com.example.mahmoud.carpoolingv1.adapter.PassengerInfoRequestAdapter;
import com.example.mahmoud.carpoolingv1.fragment.MyBookingDriverFragment;
import com.example.mahmoud.carpoolingv1.mvp.base.FragmentView;
import com.example.mahmoud.carpoolingv1.mvp.model.PassengerInfoRequest;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyBookingDriverView extends FragmentView<MyBookingDriverFragment, Pair<PassengerInfoRequest, Integer>> {
    @BindView(R.id.recycler_view_my_booking_driver)
    RecyclerView recyclerView;
    private PassengerInfoRequestAdapter adapter;

    public MyBookingDriverView(MyBookingDriverFragment fragment) {
        super(fragment);
        if (fragment.getView() != null) {
            ButterKnife.bind(this, fragment.getView());
        }
    }

    public void init() {
        final Activity activity = getActivity();
        if (activity == null) {
            return;
        }
        adapter = new PassengerInfoRequestAdapter(adapterObserver);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        recyclerView.setAdapter(adapter);
    }

    public void addAll(List<PassengerInfoRequest> list) {
        adapter.addAll(list);
    }

    public void removeAll() {
        adapter.removeAll();
    }

    public List<PassengerInfoRequest> getPassengerList() {
        return adapter.getItems();
    }

    public void remove(int position) {
        adapter.remove(position);
    }

    public void add(PassengerInfoRequest passengerInfoRequest) {
        adapter.add(passengerInfoRequest);
    }
}
