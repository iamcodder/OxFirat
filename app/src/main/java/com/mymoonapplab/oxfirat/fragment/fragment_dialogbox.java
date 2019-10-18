package com.mymoonapplab.oxfirat.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mymoonapplab.oxfirat.R;
import com.mymoonapplab.oxfirat.async_task.async_dialog;
import com.mymoonapplab.oxfirat.adapter.dialogbox_adapter;
import com.mymoonapplab.oxfirat.constant.statik_class;
import com.mymoonapplab.oxfirat.interfacee.interface_dialogbox;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class fragment_dialogbox extends DialogFragment implements interface_dialogbox {

    private TextView textView_baslik, textView_tarih, textView_icerik;
    private RecyclerView recyclerView;
    private dialogbox_adapter adapter;
    private String URL_LINKI, haber_linki;

    private ProgressBar progressBar;


    private String okul_sitesi;

    public fragment_dialogbox() {

    }

    public fragment_dialogbox(String URL_LINKI) {
        this.URL_LINKI = URL_LINKI;
        statik_class.URL_LINKI=URL_LINKI;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialogbox, container, false);


        okul_sitesi = getResources().getString(R.string.okul_sitesi);

        new async_dialog(this,getContext()).execute(URL_LINKI, okul_sitesi);

        textView_baslik = view.findViewById(R.id.dialogbox_baslik);
        textView_tarih = view.findViewById(R.id.dialogbox_tarih);
        textView_icerik = view.findViewById(R.id.dialogbox_icerik);
        progressBar = view.findViewById(R.id.dialog_progress);
        progressBar.setVisibility(View.VISIBLE);

        recyclerView = view.findViewById(R.id.dialogbox_recycleview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        textView_icerik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(haber_linki));
                startActivity(i);
            }
        });


        return view;
    }

    @Override
    public void async_sonucu(String haber_basligi, String haber_tarihi, String haber_icerigi, String haber_linki, List<String> list_resim_linkleri) {

        textView_baslik.setText(haber_basligi);
        textView_tarih.setText(haber_tarihi);
        textView_icerik.setText(haber_icerigi);
        adapter = new dialogbox_adapter(list_resim_linkleri, getContext());
        recyclerView.setAdapter(adapter);

        this.haber_linki = haber_linki;

        progressBar.setVisibility(View.INVISIBLE);


        if (haber_linki != null && !haber_linki.equals("") && !haber_linki.contains("http")) {
            this.haber_linki = okul_sitesi + haber_linki;

        }

    }


}

