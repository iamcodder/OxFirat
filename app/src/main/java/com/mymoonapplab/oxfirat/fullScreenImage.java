package com.mymoonapplab.oxfirat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.wang.avi.AVLoadingIndicatorView;

public class fullScreenImage extends AppCompatActivity {

    private ImageView imageView;
    private AVLoadingIndicatorView progress_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_image);

        imageView = findViewById(R.id.activity_full_screen_imageview);
        progress_bar = findViewById(R.id.activity_full_screen_progress_avi);

        Intent getIntent = getIntent();

        Picasso.get().load(getIntent.getStringExtra("resim_linki")).into(imageView, new Callback() {
            @Override
            public void onSuccess() {
                progress_bar.smoothToHide();
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}
