package com.example.readtracker.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.example.readtracker.R;

public class DetailsReadingActivity extends AppCompatActivity {
    Reading readingSelected;
    String userId;
    TextView detailsTitle, detailsAuthors, detailsUrl, detailsLabel, deatilsPages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_reading);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {readingSelected = extras.getString("readingSelected"); userId = extras.getString("userId");}
        fillInfo();
           
    }

    public void fillInfo(){
        TextView detailsTitle = findViewById(R.id.detailsTitleValue);
        TextView detailsAuthors = findViewById(R.id.detailsAuthorsValue);
        TextView detailsUrl = findViewById(R.id.detailsUrlValue);
        TextView detailsLabel = findViewById(R.id.detailsLabelValue);
        TextView detailsPages = findViewById(R.id.deatilsPagesValue );

        detailsTitle.setText(readingSelected.getTitle());
        detailsAuthors.setText(readingSelected.getAuthors());
        detailsUrl.setText(readingSelected.getUrl());
        detailsLabel.setText(readingSelected.getLabel());
        detailsPages.setText(readingSelected.getPages());
        
    }
}