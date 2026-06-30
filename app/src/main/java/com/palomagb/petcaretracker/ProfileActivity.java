package com.palomagb.petcaretracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity {

    private TextView textNombreUsuario;
    private TextView textEmailUsuario;
    private Button botonCerrarSesion;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // inicializar Firebase
        mAuth = FirebaseAuth.getInstance();

        textNombreUsuario = findViewById(R.id.textNombreUsuario);
        textEmailUsuario = findViewById(R.id.textEmailUsuario);
        botonCerrarSesion = findViewById(R.id.botonCerrarSesion);

        // se obtiene usuario actual y carga datos
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String nombre = currentUser.getDisplayName();
            String email = currentUser.getEmail();

            if (nombre != null && !nombre.isEmpty()) {
                textNombreUsuario.setText(getString(R.string.welcome_user, nombre));
            } else {
                textNombreUsuario.setText("¡Hola!");
            }
            textEmailUsuario.setText(email);
        }

        // botón de cerrar sesión
        botonCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }
}