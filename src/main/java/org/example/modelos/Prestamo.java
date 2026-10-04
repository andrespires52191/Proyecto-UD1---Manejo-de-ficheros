package org.example.modelos;

public class Prestamo {
    int prestamoId = -1;
    int usuarioId;
    int libroId;
    String fechaIni;
    String fechaFin;

    public Prestamo(int usuarioId, int libroId, String fechaIni, String fechaFin) {
        this.usuarioId = usuarioId;
        this.libroId = libroId;
        this.fechaIni = fechaIni;
        this.fechaFin = fechaFin;
    }

    public int getPrestamoId() {
        return prestamoId;
    }

    public void setPrestamoId(int prestamoId) {
        this.prestamoId = prestamoId;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public int getLibroId() {
        return libroId;
    }

    public void setLibroId(int libroId) {
        this.libroId = libroId;
    }

    public String getFechaIni() {
        return fechaIni;
    }

    public void setFechaIni(String fechaIni) {
        this.fechaIni = fechaIni;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }
}
