package com.example.mahmoud.carpoolingv1.service;

import com.example.mahmoud.carpoolingv1.mvp.model.PassengerBooking;
import com.example.mahmoud.carpoolingv1.service.base.BaseFirebaseService;

public class PassengerMapService extends BaseFirebaseService {

    public PassengerMapService() {
        super();
    }

    public void putPassengerBookingPerCommunityOriginDate(PassengerBooking passengerBooking, String communityNodeName, String date, String hour) {
        databaseReference.child(communityNodeName).child(date).child(hour).child(passengerBooking.getKey()).setValue(passengerBooking);
    }
}
