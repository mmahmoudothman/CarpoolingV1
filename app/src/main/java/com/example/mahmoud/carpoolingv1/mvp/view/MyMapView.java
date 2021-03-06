package com.example.mahmoud.carpoolingv1.mvp.view;


import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.StringRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import com.example.mahmoud.carpoolingv1.R;
import com.example.mahmoud.carpoolingv1.customviews.dialog.DialogMarker;
import com.example.mahmoud.carpoolingv1.customviews.dialog.DialogPassengerMap;
import com.example.mahmoud.carpoolingv1.dialogfragment.DatePickerFragment;
import com.example.mahmoud.carpoolingv1.dialogfragment.TimePickerFragment;
import com.example.mahmoud.carpoolingv1.fragment.MyMapFragment;
import com.example.mahmoud.carpoolingv1.mvp.base.FragmentView;
import com.example.mahmoud.carpoolingv1.mvp.model.PassengerBooking;
import com.example.mahmoud.carpoolingv1.mvp.model.RequestInfo;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.observers.DisposableObserver;
import timber.log.Timber;

public class MyMapView extends FragmentView<MyMapFragment, Void> {

    private static final int DEFAULT_ZOOM = 16;
    private static final int INITIAL_ZOOM = 11;
    private static final String DATE_PICKER = "datePicker";
    private static final String TIME_PICKER = "timePicker";
    private static final int BITMAP_MARKER_WIDTH = 106;
    private static final int BITMAP_MARKER_HEIGTH = 120;
    private GoogleMap map;
    private LocationRequest mLocationRequest;
    private GoogleApiClient mGoogleApiClient;

    private static final double LATITUDE_CALI_FIRST = 28.1269249;
    private static final double LONGITUDE_CALI_FIRST = 30.743365;
    private static final double LATITUDE_CALI_SECOND = 28.1269249;
    private static final double LONGITUDE_CALI_SECOND = 30.743365;
    private static final double LONGITUDE_INITIAL = 28.1269249;
    private static final double LATITUDE_INITIAL = 30.743365;

    private DialogMarker dialogMarkerClick;
    private DialogPassengerMap dialogPassengerMap;
    private TimePickerFragment timePickerFragment;
    private DatePickerFragment datePickerFragment;

    @BindView(R.id.switch_from_to)
    Switch switchFromTo;
    @BindView(R.id.txt_from_to_location)
    TextView textLocation;
    @BindView(R.id.btn_hour)
    Button btnHour;
    @BindView(R.id.btn_date)
    Button btnDate;

    private PlaceAutocompleteFragment autocompleteFragment;
    private BottomNavigationViewEx bottomMenu;

    public MyMapView(MyMapFragment fragment) {
        super(fragment);
        if (fragment.getView() != null) {
            ButterKnife.bind(this, fragment.getView());
            if (getActivity() != null) {
                bottomMenu = (BottomNavigationViewEx) getActivity().findViewById(R.id.bottom_navigation);
            }
        }
    }

    public void showMenu() {
        bottomMenu.setVisibility(View.VISIBLE);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void setAutocompleteFragment() {
        if (getFragment() == null) return;
        if (getFragment().getChildFragmentManager() == null) return;
        autocompleteFragment = initPlaceAutocompleteFragment(getFragment());
        autocompleteFragment.setBoundsBias(new LatLngBounds(
                new LatLng(LATITUDE_CALI_SECOND, LONGITUDE_CALI_SECOND),
                new LatLng(LATITUDE_CALI_FIRST, LONGITUDE_CALI_FIRST)
        ));

        autocompleteFragment.setOnPlaceSelectedListener(getFragment());
    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Nullable
    private PlaceAutocompleteFragment initPlaceAutocompleteFragment(@NonNull Fragment fragment) {
        PlaceAutocompleteFragment mapFragment = null;
        if (fragment.getChildFragmentManager() != null) {
            FragmentManager fm = getFragmentManager(getFragment());
            mapFragment = (PlaceAutocompleteFragment) fm.findFragmentByTag("autocomplete_fragment");
            if (mapFragment == null) {
                mapFragment = new PlaceAutocompleteFragment();
                FragmentTransaction ft = fm.beginTransaction();
                ft.add(R.id.place_autocomplete_fragment, mapFragment, "autocomplete_fragment");
                ft.commit();
                fm.executePendingTransactions();
            }
        }
        return mapFragment;
    }

    private PlaceAutocompleteFragment getAutocompleteMapFragment(Fragment fragment) {
        FragmentManager fm;

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            fm = fragment.getFragmentManager();
        } else {
            fm = fragment.getChildFragmentManager();
        }

        return (PlaceAutocompleteFragment) fm.findFragmentById(R.id.place_autocomplete_fragment);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private FragmentManager getFragmentManager(Fragment fragment) {
        FragmentManager fm;

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            fm = fragment.getChildFragmentManager();
        } else {
            fm = fragment.getChildFragmentManager();
        }

        return fm;
    }


    private void setTextAutocompleteFragmentWithText(String text) {
        try {
            if (autocompleteFragment != null && text != null) {
                autocompleteFragment.setText(text);
            }
        } catch (NullPointerException e) {
            Timber.e(e, e.getMessage());
        }
    }

    public void setTextAutocompleteFragmentWithCurrentCoord(String currentLocation) {
        try {
            if (autocompleteFragment != null && currentLocation != null) {
                autocompleteFragment.setText(currentLocation);
            }
        } catch (NullPointerException e) {
            Timber.e(e, e.getMessage());
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void initMap() {
        if (getFragment() == null) return;
        //if (getFragment().getChildFragmentManager() == null) return;
        MapFragment mapFragment = initMapFragment(getFragment());
        if (mapFragment != null) {
            mapFragment.getMapAsync(getFragment());
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Nullable
    private MapFragment initMapFragment(@NonNull Fragment fragment) {
        MapFragment mapFragment = null;
        if (fragment.getChildFragmentManager() != null) {
            FragmentManager fm = getFragmentManager(getFragment());
            mapFragment = (MapFragment) fm.findFragmentByTag("mapFragment");
            if (mapFragment == null) {
                mapFragment = new MapFragment();
                FragmentTransaction ft = fm.beginTransaction();
                ft.add(R.id.map_fragment_container, mapFragment, "mapFragment");
                ft.commit();
                fm.executePendingTransactions();
            }
        }
        return mapFragment;
    }

    public synchronized void buildGoogleApiClient() {
        Activity activity = getActivity();
        if (activity == null) {
            return;
        }
        MyMapFragment fragment = getFragment();
        if (fragment == null) {
            return;
        }
        mGoogleApiClient = new GoogleApiClient.Builder(activity)
                .addConnectionCallbacks(fragment)
                .addOnConnectionFailedListener(fragment)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    public void initViews() {
        switchFromTo.setOnCheckedChangeListener(getFragment());
    }

    public void setSwitchState(boolean checked) {
        switchFromTo.setOnCheckedChangeListener(null);
        switchFromTo.setChecked(checked);
        switchFromTo.setOnCheckedChangeListener(getFragment());
    }

    public void setSwitchIsClickable(boolean value) {
        switchFromTo.setClickable(value);
    }

    public void setTextLocationText(String text) {
        textLocation.setText(text);
    }

    public void showErrorDialog(GoogleApiAvailability api, int availableId) {
        Dialog dialog = api.getErrorDialog(getActivity(), availableId, 0);
        dialog.show();
    }

    public void setMap(GoogleMap map) {
        this.map = map;
        moveCamera(LATITUDE_INITIAL, LONGITUDE_INITIAL);
    }

    private void moveCamera(double latitude, double longitude) {
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), INITIAL_ZOOM));
    }

    private void setNotChoosedRedMarker(LatLng latLng, String title, int id) {
        if (getContext() == null) return;
        Bitmap scaledBitmap = getScaledBitmap(R.drawable.custom_marker, getContext().getResources());
        BitmapDescriptor icon = BitmapDescriptorFactory.fromBitmap(scaledBitmap);
        scaledBitmap.recycle();
        map.addMarker(new MarkerOptions()
                .position(latLng)
                .icon(icon)
                .title(title))
                .setTag(id);
    }

    private void setChoosedByDriverGreenMarker(LatLng latLng, String title, int id) {
        if (getContext() == null) return;
        Bitmap scaledBitmap = getScaledBitmap(R.drawable.green_marker, getContext().getResources());
        BitmapDescriptor icon = BitmapDescriptorFactory.fromBitmap(scaledBitmap);
        scaledBitmap.recycle();
        map.addMarker(new MarkerOptions()
                .position(latLng)
                .icon(icon)
                .title(title))
                .setTag(id);
    }

    private Bitmap getScaledBitmap(@DrawableRes int resId, @NonNull Resources resources) {
        Bitmap bitmap = BitmapFactory.decodeResource(resources, resId);
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, BITMAP_MARKER_WIDTH, BITMAP_MARKER_HEIGTH, false);
        bitmap.recycle();
        return scaledBitmap;
    }

    private void setNotChoosedRedMarker(LatLng latLng, String title) {
        map.addMarker(new MarkerOptions()
                .position(latLng)
                .title(title));
    }

    private void setMarkerWithTitleAndSnippet(LatLng latLng, String title) {
        map.addMarker(new MarkerOptions()
                .position(latLng)
                .title(title));
    }

    public void animateCamera(LatLng position) {
        map.animateCamera(CameraUpdateFactory.newLatLng(position));
    }

    public void setLocationRequest() {
        if (getActivity() == null) return;
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            try {
                if (mGoogleApiClient != null && mLocationRequest != null) {
                    LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, getFragment());
                }
            } catch (IllegalStateException e) {
                Timber.e(e.getMessage(), e);
            }
        }
    }

    public void goToCurrentLocation(LatLng latLng, String currentAddress) {
        if (map != null) {
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, DEFAULT_ZOOM);
            map.animateCamera(cameraUpdate);
            removeLocationUpdates();
            setTextAutocompleteFragmentWithText(currentAddress);
        }
    }

    public void requestPermissionsActivity() {
        if (getActivity() != null) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MyMapFragment.PERMISSION_REQUEST_FINE_LOCATION);
        }
    }

    @Nullable
    public LatLng getCurrentCoordinatesFromCamera() {
        LatLng current = null;
        if (map != null) {
            current = map.getCameraPosition().target;
        }
        return current;
    }

    public void addMarkerForNoChoosenPassenger(PassengerBooking passengerBooking, int id) {
        setNotChoosedRedMarker(new LatLng(passengerBooking.latitude, passengerBooking.longitude), passengerBooking.getKey(), id);
    }

    public void addMarkerForAlreadyChoosenByDriverPassenger(PassengerBooking passengerBooking, int id) {
        setChoosedByDriverGreenMarker(new LatLng(passengerBooking.latitude, passengerBooking.longitude), passengerBooking.getKey(), id);
    }

    public void showDialogQuota(DisposableObserver<PassengerBooking> observer, PassengerBooking passengerBooking) {
        if (getActivity() == null) return;
        dialogMarkerClick = new DialogMarker(getActivity(), passengerBooking);
        dialogMarkerClick.subscribeToDialogEvent(observer);
        dialogMarkerClick.show();
    }

    public void showDialogRequestBooking(DisposableObserver<Pair<PassengerBooking, RequestInfo>> observer, PassengerBooking passengerBooking, RequestInfo requestInfo) {
        if (getActivity() == null) return;
        dialogPassengerMap = new DialogPassengerMap(getActivity(), passengerBooking, requestInfo);
        dialogPassengerMap.subscribeToDialogEvent(observer);
        dialogPassengerMap.show();
    }

    public void setListeners() {
        MyMapFragment fragment = getFragment();
        if (fragment == null) {
            return;
        }
        if (map == null) {
            return;
        }
        map.setOnCameraMoveListener(getFragment());
        map.setOnCameraMoveStartedListener(getFragment());
        map.setOnCameraIdleListener(getFragment());
        map.setOnMarkerClickListener(getFragment());
    }

    public void removeListeners() {
        MyMapFragment fragment = getFragment();
        if (fragment == null) {
            return;
        }
        if (map == null) {
            return;
        }
        removeLocationUpdates();
        map.setOnCameraMoveListener(null);
        map.setOnCameraMoveStartedListener(null);
        map.setOnCameraIdleListener(null);
        map.setOnMarkerClickListener(null);
    }

    private void removeLocationUpdates() {
        try {
            if (mGoogleApiClient != null && getFragment() != null) {
                LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, getFragment());
            }
        } catch (IllegalStateException e) {
            Timber.e(e.toString(), e);
        }
    }

    public void setMyLocationEnabled() {
        try {
            map.setMyLocationEnabled(true);
        } catch (SecurityException e) {
            Timber.e(e.getMessage(), e);
        }
    }

    public void setButtonHour(String hour) {
        btnHour.setText(hour);
    }

    public void setButtonHour(@StringRes int resId) {
        btnHour.setText(resId);
    }

    public void setButtonDate(@StringRes int resId) {
        btnDate.setText(resId);
    }

    public void setButtonDate(String text) {
        btnDate.setText(text);
    }

    public void clearMap() {
        if (map == null) return;
        map.clear();
    }

    public void showTimePickerFragment(DisposableObserver<String> observer) {
        if (getFragment() == null) return;
        if (getFragment().getFragmentManager() == null) return;
        timePickerFragment = new TimePickerFragment();
        timePickerFragment.subscribeToDialogFragment(observer);
        timePickerFragment.show(getFragment().getFragmentManager(), TIME_PICKER);
    }

    public void showDatePickerFragment(DisposableObserver<String> observer) {
        if (getFragment() == null) return;
        if (getFragment().getFragmentManager() == null) return;
        DatePickerFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.subscribeToDialogFragment(observer);
        datePickerFragment.show(getFragment().getFragmentManager(), DATE_PICKER);
    }

    public void unsubscribeObservers() {
        if (timePickerFragment != null) {
            timePickerFragment.unsubscribeToDialogFragment();
            timePickerFragment.dismiss();
        }
        if (dialogMarkerClick != null) {
            dialogMarkerClick.unsubscribeToDialogEvent();
            dialogMarkerClick.dismiss();
        }
        if (datePickerFragment != null) {
            datePickerFragment.unsubscribeToDialogFragment();
            datePickerFragment.dismiss();
        }
        if (dialogPassengerMap != null) {
            dialogPassengerMap.unsubscribeToDialogEvent();
            dialogPassengerMap.dismiss();
        }
    }
}
