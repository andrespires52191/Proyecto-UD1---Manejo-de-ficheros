package org.example.repos;

import org.example.modelos.Usuario;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class RepoUsuarios {
    static File fichero = new File("ficheros/usuarios.dat");
    static List<Usuario> usuarios = new ArrayList<>();

    public boolean cargarFichero() {
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

    public boolean guardarFichero() {
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

    public List<Usuario> getLista() {
        return usuarios;
    }

    public int getLastId() {
        try {
            return getLista().getLast().getUsuarioId();
        } catch (NoSuchElementException e) {
            return -1;
        }
    }

    public void add(Usuario usuario) {
        getLista().add(usuario);
    }

    public void remove(Usuario usuario) {
        getLista().remove(usuario);
    }

    public Usuario buscarPorDni(String dni) {
        for (Usuario u : getLista()) {
            if (u.getDni().equalsIgnoreCase(dni)) {
                return u;
            }
        }
        return null;
    }
}
