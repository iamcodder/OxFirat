package com.mymoonapplab.oxfirat.haberler;

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


public class fragment_haberler extends Fragment {


    private ArrayList<String> haberBasligi, haberResmi;
    public static ArrayList<String> haberLinki;
    private fragment_haberler_adapter fragment_haberler_adapter;
    private RecyclerView recyclerView;
    private LayoutAnimationController controller;
    private AVLoadingIndicatorView progressBar_pacman;
    private WaveSwipeRefreshLayout swipeRefresh_damla;
    private int sayfa_sayisi;
    private int son_haber_konumu;
    private asynTask_haberCek asynTask_haberCek_object;
    private Resources res;
    private View rootView;
    private LottieAnimationView no_internet_animation;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_haberler, container, false);

        casting();

        recycler_islevleri();

        swipe_refresh();

        return rootView;
    }

    private void casting(){
        haberBasligi = new ArrayList<>();
        haberLinki = new ArrayList<>();
        haberResmi = new ArrayList<>();

        sayfa_sayisi = 1;

        res = getResources();

        asynTask_haberCek_object = new asynTask_haberCek();
        asynTask_haberCek_object.execute();

        progressBar_pacman = rootView.findViewById(R.id.fragmentyemekhane_progress_avi);
        no_internet_animation=rootView.findViewById(R.id.fragment_haberler_textview_no_internet_animation);
        no_internet_animation.pauseAnimation();

    }

    private void recycler_islevleri(){
        recyclerView = rootView.findViewById(R.id.fragment_haberler_recycleview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        controller=AnimationUtils.loadLayoutAnimation(getContext(), R.anim.layout_animation_down_to_up);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {

                int toplam_haber_sayisi = 0;
                son_haber_konumu = 0;

                LinearLayoutManager manager = LinearLayoutManager.class.cast(recyclerView.getLayoutManager());

                if (manager != null) {
                    toplam_haber_sayisi = manager.getItemCount();
                    son_haber_konumu = manager.findLastVisibleItemPosition();
                }

                if (son_haber_konumu + 1 == toplam_haber_sayisi) {
                    new asynTask_haberCek().execute();
                    progressBar_pacman.smoothToShow();

                }
            }

        });
    }

    private void swipe_refresh(){
        swipeRefresh_damla = rootView.findViewById(R.id.fragment_haberler_waveswipe);
        swipeRefresh_damla.setOnRefreshListener(new WaveSwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new asynTask_haberCek().execute();
            }
        });
    }


    @SuppressLint("StaticFieldLeak")
    public class asynTask_haberCek extends AsyncTask<Void, Void, Void> {

        private Elements haber1;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected Void doInBackground(Void... voids) {

            String okul_sitesi = res.getString(R.string.okul_sitesi);
            String haber_sitesi = res.getString(R.string.haber_sitesi);

            try {
                Document document = Jsoup.connect(haber_sitesi + sayfa_sayisi).get();
                haber1 = document.select("div[class=row all-news]").select("div[class=banner col-xs-12 col-sm-4 col-md-4 col-lg-3]");

                for (int i = 0; i < haber1.size(); i++) {

                    haberBasligi.add(haber1.get(i).select("div[class=top]").text());
                    haberLinki.add(okul_sitesi + haber1.get(i).select("a").attr("href"));

                    String parcalama[] = haber1.get(i).select("div").attr("style").split("'");

                    haberResmi.add(parcalama[1]);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (haberBasligi.isEmpty()) {
                no_internet_animation.playAnimation();
                if (swipeRefresh_damla.isRefreshing()) {
                    swipeRefresh_damla.setRefreshing(false);
                }

            } else {
                no_internet_animation.pauseAnimation();
                fragment_haberler_adapter = new fragment_haberler_adapter(getContext(), haberBasligi, haberResmi, haberLinki, getFragmentManager());
                recyclerView.setAdapter(fragment_haberler_adapter);
                recyclerView.scrollToPosition(son_haber_konumu - 2);
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

        if (asynTask_haberCek_object != null && asynTask_haberCek_object.cancel(true)) {
            asynTask_haberCek_object = null;
        }
    }

}
