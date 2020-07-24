package com.example.readtracker.activity.OldActivities;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.readtracker.CallbackInterface;
import com.example.readtracker.R;
import com.example.readtracker.activity.RecoverUserActivity;
import com.example.readtracker.entidades.Dto.DtoMsg;
import com.example.readtracker.webrequest.FireUser;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivityOld extends AppCompatActivity {
    private FirebaseAuth mAuth;

    String msgToast = "";
    LinearLayout linearlayoutBotones;
    Button ingresar;
    Button registrar;

    EditText usuariocorreo;
    EditText password;


    // Layout de campos a llenar
    TextInputLayout usuariocorreoLayout;
    TextInputLayout passwordLayout;
    TextInputLayout repetirpasswordLayout;
    //TextInputLayout nombreusuarioLayout;

    LinearLayout linearlayoutOlvido;

    TextView recuperar;

    boolean ocularMostrar = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_old);
        getSupportActionBar().hide(); // hide the title bar

        // Layout de campos a llenar
        usuariocorreoLayout = findViewById(R.id.correoLayout);
        passwordLayout = findViewById(R.id.passwordLayout);
        repetirpasswordLayout = findViewById(R.id.passwordrepearLayout);
        //nombreusuarioLayout = findViewById(R.id.nombreusuarioLayout);
        linearlayoutOlvido = findViewById(R.id.LinearLayoutOlvido);
        // Layout de botones
        linearlayoutBotones = findViewById(R.id.LinearLayoutBotones);
        // Acciones
        ingresar = findViewById(R.id.btnIngresar);
        registrar = findViewById(R.id.btnRegistrar);
        recuperar = findViewById(R.id.textViewRecuperar);
        recuperar.setText(Html.fromHtml("<a href='#'>Se olvidó su password? Click aquí </a>")); // Darle estilo a recuperar password.

    }

    // Abrir vista para registro.
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void btnRegistrar(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ocularMostrar = ocultarMostrarElementos(view, ocularMostrar);
        }
    }

    // Recuperar password
    public void textViewRecuperar(View view) {
        startActivity(new Intent(this, RecoverUserActivity.class));
    }

    // Intento de ingreso
    public void btnIngresar(View view) {
        msgToast = "";
        //EditText editTextUsuario = findViewById(R.id.editTextUsuario);
        //EditText editTextPassword = findViewById(R.id.editTextPassword);

        String usuario = ((EditText) findViewById(R.id.editTextUsuario)).getText().toString();
        String password = ((EditText) findViewById(R.id.editTextPassword)).getText().toString();


        if (ocularMostrar) { // Accion de Logeo
            Log.d("msgxd", "1");
            // Validar vacios
            if (usuario.isEmpty() || password.isEmpty()) {
                msgToast = "Completar los datos";
                if (!usuario.isEmpty()) {
                    msgToast = "Contraseña vacía";
                }
                if (!password.isEmpty()) {
                    msgToast = "Correo vacío";
                }
            } else { // Validar credenciales.
                FireUser fireUser = new FireUser();/*
                fireUser.buscarUsuarioPassword(usuario, password, new CallbackInterface() {
                    @Override
                    public void onSuccess(Object result) {
                        DtoMsg msg = (DtoMsg) result;
                        msgToast = msg.getMsg();
                        Toast.makeText(LoginActivityOld.this, msgToast, Toast.LENGTH_SHORT).show();
                        if (msg.getEstado() == 1) {
                            Intent intent = new Intent(LoginActivityOld.this, ListaLecturasActivity.class);
                            //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);

                        }
                    }
                });
                */
            }

        } else { // Accion de registro
            Log.d("msgxd", "2");
            String repetirpassword = ((EditText) findViewById(R.id.editTextRepetirPassword)).getText().toString();
            FireUser fireUser = new FireUser();
            fireUser.crearUsuario(usuario, password, repetirpassword, this, new CallbackInterface() {
                @Override
                public void onComplete(Object result) {
                    DtoMsg msg = (DtoMsg) result;
                    msgToast = msg.getMsg();
                    Toast.makeText(LoginActivityOld.this, msgToast, Toast.LENGTH_SHORT).show();
                    if (msg.getEstado() == 1) {
                        startActivity(new Intent(LoginActivityOld.this, ListaLecturasActivity.class));
                    }
                }
            });
        }


    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public boolean ocultarMostrarElementos(View view, boolean flag) {
        if (flag) {
            ingresar.setText(R.string.enviarFormularioRegistro);
            registrar.setText(R.string.cancelarRegistro);
            repetirpasswordLayout.setVisibility(View.VISIBLE);
            //nombreusuarioLayout.setVisibility(View.VISIBLE);

            fadeAnimation(repetirpasswordLayout, flag);
            //fadeAnimation(nombreusuarioLayout, flag);

            linearlayoutBotones.animate().translationY(100).setDuration(750);
            linearlayoutOlvido.animate().translationY(100).setDuration(750);
            //usuariocorreoLayout.animate().translationXBy(usuariocorreoLayout.getWidth()).setDuration(1000);

        } else {
            ingresar.setText(R.string.ingresar);
            registrar.setText(R.string.registarse);
            fadeAnimation(repetirpasswordLayout, flag);
            //fadeAnimation(nombreusuarioLayout, flag);
            linearlayoutBotones.animate().translationY(0).setDuration(750);
            linearlayoutOlvido.animate().translationY(0).setDuration(750);
            repetirpasswordLayout.setVisibility(View.GONE);
            //nombreusuarioLayout.setVisibility(View.GONE);

            //usuariocorreoLayout.animate().translationXBy(-  usuariocorreoLayout.getWidth()).setDuration(1000);

        }

        return !flag;
    }

    public void fadeAnimation(View view, boolean flag) {
        ObjectAnimator fadeEl;
        if (flag) {
            fadeEl = ObjectAnimator.ofFloat(view, "alpha", 0, 1f);
        } else {

            fadeEl = ObjectAnimator.ofFloat(view, "alpha", 1f, 0);
        }
        fadeEl.setDuration(750);
        fadeEl.start();
    }


}

