package com.mymoonapplab.oxfirat.etkinlik;


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

public class fragment_etkinlik extends Fragment {

    private ArrayList<String> etkinlik_tarih;
    private ArrayList<String> etkinlik_icerik;
    private ArrayList<String> etkinlik_link;
    private RecyclerView recyclerView;
    private fragment_etkinlik_adapter adapter;
    private TextView textview_text_cekilemedi;

    private AVLoadingIndicatorView progressBar_pacman;

    private asynTask_etkinlikCek asynTask_etkinlikCek_object = null;

    private WaveSwipeRefreshLayout swipeRefresh_damla;

    private int sayfa_sayisi;

    private int son_etkinlik_konumu;

    private Resources res;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_etkinlik, container, false);

        etkinlik_tarih = new ArrayList<>();
        etkinlik_icerik = new ArrayList<>();
        etkinlik_link = new ArrayList<>();

        sayfa_sayisi = 1;

        res = getResources();

        asynTask_etkinlikCek_object = new asynTask_etkinlikCek();
        asynTask_etkinlikCek_object.execute();

        progressBar_pacman = rootView.findViewById(R.id.fragmentyemekhane_progress_avi);
        textview_text_cekilemedi = rootView.findViewById(R.id.fragment_etkinlikler_textview);
        textview_text_cekilemedi.setText(res.getString(R.string.etkinlikler_cekilemedi));
        textview_text_cekilemedi.setVisibility(View.INVISIBLE);

        recyclerView = rootView.findViewById(R.id.fragment_etkinlik_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {

                LinearLayoutManager manager = LinearLayoutManager.class.cast(recyclerView.getLayoutManager());

                int toplam_etkinlik_sayisi = 0;
                son_etkinlik_konumu = 0;

                if (manager != null) {
                    toplam_etkinlik_sayisi = manager.getItemCount();
                    son_etkinlik_konumu = manager.findLastVisibleItemPosition();
                }

                if (son_etkinlik_konumu + 1 == toplam_etkinlik_sayisi) {
                    new asynTask_etkinlikCek().execute();
                    progressBar_pacman.smoothToShow();
                }


            }
        });


        swipeRefresh_damla = rootView.findViewById(R.id.fragment_etkinlik_waveswipe);
        swipeRefresh_damla.setOnRefreshListener(new WaveSwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new asynTask_etkinlikCek().execute();
            }
        });


        return rootView;
    }


    @SuppressLint("StaticFieldLeak")
    private class asynTask_etkinlikCek extends AsyncTask<Void, Void, Void> {

        private Elements etkinlikElements;


        @Override
        protected Void doInBackground(Void... voids) {

            try {
                String etkinlik_sitesi = res.getString(R.string.etkinlik_sitesi);
                String okul_sitesi = res.getString(R.string.okul_sitesi);

                Document document = Jsoup.connect(etkinlik_sitesi + sayfa_sayisi).get();
                etkinlikElements = document.select("div[class=banner col-xs-12 col-sm-4 col-lg-3");

                for (int i = 0; i < etkinlikElements.size(); i++) {

                    etkinlik_icerik.add(etkinlikElements.get(i).select("div[class=bottom").text());
                    etkinlik_tarih.add(etkinlikElements.get(i).select("span[class=day]").text());
                    etkinlik_link.add(okul_sitesi + etkinlikElements.get(i).select("a").attr("href"));

                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (etkinlik_icerik.isEmpty()) {
                textview_text_cekilemedi.setVisibility(View.VISIBLE);
            } else {
                textview_text_cekilemedi.setVisibility(View.INVISIBLE);
                adapter = new fragment_etkinlik_adapter(getContext(), etkinlik_tarih, etkinlik_icerik, etkinlik_link, getFragmentManager());
                recyclerView.setAdapter(adapter);
                recyclerView.scrollToPosition(son_etkinlik_konumu - 2);
            }

            progressBar_pacman.smoothToHide();

            if (swipeRefresh_damla.isRefreshing()) {
                swipeRefresh_damla.setRefreshing(false);
            }
            ;
            sayfa_sayisi++;

        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (progressBar_pacman.isEnabled())
            progressBar_pacman.smoothToHide();

        if (swipeRefresh_damla.isRefreshing())
            swipeRefresh_damla.setRefreshing(false);

        if (asynTask_etkinlikCek_object != null && asynTask_etkinlikCek_object.cancel(true)) {
            asynTask_etkinlikCek_object = null;
        }

    }


}
