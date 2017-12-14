package com.lighthouse.shell.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Impacto entity.
 */
public class ImpactoDTO implements Serializable {

    private Long id;

    private String categoria;

    private Integer valor;

    private String descripcion;

    private Long tablaImpactoId;

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

    public Long getTablaImpactoId() {
        return tablaImpactoId;
    }

    public void setTablaImpactoId(Long tablaImpactoId) {
        this.tablaImpactoId = tablaImpactoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ImpactoDTO impactoDTO = (ImpactoDTO) o;
        if(impactoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), impactoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ImpactoDTO{" +
            "id=" + getId() +
            ", categoria='" + getCategoria() + "'" +
            ", valor='" + getValor() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            "}";
    }
}
