package com.example.readtracker.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.readtracker.R;

public class RegisterActivity extends AppCompatActivity {

    // WebRequests
    FireUsuario fireUsuario;
    // Buttons
    private Button createUserSendButton, createUserReturnButton;
    private EditText createUserMailInput, createUserPassword1Input, createUserPassword2Input


    String msgToast;
    int requestStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        fireUsuario = new FireUsuario();


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

                fireUsuario.crearUsuario(userMail, pw1, pw2, RegisterActivity.this, new CallbackInterface(){
                   @Override
                   public void onSuccess(Object result){
                        DtoMsg msg = (DtoMsg) result;
                        msgToast = msg.getMsg();
                        requestStatus = msg.getStatus();
                        if (requestStatus){
                            FirebaseUser currentUser = (FirebaseUser) result.getObject();
                            Intent intent = new Intent(RegisterActivity.this, ListaLecturasActivity.class);
                            intent.putExtra("currentUser", currentUser);
                            startActivity(intent);
                            finish();
                        }else{
                            Toast.makeText(RegisterActivity.this, msg, Toast.LENGTH_SHORT).show();
                        }

                   } 
                });
            }
        });

        createUserReturnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                setResult(RESULT_OK,intent);
                finish();
            }

    }
}