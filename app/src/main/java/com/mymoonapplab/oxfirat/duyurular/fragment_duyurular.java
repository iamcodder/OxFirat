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

    private AVLoadingIndicatorView progressBar_pacman;

    private TextView textview_text_cekilemedi;

    private WaveSwipeRefreshLayout swipeRefresh_damla;

    private int sayfa_sayisi;

    private int son_duyuru_konumu;

    private asynTask_duyuruCek asynTask_duyuruCek_object = null;

    private Resources res;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_duyurular, container, false);

        duyuru_linki = new ArrayList<>();
        duyuru_icerigi = new ArrayList<>();
        duyuru_tarihi = new ArrayList<>();

        sayfa_sayisi = 1;


        res = getResources();

        asynTask_duyuruCek_object = new asynTask_duyuruCek();
        asynTask_duyuruCek_object.execute();

        progressBar_pacman = rootView.findViewById(R.id.fragmentyemekhane_progress_avi);

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
                    new asynTask_duyuruCek().execute();
                    progressBar_pacman.smoothToShow();
                }

            }
        });

        swipeRefresh_damla = rootView.findViewById(R.id.fragment_duyurular_waveswipe);
        swipeRefresh_damla.setOnRefreshListener(new WaveSwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new asynTask_duyuruCek().execute();
            }
        });


        return rootView;
    }


    @SuppressLint("StaticFieldLeak")
    private class asynTask_duyuruCek extends AsyncTask<Void, Void, Void> {

        private Elements duyuruElements;


        @Override
        protected Void doInBackground(Void... voids) {


            try {

                String okul_sitesi = res.getString(R.string.okul_sitesi);
                String duyurular_sitesi = res.getString(R.string.duyurular_sitesi);

                Document document = Jsoup.connect(duyurular_sitesi + sayfa_sayisi).get();
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

                if (swipeRefresh_damla.isRefreshing()) {
                    swipeRefresh_damla.setRefreshing(false);
                }
            }
            else {
                textview_text_cekilemedi.setVisibility(View.INVISIBLE);
                adapter = new fragment_duyurular_adapter(getContext(), duyuru_icerigi, duyuru_tarihi, duyuru_linki, getFragmentManager());
                recyclerView.setAdapter(adapter);
                recyclerView.scrollToPosition(son_duyuru_konumu - 2);

                if (swipeRefresh_damla.isRefreshing()) {
                    swipeRefresh_damla.setRefreshing(false);
                }
            }


            progressBar_pacman.smoothToHide();
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

        if (asynTask_duyuruCek_object != null && asynTask_duyuruCek_object.cancel(true)) {
            asynTask_duyuruCek_object = null;
        }
    }


}
