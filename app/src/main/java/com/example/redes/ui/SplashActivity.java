package com.example.redes.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import androidx.appcompat.app.AppCompatActivity;

// AGREGA ESTA LÍNEA EXACTAMENTE ASÍ:
import com.example.redes.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Ocultar la barra superior para que sea pantalla completa
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // Timer de 2 segundos (2000 milisegundos)
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            // Iniciar MainActivity
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);

            // Cerramos SplashActivity para que el usuario no pueda volver atrás con el botón del cel
            finish();
        }, 2000);
    }
}