package com.palomagb.petcaretracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SetupPetActivity extends AppCompatActivity {

    private EditText editNombre, editEspecie, editEdad, editPeso;
    private Button botonGuardar;
    private ProgressBar barraProgreso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_pet);

        editNombre = findViewById(R.id.editNombreMascota);
        editEspecie = findViewById(R.id.editEspecie);
        editEdad = findViewById(R.id.editEdad);
        editPeso = findViewById(R.id.editPeso);
        botonGuardar = findViewById(R.id.botonGuardarMascota);
        barraProgreso = findViewById(R.id.barraProgresoSetup);

        botonGuardar.setOnClickListener(v -> guardarDatosMascota());
    }

    private void guardarDatosMascota() {
        String nombre = editNombre.getText().toString().trim();
        String especie = editEspecie.getText().toString().trim();
        String edad = editEdad.getText().toString().trim();
        String peso = editPeso.getText().toString().trim();

        if (nombre.isEmpty() || especie.isEmpty() || edad.isEmpty() || peso.isEmpty()) {
            Toast.makeText(this, "Completá todos los datos de tu mascota", Toast.LENGTH_SHORT).show();
            return;
        }

        barraProgreso.setVisibility(View.VISIBLE);
        botonGuardar.setEnabled(false);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        if (user != null) {
            // datos de la mascota
            Map<String, Object> mascota = new HashMap<>();
            mascota.put("nombre", nombre);
            mascota.put("especie", especie);
            mascota.put("edad", edad);
            mascota.put("peso", peso);

            db.collection("Mascotas").document(user.getUid())
                    .set(mascota)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(SetupPetActivity.this, "¡Mascota registrada!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SetupPetActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    })
                    .addOnFailureListener(e -> {
                        barraProgreso.setVisibility(View.GONE);
                        botonGuardar.setEnabled(true);
                        Toast.makeText(SetupPetActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        }
    }
}