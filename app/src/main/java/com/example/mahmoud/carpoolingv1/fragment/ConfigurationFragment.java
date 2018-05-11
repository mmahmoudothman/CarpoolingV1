package com.example.mahmoud.carpoolingv1.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mahmoud.carpoolingv1.R;
import com.example.mahmoud.carpoolingv1.data.preference.init.InitPreferenceImpl;
import com.example.mahmoud.carpoolingv1.fragment.base.BaseMapPreferenceFragment;
import com.example.mahmoud.carpoolingv1.mvp.presenter.ConfigurationPresenter;
import com.example.mahmoud.carpoolingv1.mvp.view.ConfigurationView;
import com.example.mahmoud.carpoolingv1.service.UserService;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ConfigurationFragment extends BaseMapPreferenceFragment {

    private ConfigurationPresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_configuration, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = new ConfigurationPresenter(
                new ConfigurationView(this),
                new UserService(),
                new InitPreferenceImpl(getActivity(), InitPreferenceImpl.NAME)
        );
        presenter.init();
    }

    @OnClick(R.id.btn_close_session)
    void onCloseSessionClick() {
        presenter.onCloseSession();
    }

    @OnClick(R.id.btn_report_issue)
    void onReportIssue() {
        presenter.goToReportIssueWebPage();
    }

    @OnClick(R.id.btn_report_user)
    void onReportUser() {
        presenter.goToReportUserWebPage();
    }

    @OnClick(R.id.btn_active_user)
    void onActiveUserClick() {
        presenter.goToActivateUser();
    }
}
