package org.example;

import org.example.modelos.Usuario;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static org.example.EntradaDatos.preguntar;

public class GestionUsuarios {
    static File fichero = new File("ficheros/usuarios.dat");
    static List<Usuario> usuarios = new ArrayList<>();

    public GestionUsuarios() {
        final String MENU_USUARIO = "Acciones:\n" +
                "1) Ver Usuarios\n" +
                "2) Crear Usuario\n" +
                "3) Editar Usuario\n" +
                "4) Eliminar Usuario\n" +
                "5) Menu Principal";

        if (!cargarFicheroUsuarios()) {
            System.out.println("Error: Fallo al cargar usuarios.");
            return;
        }

        // Bucle de selección de acciones de menú.
        int opcion = -1;
        while (opcion != 5) {
            try {
                opcion = Integer.parseInt(preguntar("\n" + MENU_USUARIO + "\n\nOpción: "));
            } catch (NumberFormatException e) {
                opcion = -1;
            }

            switch (opcion) {
                case 1:
                    verUsuarios();
                    break;
                case 2:
                    cearUsuario();
                    break;
                case 3:
                    editarUsuario();
                    break;
                case 4:
                    eliminarUsuario();
                    break;
                case 5:
                    // condición de salida del bucle
                    break;
                default:
                    System.out.println("Error. Intenta de nuevo.");
            }
        }
    }

    private boolean cargarFicheroUsuarios() {
        if (!fichero.exists()) {
            // nada que cargar
            return true;
        }

        try {
            // abrir archivo
            FileInputStream fis = new FileInputStream(fichero);
            // preparar serializador de lectura
            ObjectInputStream ois = new ObjectInputStream(fis);

            while (true) {
                try {
                    // leer usuarios uno a uno
                    Usuario usuario = (Usuario) ois.readObject();
                    // poner en memoria
                    usuarios.add(usuario);
                } catch (EOFException e) {
                    // fin de archivo
                    break;
                }
            }

            // cerrar archivo
            ois.close();

            // exito
            return true;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }

        // fallo
        return false;
    }

    private boolean guardarFicheroUsuarios() {
        try {
            // abrir archivo
            FileOutputStream fos = new FileOutputStream(fichero);
            // preparar serializador de escritura
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            for (Usuario u : usuarios) {
                // guardar usuarios uno a uno
                oos.writeObject(u);
            }

            // cerrar archivo
            oos.close();

            // exito
            return true;
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }

        // fallo
        return false;
    }

    private Usuario buscarPorDni(String dni) {
        for (Usuario u : usuarios) {
            System.out.println(u.getDni());
            if (u.getDni().equalsIgnoreCase(dni)) {
                return u;
            }
        }
        return null;
    }

    private void verUsuarios() {
        System.out.println("Resultados:");
        for (Usuario u : usuarios) {
            System.out.println(" - " + u);
        }
    }

    private void cearUsuario() {
        // pedir información de usuario
        String dni = preguntar("DNI: ");

        Usuario usuarioExistente = buscarPorDni(dni);
        if (usuarioExistente != null) {
            System.out.println("Error: El usuario ya existe.");
            return;
        }

        String nombre = preguntar("Nombre: ");

        int edad;
        try {
            edad = Integer.parseInt(preguntar("Edad: "));
        } catch (NumberFormatException e) {
            System.out.println("Error: " + e.getMessage());
            return;
        }

        // añadir en memoria
        usuarios.add(new Usuario(edad, dni, nombre));

        // volcar memoria a archivo
        guardarFicheroUsuarios();
    }

    private void editarUsuario() {
        // pedir información de usuario
        String dni = preguntar("DNI: ");

        Usuario usuarioExistente = buscarPorDni(dni);
        if (usuarioExistente == null) {
            System.out.println("Error: El usuario no existe.");
            return;
        }

        String nombre = preguntar("Nombre: ");

        int edad;
        try {
            edad = Integer.parseInt(preguntar("Edad: "));
        } catch (NumberFormatException e) {
            System.out.println("Error: " + e.getMessage());
            return;
        }

        // actualizar en memoria
        usuarioExistente.setNombre(nombre);
        usuarioExistente.setEdad(edad);

        // volcar memoria a archivo
        if (guardarFicheroUsuarios()) {
            System.out.println("Usuario editado correctamente.");
        }
    }

    private void eliminarUsuario() {
        // pedir información de usuario
        String dni = preguntar("DNI: ");

        Usuario usuarioExistente = buscarPorDni(dni);
        if (usuarioExistente == null) {
            System.out.println("Error: El usuario no existe.");
            return;
        }

        // borrar en memoria
        usuarios.remove(usuarioExistente);

        // volcar memoria a archivo
        if (guardarFicheroUsuarios()) {
            System.out.println("Usuario eliminado correctamente.");
        }
    }
}
