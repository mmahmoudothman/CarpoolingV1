package com.example.mahmoud.carpoolingv1.fragment.base;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.mahmoud.carpoolingv1.data.map.MapPreference;
import com.example.mahmoud.carpoolingv1.data.map.MapPreferenceDriverImpl;
import com.example.mahmoud.carpoolingv1.data.map.MapPreferencePassengerImpl;
import com.example.mahmoud.carpoolingv1.data.role.RolePreference;
import com.example.mahmoud.carpoolingv1.data.role.RolePreferenceImpl;

public class BaseMapPreferenceFragment extends Fragment {
    protected RolePreference rolePreference;
    protected MapPreference mapPreference;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        chooseCurrentMapFragment();
    }

    private void chooseCurrentMapFragment() {
        rolePreference = new RolePreferenceImpl(getActivity(), RolePreferenceImpl.NAME);
        if (RolePreference.ROLE_DRIVER.equalsIgnoreCase(rolePreference.getCurrentRole())) {
            mapPreference = new MapPreferenceDriverImpl(getActivity(), MapPreferenceDriverImpl.NAME);
        } else {
            mapPreference = new MapPreferencePassengerImpl(getActivity(), MapPreferencePassengerImpl.NAME);
        }
    }
}
