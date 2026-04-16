package com.example.redes;

import static org.junit.Assert.*;
import org.junit.Test;
import com.example.redes.utils.NetworkCalculator;

public class NetworkCalculatorTest {

    @Test
    public void calcularSubred_esCorrectoParaClaseC() {
        // 1. Preparación: IP y prefijo típicos de casa
        String ip = "192.168.1.0";
        int cidr = 24;

        // 2. Ejecución: Llamamos a tu lógica
        String resultado = NetworkCalculator.calcularSubred(ip, cidr);

        // 3. Verificación: Comprobamos que la máscara sea la correcta
        // Si tu método devuelve un String largo, revisamos que contenga la máscara
        assertTrue("La máscara debería ser 255.255.255.0",
                resultado.contains("255.255.255.0"));
    }

    @Test
    public void calcularSubred_detectaPrefijoInvalido() {
        String ip = "10.0.0.1";
        int cidr = 35; // Esto es imposible en IPv4

        String resultado = NetworkCalculator.calcularSubred(ip, cidr);

        // Aquí verificamos que tu código maneje el error o devuelva un aviso
        assertNotNull(resultado);
    }
}