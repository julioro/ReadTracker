package com.example.readtracker.activity;

import android.content.Intent;

import com.example.readtracker.adapters.ListReadingsAdapter;
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
import android.view.View;

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
    ArrayList<Incidence> listReadings = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_readings);
        Log.d("msgxd", "Iniciando ListReadingsActivity");
        Intent intent = getIntent(); // Get serializable intent data
        currentUserWithReadings = (User) intent.getSerializableExtra("currentUserWithReadings");
        listReadings = currentUserWithReadings.getListReadings();
        fillInfo(listReadings);

    }

    /**
     * Fill recycler view.
     */
    public void fillInfo() {
        rReadingsAdapter = new ListReadingsAdapter(listReadings, ListReadingsActivity.this);
        rView = findViewById(R.id.listReadingsRecyclerView);
        rView.setLayoutManager(new LinearLayoutManager(ListReadingsActivity.this));
        rView.setAdapter(rReadingsAdapter);

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
                if (status){
                    Datea dateSelected = readingSelected.getDate();
                }

                Intent intent = new Intent(ListIncidencesActivity.this, ViewIncidenceActivity.class);
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
        intent.putExtra("userId", currentUserWithReadings.getUsuarioId);
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
        
        databaseReference.child(libraryPath).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e);
                    return;
                }
                if (dataSnapshot.getValue() != null) {
                    Log.d("msgxd", dataSnapshot.getValue().toString());

                    listReadings.clear();
                    // Iterar
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        Reading r = postSnapshot.getValue(Reading.class);
                        listReadings.add(r);  // agregar todas las readings a un arreglo
                        Log.d("msgxd", r.getTitle(); // imprimir desde un List
                    }
                    fillInfo();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { // si hay un error al obtener la información en Firebase

            }
        });

    }






}