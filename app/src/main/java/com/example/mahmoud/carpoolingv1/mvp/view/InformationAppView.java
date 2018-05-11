package com.example.mahmoud.carpoolingv1.mvp.view;

import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.mahmoud.carpoolingv1.R;
import com.example.mahmoud.carpoolingv1.activity.BaseActivity;
import com.example.mahmoud.carpoolingv1.fragment.InformationAppFragment;
import com.example.mahmoud.carpoolingv1.mvp.base.FragmentView;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InformationAppView extends FragmentView<InformationAppFragment, Void> {

    private static final String TERMS_AND_CONDITION_URL = "https://docs.google.com/forms/d/e/1FAIpQLSdk0JdNudgkl1fcoAAssgHnAjOBDByKCU4VVGhhz-V2gVMqGw/viewform";

    private BottomNavigationViewEx bottomMenu;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    public InformationAppView(InformationAppFragment fragment) {
        super(fragment);
        if (fragment.getView() != null) {
            ButterKnife.bind(this, fragment.getView());
            if (getActivity() != null)
                bottomMenu = (BottomNavigationViewEx) getActivity().findViewById(R.id.bottom_navigation);
        }
    }

    public void init() {
        final BaseActivity activity = getActivity();
        if (activity != null) {
            progressBar.setVisibility(View.VISIBLE);
            WebView webView = (WebView) activity.findViewById(R.id.webview);
            webView.getSettings().setJavaScriptEnabled(true);
            webView.getSettings().setDomStorageEnabled(true);
            webView.setWebViewClient(new SSLTolerentWebViewClient());
            webView.loadUrl(TERMS_AND_CONDITION_URL);
        }
    }

    private void hideMenu() {
        bottomMenu.setVisibility(View.GONE);
    }

    public void showMenu() {
        bottomMenu.setVisibility(View.VISIBLE);
    }

    private class SSLTolerentWebViewClient extends WebViewClient {

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progressBar.setVisibility(View.GONE);
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            progressBar.setVisibility(View.GONE);
            Toast.makeText(getContext(), "لا يمكن الاتصال الآن. جرب لاحقا", Toast.LENGTH_LONG).show();
        }

    }
}
