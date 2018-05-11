package com.example.mahmoud.carpoolingv1.mvp.view;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.example.mahmoud.carpoolingv1.R;
import com.example.mahmoud.carpoolingv1.customviews.dialog.views.RoundedImageView;
import com.example.mahmoud.carpoolingv1.fragment.MyProfileFragment;
import com.example.mahmoud.carpoolingv1.mvp.base.FragmentView;
import com.example.mahmoud.carpoolingv1.utils.StringUtils;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyProfileView extends FragmentView<MyProfileFragment, Void> {

    private static final String COMMUNITY_TEXT = " ";

    private BottomNavigationViewEx bottomMenu;

    @BindView(R.id.photo_user)
    RoundedImageView imagePhoto;
    @BindView(R.id.txt_community_my_profile)
    TextView textCommunity;
    @BindView(R.id.txt_my_profile_name)
    TextView textName;
    @BindView(R.id.txt_my_profile_departure)
    TextView textDeparture;
    @BindView(R.id.txt_my_profile_arrival)
    TextView textArrival;
    @BindView(R.id.txt_my_profile_date)
    TextView textDate;
    @BindView(R.id.txt_my_profile_hour)
    TextView textHour;

    public MyProfileView(MyProfileFragment fragment) {
        super(fragment);
        if (fragment.getView() != null) {
            ButterKnife.bind(this, fragment.getView());
            bottomMenu = (BottomNavigationViewEx) getActivity().findViewById(R.id.bottom_navigation);
        }
    }

    public void hideMenu() {
        bottomMenu.setVisibility(View.GONE);
    }

    public void showMenu() {
        bottomMenu.setVisibility(View.VISIBLE);
    }

    public void setCommunityText(String communityText) {
        textCommunity.setText(COMMUNITY_TEXT + StringUtils.capitalizeFirstLetter(communityText));
    }

    public void setTextName(@NonNull String name) {
        textName.setText(name);
    }

    public void setTextArrivalAndDeparture(String arrival, String departure) {
        if (!TextUtils.isEmpty(arrival)) {
            textDeparture.setText("Origen: " + arrival);
        }
        if (!TextUtils.isEmpty(departure)) {
            textArrival.setText("Destination: " + departure);
        }
    }

    public void setTextDate(String date) {
        textDate.setText(date);
    }

    public void setTextHour(String hour) {
        textHour.setText(hour);
    }

    public void setImagePhoto(String url) {
        Picasso.with(imagePhoto.getContext()).load(url).into(imagePhoto);

    }
}
