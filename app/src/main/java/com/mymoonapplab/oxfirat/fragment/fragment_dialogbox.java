package com.mymoonapplab.oxfirat.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.mymoonapplab.oxfirat.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class fragment_dialogbox extends DialogFragment {

    private WebView webView;
    private ProgressBar progressBar;
    private String URL_LINKI;

    public fragment_dialogbox() {

    }

    public fragment_dialogbox(String URL_LINKI) {
        this.URL_LINKI = URL_LINKI;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Dialog'un dışına basınca kapanmasını sağla
        setCancelable(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialogbox_webview, container, false);

        webView = view.findViewById(R.id.dialogbox_webview);
        progressBar = view.findViewById(R.id.dialog_progress);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressBar.setVisibility(View.GONE);
            }
        });

        if (URL_LINKI != null && !URL_LINKI.isEmpty()) {
            progressBar.setVisibility(View.VISIBLE);
            webView.loadUrl(URL_LINKI);
        }

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() != null && getDialog().getWindow() != null) {
            // Ekran genişliğinin %90'ını al
            int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.90);
            // Yükseklik wrap_content olsun
            getDialog().getWindow().setLayout(width, ViewGroup.LayoutParams.MATCH_PARENT);
        }
    }
}

