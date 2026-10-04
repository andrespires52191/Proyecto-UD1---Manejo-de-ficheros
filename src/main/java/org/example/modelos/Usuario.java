package org.example.modelos;

import java.io.Serializable;

public class Usuario implements Serializable {
    int usuarioId;
    String nombre;
    String dni;
    int edad;

    public Usuario(int edad, String dni, String nombre) {
        this.edad = edad;
        this.dni = dni;
        this.nombre = nombre;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    @Override
    public String toString() {
        return "#" + usuarioId + ": " + nombre + " (" + edad + " años, DNI " + dni + ")";
    }
}
