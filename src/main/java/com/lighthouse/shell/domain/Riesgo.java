package com.lighthouse.shell.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Riesgo.
 */
@Entity
@Table(name = "riesgo")
public class Riesgo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "probabilidad")
    private Integer probabilidad;

    @Column(name = "impacto")
    private String impacto;

    @ManyToOne
    private Proyecto proyecto;

    @OneToOne
    @JoinColumn(unique = true)
    private Medida medida;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public Riesgo description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getProbabilidad() {
        return probabilidad;
    }

    public Riesgo probabilidad(Integer probabilidad) {
        this.probabilidad = probabilidad;
        return this;
    }

    public void setProbabilidad(Integer probabilidad) {
        this.probabilidad = probabilidad;
    }

    public String getImpacto() {
        return impacto;
    }

    public Riesgo impacto(String impacto) {
        this.impacto = impacto;
        return this;
    }

    public void setImpacto(String impacto) {
        this.impacto = impacto;
    }

    public Proyecto getProyecto() {
        return proyecto;
    }

    public Riesgo proyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
        return this;
    }

    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }

    public Medida getMedida() {
        return medida;
    }

    public Riesgo medida(Medida medida) {
        this.medida = medida;
        return this;
    }

    public void setMedida(Medida medida) {
        this.medida = medida;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Riesgo riesgo = (Riesgo) o;
        if (riesgo.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), riesgo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Riesgo{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", probabilidad='" + getProbabilidad() + "'" +
            ", impacto='" + getImpacto() + "'" +
            "}";
    }
}
