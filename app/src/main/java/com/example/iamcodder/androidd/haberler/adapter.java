package com.example.iamcodder.androidd.haberler;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.iamcodder.androidd.MainActivity;
import com.example.iamcodder.androidd.R;
import com.example.iamcodder.androidd.dialogBox.dialogBox;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class adapter extends RecyclerView.Adapter<adapter.ViewHolder> {

    private String okul_web;
    private Context mContext;
    private List<String> haber_baslik;
    private List<String> haber_resim;
    private List<String> haber_linki;
    private FragmentManager manager;


    adapter(Context mContext, List<String> haber_baslik, List<String> haber_resim, List<String> haber_linki,FragmentManager manager) {
        okul_web="http://www.firat.edu.tr";
        this.mContext = mContext;
        this.haber_baslik = haber_baslik;
        this.haber_resim = haber_resim;
        this.haber_linki = haber_linki;
        this.manager=manager;


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view=LayoutInflater.from(mContext).inflate(R.layout.fragment_haberler_design,viewGroup,false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, @SuppressLint("RecyclerView") final int i) {

        viewHolder.mBar.setVisibility(View.VISIBLE);
        viewHolder.mTextview.setText(haber_baslik.get(i));


        Picasso.get().load(okul_web+haber_resim.get(i))
                .into(viewHolder.mImageview, new Callback() {
                    @Override
                    public void onSuccess() {
                        viewHolder.mBar.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onError(Exception e) {
                        viewHolder.mBar.setVisibility(View.VISIBLE);

                    }
                });


        viewHolder.mCardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialogBox dialogBox=new dialogBox(fragment_haberler.haberLinki.get(i));

                dialogBox.show(manager,"SELAM");

            }
        });

        if(i==haber_baslik.size()-1){
            Toast.makeText(mContext,"Sona Gelindi",Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public int getItemCount() {
        return haber_baslik.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private CardView mCardview;
        private ImageView mImageview;
        private TextView mTextview;
        private ProgressBar mBar;


        ViewHolder(@NonNull View itemView) {
            super(itemView);
            mCardview=itemView.findViewById(R.id.fragment_haberler_carview_design);
            mImageview=itemView.findViewById(R.id.cardView_resim);
            mTextview=itemView.findViewById(R.id.cardView_baslik);
            mBar=itemView.findViewById(R.id.cardView_progressBar);
        }
    }
}
