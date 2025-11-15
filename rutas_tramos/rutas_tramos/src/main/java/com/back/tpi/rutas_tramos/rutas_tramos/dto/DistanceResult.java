package com.back.tpi.rutas_tramos.rutas_tramos.dto;

public class DistanceResult {

    private String origen;
    private String destino;

    private String origenNombre;
    private String destinoNombre;

    private int distanciaKm;
    private String duracionTexto;
    private int duracionMinutos;

    public DistanceResult() {}

    public DistanceResult(String origen, String destino, String origenNombre, String destinoNombre,
                          int distanciaKm, String duracionTexto, int duracionMinutos) {
        this.origen = origen;
        this.destino = destino;
        this.origenNombre = origenNombre;
        this.destinoNombre = destinoNombre;
        this.distanciaKm = distanciaKm;
        this.duracionTexto = duracionTexto;
        this.duracionMinutos = duracionMinutos;
    }

    public String getOrigen() { return origen; }
    public void setOrigen(String origen) { this.origen = origen; }

    public String getDestino() { return destino; }
    public void setDestino(String destino) { this.destino = destino; }

    public String getOrigenNombre() { return origenNombre; }
    public void setOrigenNombre(String origenNombre) { this.origenNombre = origenNombre; }

    public String getDestinoNombre() { return destinoNombre; }
    public void setDestinoNombre(String destinoNombre) { this.destinoNombre = destinoNombre; }

    public int getDistanciaKm() { return distanciaKm; }
    public void setDistanciaKm(int distanciaKm) { this.distanciaKm = distanciaKm; }

    public String getDuracionTexto() { return duracionTexto; }
    public void setDuracionTexto(String duracionTexto) { this.duracionTexto = duracionTexto; }

    public int getDuracionMinutos() { return duracionMinutos; }
    public void setDuracionMinutos(int duracionMinutos) { this.duracionMinutos = duracionMinutos; }
}
