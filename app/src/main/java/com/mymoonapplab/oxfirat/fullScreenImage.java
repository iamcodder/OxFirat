package com.mymoonapplab.oxfirat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class fullScreenImage extends AppCompatActivity {

    private ProgressBar bar;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_image);

        imageView = findViewById(R.id.activity_full_screen_imageview);
        bar=findViewById(R.id.activity_full_screen_progressBar);

        Intent getIntent = getIntent();

        Picasso.get().load(getIntent.getStringExtra("resim_linki")).into(imageView, new Callback() {
            @Override
            public void onSuccess() {
                bar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(getApplicationContext(),e.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
            }
        });

    }

}
