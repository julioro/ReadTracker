package com.example.readtracker.webrequest;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.readtracker.CallbackInterface;
import com.example.readtracker.entidades.Dto.DtoMsg;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class FireUsuario {

    private FirebaseFirestore db;
    private FirebaseAuth mAuth;


    /*
     * 1:  Ingreso exitoso.
     * -1: Usuario no existe.  FirebaseAuthInvalidUserException
     * -2: Password invalida.  FirebaseAuthInvalidCredentialsException
     * */
    public void doLogin(String email, String password, Activity context, final CallbackInterface callback) {
        mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(context, new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        callback.onComplete(new DtoMessage("Ingreso exitoso", 1, mAuth.getCurrentUser()));
                    }
                })
                .addOnFailureListener(context, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        if (e instanceof FirebaseAuthInvalidUserException) {
                            callback.onComplete(new DtoMessage("Usuario no existe", -1));
                        } else if (e instanceof FirebaseAuthInvalidCredentialsException) {
                            callback.onComplete(new DtoMessage("Password inválida", -2));
                        }
                    }
                });
    }

   
    /*
     * Respuesta segun estado
     * -3: Correo invalido
     * -2: Password muy corta
     * -1: Ya existe usuario con este correo
     *  1: Dar acceso
     *  2: Passwords no coinciden
     *  3: Campos vacios
     * */
    public void crearUsuario(String correo, String pw, String pwRepetida, Activity context, final CallbackInterface callback) {
        if (!pw.equals(pwRepetida)){
            callback.onSuccess(new DtoMsg("Contraseñas no coinciden", 2));
            return;
        } else if (pw.equals("") || correo.equals("")){
            callback.onSuccess(new DtoMsg("Completar todos los campos", 3));
            return;
        }

        mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(correo, pw)
                .addOnSuccessListener(context, new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        callback.onSuccess(new DtoMsg("Usuario registrado", 1));
                    }
                })
                .addOnFailureListener(context, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        String msg;
                        int estado;

                        Log.d("msgxd", e.getClass().getName());
                        switch (e.getClass().getName()) {
                            case "com.google.firebase.auth.FirebaseAuthUserCollisionException":
                                msg = "Ya existe un usuario con ese correo";
                                estado = -1;
                                break;
                            case "com.google.firebase.auth.FirebaseAuthWeakPasswordException":
                                msg = "Longitud mínima de contrsaeña 6 caractéres";
                                estado = -2;
                                break;
                            case "com.google.firebase.auth.FirebaseAuthInvalidCredentialsException":
                                msg = "Correo inválido";
                                estado = -3;
                            default:
                                msg = "Ocurrió un error";
                                estado = 0;
                                break;
                        }
                        Log.d("msgxd", msg + String.valueOf(estado));
                        callback.onSuccess(new DtoMsg(msg, estado));

                    }

                });
    }
}

 /*
     * Respuesta segun estado
     * 1: Dar acceso
     * 2: Password incorrecto
     * 3: No existe el usuario
     * 0: Error en la consulta
     * 
    public void buscarUsuarioPassword(String usuario, final String password, final CallbackInterface callback) {
        db = FirebaseFirestore.getInstance();
        CollectionReference usuarioRef = db.collection("usuarios");
        final DocumentReference docRef = usuarioRef.document(usuario);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        if (document.get("password").equals(password)) {
                            callback.onSuccess(new DtoMsg("Identificación válida", 1));
                        } else {
                            callback.onSuccess(new DtoMsg("Password inválida", 2));
                        }
                    } else {
                        callback.onSuccess(new DtoMsg("El usuario no existe", 3));
                    }
                } else {
                    Log.d("msgxd", "get failed with ", task.getException());
                    callback.onSuccess(new DtoMsg("Ocurrió un error", 0));
                }
            }
        });

    }
    */
