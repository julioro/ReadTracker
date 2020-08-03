package com.example.readtracker.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.readtracker.CallbackInterface;
import com.example.readtracker.R;
import com.example.readtracker.entidades.Dto.DtoMsg;
import com.example.readtracker.entidades.Reading;
import com.example.readtracker.entidades.User;
import com.example.readtracker.webrequest.FireUser;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity {

    // WebRequests
    FireUser fireUsuario;
    // Buttons
    private Button createUserSendButton, createUserReturnButton;
    private EditText createUserMailInput, createUserPassword1Input, createUserPassword2Input;


    String msgToast;
    int requestStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        fireUsuario = new FireUser();


        createUserMailInput = findViewById(R.id.createUserMailInput);
        createUserPassword1Input = findViewById(R.id.createUserPassword1Input);
        createUserPassword2Input = findViewById(R.id.createUserPassword2Input);


        createUserSendButton = findViewById(R.id.createUserSendButton);
        createUserReturnButton = findViewById(R.id.createUserReturnButton);


        createUserSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userMail;
                String pw1;
                String pw2;

                userMail = createUserMailInput.getText().toString();
                pw1 = createUserPassword1Input.getText().toString();
                pw2 = createUserPassword2Input.getText().toString();

                fireUsuario.crearUsuario(userMail, pw1, pw2, RegisterActivity.this, new CallbackInterface() {
                    @Override
                    public void onComplete(Object result) {
                        DtoMsg msg = (DtoMsg) result;
                        msgToast = msg.getMsg();
                        requestStatus = msg.getEstado();
                        if (requestStatus == 1) {
                            FirebaseUser currentFirebaseUser = ((FirebaseUser) msg.getObject());
                            String email = currentFirebaseUser.getEmail();
                            String userId = currentFirebaseUser.getUid();
                            ArrayList<Reading> listReadings = new ArrayList<>();
                            User currentUser = new User(email, userId, listReadings);
                            Intent intent = new Intent(RegisterActivity.this, ListReadingsActivity.class);
                            intent.putExtra("currentUser", currentUser);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(RegisterActivity.this, msgToast, Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });

        createUserReturnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}