package com.mymoonapplab.oxfirat.haberler;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.mymoonapplab.oxfirat.R;
import com.mymoonapplab.oxfirat.dialogBox.dialogBox;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

public class fragment_haberler_adapter extends RecyclerView.Adapter<fragment_haberler_adapter.ViewHolder> {

    private Context mContext;
    private List<String> haber_baslik;
    private List<String> haber_resim;
    private List<String> haber_linki;
    private FragmentManager manager;


    fragment_haberler_adapter(Context mContext, List<String> haber_baslik, List<String> haber_resim, List<String> haber_linki, FragmentManager manager) {
        this.mContext = mContext;
        this.haber_baslik = haber_baslik;
        this.haber_resim = haber_resim;
        this.haber_linki = haber_linki;
        this.manager = manager;


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.fragment_haberler_design, null);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, @SuppressLint("RecyclerView") final int i) {

        String link = mContext.getResources().getString(R.string.okul_sitesi);

        viewHolder.mTextview.setText(haber_baslik.get(i));
        viewHolder.mProgressBar.setVisibility(View.VISIBLE);

        Glide.with(mContext)
                .load(link + haber_resim.get(i))
                .transition(DrawableTransitionOptions.withCrossFade())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        viewHolder.mProgressBar.setVisibility(View.INVISIBLE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        viewHolder.mProgressBar.setVisibility(View.INVISIBLE);
                        return false;
                    }
                })
                .into(viewHolder.mImageview);


        viewHolder.mCardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialogBox dialogBox = new dialogBox(haber_linki.get(i));

                dialogBox.show(manager, "dialogbox_showed");

            }
        });
    }

    @Override
    public int getItemCount() {
        return haber_baslik.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private CardView mCardview;
        private ImageView mImageview;
        private TextView mTextview;
        private ProgressBar mProgressBar;


        ViewHolder(@NonNull View itemView) {
            super(itemView);
            mCardview = itemView.findViewById(R.id.fragment_haberler_carview_design);
            mImageview = itemView.findViewById(R.id.cardView_resim);
            mTextview = itemView.findViewById(R.id.cardView_baslik);
            mProgressBar=itemView.findViewById(R.id.fragment_haberler_carview_design_progress);
        }
    }
}
