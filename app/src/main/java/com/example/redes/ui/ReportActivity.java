package com.example.redes.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.redes.databinding.ActivityReportBinding;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ReportActivity extends AppCompatActivity {

    private ActivityReportBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityReportBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // --- BOTÓN GUARDAR (Archivo Permanente) ---
        binding.btnGuardarReporte.setOnClickListener(v -> {
            if (validarCampos()) {
                guardarArchivoLocal();
            }
        });

        // --- BOTÓN COMPARTIR (Archivo Temporal + Intent) ---
        binding.btnCompartir.setOnClickListener(v -> {
            if (validarCampos()) {
                compartirReporte();
            }
        });

        binding.btnVolver.setOnClickListener(v -> finish());
    }

    private boolean validarCampos() {
        if (binding.etEquipo.getText().toString().trim().isEmpty() ||
                binding.etDiagnostico.getText().toString().trim().isEmpty() ||
                binding.etAccion.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "⚠️ Por favor, llena todos los datos", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private String obtenerCuerpoReporte() {
        String fecha = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(new Date());
        return "📋 REPORTE TÉCNICO IT\n" +
                "━━━━━━━━━━━━━━━━━━━━\n" +
                "🕒 Fecha: " + fecha + "\n" +
                "💻 Equipo: " + binding.etEquipo.getText().toString().trim() + "\n" +
                "🚨 Falla: " + binding.etDiagnostico.getText().toString().trim() + "\n" +
                "🛠️ Acción: " + binding.etAccion.getText().toString().trim() + "\n" +
                "━━━━━━━━━━━━━━━━━━━━\n" +
                "Técnico: Catalina Leyton\n" +
                "Generado con IT Pocket Tool";
    }

    private void guardarArchivoLocal() {
        String fechaFichero = new SimpleDateFormat("yyyyMMdd_HHmm", Locale.getDefault()).format(new Date());
        String nombreFichero = "Reporte_" + fechaFichero + ".txt";

        try {
            // Guardamos en getFilesDir() (Memoria interna permanente de la app)
            File file = new File(getFilesDir(), nombreFichero);
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(obtenerCuerpoReporte().getBytes());
            fos.close();

            Toast.makeText(this, "💾 Guardado en: " + file.getName(), Toast.LENGTH_LONG).show();
            limpiarCampos();
        } catch (Exception e) {
            Toast.makeText(this, "❌ Error al guardar: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void compartirReporte() {
        try {
            // Creamos archivo temporal en Caché para compartir
            File cachePath = new File(getCacheDir(), "reportes");
            cachePath.mkdirs();
            File tempFile = new File(cachePath, "reporte_it.txt");

            FileOutputStream stream = new FileOutputStream(tempFile);
            stream.write(obtenerCuerpoReporte().getBytes());
            stream.close();

            // URI Segura
            Uri contentUri = FileProvider.getUriForFile(this, "com.example.redes.fileprovider", tempFile);

            if (contentUri != null) {
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                shareIntent.setDataAndType(contentUri, getContentResolver().getType(contentUri));
                shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri);
                shareIntent.setType("text/plain");
                startActivity(Intent.createChooser(shareIntent, "Enviar reporte vía:"));
            }
        } catch (Exception e) {
            Toast.makeText(this, "❌ Error al compartir: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void limpiarCampos() {
        binding.etEquipo.setText("");
        binding.etDiagnostico.setText("");
        binding.etAccion.setText("");
        binding.etEquipo.requestFocus();
    }
}