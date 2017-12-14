package com.lighthouse.shell.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the RangoRiesgo entity.
 */
public class RangoRiesgoDTO implements Serializable {

    private Long id;

    private String nombre;

    private String color;

    private Integer probabilidad;

    private Integer impacto;

    private Long proyectoId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getProbabilidad() {
        return probabilidad;
    }

    public void setProbabilidad(Integer probabilidad) {
        this.probabilidad = probabilidad;
    }

    public Integer getImpacto() {
        return impacto;
    }

    public void setImpacto(Integer impacto) {
        this.impacto = impacto;
    }

    public Long getProyectoId() {
        return proyectoId;
    }

    public void setProyectoId(Long proyectoId) {
        this.proyectoId = proyectoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RangoRiesgoDTO rangoRiesgoDTO = (RangoRiesgoDTO) o;
        if(rangoRiesgoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), rangoRiesgoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RangoRiesgoDTO{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", color='" + getColor() + "'" +
            ", probabilidad='" + getProbabilidad() + "'" +
            ", impacto='" + getImpacto() + "'" +
            "}";
    }
}
