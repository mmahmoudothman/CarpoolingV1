package com.example.mahmoud.carpoolingv1.activity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.mahmoud.carpoolingv1.R;
import com.example.mahmoud.carpoolingv1.data.preference.init.InitPreferenceImpl;
import com.example.mahmoud.carpoolingv1.mvp.presenter.VideoActivityPresenter;
import com.example.mahmoud.carpoolingv1.mvp.view.VideoActivityView;

public class VideoActivity extends BaseActivity implements MediaPlayer.OnCompletionListener {
    private VideoActivityPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        presenter = new VideoActivityPresenter(new InitPreferenceImpl(this, InitPreferenceImpl.NAME),
                new VideoActivityView(this));
        presenter.init();
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        presenter.goToAuthActivity();
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onResume();
    }
}

