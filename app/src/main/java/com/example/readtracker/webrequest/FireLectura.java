package com.example.readtracker.webrequest;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.readtracker.CallbackInterface;
import com.example.readtracker.entidades.Dto.DtoMsg;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class FireLectura {
    private FirebaseFirestore db;

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
