package com.mymoonapplab.oxfirat.etkinlik;


import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.mymoonapplab.oxfirat.R;
import com.wang.avi.AVLoadingIndicatorView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;

public class fragment_etkinlik extends Fragment {

    private ArrayList<String> etkinlik_tarih;
    private ArrayList<String> etkinlik_icerik;
    private ArrayList<String> etkinlik_link;
    private RecyclerView recyclerView;
    private fragment_etkinlik_adapter adapter;

    private LayoutAnimationController controller;


    private AVLoadingIndicatorView progressBar_pacman;

    private asynTask_etkinlikCek asynTask_etkinlikCek_object = null;

    private WaveSwipeRefreshLayout swipeRefresh_damla;

    private int sayfa_sayisi;

    private int son_etkinlik_konumu;

    private Resources res;
    private View rootView;

    private LottieAnimationView no_internet_animation;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_etkinlik, container, false);

        casting();
        recycler_islemleri();
        swipe_refresh();






        return rootView;
    }

    private void casting(){
        etkinlik_tarih = new ArrayList<>();
        etkinlik_icerik = new ArrayList<>();
        etkinlik_link = new ArrayList<>();

        sayfa_sayisi = 1;

        res = getResources();

        asynTask_etkinlikCek_object = new asynTask_etkinlikCek();
        asynTask_etkinlikCek_object.execute();

        progressBar_pacman = rootView.findViewById(R.id.fragmentyemekhane_progress_avi);

        no_internet_animation=rootView.findViewById(R.id.fragment_etkinlik_no_internet_animation);
        no_internet_animation.pauseAnimation();
    }

    private void recycler_islemleri(){
        recyclerView = rootView.findViewById(R.id.fragment_etkinlik_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        controller=AnimationUtils.loadLayoutAnimation(getContext(), R.anim.layout_animation_down_to_up);


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
    }

    private void swipe_refresh(){
        swipeRefresh_damla = rootView.findViewById(R.id.fragment_etkinlik_waveswipe);
        swipeRefresh_damla.setOnRefreshListener(new WaveSwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new asynTask_etkinlikCek().execute();
            }
        });

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
                no_internet_animation.playAnimation();
                if (swipeRefresh_damla.isRefreshing()) {
                    swipeRefresh_damla.setRefreshing(false);
                }
            }
            else {
                no_internet_animation.pauseAnimation();
                adapter = new fragment_etkinlik_adapter(getContext(), etkinlik_tarih, etkinlik_icerik, etkinlik_link, getFragmentManager());
                recyclerView.setAdapter(adapter);
                recyclerView.scrollToPosition(son_etkinlik_konumu - 2);
                recyclerView.setLayoutAnimation(controller);
                recyclerView.scheduleLayoutAnimation();

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

        if (asynTask_etkinlikCek_object != null && asynTask_etkinlikCek_object.cancel(true)) {
            asynTask_etkinlikCek_object = null;
        }

    }


}
