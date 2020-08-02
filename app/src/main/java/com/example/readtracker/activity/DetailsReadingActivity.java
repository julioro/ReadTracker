package com.example.readtracker.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.readtracker.CallbackInterface;
import com.example.readtracker.R;
import com.example.readtracker.entidades.Dto.DtoMsg;
import com.example.readtracker.entidades.Reading;
import com.example.readtracker.webrequest.FireReading;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DetailsReadingActivity extends AppCompatActivity {
    Reading readingSelected;
    TextView detailsTitle, detailsAuthors, detailsUrl, detailsLabel, deatilsPages;
    // Register fields
    EditText title, author, pages, label, url;
    String valTitle, valAuthor, valPages, valUrl, valLabel, valDate;
    TextView detailsRedDate;

    // WebRequests
    FireReading fireReading;

    Switch detailsStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_reading);

        readingSelected = (Reading) getIntent().getSerializableExtra("readingSelected");
        Log.d("msgxd", "ID DE READING> " + readingSelected.getId());
        Log.d("msgxd", "ID DEL USER>" + readingSelected.getUserId());
        fillInfo();
    }


    public void fillInfo() {
        TextView detailsTitle = findViewById(R.id.detailsTitleValue);
        TextView detailsAuthors = findViewById(R.id.detailsAuthorsValue);
        TextView detailsUrl = findViewById(R.id.detailsUrlValue);
        TextView detailsLabel = findViewById(R.id.detailsLabelValue);
        TextView detailsPages = findViewById(R.id.deatilsPagesValue);
        detailsStatus = findViewById(R.id.detailsRedStatus);
        detailsRedDate = findViewById(R.id.detailsDateRed);


        detailsTitle.setText(readingSelected.getTitle());
        detailsAuthors.setText(readingSelected.getAuthor());
        detailsUrl.setText(readingSelected.getUrl());
        detailsLabel.setText(readingSelected.getLabel());
        detailsPages.setText(String.valueOf(readingSelected.getPages()));

        switchStatusIncidence();

        if (readingSelected.isStatus()) {
            Log.d("msgxd", "buenobueno");
            detailsStatus.setChecked(true);
            //detailsRedDate.setText(readingSelected.getReadDate().toString());
            detailsRedDate.setText(readingSelected.parsedDate());
            detailsRedDate.setEnabled(true);

        } else {
            detailsStatus.setChecked(false);
            detailsRedDate.setEnabled(false);
        }

    }


    public void switchStatusIncidence() {
        detailsStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    detailsRedDate.setEnabled(true);
                } else {
                    detailsRedDate.setEnabled(false);
                }
            }
        });
    }


    public void btnEditDetailsSend(View view) {
        fireReading = new FireReading();
        valTitle = ((EditText) findViewById(R.id.detailsTitleValue)).getText().toString();
        valPages = ((EditText) findViewById(R.id.deatilsPagesValue)).getText().toString();
        valLabel = ((EditText) findViewById(R.id.detailsLabelValue)).getText().toString();
        boolean flag = valTitle.equals("") || valPages.equals("") || valLabel.equals("");

        boolean isRed = ((Switch) findViewById(R.id.detailsRedStatus)).isChecked();


        if (!flag) {
            valAuthor = ((EditText) findViewById(R.id.detailsAuthorsValue)).getText().toString();
            valUrl = ((EditText) findViewById(R.id.detailsUrlValue)).getText().toString();
            Reading r = new Reading(valTitle, valAuthor, Integer.valueOf(valPages), valUrl, valLabel, readingSelected.getUserId());
            r.setId(readingSelected.getId());
            try {
                if (isRed) {
                    r.setStatus(true);
                    valDate = ((EditText) findViewById(R.id.detailsDateRed)).getText().toString();
                    r.setReadDate(new SimpleDateFormat("dd/MM/yyyy").parse(valDate));
                }
                fireReading.editReading(r, new CallbackInterface() {
                    @Override
                    public void onComplete(Object result) {
                        DtoMsg dtoMsg = (DtoMsg) result;
                        int dtoStatus = dtoMsg.getEstado();
                        if (dtoStatus == 1) {
                            backIntent();
                        }
                    }
                });
            } catch (ParseException e) {
                e.printStackTrace();
                Toast.makeText(DetailsReadingActivity.this, "Fecha inválida", Toast.LENGTH_SHORT).show();

            }
        } else {
            Toast.makeText(DetailsReadingActivity.this, "Completar campos obligatorios", Toast.LENGTH_SHORT).show();
        }

    }

    public void btnDetailsReturn(View view) {
        backIntent();
    }


    public void backIntent() {
        Log.d("msgxd", "porquenoproceaesto");
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }
}