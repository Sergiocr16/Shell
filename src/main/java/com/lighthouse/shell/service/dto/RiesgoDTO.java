package com.lighthouse.shell.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Riesgo entity.
 */
public class RiesgoDTO implements Serializable {

    private Long id;

    private String description;

    private Integer probabilidad;

    private String impacto;

    private Long proyectoId;

    private Long medidaId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getProbabilidad() {
        return probabilidad;
    }

    public void setProbabilidad(Integer probabilidad) {
        this.probabilidad = probabilidad;
    }

    public String getImpacto() {
        return impacto;
    }

    public void setImpacto(String impacto) {
        this.impacto = impacto;
    }

    public Long getProyectoId() {
        return proyectoId;
    }

    public void setProyectoId(Long proyectoId) {
        this.proyectoId = proyectoId;
    }

    public Long getMedidaId() {
        return medidaId;
    }

    public void setMedidaId(Long medidaId) {
        this.medidaId = medidaId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RiesgoDTO riesgoDTO = (RiesgoDTO) o;
        if(riesgoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), riesgoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RiesgoDTO{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", probabilidad='" + getProbabilidad() + "'" +
            ", impacto='" + getImpacto() + "'" +
            "}";
    }
}
