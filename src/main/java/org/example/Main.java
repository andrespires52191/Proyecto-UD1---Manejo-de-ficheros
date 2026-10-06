package org.example;

import org.example.repos.RepoLibros;
import org.example.repos.RepoPrestamos;
import org.example.repos.RepoUsuarios;

import java.io.File;

import static org.example.EntradaDatos.preguntar;

public class Main {
    public static void main(String[] args) {
        System.out.println("Gestión de Biblioteca\n" +
                "---------------------");

        // Asegurarse de que la carpeta ficheros esta creada para poder usarla luego.
        File carpeta = new File("ficheros");
        carpeta.mkdirs();
        // Queremos que exista, da igual si estaba creada
        // o ha sido creada en la instrucción de arriba.
        if (!carpeta.exists()) {
            System.out.println("Error preparando inicio.");
            return;
        }

        // iniciar repositorios
        RepoUsuarios repoUsuarios = new RepoUsuarios();
        if (!repoUsuarios.cargarFichero()) {
            System.out.println("Error: Fallo al cargar usuarios.");
            return;
        }

        RepoLibros repoLibros = new RepoLibros();
        if (!repoLibros.cargarFichero()) {
            System.out.println("Error: Fallo al cargar libros.");
            return;
        }

        RepoPrestamos repoPrestamos = new RepoPrestamos();
        if (!repoPrestamos.cargarFichero()) {
            System.out.println("Error: Fallo al cargar préstamos.");
            return;
        }

        // iniciar servicios
        GestionUsuarios gestionUsuarios = new GestionUsuarios(repoUsuarios);
        GestionLibros gestionLibros = new GestionLibros(repoLibros);
        GestionPrestamos gestionPrestamos = new GestionPrestamos(repoPrestamos,
                repoUsuarios,
                repoLibros);

        // mostrar menus
        menuPrincipal(gestionUsuarios, gestionLibros, gestionPrestamos);

        System.out.println("Programa terminado.");
    }

    private static void menuPrincipal(GestionUsuarios gestionUsuarios,
                                      GestionLibros gestionLibros,
                                      GestionPrestamos gestionPrestamos) {
        final String MENU_PRINCIPAL = "Acciones:\n" +
                "1) Gestionar Usuarios\n" +
                "2) Gestionar Libros\n" +
                "3) Gestionar Préstamos\n" +
                "4) Salir";

        // Bucle de selección de acciones de menú.
        int opcion = -1;
        while (opcion != 4) {
            try {
                opcion = Integer.parseInt(preguntar("\n" + MENU_PRINCIPAL + "\n\nOpción: "));
            } catch (NumberFormatException e) {
                opcion = -1;
            }

            switch (opcion) {
                case 1:
                    gestionUsuarios.mostrarMenu();
                    break;
                case 2:
                    gestionLibros.mostrarMenu();
                    break;
                case 3:
                    gestionPrestamos.mostrarMenu();
                    break;
                case 4:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Error. Intenta de nuevo.");
            }
        }
    }
}
