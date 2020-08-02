package com.example.readtracker.webrequest;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.readtracker.CallbackInterface;
import com.example.readtracker.entidades.Dto.DtoMsg;
import com.example.readtracker.entidades.Reading;
import com.example.readtracker.entidades.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.firestore.model.mutation.FieldMask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class FireReading {
    FirebaseFirestore db;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;

    /**
     * 1:  Lecturas obtenidas -> currentUser
     */
    public void listReadings(final User currentUser, final CallbackInterface callback) {
        String userId = currentUser.getUsuarioId();

        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        db = FirebaseFirestore.getInstance();
        String libraryPath = "readings/" + currentUser.getUsuarioId() + "/library";
        db.collection(libraryPath).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    Reading r = new Reading();
                    for (DocumentSnapshot dS : task.getResult()) {
                        r = dS.toObject(Reading.class);
                        r.setId(dS.getId());
                        currentUser.getListReadings().add(r);
                    }
                    callback.onComplete(new DtoMsg("Lecturas obtenidas", 1, currentUser));
                } else {
                    Log.d("msgxd", "get failed with ", task.getException());

                }
            }
        });
    }

    /**
     * 1 : Lectura agregada.
     * -1: No se pudo agregar la lectura.
     */
    public void addReading(Reading reading, final CallbackInterface callback) {
        db = FirebaseFirestore.getInstance();
        String libraryPath = "readings/" + reading.getUserId() + "/library";
        DocumentReference dR = db.collection(libraryPath).document();
        String nextId = dR.getId();
        reading.setId(nextId);
        Log.d("msgxd", "============================= ID: " + reading.getId());
        db.collection(libraryPath).add(reading).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Log.d("msgxd", "DocumentSnapshot written with ID: " + documentReference.getId());
                callback.onComplete(new DtoMsg("Lectura agregada,", 1));

            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("msgxd", "Error adding document", e);
                        callback.onComplete(new DtoMsg("No se pudo agregar la lectura.", -1));
                    }
                });

    }


    public void editReading(Reading reading, final CallbackInterface callbackInterface) {
        db = FirebaseFirestore.getInstance();
        String libraryPath = "readings/" + reading.getUserId() + "/library";
        DocumentReference dr = db.collection(libraryPath).document(reading.getId());

        dr.set(reading).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                callbackInterface.onComplete(new DtoMsg("Lectura creada", 1));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("msgxd", e.toString());
                Log.d("msgxd", e.getClass().toString());
            }
        });
    }

    public void deleteReading(Reading reading, final CallbackInterface callbackInterface) {
        db = FirebaseFirestore.getInstance();
        String libraryPath = "readings/" + reading.getUserId() + "/library";
        DocumentReference dr = db.collection(libraryPath).document(reading.getId());
        dr.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                callbackInterface.onComplete(new DtoMsg("Lectura borrada", 1));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("msgxd", e.toString());
                Log.d("msgxd", e.getClass().toString());
            }
        });
    }


    public void filterByLabel(String userid, String label, final CallbackInterface callbackInterface) {
        db = FirebaseFirestore.getInstance();
        String libraryPath = "readings/" + userid + "/library";
        CollectionReference cr = db.collection(libraryPath);
        Query q = cr.whereEqualTo("label", label)
                .whereEqualTo("status", true)
                .orderBy("readDate", Query.Direction.ASCENDING);
        Log.d("msgxd", "Ejecutando query ...");
        q.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                ArrayList<Reading> filteredList = new ArrayList<>();
                for (DocumentSnapshot dS : queryDocumentSnapshots) {
                    Reading r;
                    r = dS.toObject(Reading.class);
                    r.setId(dS.getId());
                    filteredList.add(r);
                }
                Log.d("msgxd", "Query resultado ok");
                callbackInterface.onComplete(new DtoMsg("Lista filtrada", 1, filteredList));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("msgxd", e.toString());
            }
        });
    }
}

