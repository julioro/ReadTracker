package com.example.readtracker.activity;

import android.app.Activity;
import android.content.Intent;

import com.example.readtracker.adapters.ListReadingsAdapter;
import com.example.readtracker.entidades.Dto.DtoMsg;
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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

public class ListReadingsActivity extends AppCompatActivity {

    User currentUser;
    String userId;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_readings);
        Log.d("msgxd", "Iniciando ListReadingsActivity");
        Intent intent = getIntent(); // Get serializable intent data
        currentUser = (User) intent.getSerializableExtra("currentUser");
        userId = currentUser.getUsuarioId();
        readingsEventsListener(userId);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == LAUNCH_ADD_READING_ACTIVITY) {
            if (resultCode == Activity.RESULT_OK) {
                readingsEventsListener(userId);
                if (resultCode == Activity.RESULT_CANCELED) {
                    //Toast.makeText(this, "onActivityResult RESULT_CANCELED", Toast.LENGTH_SHORT).show();
                }
            }
        }
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
                for (DocumentSnapshot dS : queryResult) {
                    Reading r = new Reading();
                    r = dS.toObject(Reading.class);
                    r.setId(dS.getId());
                    listReadings.add(r);
                }
                fillInfo();
            }
        });
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
            public void onItemClick(int position, View view) {
                Reading readingSelected = listReadings.get(position);
                String seleccionado = getResources().getResourceEntryName(view.getId());
                if (seleccionado.equals("rv_list_readings_more_details")) {
                    Intent intent = new Intent(ListReadingsActivity.this, DetailsReadingActivity.class);
                    intent.putExtra("readingSelected", readingSelected);
                    startActivityForResult(intent, LAUNCH_VIEW_READING_ACTIVITY);
                } else if (seleccionado.equals("rv_list_readings_delete_reading")) {
                    (new FireReading()).deleteReading(readingSelected, new CallbackInterface() {
                        @Override
                        public void onComplete(Object result) {
                            DtoMsg dtoMsg = (DtoMsg) result;
                            if (dtoMsg.getEstado()==1){
                                Toast.makeText(ListReadingsActivity.this, dtoMsg.getMsg(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else if (seleccionado.equals("rv_list_readings_read_status")) {
                    if (readingSelected.isStatus()) {
                        readingSelected.setStatus(false);
                        readingSelected.setReadDate(null);
                    }else{
                        readingSelected.setStatus(true);
                        readingSelected.setReadDate(new Date());
                    }
                    (new FireReading()).editReading(readingSelected, new CallbackInterface() {
                        @Override
                        public void onComplete(Object result) {

                        }
                    });
                }
            }
        });

    }


    /**
     * Button agregar una nueva lectura.
     */
    public void actionAddIncAppBar(View view) {
        Intent intent = new Intent(ListReadingsActivity.this, AddNewReadingActivity.class);
        intent.putExtra("userId", currentUser.getUsuarioId());
        startActivityForResult(intent, LAUNCH_ADD_READING_ACTIVITY);
    }


}