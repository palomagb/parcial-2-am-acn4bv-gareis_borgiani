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

        //menú
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar_profile);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("");
        }

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


        android.widget.TextView tvLabelActividades = findViewById(R.id.tv_label_actividades);

        if (currentUser != null) {
            com.google.firebase.firestore.FirebaseFirestore db = com.google.firebase.firestore.FirebaseFirestore.getInstance();
            db.collection("Mascotas").document(currentUser.getUid()).get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful() && task.getResult() != null && task.getResult().exists()) {
                            String nombreMascota = task.getResult().getString("nombre");
                            if (nombreMascota != null && tvLabelActividades != null) {
                                tvLabelActividades.setText("Actividades de " + nombreMascota + ":");
                            }
                        } else {
                            if (tvLabelActividades != null) {
                                tvLabelActividades.setText("Actividades de Mascota:");
                            }
                        }
                    });
        }

        android.widget.TextView tvContadorActividades = findViewById(R.id.tv_contador_actividades);

        if (currentUser != null) {
            String prefsName = "PetCarePrefs_" + currentUser.getUid();
            android.content.SharedPreferences prefsActividades = getSharedPreferences(prefsName, MODE_PRIVATE);

            String historialGuardado = prefsActividades.getString("historial", "");
            int cantidadActividades = 0;

            if (!historialGuardado.isEmpty()) {
                String[] registros = historialGuardado.split("\n");
                cantidadActividades = registros.length;
            }

            if (tvContadorActividades != null) {
                tvContadorActividades.setText(String.valueOf(cantidadActividades));
            }
        }


        android.widget.Switch switchNotificaciones = findViewById(R.id.switch_notificaciones);
        android.widget.Switch switchTips = findViewById(R.id.switch_tips);

        switchNotificaciones.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                android.widget.Toast.makeText(ProfileActivity.this, "Recordatorios de hidratación activados", android.widget.Toast.LENGTH_SHORT).show();
            } else {
                android.widget.Toast.makeText(ProfileActivity.this, "Recordatorios de hidratación desactivados", android.widget.Toast.LENGTH_SHORT).show();
            }
        });

        switchTips.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                android.widget.Toast.makeText(ProfileActivity.this, "Tips diarios activados", android.widget.Toast.LENGTH_SHORT).show();
            } else {
                android.widget.Toast.makeText(ProfileActivity.this, "Tips diarios desactivados", android.widget.Toast.LENGTH_SHORT).show();
            }
        });

        android.widget.Switch switchAlimentacion = findViewById(R.id.switch_alimentacion);
        android.widget.Switch switchMedicacion = findViewById(R.id.switch_medicacion);


        switchAlimentacion.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                android.widget.Toast.makeText(ProfileActivity.this, "Recordatorios de alimentación activados", android.widget.Toast.LENGTH_SHORT).show();
            } else {
                android.widget.Toast.makeText(ProfileActivity.this, "Recordatorios de alimentación desactivados", android.widget.Toast.LENGTH_SHORT).show();
            }
        });

        switchMedicacion.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                android.widget.Toast.makeText(ProfileActivity.this, "Recordatorios de medicación activados", android.widget.Toast.LENGTH_SHORT).show();
            } else {
                android.widget.Toast.makeText(ProfileActivity.this, "Recordatorios de medicación desactivados", android.widget.Toast.LENGTH_SHORT).show();
            }
        });

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

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        int id = item.getItemId();

        // flecha para volver
        if (id == android.R.id.home) {
            finish();
            return true;
        }

        if (id == R.id.action_mi_mascota) {
            Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            return true;
        }

        if (id == R.id.action_logout) {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            return true;
        }

        if (id == R.id.action_perfil) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}