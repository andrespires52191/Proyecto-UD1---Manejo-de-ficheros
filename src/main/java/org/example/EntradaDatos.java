package org.example;

import java.util.Scanner;

public class EntradaDatos {
    // lector de terminal
    static Scanner scanner = new Scanner(System.in);

    public static String preguntar(String pregunta) {
        // petición al usuario
        System.out.print(pregunta);
        // respuesta del usuario
        return scanner.nextLine();
    }
}
