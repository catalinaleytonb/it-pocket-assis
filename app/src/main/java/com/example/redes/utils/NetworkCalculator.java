package com.example.redes.utils;

public class NetworkCalculator {

    public static String calcularSubred(String ip, int cidr) {
        try {
            // Dividimos la IP en sus 4 octetos
            String[] ipParts = ip.split("\\.");
            if (ipParts.length != 4) return "Error: Formato de IP inválido";

            // Convertimos la IP a un número entero de 32 bits
            int ipInt = 0;
            for (int i = 0; i < 4; i++) {
                ipInt |= (Integer.parseInt(ipParts[i]) << (24 - (8 * i)));
            }

            // Calculamos la máscara en base al CIDR
            int mask = 0xffffffff << (32 - cidr);

            // Calculamos Network ID y Broadcast
            int networkId = ipInt & mask;
            int broadcastId = networkId | ~mask;

            // Rango de IPs usables
            int usableMin = networkId + 1;
            int usableMax = broadcastId - 1;

            // Si el CIDR es 32 o 31, la lógica de usables cambia, sino es max - min + 1
            int totalUsable = (cidr >= 31) ? 0 : usableMax - usableMin + 1;

            // Formateamos todo de vuelta a String
            return "Máscara de Subred: " + intToIp(mask) + "\n" +
                    "ID de Red: " + intToIp(networkId) + "\n" +
                    "IP Broadcast: " + intToIp(broadcastId) + "\n\n" +
                    "Primera IP Usable: " + intToIp(usableMin) + "\n" +
                    "Última IP Usable: " + intToIp(usableMax) + "\n" +
                    "Total de Hosts Usables: " + totalUsable;

        } catch (Exception e) {
            return "Error: Verifica que los datos sean números válidos.";
        }
    }

    // Método auxiliar para convertir el número entero de vuelta a formato IP (x.x.x.x)
    private static String intToIp(int ipInt) {
        return ((ipInt >> 24) & 0xFF) + "." +
                ((ipInt >> 16) & 0xFF) + "." +
                ((ipInt >> 8) & 0xFF) + "." +
                (ipInt & 0xFF);
    }
}