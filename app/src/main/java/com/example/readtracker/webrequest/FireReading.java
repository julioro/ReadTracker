package com.example.readtracker.webrequest;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.readtracker.CallbackInterface;
import com.example.readtracker.entidades.Dto.DtoMsg;
import com.example.readtracker.entidades.Reading;
import com.example.readtracker.entidades.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
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
                    Log.d("msgxd", currentUser.getListReadings().toString());
                    callback.onComplete(new DtoMsg("Lecturas obtenidas", 1, currentUser));
                } else {
                    Log.d("msgxd", "get failed with ", task.getException());

                }
            }
        });
    }

    /*
     * Respuesta segun estado
     * 1: Dar acceso
     * 2: Password incorrecto
     * 3: No existe el usuario
     * 0: Error en la consulta
     * */
    public void listarLecturas(String usuario, final CallbackInterface callback) {
        db = FirebaseFirestore.getInstance();
        CollectionReference usuarioRef = db.collection("lecturas");
        final DocumentReference docRef = usuarioRef.document(usuario);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("msgxd", document.toString());
                        Log.d("msgxd", document.getData().toString());
                    } else {
                        Log.d("msgxd", "get failed with ", task.getException());
                    }
                }
            }
        });
    }
}
