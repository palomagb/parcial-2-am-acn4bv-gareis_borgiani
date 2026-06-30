package com.palomagb.petcaretracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    // variables
    private EditText editCorreo, editPassword;
    private Button botonLogin;
    private TextView botonRegistro;
    private ProgressBar barraProgreso;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // inicializa firebase
        mAuth = FirebaseAuth.getInstance();

        editCorreo = findViewById(R.id.editCorreo);
        editPassword = findViewById(R.id.editPassword);
        botonLogin = findViewById(R.id.botonLogin);
        botonRegistro = findViewById(R.id.botonRegistro);
        barraProgreso = findViewById(R.id.barraProgreso);

        // bot iniciar sesion
        botonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciarSesion();
            }
        });

        // clic del texto de Registro
        botonRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ir a pantalla de registro
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void iniciarSesion() {
        String correo = editCorreo.getText().toString().trim();
        String password = editPassword.getText().toString().trim();

        // validacion: que no esten vacios
        if (correo.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Por favor, completá todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        // evita multiples clics
        barraProgreso.setVisibility(View.VISIBLE);
        botonLogin.setEnabled(false);

        // conecta con Firebase
        mAuth.signInWithEmailAndPassword(correo, password)
                .addOnCompleteListener(this, task -> {
                    barraProgreso.setVisibility(View.GONE);
                    botonLogin.setEnabled(true);

                    if (task.isSuccessful()) {
                        // login exitoso
                        FirebaseUser user = mAuth.getCurrentUser();
                        Toast.makeText(LoginActivity.this, "¡Bienvenida!", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        // error en login (contraseña mal, usuario no existe, etc.)
                        Toast.makeText(LoginActivity.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }
}