package com.example.readtracker.activity;

import android.content.Intent;
import com.example.readtracker.entidades.Reading;
import com.example.readtracker.entidades.User;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.readtracker.CallbackInterface;
import com.example.readtracker.R;
import com.example.readtracker.adapters.ListaLecturasAdapter;
import com.example.readtracker.webrequest.FireReading;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import android.os.Bundle;
import android.util.Log;

public class ListReadingsActivity extends AppCompatActivity {

    User currentUser;

    // Recycler & Adapter
    RecyclerView rView;
    ListReadingsAdapter rReadingsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_readings);

        Intent intent = getIntet(); // Get serializable intent data
        currentUser = (User) intent.getSerializable("currentUser");
        fillInfo(currentUser);

    }

    /**
     * Fill recycler view.
     */
    public void fillInfo(User user){
        rReadingsAdapter = new ListReadingsAdapter(user.getListReadings(), ListReadingsActivity.this);
        rView = findViewById(R.id.listReadingsRecyclerView);
        rView.setLayoutManager(new LinearLayoutManager(ListReadingsActivity.this));
        rView.setAdapter(rReadingsAdapter);
    }


    /**
     * Button agregar una nueva lectura.
     */
    public void buttonAddNewReading(View view) {
        Intent intent = new Intent(ListReadingsActivity.this, AddNewReadingActivity.class);
        startActivity(intent);
    }

    /**
     * Button ver detalles
     */
    public void buttonDetailsReading(View view) {
        Intent intent = new Intent(ListReadingsActivity.this, DetailsReadingActivity.class);
        startActivity(intent);
    }

}