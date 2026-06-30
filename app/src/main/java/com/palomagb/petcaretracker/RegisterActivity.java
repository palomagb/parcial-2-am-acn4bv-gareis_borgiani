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

public class RegisterActivity extends AppCompatActivity {

    // variables
    private EditText editNombre, editCorreo, editPassword;
    private Button botonRegistrar;
    private TextView textVolverLogin;
    private ProgressBar barraProgreso;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // inicializacion Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        editNombre = findViewById(R.id.editNombre);
        editCorreo = findViewById(R.id.editCorreoReg);
        editPassword = findViewById(R.id.editPasswordReg);
        botonRegistrar = findViewById(R.id.botonRegistrar);
        textVolverLogin = findViewById(R.id.textVolverLogin);
        barraProgreso = findViewById(R.id.barraProgresoReg);

        // clic de Registro
        botonRegistrar.setOnClickListener(v -> registrarUsuario());

        textVolverLogin.setOnClickListener(v -> {
            // vuelve al login
            finish();
        });
    }

    private void registrarUsuario() {
        String nombre = editNombre.getText().toString().trim();
        String correo = editCorreo.getText().toString().trim();
        String password = editPassword.getText().toString().trim();

        // validacion: campos vacios
        if (nombre.isEmpty() || correo.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Por favor, completá todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        //validacion: contraseñas de al menos 6 caracteres
        if (password.length() < 6) {
            Toast.makeText(this, "La contraseña debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show();
            return;
        }

        barraProgreso.setVisibility(View.VISIBLE);
        botonRegistrar.setEnabled(false);

        // conecta con Firebase para crear cuenta
        mAuth.createUserWithEmailAndPassword(correo, password)
                .addOnCompleteListener(this, task -> {
                    barraProgreso.setVisibility(View.GONE);
                    botonRegistrar.setEnabled(true);

                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();

                        if (user != null) {
                            com.google.firebase.auth.UserProfileChangeRequest profileUpdates =
                                    new com.google.firebase.auth.UserProfileChangeRequest.Builder()
                                            .setDisplayName(nombre) // Acá le pasamos el nombre que escribió
                                            .build();

                            user.updateProfile(profileUpdates).addOnCompleteListener(taskUpdate -> {
                                Toast.makeText(RegisterActivity.this, "¡Cuenta creada con éxito!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            });
                        }
                    } else {
                        Toast.makeText(RegisterActivity.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }
}