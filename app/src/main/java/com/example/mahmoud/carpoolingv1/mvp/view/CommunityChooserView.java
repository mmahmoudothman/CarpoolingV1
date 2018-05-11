package com.example.mahmoud.carpoolingv1.mvp.view;


import android.support.design.widget.BottomNavigationView;
import android.view.View;

import com.example.mahmoud.carpoolingv1.R;
import com.example.mahmoud.carpoolingv1.fragment.CommunityChooserFragment;
import com.example.mahmoud.carpoolingv1.mvp.base.FragmentView;

import butterknife.ButterKnife;

public class CommunityChooserView extends FragmentView<CommunityChooserFragment, Void> {


    private BottomNavigationView bottomMenu;

    public CommunityChooserView(CommunityChooserFragment fragment) {
        super(fragment);
        if (fragment.getView() != null) {
            ButterKnife.bind(this, fragment.getView());
            if (getActivity() != null)
                bottomMenu = (BottomNavigationView) getActivity().findViewById(R.id.bottom_navigation);
        }
    }

    public void hideMenu() {
        bottomMenu.setVisibility(View.GONE);
    }

    public void showMenu() {
        bottomMenu.setVisibility(View.VISIBLE);
    }
}
