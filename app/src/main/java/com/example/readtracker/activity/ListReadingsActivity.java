package com.example.readtracker.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.readtracker.CallbackInterface;
import com.example.readtracker.R;
import com.example.readtracker.adapters.ListaLecturasAdapter;
import com.example.readtracker.entidades.Dto.DtoReading;
import com.example.readtracker.entidades.Reading;
import com.example.readtracker.webrequest.FireReading;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import android.os.Bundle;
import android.util.Log;

public class ListReadingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_lecturas);

    }

}