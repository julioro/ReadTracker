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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_readings);

        // Get serializable intent data
        Intent intent = getIntet();
        currentUser = (User) intent.getSerializable("currentUser");

    }

}