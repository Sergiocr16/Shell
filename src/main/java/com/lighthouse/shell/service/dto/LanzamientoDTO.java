package com.lighthouse.shell.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Lanzamiento entity.
 */
public class LanzamientoDTO implements Serializable {

    private Long id;

    private String numero;

    private Long proyectoId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
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

        LanzamientoDTO lanzamientoDTO = (LanzamientoDTO) o;
        if(lanzamientoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), lanzamientoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LanzamientoDTO{" +
            "id=" + getId() +
            ", numero='" + getNumero() + "'" +
            "}";
    }
}
