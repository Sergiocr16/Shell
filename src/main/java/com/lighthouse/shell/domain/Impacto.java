package com.lighthouse.shell.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Impacto.
 */
@Entity
@Table(name = "impacto")
public class Impacto implements Serializable {

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

    @ManyToOne
    private TablaImpacto tablaImpacto;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategoria() {
        return categoria;
    }

    public Impacto categoria(String categoria) {
        this.categoria = categoria;
        return this;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Integer getValor() {
        return valor;
    }

    public Impacto valor(Integer valor) {
        this.valor = valor;
        return this;
    }

    public void setValor(Integer valor) {
        this.valor = valor;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Impacto descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public TablaImpacto getTablaImpacto() {
        return tablaImpacto;
    }

    public Impacto tablaImpacto(TablaImpacto tablaImpacto) {
        this.tablaImpacto = tablaImpacto;
        return this;
    }

    public void setTablaImpacto(TablaImpacto tablaImpacto) {
        this.tablaImpacto = tablaImpacto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Impacto impacto = (Impacto) o;
        if (impacto.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), impacto.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Impacto{" +
            "id=" + getId() +
            ", categoria='" + getCategoria() + "'" +
            ", valor='" + getValor() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            "}";
    }
}
