package com.example.readtracker.activity;

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
import com.example.readtracker.entidades.Dto.DtoMsg;
import com.example.readtracker.webrequest.FireUsuario;
import com.firebase.ui.auth.AuthMethodPickerLayout;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseUiException;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.example.readtracker.webrequest.Internet;

public class LoginAct extends AppCompatActivity {

    // Instancias de WebRequests.
    Internet internetInstance;
    FireUsuario fireUsuario;

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
    String msgToas;


    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setConentView(R.layout.act_login);
        getSupportActionBar().hide(); // hide the title bar;
        
        // Ubicar campos a llenar;
        loginMailInput = findViewById(R.id.loginMailInput);
        loginPwInput = findViewById(R.id.loginPwInput);

        // Recuperar password
        recoverLoginButton.findViewById(R.id.recoverLoginButton);
        recuperar.setText(Html.fromHtml("<a href='#'>Se olvidó su password? Click aquí </a>")); // Darle estilo a recuperar password.

    }

    /**
     * Button logueo
     */
    public void buttonLogin(View view){

        loginMailInputValue = loginMailInput.getText().toString();
        loginPwInputValue = loginPwInput.getText().toString();
        internetInstance = new Internet();
        
        if (internetInstance.isInternetAvailable()){    // Hay conexion de internet.
            fireUsuario = new FireUsuario();
            fireUsuario.doLogin(loginPwInputValue, internetInstance, LoginAct.this, new CallbackInterface() {
                @Override
                public void onSuccess(Object result) {
                    DtoMsg msg = (DtoMsg) result;
                    msgToast = msg.getMsg();
                    Toast.makeText(LoginAct.this, msgToast, Toast.LENGTH_SHORT).show();
                    if (msg.getEstado() == 1) {         // Logueo exitoso.
                        Intent intent = new Intent(LoginAct.this, ListaLecturasActivity.class);
                        startActivity(intent);
                    }
                }
            });
        }else{                                          // No hay conexion de internet.
            Toast.makeText(LoginAct.this, "Fallo en la conexión", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Button registrar nuevo usuario
     */
    public void buttonRegisterUser(View view){
        Intent intent = new Intent(LoginAct.this, CreateUserAct.class);
        startActivity(intent);
    }

    /**
     * Button recuperar password
     */
    public void buttonRecoverPassword(View view){
        Intent intent = new Intent(LoginAct.this, RecoverUserAct.class);
        startActivity(intent);
    }


}