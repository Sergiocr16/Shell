package com.lighthouse.shell.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A RangoRiesgo.
 */
@Entity
@Table(name = "rango_riesgo")
public class RangoRiesgo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "color")
    private String color;

    @Column(name = "probabilidad")
    private Integer probabilidad;

    @Column(name = "impacto")
    private Integer impacto;

    @Column(name = "impacto_description")
    private String impactoDescription;

    @Column(name = "probabilidad_description")
    private String probabilidadDescription;

    @ManyToOne
    private Proyecto proyecto;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public RangoRiesgo nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getColor() {
        return color;
    }

    public RangoRiesgo color(String color) {
        this.color = color;
        return this;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getProbabilidad() {
        return probabilidad;
    }

    public RangoRiesgo probabilidad(Integer probabilidad) {
        this.probabilidad = probabilidad;
        return this;
    }

    public void setProbabilidad(Integer probabilidad) {
        this.probabilidad = probabilidad;
    }

    public Integer getImpacto() {
        return impacto;
    }

    public RangoRiesgo impacto(Integer impacto) {
        this.impacto = impacto;
        return this;
    }

    public void setImpacto(Integer impacto) {
        this.impacto = impacto;
    }

    public String getImpactoDescription() {
        return impactoDescription;
    }

    public RangoRiesgo impactoDescription(String impactoDescription) {
        this.impactoDescription = impactoDescription;
        return this;
    }

    public void setImpactoDescription(String impactoDescription) {
        this.impactoDescription = impactoDescription;
    }

    public String getProbabilidadDescription() {
        return probabilidadDescription;
    }

    public RangoRiesgo probabilidadDescription(String probabilidadDescription) {
        this.probabilidadDescription = probabilidadDescription;
        return this;
    }

    public void setProbabilidadDescription(String probabilidadDescription) {
        this.probabilidadDescription = probabilidadDescription;
    }

    public Proyecto getProyecto() {
        return proyecto;
    }

    public RangoRiesgo proyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
        return this;
    }

    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RangoRiesgo rangoRiesgo = (RangoRiesgo) o;
        if (rangoRiesgo.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), rangoRiesgo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RangoRiesgo{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", color='" + getColor() + "'" +
            ", probabilidad='" + getProbabilidad() + "'" +
            ", impacto='" + getImpacto() + "'" +
            ", impactoDescription='" + getImpactoDescription() + "'" +
            ", probabilidadDescription='" + getProbabilidadDescription() + "'" +
            "}";
    }
}
