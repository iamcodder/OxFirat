package com.mymoonapplab.oxfirat.duyurular;


import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mymoonapplab.oxfirat.R;
import com.wang.avi.AVLoadingIndicatorView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;

public class fragment_duyurular extends Fragment {

    private ArrayList<String> duyuru_linki, duyuru_icerigi, duyuru_tarihi;
    private RecyclerView recyclerView;
    private fragment_duyurular_adapter adapter;

    private AVLoadingIndicatorView progress_bar;

    private TextView textview_text_cekilemedi;

    private WaveSwipeRefreshLayout swipeRefreshLayout;

    private int page_number;

    private int son_duyuru_konumu;

    private duyuruCek duyuruCekObject = null;

    private Resources res;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_duyurular, container, false);

        duyuru_linki = new ArrayList<>();
        duyuru_icerigi = new ArrayList<>();
        duyuru_tarihi = new ArrayList<>();

        page_number = 1;


        res = getResources();

        duyuruCekObject = new duyuruCek();
        duyuruCekObject.execute();

        progress_bar = rootView.findViewById(R.id.fragmentyemekhane_progress_avi);

        textview_text_cekilemedi = rootView.findViewById(R.id.fragment_duyurular_textview);
        textview_text_cekilemedi.setText(getResources().getText(R.string.duyurular_cekilemedi));
        textview_text_cekilemedi.setVisibility(View.INVISIBLE);

        recyclerView = rootView.findViewById(R.id.fragment_duyurular_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {

                son_duyuru_konumu = 0;
                int toplam_duyuru = 0;

                LinearLayoutManager manager = LinearLayoutManager.class.cast(recyclerView.getLayoutManager());

                if (manager != null) {

                    son_duyuru_konumu = manager.findLastVisibleItemPosition();
                    toplam_duyuru = manager.getItemCount();
                }

                if (son_duyuru_konumu + 1 == toplam_duyuru) {
                    new duyuruCek().execute();
                    progress_bar.smoothToShow();
                }

            }
        });

        swipeRefreshLayout = rootView.findViewById(R.id.fragment_duyurular_waveswipe);
        swipeRefreshLayout.setOnRefreshListener(new WaveSwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new duyuruCek().execute();
            }
        });


        return rootView;
    }


    @SuppressLint("StaticFieldLeak")
    private class duyuruCek extends AsyncTask<Void, Void, Void> {

        private Elements duyuruElements;


        @Override
        protected Void doInBackground(Void... voids) {


            try {

                String okul_sitesi = res.getString(R.string.okul_sitesi);
                String duyurular_sitesi = res.getString(R.string.duyurular_sitesi);

                Document document = Jsoup.connect(duyurular_sitesi + page_number).get();
                duyuruElements = document.select("div[class=banner col-xs-12 col-sm-4 col-lg-3]");

                for (int i = 0; i < duyuruElements.size(); i++) {

                    duyuru_icerigi.add(duyuruElements.get(i).select("div[class=top]").text());
                    duyuru_tarihi.add(duyuruElements.get(i).select("span[class=day").text());
                    duyuru_linki.add((okul_sitesi) + duyuruElements.get(i).select("a").attr("href"));

                }

            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (duyuru_icerigi.isEmpty()) {
                textview_text_cekilemedi.setVisibility(View.VISIBLE);
            } else {
                textview_text_cekilemedi.setVisibility(View.INVISIBLE);
                adapter = new fragment_duyurular_adapter(getContext(), duyuru_icerigi, duyuru_tarihi, duyuru_linki, getFragmentManager());
                recyclerView.setAdapter(adapter);
                recyclerView.scrollToPosition(son_duyuru_konumu - 1);
            }

            progress_bar.smoothToHide();

            if (swipeRefreshLayout.isRefreshing()) {
                swipeRefreshLayout.setRefreshing(false);
            }
            page_number++;
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (progress_bar.isEnabled())
            progress_bar.smoothToHide();

        if (swipeRefreshLayout.isRefreshing())
            swipeRefreshLayout.setRefreshing(false);

        if (duyuruCekObject != null && duyuruCekObject.cancel(true)) {
            duyuruCekObject = null;
        }
    }


}
