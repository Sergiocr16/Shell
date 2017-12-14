package com.lighthouse.shell.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Proyecto entity.
 */
public class ProyectoDTO implements Serializable {

    private Long id;

    private String nombre;

    private Integer tablaImpactoId;

    private Integer tablaProbabilidadId;

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

    public Integer getTablaImpactoId() {
        return tablaImpactoId;
    }

    public void setTablaImpactoId(Integer tablaImpactoId) {
        this.tablaImpactoId = tablaImpactoId;
    }

    public Integer getTablaProbabilidadId() {
        return tablaProbabilidadId;
    }

    public void setTablaProbabilidadId(Integer tablaProbabilidadId) {
        this.tablaProbabilidadId = tablaProbabilidadId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProyectoDTO proyectoDTO = (ProyectoDTO) o;
        if(proyectoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), proyectoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProyectoDTO{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", tablaImpactoId='" + getTablaImpactoId() + "'" +
            ", tablaProbabilidadId='" + getTablaProbabilidadId() + "'" +
            "}";
    }
}
