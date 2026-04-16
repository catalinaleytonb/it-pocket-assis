package com.example.redes.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.redes.databinding.ActivityProfileBinding;
import com.example.redes.R;

public class ProfileActivity extends AppCompatActivity {

    private ActivityProfileBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 1. Inflamos el diseño con ViewBinding
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 2. Configuración del botón de LinkedIn con tu perfil real
        binding.btnLinkedin.setOnClickListener(v -> {
            // Tu URL personalizada
            String url = "https://www.linkedin.com/in/catalina-leyton-bascu%C3%B1an-605b54288/?skipRedirect=true";

            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        });

        // 3. Botón para cerrar la actividad y regresar al menú principal
        binding.btnVolverPerfil.setOnClickListener(v -> {
            finish();
        });
    }
}