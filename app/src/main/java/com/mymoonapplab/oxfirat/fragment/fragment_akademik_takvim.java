package com.mymoonapplab.oxfirat.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.barteksc.pdfviewer.PDFView;
import com.mymoonapplab.oxfirat.R;
import com.mymoonapplab.oxfirat.navigationMenu.statik_class;

public class fragment_akademik_takvim extends Fragment {

    private View rootView;
    private PDFView pdfView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_akademik_takvim, container, false);


        pdfView = rootView.findViewById(R.id.fragment_akademik_takvim_pdf);
        pdfView.fromAsset(statik_class.PDF_ISMI + ".pdf").load();


        return rootView;
    }

}
