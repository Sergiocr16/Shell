package com.lighthouse.shell.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A TablaProbabilidad.
 */
@Entity
@Table(name = "tabla_probabilidad")
public class TablaProbabilidad implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @OneToMany(mappedBy = "tablaProbabilidad")
    @JsonIgnore
    private Set<Probabilidad> impactos = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public TablaProbabilidad nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Set<Probabilidad> getImpactos() {
        return impactos;
    }

    public TablaProbabilidad impactos(Set<Probabilidad> probabilidads) {
        this.impactos = probabilidads;
        return this;
    }

    public TablaProbabilidad addImpacto(Probabilidad probabilidad) {
        this.impactos.add(probabilidad);
        probabilidad.setTablaProbabilidad(this);
        return this;
    }

    public TablaProbabilidad removeImpacto(Probabilidad probabilidad) {
        this.impactos.remove(probabilidad);
        probabilidad.setTablaProbabilidad(null);
        return this;
    }

    public void setImpactos(Set<Probabilidad> probabilidads) {
        this.impactos = probabilidads;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TablaProbabilidad tablaProbabilidad = (TablaProbabilidad) o;
        if (tablaProbabilidad.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tablaProbabilidad.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TablaProbabilidad{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            "}";
    }
}
