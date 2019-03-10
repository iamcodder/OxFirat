package com.mymoonapplab.oxfirat.haberler;


import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

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
    private AVLoadingIndicatorView progressBar_pacman;
    private TextView textview_text_cekilemedi;

    private WaveSwipeRefreshLayout swipeRefresh_damla;

    private int sayfa_sayisi;

    private int son_haber_konumu;

    private asynTask_haberCek asynTask_haberCek_object;

    private Resources res;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_haberler, container, false);

        haberBasligi = new ArrayList<>();
        haberLinki = new ArrayList<>();
        haberResmi = new ArrayList<>();

        sayfa_sayisi = 1;

        res = getResources();

        asynTask_haberCek_object = new asynTask_haberCek();
        asynTask_haberCek_object.execute();

        progressBar_pacman = rootView.findViewById(R.id.fragmentyemekhane_progress_avi);

        textview_text_cekilemedi = rootView.findViewById(R.id.fragment_haberler_textview);
        textview_text_cekilemedi.setText(res.getString(R.string.haberler_cekilemedi));
        textview_text_cekilemedi.setVisibility(View.INVISIBLE);


        recyclerView = rootView.findViewById(R.id.fragment_haberler_recycleview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

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


        swipeRefresh_damla = rootView.findViewById(R.id.fragment_haberler_waveswipe);
        swipeRefresh_damla.setOnRefreshListener(new WaveSwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new asynTask_haberCek().execute();
            }
        });


        return rootView;
    }


    @SuppressLint("StaticFieldLeak")
    public class asynTask_haberCek extends AsyncTask<Void, Void, Void> {

        private Elements haber1;

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
                textview_text_cekilemedi.setVisibility(View.VISIBLE);

                if (swipeRefresh_damla.isRefreshing()) {
                    swipeRefresh_damla.setRefreshing(false);
                }
            } else {
                textview_text_cekilemedi.setVisibility(View.INVISIBLE);
                fragment_haberler_adapter = new fragment_haberler_adapter(getContext(), haberBasligi, haberResmi, haberLinki, getFragmentManager());
                recyclerView.setAdapter(fragment_haberler_adapter);
                recyclerView.scrollToPosition(son_haber_konumu - 2);

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
