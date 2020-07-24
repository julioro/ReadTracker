package com.example.readtracker.activity;

import android.content.Intent;

import com.example.readtracker.adapters.ListReadingsAdapter;
import com.example.readtracker.entidades.Reading;
import com.example.readtracker.entidades.User;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.readtracker.CallbackInterface;
import com.example.readtracker.R;
import com.example.readtracker.adapters.ListaLecturasAdapter;
import com.example.readtracker.webrequest.FireReading;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.Date;

public class ListReadingsActivity extends AppCompatActivity {

    User currentUser;
    int LAUNCH_ADD_READING_ACTIVITY = 1;
    int LAUNCH_VIEW_READING_ACTIVITY = 2;
    // Recycler & Adapter
    RecyclerView rView;
    ListReadingsAdapter rReadingsAdapter;

    FirebaseFirestore db;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;

    // Lista de readings
    ArrayList<Reading> listReadings = new ArrayList<>();

    User currentUserWithReadings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_readings);
        Log.d("msgxd", "Iniciando ListReadingsActivity");
        Intent intent = getIntent(); // Get serializable intent data
        currentUserWithReadings = (User) intent.getSerializableExtra("currentUserWithReadings");
        listReadings = currentUserWithReadings.getListReadings();
        fillInfo();

    }

    /**
     * Fill recycler view.
     */
    public void fillInfo() {
        rReadingsAdapter = new ListReadingsAdapter(listReadings, ListReadingsActivity.this);
        rView = findViewById(R.id.listReadingsRecyclerView);
        rView.setLayoutManager(new LinearLayoutManager(ListReadingsActivity.this));
        rView.setAdapter(rReadingsAdapter);
        rView.setLayoutManager(new LinearLayoutManager(ListReadingsActivity.this));
        rReadingsAdapter.setOnItemClickListener(new ListReadingsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Reading readingSelected = listReadings.get(position);
                String readingTitleSelected = readingSelected.getTitle();
                String urlSelected = readingSelected.getUrl();
                String labelSelected = readingSelected.getLabel();
                String authorSelected = readingSelected.getAuthor();

                String pagesSelected = String.valueOf(readingSelected.getPages());
                boolean status = readingSelected.isStatus();
                if (status) {
                    Date dateSelected = readingSelected.getReadDate();
                }

                Intent intent = new Intent(ListReadingsActivity.this, DetailsReadingActivity.class);
                intent.putExtra("readingSelected", readingSelected);
                /*intent.putExtra("incidenceNameSelected", incidenceNameSelected);
                intent.putExtra("incidenceDescriptionSelected", incidenceDescriptionSelected);
                intent.putExtra("incidenceStatusSelected", incidenceStatusSelected);
                intent.putExtra("incidenceCommentSelected", incidenceCommentSelected);
                intent.putExtra("rol", rol);
                intent.putExtra("incidenceOwnerSelected", incidenceOwnerSelected);
                */
                startActivityForResult(intent, LAUNCH_VIEW_READING_ACTIVITY);
            }
        });

    }


    /**
     * Button agregar una nueva lectura.
     */
    public void actionAddIncAppBar(MenuItem item) {
        Intent intent = new Intent(ListReadingsActivity.this, AddNewReadingActivity.class);
        intent.putExtra("userId", currentUserWithReadings.getUsuarioId());
        startActivityForResult(intent, LAUNCH_ADD_READING_ACTIVITY);
    }

    /**
     * Button ver detalles
     */
    public void buttonDetailsReading(View view) {
        Intent intent = new Intent(ListReadingsActivity.this, DetailsReadingActivity.class);
        startActivity(intent);
    }


    /**
     * Listener para los cambios
     */
    public void readingsEventsListener(String userId) {
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        db = FirebaseFirestore.getInstance();
        String libraryPath = "readings/" + userId + "/library";

        db.collection(libraryPath).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryResult, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.d("msgxd", "Listen failed", e);
                }
                listReadings.clear();
                Reading r = new Reading();
                for (DocumentSnapshot dS : queryResult) {
                    r = dS.toObject(Reading.class);
                    r.setId(dS.getId());
                    listReadings.add(r);
                }
                fillInfo();
            }
        });

    }


}