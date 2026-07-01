package com.palomagb.petcaretracker;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {

    private LinearLayout containerHistorial;
    private TextView tvNombreMascota, tvDisplayEdad, tvDisplayPeso, tvDisplayEspecie;
    private Button btnComida, btnAgua, btnPaseo, btnJuego, btnHigiene, btnMedicacion;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // menú
        Toolbar toolbar = findViewById(R.id.toolbar_main);
        toolbar.inflateMenu(R.menu.menu_main);

        toolbar.setOnMenuItemClickListener(item -> {
            int id = item.getItemId();

            if (id == R.id.action_logout) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                return true;

            } else if (id == R.id.action_perfil) {
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(intent);
                return true;
            }
            return false;
        });

        containerHistorial = findViewById(R.id.container_historial);

        btnComida = findViewById(R.id.btn_comida);
        btnAgua = findViewById(R.id.btn_agua);
        btnPaseo = findViewById(R.id.btn_paseo);
        btnJuego = findViewById(R.id.btn_juego);
        btnHigiene = findViewById(R.id.btn_higiene);
        btnMedicacion = findViewById(R.id.btn_medicacion);

        tvNombreMascota = findViewById(R.id.tv_nombre_mascota);
        tvDisplayEdad = findViewById(R.id.tv_display_edad);
        tvDisplayPeso = findViewById(R.id.tv_display_peso);
        tvDisplayEspecie = findViewById(R.id.tv_display_especie);

        findViewById(R.id.fab_edit_profile).setOnClickListener(v -> mostrarDialogoEdicion());

        configurarBotones();
        cargarDatos();
        cargarDatosDeFirebase();

        ScrollView mainScroll = findViewById(R.id.main_scroll);
        ScrollView historyScroll = findViewById(R.id.scroll_historial);

        ImageView ivFoto = findViewById(R.id.iv_foto_mascota);
        ivFoto.setClipToOutline(true);

        historyScroll.setOnTouchListener((v, event) -> {
            if (event.getAction() == android.view.MotionEvent.ACTION_DOWN) {
                mainScroll.requestDisallowInterceptTouchEvent(true);
            }
            v.performClick();
            return false;
        });
    }

    private void configurarBotones() {
        btnComida.setOnClickListener(v -> registrarActividad(getString(R.string.log_comida)));
        btnAgua.setOnClickListener(v -> registrarActividad(getString(R.string.log_agua)));
        btnPaseo.setOnClickListener(v -> registrarActividad(getString(R.string.log_paseo)));
        btnJuego.setOnClickListener(v -> registrarActividad(getString(R.string.log_juego)));
        btnHigiene.setOnClickListener(v -> registrarActividad(getString(R.string.log_higiene)));
        btnMedicacion.setOnClickListener(v -> registrarActividad(getString(R.string.log_medicacion)));
    }

    private void registrarActividad(String actividad) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        sdf.setTimeZone(TimeZone.getTimeZone("America/Argentina/Cordoba"));
        String horaActual = sdf.format(new Date());

        if (containerHistorial.getChildCount() == 1) {
            TextView vistaHija = (TextView) containerHistorial.getChildAt(0);
            if (vistaHija.getText().toString().equals(getString(R.string.msg_empty_history))) {
                containerHistorial.removeAllViews();
            }
        }

        TextView nuevoItem = new TextView(this);
        nuevoItem.setText(getString(R.string.log_entry_format, horaActual, actividad));
        nuevoItem.setTextColor(androidx.core.content.ContextCompat.getColor(this, R.color.black));

        float textSizePx = getResources().getDimension(R.dimen.text_size_history);
        float density = getResources().getDisplayMetrics().density;
        nuevoItem.setTextSize(textSizePx / density);

        nuevoItem.setPadding(0, 8, 0, 8);
        containerHistorial.addView(nuevoItem, 0);

        guardarDatos();
    }

    private void guardarDatos() {
        // obtiene usuario
        com.google.firebase.auth.FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String prefsName = (user != null) ? "PetCarePrefs_" + user.getUid() : "PetCarePrefs";

        SharedPreferences preferences = getSharedPreferences(prefsName, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < containerHistorial.getChildCount(); i++) {
            TextView tv = (TextView) containerHistorial.getChildAt(i);
            String texto = tv.getText().toString();

            if (!texto.equals(getString(R.string.msg_empty_history))) {
                sb.append(texto);
                if (i < containerHistorial.getChildCount() - 1) sb.append("\n");
            }
        }

        editor.putString("historial", sb.toString());
        editor.putString("edad", tvDisplayEdad.getText().toString());
        editor.putString("peso", tvDisplayPeso.getText().toString());
        editor.apply();
    }

    private void cargarDatos() {
        // obtiene usuario
        com.google.firebase.auth.FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String prefsName = (user != null) ? "PetCarePrefs_" + user.getUid() : "PetCarePrefs";

        SharedPreferences preferences = getSharedPreferences(prefsName, MODE_PRIVATE);

        tvNombreMascota.setText(getString(R.string.pet_name_default));
        tvDisplayEdad.setText(preferences.getString("edad", getString(R.string.label_edad)));
        tvDisplayPeso.setText(preferences.getString("peso", getString(R.string.label_peso)));

        String historialGuardado = preferences.getString("historial", "");
        containerHistorial.removeAllViews();

        if (historialGuardado.isEmpty()) {
            TextView tvVacio = new TextView(this);
            tvVacio.setText(getString(R.string.msg_empty_history));
            tvVacio.setTextColor(androidx.core.content.ContextCompat.getColor(this, R.color.black));
            float textSizePx = getResources().getDimension(R.dimen.text_size_history);
            tvVacio.setTextSize(textSizePx / getResources().getDisplayMetrics().density);
            containerHistorial.addView(tvVacio);
        } else {
            String[] registros = historialGuardado.split("\n");
            for (String registro : registros) {
                if (!registro.trim().isEmpty()) {
                    TextView tv = new TextView(this);
                    tv.setText(registro);
                    tv.setTextColor(androidx.core.content.ContextCompat.getColor(this, R.color.black));
                    float textSizePx = getResources().getDimension(R.dimen.text_size_history);
                    tv.setTextSize(textSizePx / getResources().getDisplayMetrics().density);
                    tv.setPadding(0, 8, 0, 8);
                    containerHistorial.addView(tv);
                }
            }
        }
    }

    private void mostrarDialogoEdicion() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        String titulo = String.format(getString(R.string.dialog_edit_title), getString(R.string.pet_name_default));
        builder.setTitle(titulo);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        int padding = (int) getResources().getDimension(R.dimen.padding_inner_data);
        layout.setPadding(padding, padding / 2, padding, padding / 4);

        final EditText inputEdad = new EditText(this);
        inputEdad.setHint(getString(R.string.hint_edad));
        inputEdad.setInputType(InputType.TYPE_CLASS_NUMBER);
        String edadActual = tvDisplayEdad.getText().toString().replaceAll("[^0-9]", "");
        inputEdad.setText(edadActual);
        layout.addView(inputEdad);

        final EditText inputPeso = new EditText(this);
        inputPeso.setHint(getString(R.string.hint_peso));
        inputPeso.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        String pesoActual = tvDisplayPeso.getText().toString().replace("Peso: ", "").replace(" kg", "");
        inputPeso.setText(pesoActual);
        layout.addView(inputPeso);

        builder.setView(layout);

        builder.setPositiveButton(getString(R.string.btn_save), (dialog, which) -> {
            String nuevaEdad = inputEdad.getText().toString();
            String nuevoPeso = inputPeso.getText().toString();

            if (!nuevaEdad.isEmpty()) {
                tvDisplayEdad.setText(getString(R.string.display_edad_format, nuevaEdad));
            }
            if (!nuevoPeso.isEmpty()) {
                tvDisplayPeso.setText(getString(R.string.display_peso_format, nuevoPeso));
            }
            guardarDatos();
        });

        builder.setNegativeButton(getString(R.string.btn_cancel), null);
        builder.show();
    }

    private void cargarDatosDeFirebase() {
        com.google.firebase.auth.FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            com.google.firebase.firestore.FirebaseFirestore db = com.google.firebase.firestore.FirebaseFirestore.getInstance();

            db.collection("Mascotas").document(user.getUid()).get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful() && task.getResult() != null && task.getResult().exists()) {
                            String nombre = task.getResult().getString("nombre");
                            String especie = task.getResult().getString("especie");
                            String edad = task.getResult().getString("edad");
                            String peso = task.getResult().getString("peso");

                            if (nombre != null) tvNombreMascota.setText(nombre);
                            if (especie != null) tvDisplayEspecie.setText("Especie: " + especie);
                            if (edad != null) tvDisplayEdad.setText("Edad: " + edad + " años");
                            if (peso != null) tvDisplayPeso.setText("Peso: " + peso + " kg");

                            guardarDatos();
                        }
                    });
        }
    }
}