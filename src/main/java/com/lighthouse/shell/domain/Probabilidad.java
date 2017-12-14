package com.lighthouse.shell.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Probabilidad.
 */
@Entity
@Table(name = "probabilidad")
public class Probabilidad implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "categoria")
    private String categoria;

    @Column(name = "valor")
    private Integer valor;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "rango_mayor")
    private Double rangoMayor;

    @Column(name = "rango_menor")
    private Double rangoMenor;

    @ManyToOne
    private TablaProbabilidad tablaProbabilidad;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategoria() {
        return categoria;
    }

    public Probabilidad categoria(String categoria) {
        this.categoria = categoria;
        return this;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Integer getValor() {
        return valor;
    }

    public Probabilidad valor(Integer valor) {
        this.valor = valor;
        return this;
    }

    public void setValor(Integer valor) {
        this.valor = valor;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Probabilidad descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getRangoMayor() {
        return rangoMayor;
    }

    public Probabilidad rangoMayor(Double rangoMayor) {
        this.rangoMayor = rangoMayor;
        return this;
    }

    public void setRangoMayor(Double rangoMayor) {
        this.rangoMayor = rangoMayor;
    }

    public Double getRangoMenor() {
        return rangoMenor;
    }

    public Probabilidad rangoMenor(Double rangoMenor) {
        this.rangoMenor = rangoMenor;
        return this;
    }

    public void setRangoMenor(Double rangoMenor) {
        this.rangoMenor = rangoMenor;
    }

    public TablaProbabilidad getTablaProbabilidad() {
        return tablaProbabilidad;
    }

    public Probabilidad tablaProbabilidad(TablaProbabilidad tablaProbabilidad) {
        this.tablaProbabilidad = tablaProbabilidad;
        return this;
    }

    public void setTablaProbabilidad(TablaProbabilidad tablaProbabilidad) {
        this.tablaProbabilidad = tablaProbabilidad;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Probabilidad probabilidad = (Probabilidad) o;
        if (probabilidad.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), probabilidad.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Probabilidad{" +
            "id=" + getId() +
            ", categoria='" + getCategoria() + "'" +
            ", valor='" + getValor() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", rangoMayor='" + getRangoMayor() + "'" +
            ", rangoMenor='" + getRangoMenor() + "'" +
            "}";
    }
}
