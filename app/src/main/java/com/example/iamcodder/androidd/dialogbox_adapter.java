package com.example.iamcodder.androidd;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class dialogbox_adapter extends RecyclerView.Adapter<dialogbox_adapter.dialogViewHolder> {

    private List<String> resim_linkleri;
    private Context mContext;

    public dialogbox_adapter(List<String> resim_linkleri, Context mContext) {
        this.resim_linkleri = resim_linkleri;
        this.mContext = mContext;

    }

    @NonNull
    @Override
    public dialogViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view=LayoutInflater.from(mContext).inflate(R.layout.dialogbox_image_design,viewGroup,false);

        System.out.println("SÜLEYMAN 1");

        return new dialogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final dialogViewHolder dialogViewHolder, int i) {



        dialogViewHolder.bar.setVisibility(View.VISIBLE);

        Picasso.get().load(resim_linkleri.get(i)).into(dialogViewHolder.image, new Callback() {
            @Override
            public void onSuccess() {

                dialogViewHolder.bar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(mContext,e.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return resim_linkleri.size();
    }

    class dialogViewHolder extends RecyclerView.ViewHolder{

        private ImageView image;
        private ProgressBar bar;

        public dialogViewHolder(@NonNull View itemView) {
            super(itemView);

            image=itemView.findViewById(R.id.dialogbox_design_imageView);
            bar=itemView.findViewById(R.id.dialogbox_design_progressBar);

        }
    }
}
