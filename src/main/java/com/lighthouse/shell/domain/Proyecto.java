package com.lighthouse.shell.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Proyecto.
 */
@Entity
@Table(name = "proyecto")
public class Proyecto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "tabla_impacto_id")
    private Integer tablaImpactoId;

    @Column(name = "tabla_probabilidad_id")
    private Integer tablaProbabilidadId;

    @OneToMany(mappedBy = "proyecto")
    @JsonIgnore
    private Set<Lanzamiento> lanzamientos = new HashSet<>();

    @OneToMany(mappedBy = "proyecto")
    @JsonIgnore
    private Set<Riesgo> riesgos = new HashSet<>();

    @OneToMany(mappedBy = "proyecto")
    @JsonIgnore
    private Set<RangoRiesgo> rangoRiesgos = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public Proyecto nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getTablaImpactoId() {
        return tablaImpactoId;
    }

    public Proyecto tablaImpactoId(Integer tablaImpactoId) {
        this.tablaImpactoId = tablaImpactoId;
        return this;
    }

    public void setTablaImpactoId(Integer tablaImpactoId) {
        this.tablaImpactoId = tablaImpactoId;
    }

    public Integer getTablaProbabilidadId() {
        return tablaProbabilidadId;
    }

    public Proyecto tablaProbabilidadId(Integer tablaProbabilidadId) {
        this.tablaProbabilidadId = tablaProbabilidadId;
        return this;
    }

    public void setTablaProbabilidadId(Integer tablaProbabilidadId) {
        this.tablaProbabilidadId = tablaProbabilidadId;
    }

    public Set<Lanzamiento> getLanzamientos() {
        return lanzamientos;
    }

    public Proyecto lanzamientos(Set<Lanzamiento> lanzamientos) {
        this.lanzamientos = lanzamientos;
        return this;
    }

    public Proyecto addLanzamiento(Lanzamiento lanzamiento) {
        this.lanzamientos.add(lanzamiento);
        lanzamiento.setProyecto(this);
        return this;
    }

    public Proyecto removeLanzamiento(Lanzamiento lanzamiento) {
        this.lanzamientos.remove(lanzamiento);
        lanzamiento.setProyecto(null);
        return this;
    }

    public void setLanzamientos(Set<Lanzamiento> lanzamientos) {
        this.lanzamientos = lanzamientos;
    }

    public Set<Riesgo> getRiesgos() {
        return riesgos;
    }

    public Proyecto riesgos(Set<Riesgo> riesgos) {
        this.riesgos = riesgos;
        return this;
    }

    public Proyecto addRiesgo(Riesgo riesgo) {
        this.riesgos.add(riesgo);
        riesgo.setProyecto(this);
        return this;
    }

    public Proyecto removeRiesgo(Riesgo riesgo) {
        this.riesgos.remove(riesgo);
        riesgo.setProyecto(null);
        return this;
    }

    public void setRiesgos(Set<Riesgo> riesgos) {
        this.riesgos = riesgos;
    }

    public Set<RangoRiesgo> getRangoRiesgos() {
        return rangoRiesgos;
    }

    public Proyecto rangoRiesgos(Set<RangoRiesgo> rangoRiesgos) {
        this.rangoRiesgos = rangoRiesgos;
        return this;
    }

    public Proyecto addRangoRiesgo(RangoRiesgo rangoRiesgo) {
        this.rangoRiesgos.add(rangoRiesgo);
        rangoRiesgo.setProyecto(this);
        return this;
    }

    public Proyecto removeRangoRiesgo(RangoRiesgo rangoRiesgo) {
        this.rangoRiesgos.remove(rangoRiesgo);
        rangoRiesgo.setProyecto(null);
        return this;
    }

    public void setRangoRiesgos(Set<RangoRiesgo> rangoRiesgos) {
        this.rangoRiesgos = rangoRiesgos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Proyecto proyecto = (Proyecto) o;
        if (proyecto.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), proyecto.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Proyecto{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", tablaImpactoId='" + getTablaImpactoId() + "'" +
            ", tablaProbabilidadId='" + getTablaProbabilidadId() + "'" +
            "}";
    }
}
