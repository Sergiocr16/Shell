package com.lighthouse.shell.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A TablaImpacto.
 */
@Entity
@Table(name = "tabla_impacto")
public class TablaImpacto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @OneToMany(mappedBy = "tablaImpacto")
    @JsonIgnore
    private Set<Impacto> impactos = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public TablaImpacto nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Set<Impacto> getImpactos() {
        return impactos;
    }

    public TablaImpacto impactos(Set<Impacto> impactos) {
        this.impactos = impactos;
        return this;
    }

    public TablaImpacto addImpacto(Impacto impacto) {
        this.impactos.add(impacto);
        impacto.setTablaImpacto(this);
        return this;
    }

    public TablaImpacto removeImpacto(Impacto impacto) {
        this.impactos.remove(impacto);
        impacto.setTablaImpacto(null);
        return this;
    }

    public void setImpactos(Set<Impacto> impactos) {
        this.impactos = impactos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TablaImpacto tablaImpacto = (TablaImpacto) o;
        if (tablaImpacto.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tablaImpacto.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TablaImpacto{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            "}";
    }
}
