package com.example.mahmoud.carpoolingv1.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mahmoud.carpoolingv1.R;
import com.example.mahmoud.carpoolingv1.activity.MainActivity;
import com.example.mahmoud.carpoolingv1.data.preference.init.InitPreferenceImpl;
import com.example.mahmoud.carpoolingv1.fragment.base.BaseMapPreferenceFragment;
import com.example.mahmoud.carpoolingv1.mvp.presenter.RegisterPresenter;
import com.example.mahmoud.carpoolingv1.mvp.view.RegisterView;
import com.example.mahmoud.carpoolingv1.service.UserService;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterFragment extends BaseMapPreferenceFragment {

    private RegisterPresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register_form, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = new RegisterPresenter(
                new RegisterView(this),
                new UserService(),
                new InitPreferenceImpl(getActivity(), InitPreferenceImpl.NAME)
        );
        presenter.init();

    }

    @OnClick(R.id.btn_continue)
    void onContinueClick() {
        if (presenter.saveUserData()) {
            MainActivity activity = (MainActivity) getActivity();
            if (activity == null) return;
            if (activity.getNavigationManager() == null) return;
            activity.getNavigationManager().chooseInitialScreen();
        }
    }
}
