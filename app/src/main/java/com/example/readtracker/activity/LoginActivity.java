package com.example.readtracker.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.readtracker.CallbackInterface;
import com.example.readtracker.R;
import com.example.readtracker.entidades.Dto.DtoMsg;
import com.example.readtracker.entidades.User;
import com.example.readtracker.webrequest.FireUsuario;
import com.google.firebase.auth.FirebaseAuth;

import com.example.readtracker.webrequest.Internet;

public class LoginActivity extends AppCompatActivity {

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
        recoverLoginButton.findViewById(R.id.recoverLoginButton);
        recoverLoginButton.setText(Html.fromHtml("<a href='#'>Se olvidó su password? Click aquí </a>")); // Darle estilo a recuperar password.

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
            fireUsuario.doLogin(loginPwInputValue, loginPwInputValue, LoginActivity.this, new CallbackInterface() {
                @Override
                public void onSuccess(Object result) {
                    DtoMsg msg = (DtoMsg) result;
                    msgToast = msg.getMsg();
                    Toast.makeText(LoginActivity.this, msgToast, Toast.LENGTH_SHORT).show();
                    if (msg.getEstado() == 1) {         // Logueo exitoso.
                        Intent intent = new Intent(LoginActivity.this, ListaLecturasActivity.class);
                        User user = (User) msg.getObject();

                        intent.putExtra("user", user);
                        startActivity(intent);
                    }
                }
            });
        }else{                                          // No hay conexion de internet.
            Toast.makeText(LoginActivity.this, "Fallo en la conexión", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Button registrar nuevo usuario
     */
    public void buttonRegisterUser(View view){
        Intent intent = new Intent(LoginActivity.this, CreateUserActivity.class);
        startActivity(intent);
    }

    /**
     * Button recuperar password
     */
    public void buttonRecoverPassword(View view){
        Intent intent = new Intent(LoginActivity.this, RecoverUserActivity.class);
        startActivity(intent);
    }


}