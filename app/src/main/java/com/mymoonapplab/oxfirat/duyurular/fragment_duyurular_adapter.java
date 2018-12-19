package com.mymoonapplab.oxfirat.duyurular;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mymoonapplab.oxfirat.R;
import com.mymoonapplab.oxfirat.dialogBox.dialogBox;

import java.util.List;

public class fragment_duyurular_adapter extends RecyclerView.Adapter<fragment_duyurular_adapter.duyurularViewHolder>{

    private Context mContext;
    private List<String> list_icerik;
    private List<String> list_tarih;
    private List<String> list_link;
    private FragmentManager manager;

    fragment_duyurular_adapter(Context mContext, List<String> list_icerik, List<String> list_tarih, List<String> list_link,FragmentManager manager) {
        this.mContext = mContext;
        this.list_icerik = list_icerik;
        this.list_tarih = list_tarih;
        this.list_link = list_link;
        this.manager=manager;
    }


    @NonNull
    @Override
    public duyurularViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view=LayoutInflater.from(mContext).inflate(R.layout.fragment_duyurular_design,viewGroup,false);

        return new duyurularViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull duyurularViewHolder duyurularViewHolder, @SuppressLint("RecyclerView") final int i) {
        duyurularViewHolder.tarih.setText(list_tarih.get(i));
        duyurularViewHolder.icerik.setText(list_icerik.get(i));

        duyurularViewHolder.mCardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialogBox dialogBox=new dialogBox(list_link.get(i));
                dialogBox.show(manager,"hi");
            }
        });
    }

    @Override
    public int getItemCount() {
        return list_tarih.size();
    }

    class duyurularViewHolder extends RecyclerView.ViewHolder{

        private TextView tarih;
        private TextView icerik;
        private CardView mCardview;

        duyurularViewHolder(@NonNull View itemView) {
            super(itemView);
            tarih=itemView.findViewById(R.id.fragment_duyurular_tarih);
            icerik=itemView.findViewById(R.id.fragment_duyurular_icerik);
            mCardview=itemView.findViewById(R.id.fragment_duyurular_cardview);
        }
    }
}