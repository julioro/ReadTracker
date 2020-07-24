package com.example.readtracker.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.readtracker.R;

public class AddNewReadingActivity extends AppCompatActivity {

    // Register fields
    EditText title, author, pages, label, url
    String valTitle, valAuthor, valPages, valUrl, userId;

    // WebRequests
    FireReading fireReading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_reading);

        Intent intent = getIntent(); // Get serializable intent data
        userId = (String) intent.getSerializableExtra("userId");

        }

        public void addReadings(View view){
            fireReading = new FireReading();
            valTitle = ((EditText) findViewById(R.id.addReadingTitleInput)).getText().toString();
            valAuthor = ((EditText) findViewById(R.id.addReadingAuthorInput)).getText().toString();
            valPages = ((EditText) findViewById(R.id.addReadingPagesInput)).getText().toString();
            valUrl = ((EditText) findViewById(R.id.addReadingUrlInput)).getText().toString();
            
            Reading r = new (valTitle, valAuthor, Integer.valueOf(valPages), valUrl);
            fireReading.addReading(r, userId, new CallbackInterface(){
                @Override
                public void onComplete(Object result){
                    DtoMsg dtoMsg = (DtoMsg) result;
                    int dtoStatus = dtoMsg.getEstado();
                    returnListReadings();

                }

            });        
        }

        public void returnListReadings(){
            Intent returnIntent = new Intent();
            setResult(Activity.RESULT_OK,returnIntent);
            finish();   
        }

    }
}