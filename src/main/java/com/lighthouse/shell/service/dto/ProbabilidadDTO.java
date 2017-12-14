package com.lighthouse.shell.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Probabilidad entity.
 */
public class ProbabilidadDTO implements Serializable {

    private Long id;

    private String categoria;

    private Integer valor;

    private String descripcion;

    private Double rangoMayor;

    private Double rangoMenor;

    private Long tablaProbabilidadId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Integer getValor() {
        return valor;
    }

    public void setValor(Integer valor) {
        this.valor = valor;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getRangoMayor() {
        return rangoMayor;
    }

    public void setRangoMayor(Double rangoMayor) {
        this.rangoMayor = rangoMayor;
    }

    public Double getRangoMenor() {
        return rangoMenor;
    }

    public void setRangoMenor(Double rangoMenor) {
        this.rangoMenor = rangoMenor;
    }

    public Long getTablaProbabilidadId() {
        return tablaProbabilidadId;
    }

    public void setTablaProbabilidadId(Long tablaProbabilidadId) {
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

        ProbabilidadDTO probabilidadDTO = (ProbabilidadDTO) o;
        if(probabilidadDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), probabilidadDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProbabilidadDTO{" +
            "id=" + getId() +
            ", categoria='" + getCategoria() + "'" +
            ", valor='" + getValor() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", rangoMayor='" + getRangoMayor() + "'" +
            ", rangoMenor='" + getRangoMenor() + "'" +
            "}";
    }
}
