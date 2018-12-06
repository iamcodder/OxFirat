package com.example.iamcodder.androidd.etkinlik;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.iamcodder.androidd.R;
import com.example.iamcodder.androidd.dialogBox;

import java.util.List;

public class fragment_etkinlik_adapter extends RecyclerView.Adapter<fragment_etkinlik_adapter.etkinlikViewHolder> {

    private Context mContext;
    private List<String> list_tarih;
    private List<String> list_icerik;
    private List<String> list_link;
    private FragmentManager manager;

    public fragment_etkinlik_adapter(Context mContext, List<String> list_tarih, List<String> list_icerik, List<String> list_link,FragmentManager manager) {
        this.mContext = mContext;
        this.list_tarih = list_tarih;
        this.list_icerik = list_icerik;
        this.list_link = list_link;
        this.manager=manager;
    }

    @NonNull
    @Override
    public etkinlikViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view=LayoutInflater.from(mContext).inflate(R.layout.fragment_etkinlik_design,viewGroup,false);

        return new etkinlikViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final etkinlikViewHolder etkinlikViewHolder, final int i) {

        etkinlikViewHolder.tarih.setText(list_tarih.get(i));
        etkinlikViewHolder.icerik.setText(list_icerik.get(i));

        etkinlikViewHolder.mCardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialogBox dialogBox=new dialogBox(list_link.get(i));
                dialogBox.show(manager,"hello");

            }
        });

    }

    @Override
    public int getItemCount() {
        return list_icerik.size();
    }

    protected class etkinlikViewHolder extends RecyclerView.ViewHolder{

        private TextView tarih;
        private TextView icerik;
        private CardView mCardview;

        protected etkinlikViewHolder(@NonNull View itemView) {
            super(itemView);

            tarih=itemView.findViewById(R.id.fragment_etkinlik_tarih);
            icerik=itemView.findViewById(R.id.fragment_etkinlik_icerik);
            mCardview=itemView.findViewById(R.id.fragment_etkinlik_cardview);

        }
    }
}
