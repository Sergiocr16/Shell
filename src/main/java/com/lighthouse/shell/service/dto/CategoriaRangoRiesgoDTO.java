package com.lighthouse.shell.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the CategoriaRangoRiesgo entity.
 */
public class CategoriaRangoRiesgoDTO implements Serializable {

    private Long id;

    private String nombre;

    private String color;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CategoriaRangoRiesgoDTO categoriaRangoRiesgoDTO = (CategoriaRangoRiesgoDTO) o;
        if(categoriaRangoRiesgoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), categoriaRangoRiesgoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CategoriaRangoRiesgoDTO{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", color='" + getColor() + "'" +
            "}";
    }
}
