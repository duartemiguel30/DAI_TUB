package com.example.dai_tub;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextEmail, editTextPassword;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        mAuth = FirebaseAuth.getInstance();

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);

        Intent intent = getIntent();
        if (intent != null) {
            String email = intent.getStringExtra("email");
            String password = intent.getStringExtra("password");
            if (email != null && password != null) {
                editTextEmail.setText(email);
                editTextPassword.setText(password);
            }
        }

        Button buttonLogin = findViewById(R.id.buttonLogin);
        Button buttonBack = findViewById(R.id.buttonBack);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEmail.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();

                authenticateUser(email, password);
            }
        });

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void authenticateUser(String email, String password) {
        // Tenta autenticar com o Firebase
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            String userEmail = mAuth.getCurrentUser().getEmail();
                            if (userEmail != null && userEmail.equals("admin@admin.com")) {
                                Intent intent = new Intent(LoginActivity.this, MenuAdministradorActivity.class);
                                startActivity(intent);
                            } else {
                                Intent intent = new Intent(LoginActivity.this, MenuPrincipalActivity.class);
                                startActivity(intent);
                            }
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, "Falha no login.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
