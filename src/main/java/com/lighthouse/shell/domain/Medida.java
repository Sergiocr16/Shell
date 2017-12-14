package com.lighthouse.shell.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Medida.
 */
@Entity
@Table(name = "medida")
public class Medida implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "contencion")
    private String contencion;

    @Column(name = "mitigacion")
    private String mitigacion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContencion() {
        return contencion;
    }

    public Medida contencion(String contencion) {
        this.contencion = contencion;
        return this;
    }

    public void setContencion(String contencion) {
        this.contencion = contencion;
    }

    public String getMitigacion() {
        return mitigacion;
    }

    public Medida mitigacion(String mitigacion) {
        this.mitigacion = mitigacion;
        return this;
    }

    public void setMitigacion(String mitigacion) {
        this.mitigacion = mitigacion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Medida medida = (Medida) o;
        if (medida.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), medida.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Medida{" +
            "id=" + getId() +
            ", contencion='" + getContencion() + "'" +
            ", mitigacion='" + getMitigacion() + "'" +
            "}";
    }
}
