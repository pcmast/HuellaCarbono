package org.example.huelladecarbono.utils;

public class Utilidades {

    //Metodo que verifica si una cadena es un correo electronico
    public static boolean esCorreoElectronicoValido(String correo) {
        if (correo == null || correo.isEmpty()) {
            return false;
        }
        String patronCorreo = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return correo.matches(patronCorreo);
    }


}
