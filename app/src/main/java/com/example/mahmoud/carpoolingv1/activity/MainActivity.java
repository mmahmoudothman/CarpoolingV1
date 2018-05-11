package com.example.mahmoud.carpoolingv1.activity;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;

import com.example.mahmoud.carpoolingv1.R;
import com.example.mahmoud.carpoolingv1.data.preference.init.InitPreferenceImpl;
import com.example.mahmoud.carpoolingv1.data.role.RolePreference;
import com.example.mahmoud.carpoolingv1.data.role.RolePreferenceImpl;
import com.example.mahmoud.carpoolingv1.fragment.MyMapFragment;
import com.example.mahmoud.carpoolingv1.mvp.presenter.MainPresenter;
import com.example.mahmoud.carpoolingv1.mvp.view.MainView;
import com.example.mahmoud.carpoolingv1.navigation.NavigationManager;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    private static final int ZERO_FRAGMENTS = 0;

    @BindView(R.id.bottom_navigation)
    BottomNavigationViewEx bottomMenu;

    private MainPresenter presenter;
    private NavigationManager navigationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        presenter = new MainPresenter(new MainView(this));
        presenter.init();
        initNavigationManager();
        bottomMenu.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_my_profile:
                                navigationManager.goToMyProfileFragment();
                                break;
                            case R.id.action_my_quota:
                                navigationManager.goToMyBookingsFragment();
                                break;
                            case R.id.action_map:
                                navigationManager.goToMapFragment();
                                break;
                            case R.id.action_information_app:
                                navigationManager.goToInformatioAppFragment();
                                break;
                            case R.id.action_configuration:
                                navigationManager.goToConfigurationFragment();
                                break;
                        }
                        return true;
                    }
                });
    }

    private void initNavigationManager() {
        final RolePreference rolePreference = new RolePreferenceImpl(this, RolePreferenceImpl.NAME);
        navigationManager = new NavigationManager(getFragmentManager(), rolePreference, new InitPreferenceImpl(this, InitPreferenceImpl.NAME));
        navigationManager.chooseInitialScreen();
        setBottomMenuProperties();
    }

    private void setBottomMenuProperties() {
        bottomMenu.enableShiftingMode(false);
        bottomMenu.enableItemShiftingMode(false);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        MyMapFragment fragment = navigationManager.getMapFragment();
        if (fragment != null) {
            fragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (navigationManager.getBackStackEntryCount() <= ZERO_FRAGMENTS) {
            finish();
        }
    }

    public NavigationManager getNavigationManager() {
        return navigationManager;
    }
}
