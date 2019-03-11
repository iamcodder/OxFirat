package com.mymoonapplab.oxfirat.duyurular;


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
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;

public class fragment_duyurular extends Fragment {

    private ArrayList<String> duyuru_linki, duyuru_icerigi, duyuru_tarihi;
    private RecyclerView recyclerView;
    private LayoutAnimationController controller;

    private fragment_duyurular_adapter adapter;

    private AVLoadingIndicatorView progressBar_pacman;


    private WaveSwipeRefreshLayout swipeRefresh_damla;

    private int sayfa_sayisi;

    private int son_duyuru_konumu;

    private asynTask_duyuruCek asynTask_duyuruCek_object = null;

    private Resources res;

    private View rootView;

    private LottieAnimationView no_internet_animation;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_duyurular, container, false);

        casting();
        recyler_islemleri();
        swipe_refresh();




        return rootView;
    }

    private void casting(){
        duyuru_linki = new ArrayList<>();
        duyuru_icerigi = new ArrayList<>();
        duyuru_tarihi = new ArrayList<>();

        sayfa_sayisi = 1;

        res = getResources();

        asynTask_duyuruCek_object = new asynTask_duyuruCek();
        asynTask_duyuruCek_object.execute();

        progressBar_pacman = rootView.findViewById(R.id.fragmentyemekhane_progress_avi);
        no_internet_animation=rootView.findViewById(R.id.fragment_duyurular_no_internet_animation);
    }

    private void recyler_islemleri(){
        recyclerView = rootView.findViewById(R.id.fragment_duyurular_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        controller=AnimationUtils.loadLayoutAnimation(getContext(), R.anim.layout_animation_down_to_up);


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
    }

    private void swipe_refresh(){

        swipeRefresh_damla = rootView.findViewById(R.id.fragment_duyurular_waveswipe);
        swipeRefresh_damla.setOnRefreshListener(new WaveSwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new asynTask_duyuruCek().execute();
            }
        });
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
                no_internet_animation.playAnimation();

                if (swipeRefresh_damla.isRefreshing()) {
                    swipeRefresh_damla.setRefreshing(false);
                }
            }
            else {

                no_internet_animation.pauseAnimation();
                adapter = new fragment_duyurular_adapter(getContext(), duyuru_icerigi, duyuru_tarihi, duyuru_linki, getFragmentManager());
                recyclerView.setAdapter(adapter);
                recyclerView.scrollToPosition(son_duyuru_konumu - 2);
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

        if (asynTask_duyuruCek_object != null && asynTask_duyuruCek_object.cancel(true)) {
            asynTask_duyuruCek_object = null;
        }
    }


}
