package com.mymoonapplab.oxfirat.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.mymoonapplab.oxfirat.R;
import com.mymoonapplab.oxfirat.constant.statik_class;

public class fragment_akademik_takvim extends Fragment {

    private View rootView;
    private WebView pdfView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_akademik_takvim, container, false);

        pdfView = rootView.findViewById(R.id.fragment_akademik_takvim_pdf);
        pdfView.getSettings().setJavaScriptEnabled(true);
        pdfView.setWebViewClient(new WebViewClient());

        // Load PDF from assets using Google Docs Viewer or direct file path
        String pdfPath = "file:///android_asset/" + statik_class.PDF_ISMI + ".pdf";
        pdfView.loadUrl("https://docs.google.com/gview?embedded=true&url=" + pdfPath);

        return rootView;
    }

}
