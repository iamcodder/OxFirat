package com.example.iamcodder.androidd;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

public class bilgisayar_muhendisligi_adapter extends RecyclerView.Adapter<bilgisayar_muhendisligi_adapter.viewHolder>  {

    private Context mContext;
    private List<String> haber_basligi;
    private List<String> haber_linki;

    public bilgisayar_muhendisligi_adapter(Context mContext, List<String> haber_basligi, List<String> haber_linki) {
        this.mContext = mContext;
        this.haber_basligi = haber_basligi;
        this.haber_linki = haber_linki;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view=LayoutInflater.from(mContext).inflate(R.layout.bilgisayar_muhendisligi_cardview,viewGroup,false);

        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder viewHolder, int i) {

        viewHolder.mBar.setVisibility(View.VISIBLE);
        viewHolder.mTextview.setText(haber_basligi.get(i));
        viewHolder.mImageview.setVisibility(View.INVISIBLE);
    }

    @Override
    public int getItemCount() {
        return haber_linki.size();
    }
    public class viewHolder extends RecyclerView.ViewHolder{

        private CardView mCardview;
        private ImageView mImageview;
        private TextView mTextview;
        private ProgressBar mBar;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            mCardview=itemView.findViewById(R.id.bilgisayar_cardView);
            mImageview=itemView.findViewById(R.id.bilgisayar_cardView_resim);
            mTextview=itemView.findViewById(R.id.bilgisayar_cardView_baslik);
            mBar=itemView.findViewById(R.id.bilgisayar_cardView_progressBar);

        }
    }
}
