package com.example.readtracker.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.example.readtracker.CallbackInterface;
import com.example.readtracker.R;
import com.example.readtracker.entidades.Dto.DtoMsg;
import com.example.readtracker.entidades.Reading;
import com.example.readtracker.webrequest.FireReading;

public class AddNewReadingActivity extends AppCompatActivity {

    // Register fields
    EditText title, author, pages, label, url;
    String valTitle, valAuthor, valPages, valUrl, userId, valLabel;

    // WebRequests
    FireReading fireReading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_reading);

        Intent intent = getIntent(); // Get serializable intent data
        userId = (String) intent.getSerializableExtra("userId");
    }

    public void btnAddReadingSend(View view) {
        fireReading = new FireReading();
        valTitle = ((EditText) findViewById(R.id.addReadingTitleInput)).getText().toString();
        valPages = ((EditText) findViewById(R.id.addReadingPagesPickerInput)).getText().toString();
        valLabel = ((EditText) findViewById(R.id.addReadingLabelInput)).getText().toString();
        boolean flag = valTitle.equals("") || valPages.equals("") || valLabel.equals("");
        if (!flag) {
            valAuthor = ((EditText) findViewById(R.id.addReadingAuthorInput)).getText().toString();
            valUrl = ((EditText) findViewById(R.id.addReadingUrlInput)).getText().toString();
            Reading r = new Reading(valTitle, valAuthor, Integer.valueOf(valPages), valUrl, valLabel, userId);
            fireReading.addReading(r, new CallbackInterface() {
                @Override
                public void onComplete(Object result) {
                    DtoMsg dtoMsg = (DtoMsg) result;
                    int dtoStatus = dtoMsg.getEstado();
                    backIntent();
                }

            });
        }else{
            Toast.makeText(AddNewReadingActivity.this, "Completar campos obligatorios", Toast.LENGTH_SHORT).show();
        }
    }

    public void btnAddReadingReturn(View view) {
        backIntent();
    }

    public void backIntent() {
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

}
