package org.example.repos;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.security.AnyTypePermission;
import org.example.modelos.Libro;
import org.example.modelos.ListaLibros;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

public class RepoLibros {
    static XStream xstream = new XStream();
    static File fichero = new File("ficheros/libros.xml");
    static ListaLibros libros = new ListaLibros();

    public boolean cargarFichero() {
        if (!fichero.exists()) {
            // nada que cargar
            return true;
        }

        try {
            // cambiar de nombre a las etiquetas XML
            xstream.alias("ListaLibros", ListaLibros.class);
            xstream.alias("DatosLibro", Libro.class);

            // colección por defecto
            xstream.addImplicitCollection(ListaLibros.class, "lista");

            // cargar fichero a memoria
            // necesita permisos para no dar error
            xstream.addPermission(AnyTypePermission.ANY);
            libros = (ListaLibros) xstream.fromXML(new FileInputStream(fichero));

            // exito
            return true;
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
        // fallo
        return false;
    }

    public boolean guardarFichero() {
        try {
            // cambiar de nombre a las etiquetas XML
            xstream.alias("ListaLibros", ListaLibros.class);
            xstream.alias("DatosLibro", Libro.class);

            // colección por defecto
            xstream.addImplicitCollection(ListaLibros.class, "lista");

            // guardar
            xstream.toXML(libros, new FileOutputStream(fichero));

            // exito
            return true;
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
        // fallo
        return false;
    }

    public List<Libro> getLista() {
        return libros.getLista();
    }

    public int getLastId() {
        try {
            return getLista().getLast().getLibroId();
        } catch (NoSuchElementException e) {
            return -1;
        }
    }

    public boolean add(Libro libro) {
        return getLista().add(libro);
    }

    public boolean remove(Libro libro) {
        return getLista().remove(libro);
    }

    public Libro buscarPorId(int libroId) {
        List<Libro> lista = getLista();
        for (int i = 0; i < lista.size(); i++) {
            Libro libro = lista.get(i);
            if (libro.getLibroId() == libroId) {
                return libro;
            }
        }
        return null;
    }

    public Libro buscarLibro(Libro libro) {
        for (Libro l : getLista()) {
            // buscar primera coincidencia
            if (l.getTitulo().equals(libro.getTitulo())
                    && l.getAutor().equals(libro.getAutor())
                    && l.getAnyo() == libro.getAnyo()
            ) {
                return l;
            }
        }
        return null;
    }
}
