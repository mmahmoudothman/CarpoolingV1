package com.example.mahmoud.carpoolingv1.mvp.view;


import android.widget.Toast;

import com.example.mahmoud.carpoolingv1.activity.AuthActivity;
import com.example.mahmoud.carpoolingv1.mvp.base.ActivityView;

import butterknife.ButterKnife;

public class AuthView extends ActivityView<AuthActivity> {

    public AuthView(AuthActivity activity) {
        super(activity);
        ButterKnife.bind(this, activity);
    }

    public void showErrorMessage(int resId) {
        Toast.makeText(getContext(), resId, Toast.LENGTH_LONG).show();
    }
}
