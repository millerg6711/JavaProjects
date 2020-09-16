package edu.ranken.gmiller.materialmeplayground;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class DetailActivity extends AppCompatActivity {
    private TextView mSportsTitle;
    private TextView mSportsDetails;
    private ImageView mSportsImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mSportsTitle = findViewById(R.id.titleDetail);
        mSportsImage = findViewById(R.id.sportsImageDetail);
        mSportsDetails = findViewById(R.id.subTitleDetail);

        Sport sport = getIntent().getParcelableExtra("Sport");
        if (sport != null) {
            mSportsTitle.setText(sport.getTitle());
            mSportsDetails.setText(sport.getDetails());
            /*Glide.with(this).load(
            getIntent().getIntExtra("image_resource", 0)).into(mSportsImage);*/
            Glide.with(this).load(sport.getImageResource()).into(mSportsImage);
        }
    }
}
