package com.example.readtracker.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.readtracker.R;
import com.example.readtracker.entidades.Reading;

public class DetailsReadingActivity extends AppCompatActivity {
    Reading readingSelected;
    String userId;
    TextView detailsTitle, detailsAuthors, detailsUrl, detailsLabel, deatilsPages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_reading);

        readingSelected = (Reading) getIntent().getSerializableExtra("readingSelected");
        userId = getIntent().getStringExtra("userId");
        fillInfo();
    }


    public void fillInfo() {
        TextView detailsTitle = findViewById(R.id.detailsTitleValue);
        TextView detailsAuthors = findViewById(R.id.detailsAuthorsValue);
        TextView detailsUrl = findViewById(R.id.detailsUrlValue);
        TextView detailsLabel = findViewById(R.id.detailsLabelValue);
        TextView detailsPages = findViewById(R.id.deatilsPagesValue);

        detailsTitle.setText(readingSelected.getTitle());
        detailsAuthors.setText(readingSelected.getAuthor());
        detailsUrl.setText(readingSelected.getUrl());
        detailsLabel.setText(readingSelected.getLabel());
        detailsPages.setText(readingSelected.getPages());

    }
}