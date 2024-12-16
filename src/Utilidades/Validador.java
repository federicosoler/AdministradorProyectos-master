package Utilidades;

public class Validador {
    public static boolean esNumeroValido(String valor, String campo) {
        try {
            Double.parseDouble(valor);
            return true;
        } catch (NumberFormatException e) {
            System.err.println("El campo " + campo + " debe ser un número válido.");
            return false;
        }
    }

    public static boolean esEnteroValido(String valor, String campo) {
        try {
            Integer.parseInt(valor);
            return true;
        } catch (NumberFormatException e) {
            System.err.println("El campo " + campo + " debe ser un número entero válido.");
            return false;
        }
    }
}

