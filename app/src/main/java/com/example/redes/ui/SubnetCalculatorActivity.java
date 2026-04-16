package com.example.redes.ui;

import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.redes.databinding.ActivitySubnetCalculatorBinding;
import com.example.redes.utils.NetworkCalculator;

public class SubnetCalculatorActivity extends AppCompatActivity {

    private ActivitySubnetCalculatorBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivitySubnetCalculatorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Lógica de cálculo con validación visual (Avisos rojos)
        binding.btnCalcularRes.setOnClickListener(v -> {
            // 1. Limpiamos errores anteriores para que no se queden pegados
            binding.tilIpAddress.setError(null);
            binding.tilCidr.setError(null);

            String ipStr = binding.etIpAddress.getText().toString().trim();
            String cidrStr = binding.etCidr.getText().toString().trim();

            // 2. Si la IP está vacía, mostramos el error rojo en el campo
            if (ipStr.isEmpty()) {
                binding.tilIpAddress.setError("La dirección IP es obligatoria");
                binding.etIpAddress.requestFocus();
                return;
            }

            // 3. Si el CIDR está vacío, mostramos el error rojo
            if (cidrStr.isEmpty()) {
                binding.tilCidr.setError("Ingresa el prefijo (ej: 24)");
                binding.etCidr.requestFocus();
                return;
            }

            try {
                int cidr = Integer.parseInt(cidrStr);

                // 4. Validación de rango (0 a 32)
                if (cidr < 0 || cidr > 32) {
                    binding.tilCidr.setError("Debe ser entre 0 y 32");
                    binding.etCidr.requestFocus();
                    return;
                }

                // Si todo está OK, calculamos el resultado
                String resultado = NetworkCalculator.calcularSubred(ipStr, cidr);
                binding.tvResultados.setText(resultado);

            } catch (NumberFormatException e) {
                binding.tilCidr.setError("Solo se permiten números");
                binding.etCidr.requestFocus();
            }
        });

        // Botón volver
        binding.btnVolverCalc.setOnClickListener(v -> {
            finish();
        });
    }
}