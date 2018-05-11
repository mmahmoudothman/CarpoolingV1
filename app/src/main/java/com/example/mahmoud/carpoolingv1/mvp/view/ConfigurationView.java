package com.example.mahmoud.carpoolingv1.mvp.view;


import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.BottomNavigationView;
import android.view.View;

import com.example.mahmoud.carpoolingv1.R;
import com.example.mahmoud.carpoolingv1.activity.BaseActivity;
import com.example.mahmoud.carpoolingv1.fragment.ConfigurationFragment;
import com.example.mahmoud.carpoolingv1.mvp.base.FragmentView;

import butterknife.ButterKnife;

public class ConfigurationView extends FragmentView<ConfigurationFragment, Void> {

    private static final String REPORT_ISSUE_URL = "https://docs.google.com/forms/d/e/1FAIpQLSfQCvTqcM72TZooED54ptO1J6X2T7rFPRNlQRHROPdNL0CGeA/viewform";
    private static final String ACTIVATE_USER_URL = "https://docs.google.com/forms/d/e/1FAIpQLSdk0JdNudgkl1fcoAAssgHnAjOBDByKCU4VVGhhz-V2gVMqGw/viewform";
    private static final String REPORT_USER_URL = "https://docs.google.com/forms/d/e/1FAIpQLSdMwfjbK9bZLHMM2qZwSZfVghGv4_P_lG2HYLMcerOPagc3Zw/viewform";

    private BottomNavigationView bottomMenu;

    public ConfigurationView(ConfigurationFragment fragment) {
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

    public void finishActivity() {
        final BaseActivity activity = getActivity();
        if (activity != null) {
            activity.finish();
        }
    }

    public void init() {

    }

    public void goToReportIssueWebPage() {
        final BaseActivity activity = getActivity();
        if (activity != null) {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(REPORT_ISSUE_URL));
            activity.startActivity(i);
        }

    }

    public void goToActiveUserWebPage() {
        final BaseActivity activity = getActivity();
        if (activity != null) {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(ACTIVATE_USER_URL));
            activity.startActivity(i);
        }
    }

    public void goToReportUserWebPage() {
        final BaseActivity activity = getActivity();
        if (activity != null) {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(REPORT_USER_URL));
            activity.startActivity(i);
        }
    }
}
