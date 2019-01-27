package com.mymoonapplab.oxfirat.dialogBox;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mymoonapplab.oxfirat.R;
import com.mymoonapplab.oxfirat.fullScreenImage;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.List;

public class dialogbox_adapter extends RecyclerView.Adapter<dialogbox_adapter.dialogViewHolder> {

    private List<String> resim_linkleri;
    private Context mContext;


    dialogbox_adapter(List<String> resim_linkleri, Context mContext) {
        this.resim_linkleri = resim_linkleri;
        this.mContext = mContext;

    }

    @NonNull
    @Override
    public dialogViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.dialogbox_image_design, viewGroup, false);


        return new dialogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final dialogViewHolder dialogViewHolder, final int i) {


        dialogViewHolder.bar.smoothToShow();

        Picasso.get().load(resim_linkleri.get(i)).into(dialogViewHolder.image, new Callback() {
            @Override
            public void onSuccess() {

                dialogViewHolder.bar.smoothToHide();
            }

            @Override
            public void onError(Exception e) {
            }
        });


        dialogViewHolder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), fullScreenImage.class);

                intent.putExtra("resim_linki", resim_linkleri.get(i));

                v.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return resim_linkleri.size();
    }

    class dialogViewHolder extends RecyclerView.ViewHolder {

        private ImageView image;
        private AVLoadingIndicatorView bar;

        dialogViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.dialogbox_design_imageView);
            bar = itemView.findViewById(R.id.dialogbox_design_progress_avi);

        }
    }
}
