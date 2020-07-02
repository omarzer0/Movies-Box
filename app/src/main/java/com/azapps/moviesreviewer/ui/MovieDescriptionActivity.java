package com.azapps.moviesreviewer.ui;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.azapps.moviesreviewer.R;
import com.azapps.moviesreviewer.pojo.Results;
import com.bumptech.glide.Glide;

import static com.azapps.moviesreviewer.repository.Constant.RESULT_EXTRA;


public class MovieDescriptionActivity extends AppCompatActivity {
    private ImageView cover,backArrow;
    private TextView title, date, rateNumber, description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_description);
        backArrow = findViewById(R.id.back_arrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        cover = findViewById(R.id.movie_img_cover);
        title = findViewById(R.id.description_title);
        date = findViewById(R.id.description_release_date);
        rateNumber = findViewById(R.id.description_rating_number);
        description = findViewById(R.id.overview_description);

        if (getIntent().hasExtra(RESULT_EXTRA)) {
            Results result = (Results) getIntent().getSerializableExtra(RESULT_EXTRA);

            Uri image_url = Uri.parse(result.getPoster_path());
            Glide.with(this).load(image_url).into(cover);

            title.setText(result.getTitle());
            date.setText(result.getRelease_date());
            rateNumber.setText(result.getVote_average());
            description.setText(result.getOverview());
        }else {
            Toast.makeText(this, "Can not load the data", Toast.LENGTH_SHORT).show();
        }
    }
}