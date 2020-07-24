package com.example.readtracker.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.readtracker.CallbackInterface;
import com.example.readtracker.R;
import com.example.readtracker.entidades.Dto.DtoMsg;
import com.example.readtracker.entidades.Reading;
import com.example.readtracker.entidades.User;
import com.example.readtracker.webrequest.FireReading;
import com.example.readtracker.webrequest.FireUser;
import com.google.firebase.auth.FirebaseAuth;

import com.example.readtracker.webrequest.Internet;
import com.google.firebase.auth.FirebaseUser;

import java.io.Serializable;
import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    // Instancias de WebRequests.
    Internet internetInstance;
    FireUser fireUser;
    FireReading fireReading;

    // EditText de campos a llenar.
    EditText loginMailInput;
    EditText loginPwInput;

    // Buttons
    Button loginLoginButton;
    Button registerLoginButton;
    TextView recoverLoginButton;

    // Valores de los campos.
    String loginMailInputValue;
    String loginPwInputValue;

    // Notificaciones.
    String msgToast;


    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide(); // hide the title bar;

        // Ubicar campos a llenar;
        loginMailInput = findViewById(R.id.loginMailInput);
        loginPwInput = findViewById(R.id.loginPwInput);

        // Recuperar password
        recoverLoginButton = findViewById(R.id.recoverLoginButton);
        recoverLoginButton.setText(Html.fromHtml("<a href='#'>Se olvidó su password? Click aquí </a>")); // Darle estilo a recuperar password.

    }

    /**
     * Button logueo
     */
    public void buttonLogin(View view) {

        loginMailInputValue = loginMailInput.getText().toString();
        loginPwInputValue = loginPwInput.getText().toString();
        internetInstance = new Internet();

        if (internetInstance.isInternetAvailable(LoginActivity.this)) {    // Hay conexion de internet.
            fireUser = new FireUser();
            fireUser.doLogin(loginMailInputValue, loginPwInputValue, LoginActivity.this, new CallbackInterface() {
                @Override
                public void onComplete(Object result) {
                    DtoMsg msg = (DtoMsg) result;
                    msgToast = msg.getMsg();
                    Toast.makeText(LoginActivity.this, msgToast, Toast.LENGTH_SHORT).show();
                    if (msg.getEstado() == 1) {         // Logueo exitoso.
                        FirebaseUser currentFirebaseUser = ((FirebaseUser) msg.getObject());
                        String name = currentFirebaseUser.getDisplayName();
                        String email = currentFirebaseUser.getEmail();
                        String userId = currentFirebaseUser.getUid();
                        ArrayList<Reading> listReadings = new ArrayList<>();

                        final User currentUserWithReadings = new User(email, userId, listReadings);
                        Log.d("msgxd", currentUserWithReadings.getCorreo());
                       
                        (new FireReading()).listReadings(currentUserWithReadings, new CallbackInterface() {
                            @Override
                            public void onComplete(Object result) {
                                DtoMsg dtoMsg = (DtoMsg) result;
                                if (dtoMsg.getEstado() == 1) {
                                    User userWithReadings = (User) dtoMsg.getObject();
                                    Intent intent = new Intent(LoginActivity.this, ListReadingsActivity.class);
                                    intent.putExtra("currentUserWithReadings",  currentUserWithReadings);
                                    Log.d("msgxd", "Size of list: " + String.valueOf(currentUserWithReadings.getListReadings().size()));
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });
                        
                    }
                }
            });
        } else {                                          // No hay conexion de internet.
            Toast.makeText(LoginActivity.this, "Fallo en la conexión", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Button registrar nuevo usuario
     * FALTARIA RECIBIR LA RESPUESTA DEL FRAGMENTO PARA LOGUEARSE Y REDIRIGIRLO LUEGO DE REGISTRARSE EXITOSAMENTE.
     */
    public void buttonRegisterUser(View view) {
        //


        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivityForResult(intent, 2);
    }

    /**
     * Button recuperar password
     */
    public void buttonRecoverPassword(View view) {
        Intent intent = new Intent(LoginActivity.this, RecoverUserActivity.class);
        startActivity(intent);
    }


}