package com.example.dai_tub;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Registro2Activity extends AppCompatActivity {

    private EditText editTextName, editTextEmail, editTextPassword, editTextNIF, editTextPassNumber;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registro2);

        mAuth = FirebaseAuth.getInstance();

        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextNIF = findViewById(R.id.editTextNIF);
        editTextPassNumber = findViewById(R.id.editTextPassNumber);

        Button buttonRegister = findViewById(R.id.buttonRegister);
        Button buttonLogin = findViewById(R.id.buttonLogin);
        Button buttonBack = findViewById(R.id.buttonBack);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString().trim();
                String email = editTextEmail.getText().toString().trim();
                String password = editTextPassword.getText().toString();
                String nif = editTextNIF.getText().toString().trim();
                String passNumber = editTextPassNumber.getText().toString().trim();

                if (name.isEmpty() || email.isEmpty() || password.isEmpty() || nif.isEmpty()) {
                    Toast.makeText(Registro2Activity.this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show();
                    return;
                }

                Log.d("Registro2Activity", "Dados do utilizador: Name=" + name + ", Email=" + email + ", NIF=" + nif);

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(Registro2Activity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    String userId = user.getUid();

                                    DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(userId);

                                    User newUser = new User(userId, name, email, nif, passNumber, 0.0, 0);
                                    userRef.setValue(newUser).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> databaseTask) {
                                            if (databaseTask.isSuccessful()) {
                                                Log.d("Registro2Activity", "Detalhes do utilizador guardados na base de dados");


                                                DatabaseReference userRouteRef = FirebaseDatabase.getInstance().getReference("users").child(userId).child("viagensCompradas");
                                                userRouteRef.setValue(0).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> viagensTask) {
                                                        if (viagensTask.isSuccessful()) {
                                                            Log.d("Registro2Activity", "Número de viagens compradas guardados no user");

                                                            Intent intent = new Intent(Registro2Activity.this, MenuPrincipalActivity.class);
                                                            intent.putExtra("nome", name);
                                                            intent.putExtra("email", email);
                                                            intent.putExtra("nif", nif);
                                                            if (!passNumber.isEmpty()) {
                                                                intent.putExtra("numeroPasse", passNumber);
                                                            }
                                                            startActivity(intent);
                                                            finish();
                                                        } else {
                                                            Log.e("Registro2Activity", "Erro ao guardar o número de viagens compradas no user", viagensTask.getException());
                                                            Toast.makeText(Registro2Activity.this, "Erro no registo.", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });
                                            } else {
                                                Log.e("Registro2Activity", "Erro ao guardar os detalhes do utilizador na base de dados", databaseTask.getException());
                                                Toast.makeText(Registro2Activity.this, "Erro ao registrar. ", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                } else {
                                    Log.e("Registro2Activity", "Erro ao registar", task.getException());
                                    Toast.makeText(Registro2Activity.this, "Erro ao registar. ", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Registro2Activity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
