package model;

import java.util.List;

public class CarroUsuario {

    private String marca;

    private String modelo;

    private Double capacidadeBateria;

    private List<TipoConector> tipoConector;

    private TipoCarga tipoCarga;


    public CarroUsuario(String marca, String modelo, Double capacidadeBateria, List<TipoConector> tipoConector, TipoCarga tipoCarga) {
        this.marca = marca;
        this.modelo = modelo;
        this.capacidadeBateria = capacidadeBateria;
        this.tipoConector = tipoConector;
        this.tipoCarga = tipoCarga;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public Double getCapacidadeBateria() {
        return capacidadeBateria;
    }

    public void setCapacidadeBateria(Double capacidadeBateria) {
        this.capacidadeBateria = capacidadeBateria;
    }

    public List<TipoConector> getTipoConector() {
        return tipoConector;
    }

    public void setTipoConector(List<TipoConector> tipoConector) {
        this.tipoConector = tipoConector;
    }

    public TipoCarga getTipoCarga() {
        return tipoCarga;
    }

    public void setTipoCarga(TipoCarga tipoCarga) {
        this.tipoCarga = tipoCarga;
    }

    @Override
    public String toString() {
        return "carroUsuario{" +
                "marca='" + marca + '\'' +
                ", modelo='" + modelo + '\'' +
                ", capacidadeBateria=" + capacidadeBateria +
                ", tipoConector=" + tipoConector +
                ", tipoCarga=" + tipoCarga +
                '}';
    }
}
