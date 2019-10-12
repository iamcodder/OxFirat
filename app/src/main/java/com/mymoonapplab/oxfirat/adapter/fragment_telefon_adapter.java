package com.mymoonapplab.oxfirat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.mymoonapplab.oxfirat.R;
import com.mymoonapplab.oxfirat.interfacee.interface_telefon;
import com.mymoonapplab.oxfirat.model.model_telefon_numara;

import java.util.List;

public class fragment_telefon_adapter extends RecyclerView.Adapter<fragment_telefon_adapter.viewHolder> {

    private List<model_telefon_numara> liste;
    private Context mContext;
    private interface_telefon interface_telefon;

    public fragment_telefon_adapter(List<model_telefon_numara> liste, Context mContext,interface_telefon interface_telefon) {
        this.liste = liste;
        this.mContext = mContext;
        this.interface_telefon=interface_telefon;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView= LayoutInflater.from(mContext).inflate(R.layout.fragment_telefonlar_design,parent,false);


        return new viewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, final int position) {

        holder.txt_isim.setText(liste.get(position).getIsim());
        holder.txt_numara.setText(liste.get(position).getTelefon());
        holder.txt_dahili.setText(liste.get(position).getDahili());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                interface_telefon.cardview_tiklama(position);
            }
        });


    }

    @Override
    public int getItemCount() {
        return liste.size();
    }

    class viewHolder extends RecyclerView.ViewHolder{

        private TextView txt_isim,txt_numara,txt_dahili;
        private CardView cardView;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            txt_isim=itemView.findViewById(R.id.fragment_telefonlar_design_txt_isim);
            txt_numara=itemView.findViewById(R.id.fragment_telefonlar_design_txt_numara);
            txt_dahili=itemView.findViewById(R.id.fragment_telefonlar_design_txt_alan);
            cardView=itemView.findViewById(R.id.fragment_telefonlar_design_cardview);
        }
    }
}

